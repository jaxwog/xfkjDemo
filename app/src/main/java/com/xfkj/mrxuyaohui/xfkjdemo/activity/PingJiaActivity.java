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
import android.widget.RatingBar;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.bean.User_ziliao;
import com.xfkj.mrxuyaohui.xfkjdemo.util.IpCofig;
import com.xfkj.mrxuyaohui.xfkjdemo.util.register.Register_submit_JsonHelp;

import java.net.URLEncoder;
import java.util.List;

public class PingJiaActivity extends Activity {
    private Button back;
    private RatingBar ratingBar;
    private EditText text;
    private Button sub;
    private int docId;
    private String result_register;

//    private String path_url="http://192.168.1.177:8080/MyheartDoctot/Commentadd?CommentMessage=%E5%91%B5%E5%91%B5&CommentScore=5&UserId=1&DocId=1";
    private String path_url= IpCofig.getIP_SERVER()+"MyheartDoctot/Commentadd?CommentMessage=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_jia);
        Intent intent=getIntent();
        docId=intent.getIntExtra("docId",-1);
        System.out.println("得到的docID为："+docId);

        back=(Button)findViewById(R.id.title);
        ratingBar=(RatingBar)findViewById(R.id.rating);
        text=(EditText)findViewById(R.id.content);
        sub=(Button)findViewById(R.id.peo_tijiao);
    }
    public void click(View v)
    {
        switch (v.getId())
        {
            case R.id.title:
                finish();
                break;
            case R.id.peo_tijiao:
                    if(check()){
                        new Thread(new RegisterThread()).start();
                    }
                break;
        }
    }
    class RegisterThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            int star=ratingBar.getNumStars();
            System.out.println("得到的star："+star);

            String content=text.getText().toString().trim();
            try{
                content = URLEncoder.encode(content, "utf-8") ;
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            String newInfoList= Register_submit_JsonHelp.getListPerson(path_url+content +"&CommentScore="+star+"&UserId="+((MyAppliction) getApplication()).getUserId()+"&DocId="+docId);
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
                    result_register=(String)msg.obj;
                    System.out.print("得到的结果是："+result_register);
                    if (result_register.equals("sucess"))
                    {
                        Toast.makeText(PingJiaActivity.this,"信息提交成功！",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(PingJiaActivity.this,"信息提交失败！",Toast.LENGTH_SHORT).show();
                    }
            }
        }
    };
    public boolean check()
    {
        if(text.getText().toString()!=null)
        {
            return true;
        }else {
            Toast.makeText(PingJiaActivity.this,"评价不能为空！",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ping_jia, menu);
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
