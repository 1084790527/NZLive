package com.example.nzlive.fragment.homePage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.nzlive.R;
import com.example.nzlive.util.ImageUtil;
import com.example.nzlive.util.NetTimeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class KnowingActivity extends Activity {

    private View ll_return,rl_addPhotograph;
    private ImageView iv_pictur;
    private Uri imageUri;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowing);
        init();
        returnOnClick();
        addPhotographOnClick();
    }

    private void addPhotographOnClick() {
        rl_addPhotograph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

            }
        });
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

    private void returnOnClick() {
        ll_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        ll_return=findViewById(R.id.ll_return);
        rl_addPhotograph=findViewById(R.id.rl_addPhotograph);
        iv_pictur=findViewById(R.id.iv_pictur);
    }
}
