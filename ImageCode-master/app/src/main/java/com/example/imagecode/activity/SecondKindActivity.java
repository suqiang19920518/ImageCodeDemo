package com.example.imagecode.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imagecode.R;
import com.example.imagecode.view.VerificationCodeView;

/**
 * @author: sq
 * @date: 2017/9/7
 * @corporation: 深圳市思迪信息技术股份有限公司
 * @description: 第二种验证码样式演示
 */
public class SecondKindActivity extends AppCompatActivity {

    private EditText codeEt;
    private Button mButton;
    private VerificationCodeView mVerificationCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_kind);
        mVerificationCodeView = (VerificationCodeView) findViewById(R.id.verification_code);
        mButton = (Button) findViewById(R.id.button);
        codeEt = (EditText) findViewById(R.id.code_et);

//        mVerificationCodeView.setCodeQuantity(6); //验证码数量
//        mVerificationCodeView.setCodeMode(2); //验证码模式
        mVerificationCodeView.setPointInterfere(true); //是否开启点干扰
        mVerificationCodeView.setLineInterfere(true);  //是否开启线干扰
        mVerificationCodeView.setLineInterfereQuantity(4); //干扰线的数量
        mVerificationCodeView.setPointInterfereQuantity(40); //干扰点的数量
        mVerificationCodeView.setMatchCase(false); //是否区分验证码大小写，默认区分。如果设置为不区分大小写，则会返回全小写格式

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("SecondKindActivity:", mVerificationCodeView.getVerificationCode());
                if (mVerificationCodeView.getVerificationCode().equals(codeEt.getText().toString().trim())) {
                    Toast.makeText(SecondKindActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SecondKindActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
