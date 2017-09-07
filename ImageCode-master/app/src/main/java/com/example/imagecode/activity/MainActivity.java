package com.example.imagecode.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.imagecode.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_first:
                startActivity(new Intent(this, FirstKindActivity.class));
                break;
            case R.id.btn_second:
                startActivity(new Intent(this, SecondKindActivity.class));
                break;
        }
    }
}
