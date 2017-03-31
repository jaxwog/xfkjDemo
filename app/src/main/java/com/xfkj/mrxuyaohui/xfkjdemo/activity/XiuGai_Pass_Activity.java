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
import com.xfkj.mrxuyaohui.xfkjdemo.bean.User_ziliao;
import com.xfkj.mrxuyaohui.xfkjdemo.util.IpCofig;
import com.xfkj.mrxuyaohui.xfkjdemo.util.ZiLiao.Ziliao_jsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.register.Register_submit_JsonHelp;

import java.util.List;

public class XiuGai_Pass_Activity extends Activity {


    private EditText old_password;
    private EditText new_password;
    private EditText new_password1;
    private Button sub;
    private String result_register;

//    private String receive_url = "http://192.168.1.177:8080/MyheartDoctot/UpdateUserPasswd?tape=user&UserId=2&Userpasswd=456";
    private String receive_url = IpCofig.getIP_SERVER()+"MyheartDoctot/UpdateUserPasswd?&UserId=";
    private String tape;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiu_gai__pass_);
        Intent intent=getIntent();
        tape=intent.getStringExtra("tape");

        old_password=(EditText)findViewById(R.id.old_pass);
        new_password=(EditText)findViewById(R.id.new_pass);
        new_password1=(EditText)findViewById(R.id.new_pass1);
        sub=(Button)findViewById(R.id.sub);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check()){
                    //开启线程提交数据
                    new Thread(new ReceiveThread()).start();

                }
            }
        });
    }
    public boolean check()
    {
        if (old_password.getText().toString().length()!=0&&new_password.getText().toString().length()!=0&&new_password1.getText().toString().length()!=0&&new_password.getText().toString().trim().equals(new_password1.getText().toString().trim()))
        {
            System.out.print("得到的旧密码为："+((MyAppliction)getApplication()).getPassword());
            System.out.print("输入的旧密码为："+old_password.getText().toString().trim());

            if(old_password.getText().toString().trim().equals(((MyAppliction)getApplication()).getPassword())){
                return true;
            }else {
                Toast.makeText(XiuGai_Pass_Activity.this,"输入的旧密码错误！！",Toast.LENGTH_SHORT).show();

            }
        }else {
            Toast.makeText(XiuGai_Pass_Activity.this,"输入的数据有误！",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    class ReceiveThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            String result = Register_submit_JsonHelp.getListPerson(receive_url+((MyAppliction)getApplication()).getUserId()+"&tape="+tape+"&Userpasswd="+new_password1.getText().toString().trim());
            Message msg = new Message();
            if(result != null) {
                msg.what = 1;
                msg.obj = result;
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
                    result_register=(String)msg.obj;
                    System.out.println("获取到的是否提交成功为："+result_register);
                    if (result_register.equals("sucess"))
                    {
                        Toast.makeText(XiuGai_Pass_Activity.this,"信息提交成功！",Toast.LENGTH_SHORT).show();
                        Intent logoutIntent = new Intent(XiuGai_Pass_Activity.this, FristActivity.class);
                        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        ((MyAppliction)getApplication()).setFlag(false);
                        startActivity(logoutIntent);
                    }else{
                        Toast.makeText(XiuGai_Pass_Activity.this,"信息提交失败！",Toast.LENGTH_SHORT).show();

                    }


            }
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_xiu_gai__pass_, menu);
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
