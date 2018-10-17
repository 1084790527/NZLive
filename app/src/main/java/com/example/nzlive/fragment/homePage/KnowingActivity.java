package com.example.nzlive.fragment.homePage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
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
import com.example.nzlive.websocket.SocketConnet;

import org.json.JSONArray;
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
    private View ll_return, rl_addPhotograph;
    private ImageView iv_pictur;
    private Uri imageUri;
    private Bitmap bitmap;
    private LinearLayout ll_upload_image, ll_recording;
    private Handler handler;
    private JSONObject positioning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowing);
        init();
        positioning();
    }

    private void positioning() {
        LogUtil.Logd(getApplicationContext(), "定位");

        String serviceString = getApplicationContext().LOCATION_SERVICE;// 获取的是位置服务
        LocationManager locationManager = (LocationManager) getSystemService(serviceString);// 调用getSystemService()方法来获取LocationManager对象

        //使用gps定位
        String provider = LocationManager.GPS_PROVIDER;// 指定LocationManager的定位方法
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);// 调用getLastKnownLocation()方法获取当前的位置信息

        double lat = location.getLatitude();//获取纬度
        double lng = location.getLongitude();//获取经度

        LogUtil.Logd(getApplicationContext(),lat+","+lng);

        try {
            Log.i("wxy","main thread id is "+Thread.currentThread().getId());
            String url = "http://gc.ditu.aliyun.com/regeocoding?l="+lat+","+lng+"&type=010";
            OkHttpClient client = new OkHttpClient();
            Request requests = new Request.Builder().url(url).build();
            client.newCall(requests).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {

                }
                @Override
                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                    // 注：该回调是子线程，非主线程
//                            Log.i(TAG,"callback thread id is "+Thread.currentThread().getId());
                    String string=response.body().string();
                    Log.i(TAG,string);
                    try {
                        positioning=new JSONObject(string);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
                        Date date = new Date(System.currentTimeMillis());
                        String time = simpleDateFormat.format(date);
                        bitmap = ImageUtil.drawTextToLeftTop(getApplicationContext(), bitmap, time, 100, Color.RED, 0, 0);

                        String s = SharePreUtil.getData(getApplicationContext(), "user", "data", "");
                        JSONObject object = new JSONObject(s);
                        String userid = object.getString("userid");
                        bitmap = ImageUtil.drawTextToLeftTop(getApplicationContext(), bitmap, "学号：" + userid, 100, Color.RED, 0, 120);

                        String username = object.getString("username");
                        bitmap = ImageUtil.drawTextToLeftTop(getApplicationContext(), bitmap, "姓名：" + username, 100, Color.RED, 0, 240);

                        String dormroom = object.getString("dormroom");
                        LogUtil.Logd(getApplicationContext(), dormroom);
                        bitmap = ImageUtil.drawTextToLeftTop(getApplicationContext(), bitmap, "宿舍：" + dormroom, 100, Color.RED, 0, 360);

                        if (positioning!=null){
                            JSONArray jsonArray=positioning.getJSONArray("addrList");
                            JSONObject jsonObject=jsonArray.getJSONObject(0);
                            String admName=jsonObject.getString("admName");
                            bitmap = ImageUtil.drawTextToLeftTop(getApplicationContext(), bitmap, admName, 100, Color.RED, 0, 480);
                            String addr=jsonObject.getString("addr");
                            bitmap = ImageUtil.drawTextToLeftTop(getApplicationContext(), bitmap, addr, 100, Color.RED, 0, 600);
                            String nearestPoint=jsonObject.getString("nearestPoint");
                            bitmap = ImageUtil.drawTextToLeftTop(getApplicationContext(), bitmap, nearestPoint, 100, Color.RED, 0, 720);
                        }else {
                            bitmap = ImageUtil.drawTextToLeftTop(getApplicationContext(), bitmap, "定位获取失败！", 100, Color.RED, 0, 480);
                        }

//                        bitmap= ImageUtil.drawTextToLeftTop(getApplicationContext(),bitmap, new NetTimeUtil().getNetTime(getApplicationContext())+"sj,rq,xm,xh,bj", 100, Color.RED,0,400);
                        iv_pictur.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
    private void init() {
        ll_return = findViewById(R.id.ll_return);
        ll_return.setOnClickListener(this);
        rl_addPhotograph = findViewById(R.id.rl_addPhotograph);
        rl_addPhotograph.setOnClickListener(this);
        iv_pictur = findViewById(R.id.iv_pictur);
        ll_upload_image = findViewById(R.id.ll_upload_image);
        ll_upload_image.setOnClickListener(this);
        handler = new Handler(Looper.getMainLooper());
        ll_recording = findViewById(R.id.ll_recording);
        ll_recording.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_return:
                finish();
                break;
            case R.id.rl_addPhotograph:

                addPhotoGraph();

                break;
            case R.id.ll_upload_image:

                photoUpload();


                break;
            case R.id.ll_recording:
                Intent intent=new Intent(getApplicationContext(),KnowingRecordingActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void addPhotoGraph() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new NetTimeUtil().setNetTime(getApplicationContext());
            }
        }).start();
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(getApplicationContext(), "com.example.nzlive.fragment.homePage.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 1);
    }

    private void photoUpload() {
        //拍照
        try {
            Drawable.ConstantState pictur = iv_pictur.getDrawable().getCurrent().getConstantState();
            if (pictur == null) {
                LogUtil.Logd(getApplicationContext(), "请先拍照");
                return;
            }
        } catch (Exception e) {
            LogUtil.Logd(getApplicationContext(), "请先拍照");
            return;
        }

        LogUtil.Logd(getApplicationContext(), "上传图片");

        iv_pictur.setDrawingCacheEnabled(true);
        final Bitmap bitmap = Bitmap.createBitmap(iv_pictur.getDrawingCache());
        iv_pictur.setDrawingCacheEnabled(false);

        //保存到本地
        String userid = SharePreUtil.getData(getApplicationContext(), "user", "data", "");
        try {
            JSONObject object = new JSONObject(userid);
            userid = object.getString("userid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (userid.length() > 10) {
            LogUtil.Logd(getApplicationContext(), "上传失败请重新登入后，尝试！");
            return;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        LogUtil.Logd(getApplicationContext(), simpleDateFormat.format(date) + "");
        //时间  +  学号
        String fileName = simpleDateFormat.format(date) + userid + ".jpg";
        String sdcard = Environment.getExternalStorageDirectory().toString() + "/nzlive/";
        LogUtil.Logd(getApplicationContext(), sdcard + "");
        File file = new File(sdcard);
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
            LogUtil.Logd(getApplicationContext(), "保存完成");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.Logd(getApplicationContext(), "保存失败,请重新登入后，尝试！");
            return;
        }


        //上传图片
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//                File f=new File(sdcard+fileName);
//                File f=imageFile;
        if (imageFile != null) {
            RequestBody body = RequestBody.create(MediaType.parse("image/*"), imageFile);
            builder.addFormDataPart("imagebyte", imageFile.getName(), body);
        }
        builder.addFormDataPart("userid", userid);
        Request request = new Request.Builder().url(Variable.ServiceIP + "setNameRecord").post(builder.build()).tag(getApplication()).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                        Log.d(TAG, "onFailure: "+"获取失败"+e);
                e.printStackTrace();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        LogUtil.Logd(getApplicationContext(), "上传失败，请检查网络是否连接！");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                Log.d(TAG, "onResponse: " + s);
                String status = "";
                try {
                    JSONObject object = new JSONObject(s);
                    status = object.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                switch (status) {
                    case "":
                        break;
                    case "0":
                        JSONObject object = new JSONObject();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                        Date date = new Date(System.currentTimeMillis());
                        try {
                            object.put("type", "returnCheckTheBed");
                            object.put("data", simpleDateFormat.format(date) + "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        SocketConnet.sendTextMessage(object.toString());
                        finish();
                        break;
                    case "1":
                    case "2":
                    case "3":
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                LogUtil.Logd(getApplicationContext(), "上传失败，请重新上传！");
                            }
                        });
                        break;

                }
            }
        });
    }

}
