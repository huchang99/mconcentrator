package com.system.mconcentrator.mconcentrator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.Window.FEATURE_NO_TITLE;

public class DeviceSetLoginActivity extends Activity implements View.OnClickListener{

    private final static String TAG = "DeviceSetLoginActivity";

    //存储管理员帐号和密码
    private SharedPreferences adminsp;

    //控件
    private FrameLayout menu_titleup;
    //  private FrameLayout menu_titleend;
    //表头控件
    private TextView Back_tv;
    // private TextView tv_connect;
    // private TextView tv_right;
    private Button adminlogin;
    private EditText admininputpsd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_device_set_login);

        initViews();
        initData();
        initLinstener();

        /**
         * 判断app是否第一次登录,设置管理员初始密码
         */
        adminsp = getSharedPreferences("adminsp", Context.MODE_PRIVATE);
        Boolean admin_first = adminsp.getBoolean("AdminFirst", true);
        if (admin_first) {//第一次
            adminsp.edit().putBoolean("AdminFirst", false).commit();
            Toast.makeText(DeviceSetLoginActivity.this, "第一次", Toast.LENGTH_LONG).show();
            /*设置最初密码*/
            adminsp.edit().putString("adminpsd", "123456").commit();
        } else {
            Toast.makeText(DeviceSetLoginActivity.this, "不是第一次", Toast.LENGTH_LONG).show();
        }
    }


    private void initData() {
        //设置表头透明
        menu_titleup.getBackground().setAlpha(0);
        //显示左边的组件
        Back_tv.setVisibility(View.VISIBLE);
        Back_tv.setText("返回");



    }

    private void initViews() {
        adminlogin = (Button)findViewById(R.id.login_admin_btn);
        admininputpsd = (EditText)findViewById(R.id.login_admin_pwd_et);

        //顶部条控件
        menu_titleup = (FrameLayout) findViewById(R.id.menu_titleup);
        Back_tv = (TextView) findViewById(R.id.Back_tv);

    }

    private void initLinstener() {
        adminlogin.setOnClickListener(this);
        Back_tv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.login_admin_btn:

                String userpassword = admininputpsd.getText().toString(); //获取用户输入的帐号和密码
                String adminpassword = adminsp.getString("adminpsd",null);
                if(adminpassword.equals(userpassword))
                {
                    Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
                    intent.setClass(getApplicationContext(),DeviceSetupActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(this, "登录失败，密码错误", Toast.LENGTH_LONG).show();
                }
                intent = null;
                break;
            case R.id.Back_tv:
                finish();
                break;

        }

    }
}
