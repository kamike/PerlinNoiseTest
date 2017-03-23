package com.example.administrator.myapplication.noise;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.view.WaveDynamicDrawView;

import java.util.Random;

import static android.R.attr.x;

public class PerlinNoiseActivity extends Activity {
    private WaveDynamicDrawView waveView;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perlin_noise);
        waveView = (WaveDynamicDrawView) findViewById(R.id.perlin_view);
        btn = (Button) findViewById(R.id.perlin_btn);
        isRuning = false;
        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(PerlinNoiseActivity.this, "清空数据", Toast.LENGTH_SHORT).show();
                isRuning = false;
                waveView.resetView();
                return false;
            }
        });
    }

    private boolean isRuning = false;

    public void onclickStartPerlin(View view) {
        if (isRuning) {
            isRuning = false;
            handler.removeMessages(0);
            btn.setText("点击开始运行...");
        } else {
            isRuning = true;
            btn.setText("点击停止运行...");
            handler.sendEmptyMessageDelayed(0, 100);
        }


    }

    private Random random = new Random();
    public static final int maxLength = 1080;
    /**
     * 振幅
     */
    private int maxValue = 500;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!isRuning) {
                removeMessages(0);
                return;
            }
            handler.sendEmptyMessageDelayed(0, 80);
//            handler.sendEmptyMessage(0);
            waveView.updataValue((float) (PerlinNoise_1D(i) * maxValue));
            i += 20;


        }
    };
    private int i = 0;

    /**
     * 噪声函数
     *
     * @param n
     * @return
     */
    private static double integerNoise1D(int n) {
        n = (n >> 13) ^ n;
        int nn = (n * (n * n * 60493 + 19990303) + 1376312589) & 0x7fffffff;
        return 1.0 - ((double) nn / 1073741824.0);
    }

    private static double smoothNoise1D(int x) {
        return integerNoise1D(x) / 2 + integerNoise1D(x - 1) / 4 + integerNoise1D(x + 1) / 4;

    }

    /**
     * 插值函数
     *
     * @param x
     * @return
     */
    private static double interpolateNoise(double x) {
        int intX = (int) (Math.floor(x));

        double n0 = smoothNoise1D(intX);

        double n1 = smoothNoise1D(intX + 1);

        double weight = x - Math.floor(x);

        double noise = lerp(n0, n1, fade(weight));

        return noise;
    }

    private static double interpolateCubicNoise(double x) {
        int intX = (int) (Math.floor(x));

        double n0 = smoothNoise1D(intX - 1);

        double n1 = smoothNoise1D(intX);

        double n2 = smoothNoise1D(intX + 1);

        double n3 = smoothNoise1D(intX + 2);

        double weight = x - Math.floor(x);

        double noise = lerpCubic(n0, n1, n2, n3, fade(weight));

        return noise;
    }


    /**
     * ：s(x) = 6x5 - 15x4 + 10x3
     *
     * @param t
     * @return
     */
    private static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private static double lerp(double a, double b, double t) {
//        return a * (1 - t) + b * t;
        float ft = (float) (x * 3.1415927);
        float f = (float) ((1 - Math.cos(ft)) * 5);
        return a * (1 - f) + b * f;
    }

    private static double lerpCubic(double v0, double v1, double v2, double v3, double x) {
        double P = (v3 - v2) - (v0 - v1);
        double Q = (v0 - v1) - P;
        double R = v2 - v0;
        double S = v1;
        return P * x * x * x + Q * x * x + R * x + S;
    }


    public static float PerlinNoise_1D(float x) {

        float total = 0.0f;
        float amplitude = 1f;
        float p=1/2.f;
        for (int i = 0; i < 4; i++) {
            float frequency = (float) Math.pow(2, i);
            amplitude *=Math.pow(p, i);
            total += amplitude * interpolateCubicNoise(x * frequency);
        }
        return total;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRuning = true;
        handler.removeMessages(0);
    }
}
