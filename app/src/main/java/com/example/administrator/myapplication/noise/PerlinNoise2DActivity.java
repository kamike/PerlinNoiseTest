package com.example.administrator.myapplication.noise;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.administrator.myapplication.R;

import java.util.Random;

public class PerlinNoise2DActivity extends AppCompatActivity {
    private ImageView iv;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perlin_noise2_d);
        iv = (ImageView) findViewById(R.id.main_iv);
        progress = (ProgressBar) findViewById(R.id.perlin_noise_2d_progress);
        initAllData();
    }

    private static final int size = 512;
    //    private Bitmap bmp;
    private int[] array;

    private void initAllData() {
        array = new int[size * size];
        progress.setMax(size);
    }

    public void onclickGenerateImg(View view) {
        iv.setImageResource(0);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                progress.setProgress(msg.what);

                Bitmap bmp = Bitmap.createBitmap(array, size, size, Bitmap.Config.ARGB_8888);
                iv.setImageBitmap(bmp);


            }
        };


        new Thread() {
            @Override
            public void run() {
                Random random = new Random();
                // Create a seeded noise generator
                PerlinNoise2D n = new PerlinNoise2D(random.nextInt());

                for (int width = 0; width < size; width++) {
                    handler.sendEmptyMessage(width);
                    for (int height = 0; height < size; height++) {
                        float noiseValue = 0;
                        noiseValue += n.interpolatedNoise(width * 0.01f, height * 0.01f);
                        noiseValue += n.interpolatedNoise(width * 0.02f, height * 0.02f);
                        noiseValue += n.interpolatedNoise(width * 0.04f, height * 0.04f);
                        noiseValue += n.interpolatedNoise(width * 0.08f, height * 0.08f);

                        float roundedValue=noiseValue/4;
                        array[height + width * size] = Color.rgb((int) (255*roundedValue),0,0);
                    }
                }
            }
        }.start();
    }
}
