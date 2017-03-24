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
import android.widget.RelativeLayout;

import com.example.administrator.myapplication.R;

import java.util.Random;

import static com.example.administrator.myapplication.R.id.main_iv;

public class PerlinNoise2DActivity extends AppCompatActivity {
    private ImageView iv;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perlin_noise2_d);
        iv = (ImageView) findViewById(main_iv);
        progress = (ProgressBar) findViewById(R.id.perlin_noise_2d_progress);
        initAllData();
    }

    private static final int size = 512;
    //    private Bitmap bmp;
    private int[] array;

    private void initAllData() {
        array = new int[size * size];
        progress.setMax(size);
        RelativeLayout.LayoutParams paremms = (RelativeLayout.LayoutParams) iv.getLayoutParams();
        paremms.height = 1080;
        paremms.width = 1080;
        iv.setLayoutParams(paremms);
    }

    public void onclickGenerateImg(View view) {
        iv.setImageResource(0);


        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                progress.setProgress(msg.what);
                Bitmap bmp = Bitmap.createBitmap(array, size, size, Bitmap.Config.RGB_565);
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
                    if (width % 2 == 0) {
                        handler.sendEmptyMessage(width);
                    }
                    for (int height = 0; height < size; height++) {
                        float noiseValue = 0;
                        for (int octive = 1; octive < 5; octive++) {
                            noiseValue += interpolatedNoise(width * 0.01f * octive, height * 0.1f / (2*octive));
                        }
                        float roundedValue = noiseValue / 4;
                        array[height + width * size] = Color.rgb((int) (250 * roundedValue), 0, 0);
//                        array[height + width * size] = Color.rgb((int) (127.5f*(noiseValue+1)),0,0);
//                        array[height + width * size] = (int) (Integer.MAX_VALUE * (noiseValue+1));

                    }
                }
            }
        }.start();
    }

    public float noise(int x, int y) {
        int n = x + y * 200;
        n = (n << 13) ^ n;
        float rand = 1 - ((n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824f;
        return rand;
    }

    public float interpolatedNoise(float x, float y) {
        int integerX = (int) x;
        float fractionalX = x - integerX;

        int integerY = (int) y;
        float fractionalY = y - integerY;

        float v1 = noise(integerX, integerY);
        float v2 = noise(integerX + 1, integerY);
        float v3 = noise(integerX, integerY + 1);
        float v4 = noise(integerX + 1, integerY + 1);

        float i1 = interpolateLinear(v1, v2, fractionalX);
        float i2 = interpolateLinear(v3, v4, fractionalX);

        return interpolateLinear(i1, i2, fractionalY);
    }

    public float interpolateLinear(float a, float b, float x) {
        return a * (1 - x) + b * x;
    }

    // x must be in the range [0,1]
    public float interpolateCosine(float a, float b, float x) {
        float ft = x * (float) Math.PI;
        float f = (1 - ((float) Math.cos(ft))) * 0.5f;
        return a * (1 - f) + b * f;
    }
}
