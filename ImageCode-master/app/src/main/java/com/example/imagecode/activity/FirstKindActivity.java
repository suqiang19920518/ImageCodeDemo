package com.example.imagecode.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imagecode.R;
import com.example.imagecode.view.CaptchaImageView;

/**
 * @author: sq
 * @date: 2017/9/7
 * @corporation: 深圳市思迪信息技术股份有限公司
 * @description: 第一种验证码样式演示
 */
public class FirstKindActivity extends AppCompatActivity {

    private EditText captchaInput;
    private Button submitButton;
    private CaptchaImageView captchaImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_kind);
        captchaInput = (EditText) findViewById(R.id.captchaInput);
        submitButton = (Button) findViewById(R.id.submitButton);
        captchaImageView = (CaptchaImageView) findViewById(R.id.image_code);

        //设置验证码长度
        captchaImageView.setCaptchaLength(6);
        //设置验证码是否显示点阴影
        captchaImageView.setIsDotNeeded(true);
        //设置验证码格式，字母、数字混合
        captchaImageView.setCaptchaType(CaptchaImageView.CaptchaGenerator.BOTH);

        captchaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captchaImageView.regenerate();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = captchaImageView.matchCode(captchaInput.getText().toString(), false);

                if (result) {
                    Toast.makeText(FirstKindActivity.this, "验证成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FirstKindActivity.this, "验证失败！", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
