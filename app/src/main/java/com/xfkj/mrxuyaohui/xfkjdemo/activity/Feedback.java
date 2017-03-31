package com.xfkj.mrxuyaohui.xfkjdemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.util.IpCofig;
import com.xfkj.mrxuyaohui.xfkjdemo.util.register.Register_submit_JsonHelp;

import java.net.URLEncoder;

public class Feedback extends Activity {
    private Button back;//返回按钮
    private EditText peo_fankui;
    private Button peo_tijiao;
//    private String receive_url = IpCofig.getIP_SERVER()+"MyheartDoctot/ObjectionInsert?ObjectionMessage=xuyaohui&UserPhone=15638808580";
    private String receive_url = IpCofig.getIP_SERVER()+"MyheartDoctot/ObjectionInsert?ObjectionMessage=";
    private String result_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peo_feedback);
        back=(Button)findViewById(R.id.back_before);
        peo_fankui=(EditText)findViewById(R.id.peo_fankui);
        peo_tijiao=(Button)findViewById(R.id.peo_tijiao);

    }
    class ReceiveThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            String message=peo_fankui.getText().toString().trim();
            try{
                message= URLEncoder.encode(message,"utf-8");
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            String result = Register_submit_JsonHelp.getListPerson(receive_url +message+"&UserPhone="+((MyAppliction)getApplication()).getPhone());
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
                        Toast.makeText(Feedback.this, "信息提交成功！", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Feedback.this,"信息提交失败！",Toast.LENGTH_SHORT).show();

                    }


            }
        }
    };
    public void click(View v)
    {
        switch (v.getId())
        {
            case R.id.back_before:
                finish();
                break;
            case R.id.peo_tijiao:
                if(peo_fankui.getText().toString().length()==0){
                    Toast.makeText(Feedback.this,"你输入的数据为空...",Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(Feedback.this,"正在提交数据...",Toast.LENGTH_SHORT).show();
                    new Thread(new ReceiveThread()).start();
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
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
