package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shizhefei.view.hvscrollview.HVScrollView;

public class TestActivity extends AppCompatActivity {
    private HVScrollView scrollview;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        scrollview = (HVScrollView) findViewById(R.id.test_hvscrollview);
        linearLayout = (LinearLayout) findViewById(R.id.test_linearlayout);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                TextView tv = new TextView(this);
                tv.setPadding(20, 20, 20, 20);
                tv.setText("文字添加下标dwjaoejwkaopejkowajeopwjapoejapwojeopwajepowajepowajeopwjapoejpoawejop：" + i);
                linearLayout.addView(tv);
            }

        }
    }
}
