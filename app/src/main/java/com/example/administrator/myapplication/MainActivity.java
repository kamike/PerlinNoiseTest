package com.example.administrator.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.myapplication.noise.PerlinNoise2DActivity;
import com.example.administrator.myapplication.noise.PerlinNoiseActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onclickMainSelect(null);

    }


    private String[] array = {"HVScrollview", "Perlin噪声算法1D", "Perlin噪声算法2D"};

    public void onclickMainSelect(View view) {
        new AlertDialog.Builder(this).setTitle("选择功能").setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, TestActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, PerlinNoiseActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, PerlinNoise2DActivity.class));
                        break;
                    case 3:
                        break;
                    default:

                }
            }
        }).setPositiveButton("cancle", null).show();
    }


}
