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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.util.IpCofig;
import com.xfkj.mrxuyaohui.xfkjdemo.util.news.News;
import com.xfkj.mrxuyaohui.xfkjdemo.util.news.News_JsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.register.Register_JsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.register.Register_bean;

import java.util.List;

public class Forget_passwd_main extends Activity {
    private EditText phoneNum;
    private EditText port;
    private Button huoqu;
    private Button next;

    private String phone_text;
    private String port_text;

    private String urlPath=IpCofig.getIP_SERVER()+"MyheartDoctot/ChecMessage?UserPhone=";
    private List<Register_bean> persons;
    private String result;//验证码发送成功
    //判断是用户还是医生
    private String tape;
    private Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_passwd_main);
        Intent intent=getIntent();
        tape=intent.getStringExtra("tape");

        phoneNum=(EditText)findViewById(R.id.phoneNum);
        port=(EditText)findViewById(R.id.port);
        huoqu=(Button)findViewById(R.id.huoqu);
        next=(Button)findViewById(R.id.next);
        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public void click(View v)
    {
        switch (v.getId())
        {
            case R.id.huoqu:
                if(phoneNum.getText().toString().length()!=0)
                {
                    System.out.print("得到的输入框中内容为："+phoneNum.getText().toString().trim());
                    Toast.makeText(Forget_passwd_main.this, "已经向你的手机发送验证码！", Toast.LENGTH_SHORT).show();
                    new Thread(new JsonThread()).start();
                }else{
                    Toast.makeText(Forget_passwd_main.this, "输入的手机号不能为空...！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.next:
                if(phoneNum.getText().toString().length()!=0&&port.getText().toString().length()!=0)
                {
                    if(result.equals(port.getText().toString().trim()))
                    {
                        Intent intent=new Intent();
                        intent.setClass(Forget_passwd_main.this,Forget_passwd_1.class);
                        intent.putExtra("phone",phoneNum.getText().toString().trim());
                        intent.putExtra("tape",tape);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(Forget_passwd_main.this, "输入的验证码不正确！", Toast.LENGTH_SHORT).show();

                    }
                }else {
                    Toast.makeText(Forget_passwd_main.this, "请完整填写信息！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    //获取验证码的实现
    class JsonThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            System.out.println("输入的手机号是"+phoneNum.getText().toString().trim());
            List<Register_bean> newInfoList= Register_JsonHelp.getListPerson(urlPath + phoneNum.getText().toString().trim());
            Message msg = new Message();
            if(newInfoList != null) {
                msg.what = 1;
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
                case 1:
                    persons = (List<Register_bean>) msg.obj;
                    result=persons.get(0).getPorts();
                    System.out.println("获取到的验证码为："+result);
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forget_passwd_main, menu);
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
