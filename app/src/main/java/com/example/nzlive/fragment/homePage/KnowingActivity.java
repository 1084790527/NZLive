package com.example.nzlive.fragment.homePage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.nzlive.R;
import com.example.nzlive.util.ImageUtil;
import com.example.nzlive.util.LogUtil;
import com.example.nzlive.util.NetTimeUtil;
import com.example.nzlive.util.SharePreUtil;
import com.example.nzlive.util.Util;
import com.example.nzlive.util.Variable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class KnowingActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "AAA";
    private View ll_return,rl_addPhotograph;
    private ImageView iv_pictur;
    private Uri imageUri;
    private Bitmap bitmap;
    private LinearLayout ll_upload_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowing);
        init();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if (resultCode==RESULT_OK){
                    try {
                        bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        bitmap= ImageUtil.drawTextToLeftTop(getApplicationContext(),bitmap,
                                new NetTimeUtil().getNetTime(getApplicationContext())+"sj,rq,xm,xh,bj",
                                100, Color.RED,0,0);
                        iv_pictur.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }


    private void init() {
        ll_return=findViewById(R.id.ll_return);
        ll_return.setOnClickListener(this);
        rl_addPhotograph=findViewById(R.id.rl_addPhotograph);
        rl_addPhotograph.setOnClickListener(this);
        iv_pictur=findViewById(R.id.iv_pictur);
        ll_upload_image=findViewById(R.id.ll_upload_image);
        ll_upload_image.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_return:
                finish();
                break;
            case R.id.rl_addPhotograph:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new NetTimeUtil().setNetTime(getApplicationContext());
                    }
                }).start();
                File outputImage=new File(getExternalCacheDir(),"output_image.jpg");
                try {
                    if (outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT>=24){
                    imageUri= FileProvider.getUriForFile(getApplicationContext(),"com.example.nzlive.fragment.homePage.fileprovider",outputImage);
                }else {
                    imageUri=Uri.fromFile(outputImage);
                }
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,1);
                break;
            case R.id.ll_upload_image:
                //拍照
                try {
                    Drawable.ConstantState pictur=iv_pictur.getDrawable().getCurrent().getConstantState();
                    if (pictur==null){
                        LogUtil.Logd(getApplicationContext(),"请先拍照");
                        return;
                    }
                }catch (Exception e){
                    LogUtil.Logd(getApplicationContext(),"请先拍照");
                    return;
                }

                LogUtil.Logd(getApplicationContext(),"上传图片");

                iv_pictur.setDrawingCacheEnabled(true);
                final Bitmap bitmap=Bitmap.createBitmap(iv_pictur.getDrawingCache());
                iv_pictur.setDrawingCacheEnabled(false);

                //保存到本地
                String userid=SharePreUtil.getData(getApplicationContext(),"user","data","");
                try {
                    JSONObject object=new JSONObject(userid);
                    userid=object.getString("userid");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (userid.length()>10){
                    LogUtil.Logd(getApplicationContext(),"上传失败请重新登入后，尝试！");
                    return;
                }

                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
                Date date=new Date(System.currentTimeMillis());
                LogUtil.Logd(getApplicationContext(),simpleDateFormat.format(date)+"");
                //时间  +  学号
                String fileName=simpleDateFormat.format(date)+userid+".jpg";
                String sdcard = Environment.getExternalStorageDirectory().toString()+"/nzlive/";
                LogUtil.Logd(getApplicationContext(),sdcard+"");
                File file=new File(sdcard);
                File imageFile;
                try {
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    imageFile = new File(file.getAbsolutePath(), fileName);
                    FileOutputStream outStream = null;
                    outStream = new FileOutputStream(imageFile);
                    Bitmap image = bitmap;
                    image.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                    LogUtil.Logd(getApplicationContext(),"保存完成");
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.Logd(getApplicationContext(),"保存失败");
                }


                //上传图片
                OkHttpClient okHttpClient  = new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10,TimeUnit.SECONDS)
                        .readTimeout(20, TimeUnit.SECONDS)
                        .build();
                MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
                File f=new File(sdcard+fileName);
                if (f!=null){
                    RequestBody body=RequestBody.create(MediaType.parse("image/*"),f);
                    builder.addFormDataPart("imagebyte",f.getName(),body);
                }
                builder.addFormDataPart("userid",userid);
                final Request request=new Request.Builder().url(Variable.ServiceIP+"setNameRecord").post(builder.build()).tag(getApplication()).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: "+"获取失败"+e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String s=response.body().string();
                        Log.d(TAG, "onResponse: "+s);
                    }
                });


                break;
        }
    }

}
