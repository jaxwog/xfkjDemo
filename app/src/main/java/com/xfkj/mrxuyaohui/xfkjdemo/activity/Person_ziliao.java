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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.bean.User_ziliao;
import com.xfkj.mrxuyaohui.xfkjdemo.util.IpCofig;
import com.xfkj.mrxuyaohui.xfkjdemo.util.ZiLiao.Ziliao_jsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.news.News;
import com.xfkj.mrxuyaohui.xfkjdemo.util.news.News_JsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.register.Register_bean;
import com.xfkj.mrxuyaohui.xfkjdemo.util.register.Register_submit_JsonHelp;

import org.w3c.dom.Text;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

public class Person_ziliao extends Activity {

    private EditText name;
    private RadioGroup sexGroup;
    private RadioButton boy;
    private RadioButton girl;
    private EditText peo_desc;
    private EditText age;
    private TextView phone;
    private Button xiugai;
    private Button peo_sub;
    private TextView balance;//账户余额


    private String receive_url=IpCofig.getIP_SERVER()+"MyheartDoctot/Userbyid?UserId=";
    private String path_url= IpCofig.getIP_SERVER()+"MyheartDoctot/UpdateUserMessage?UserName=";
    private String userSex;
    private String result_register;//获取返回的结果

    private  List<User_ziliao> persons;

    private int userId;
    private String userPhone;
    private Button title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_ziliao);
        Intent intent=getIntent();
        userPhone=intent.getStringExtra("userPhone");

        userId=((MyAppliction)getApplication()).getUserId();//登录用户ID

        name=(EditText)findViewById(R.id.name);
        peo_desc=(EditText)findViewById(R.id.peo_desc);
        phone=(TextView)findViewById(R.id.phone);

        //返回按钮
        title=(Button)findViewById(R.id.title);

        age=(EditText)findViewById(R.id.age);
        sexGroup=(RadioGroup)findViewById(R.id.sex);
        sexGroup.setOnCheckedChangeListener(new AnswerGroupChangeListener());
        boy=(RadioButton)findViewById(R.id.male);
        girl=(RadioButton)findViewById(R.id.female);

        xiugai=(Button)findViewById(R.id.xiugai);
        peo_sub=(Button)findViewById(R.id.peo_sub);

        balance=(TextView)findViewById(R.id.balance);
        new Thread(new ReceiveThread()).start();


    }
    class AnswerGroupChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int arg1) {
            int result = 0;
            switch (group.getCheckedRadioButtonId()) {
                case R.id.male:
                userSex="male";

                    break;
                case R.id.female:
                    userSex="female";
                    break;

            }
        }
    }
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.title:
                finish();
                break;
            case R.id.xiugai:
                Intent intent=new Intent();
                intent.setClass(Person_ziliao.this,XiuGai_Pass_Activity.class);
                intent.putExtra("tape","user");
                startActivity(intent);
                break;
            case R.id.peo_sub:
                String ni_name=name.getText().toString().trim();

                if(name.getText().toString().length()!=0&&age.getText().toString().length()!=0&&peo_desc.getText().toString().length()!=0)
                {
                    Toast.makeText(Person_ziliao.this,"正在上传你的个人信息...",Toast.LENGTH_SHORT).show();
                    new Thread(new RegisterThread()).start();

                }else {
                    Toast.makeText(Person_ziliao.this,"请补全你的个人信息！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    //用户提交信息
    class RegisterThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            System.out.println("获取到的name为："+name.getText().toString().trim()+"年龄是："+age.getText().toString().trim()+"性别是："+userSex+"个人说明是："+peo_desc.getText().toString().trim());

//            String str=name.getText().toString().trim() +"&UserBalance"+20+ "&UserAge=" + age.getText().toString().trim() +"&UserSex=" + userSex+"&UserMessage"+peo_desc.getText().toString().trim()+"&UserId"+2;
            String name_space=name.getText().toString().trim();
            String name_desc=peo_desc.getText().toString().trim();
            try{
                name_space = URLEncoder.encode(name_space, "utf-8") ;
                name_desc = URLEncoder.encode(name_desc, "utf-8") ;

            }catch (Exception e)
            {
                e.printStackTrace();
            }
//            private String getPath_url="http://192.168.1.177:8080/MyheartDoctot/UpdateUserMessage?UserName=xuyaohui@&UserAge=24@&UserSex=%E7%94%B7&UserBalance=20&UserMessage=%E5%93%88%E5%93%88@&UserId=2";
            String newInfoList=Register_submit_JsonHelp.getListPerson(path_url+name_space+"&UserAge="+ age.getText().toString()+"&UserSex="+"nan"+"&UserBalance="+20+ "&UserMessage="+name_desc+"&UserId="+2);
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
    //获取用户提交信息
    class ReceiveThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            List<User_ziliao> newInfoList = Ziliao_jsonHelp.getListPerson(receive_url + userId);
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
                    persons = (List<User_ziliao>) msg.obj;
                    System.out.println("得到的昵称为："+persons.get(0).getUserName());
                    name.setText(persons.get(0).getUserName());//昵称的填充
                    age.setText(""+persons.get(0).getUserAge());
                    peo_desc.setText(persons.get(0).getUserMessage());
                    if(persons.get(0).getUserSex().equals("male"))
                    {
                        boy.setChecked(true);
                    }else {
                        girl.setChecked(true);
                    }
                    balance.setText(""+persons.get(0).getUserBalance());
                    phone.setText(userPhone);
                    break;

                case 2:
                    result_register=(String)msg.obj;
                    System.out.println("获取到的是否提交成功为："+result_register);
                    if (result_register.equals("sucess"))
                    {
                        Toast.makeText(Person_ziliao.this,"信息提交成功！",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Person_ziliao.this,"信息提交失败！",Toast.LENGTH_SHORT).show();

                    }


            }
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_person_ziliao, menu);
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
