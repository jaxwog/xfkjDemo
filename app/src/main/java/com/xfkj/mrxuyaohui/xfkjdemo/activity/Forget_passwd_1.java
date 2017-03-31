package com.xfkj.mrxuyaohui.xfkjdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.util.IpCofig;
import com.xfkj.mrxuyaohui.xfkjdemo.util.register.Register_bean;
import com.xfkj.mrxuyaohui.xfkjdemo.util.register.Register_submit_JsonHelp;

import java.util.List;

public class Forget_passwd_1 extends Activity {
    private EditText passwd;
    private EditText passwd_1;
    private Button submitt;

    private String phone_num;
    private String passwd_frist;
    private String passed_second;

    private String forget_url= IpCofig.getIP_SERVER()+"MyheartDoctot/ForgetUserPasswd?tape=";
    private String tape;
    private String result_register;//得到提交修改密码后的状态
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_passwd_1);
        Intent intent=getIntent();
        phone_num=intent.getStringExtra("phone");
        tape=intent.getStringExtra("tape");



        passwd=(EditText)findViewById(R.id.passwd);
        passwd_1=(EditText)findViewById(R.id.passwd_1);

        passwd_frist=passwd.getText().toString().trim();
        passed_second=passwd_1.getText().toString().trim();
        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submitt=(Button)findViewById(R.id.sub);
        submitt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwd.getText().toString().length()!=0&&passwd_1.getText().toString().length()!=0){
                    if(passwd.getText().toString().trim().equals(passwd_1.getText().toString().trim())){
                        Toast.makeText(Forget_passwd_1.this,"正在提交...",Toast.LENGTH_SHORT).show();
                        new Thread(new RegisterThread()).start();

                    }else {
                        Toast.makeText(Forget_passwd_1.this,"两次输入的密码不一样！请重新输入！",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Forget_passwd_1.this,"请输入新的密码",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //用户修改密码
    class RegisterThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            System.out.print("获取到的tape为："+tape+"手机号为："+phone_num+"密码为："+passwd.getText().toString().trim());
            String newInfoList= Register_submit_JsonHelp.getListPerson(forget_url + tape + "&Userphone=" + phone_num + "&Userpasswd=" + passwd.getText().toString().trim());
            Message msg = new Message();
            if(newInfoList != null) {
                msg.what = 2;
                msg.obj = newInfoList;
            } else {
                msg.what = 0;
            }
            shandler.sendMessage(msg);
        }
    }
    Handler shandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 2:
                    result_register=(String)msg.obj;
                    System.out.println("获取到的是否修改成功为："+result_register);
                    if (result_register.equals("sucess"))
                    {
                        Toast.makeText(Forget_passwd_1.this,"密码修改成功！",Toast.LENGTH_SHORT).show();
                          finish();
                    }else{
                        Toast.makeText(Forget_passwd_1.this,"密码修改失败！",Toast.LENGTH_SHORT).show();
                    }


            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forget_passwd_1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
