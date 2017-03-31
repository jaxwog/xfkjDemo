package com.xfkj.mrxuyaohui.xfkjdemo.activity;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.util.image.SmartImageView;
import com.xfkj.mrxuyaohui.xfkjdemo.util.news.News;
import com.xfkj.mrxuyaohui.xfkjdemo.util.news.News_JsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.register.Register_JsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.register.Register_bean;
import com.xfkj.mrxuyaohui.xfkjdemo.util.register.Register_submit_JsonHelp;

import java.util.List;

public class Register_main extends TabActivity {
    private EditText phone;//peo_phone
    private EditText passwd;//passwd
    private EditText passwd_1;//passwd_queding
    private Button huoqu;//huoqu
    private Button sub;//peo_login
    private EditText port;//peo_yzm_1

    private String tape="user";//设置是普通用户登录还是医生注册

    private List<Register_bean> persons;
    private String result;//验证码发送成功
    private String result_register;//用户注册成功
    private String urlPath="http://192.168.1.177:8080/MyheartDoctot/ChecMessage?UserPhone=";
//    private String register_url="http://192.168.1.177:8080/MyheartDoctot/RegisteredAction?tape=user&UserPhone=";
private String register_url="http://192.168.1.177:8080/MyheartDoctot/RegisteredAction?tape=";


    private String phone_num;
    private String phone_passwd;
    private String phone_passwd_1;


    //分页的实现
    public TabHost mth;
    public static final String TAB_NEWS = "用户注册";
    public static final String TAB_HOME = "医生注册";
    public RadioGroup radioGroup;
    //返回按钮
    private Button backButton;
    //listview设置
    private ListView listView;//从服务器端获取数据
    private TextView empty;//为空时的显示
    //rediaoButton
    private RadioButton user_radio;
    private RadioButton doc_radio;

    private Button tv_wb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);
        phone=(EditText)this.findViewById(R.id.peo_phone);
        passwd=(EditText)this.findViewById(R.id.passwd);
        passwd_1=(EditText)this.findViewById(R.id.passwd_queding);
        huoqu=(Button)this.findViewById(R.id.huoqu);
        sub=(Button)this.findViewById(R.id.peo_login);

        user_radio=(RadioButton)this.findViewById(R.id.radio_button0);
        doc_radio=(RadioButton)this.findViewById(R.id.radio_button1);
        port=(EditText)this.findViewById(R.id.peo_yzm_1);

        ////返回按钮
        backButton=(Button)this.findViewById(R.id.tv_wb);
        init();
        // 底部菜单点击事件
        clickevent();
    }
    private void clickevent() {
        this.radioGroup = (RadioGroup) findViewById(R.id.main_radio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 根据点击的按钮跳转到相应的界面
                switch (checkedId) {
                    case R.id.radio_button0:
                        mth.setCurrentTabByTag(TAB_NEWS);
                        tape="user";
                        break;
                    case R.id.radio_button1:
                        mth.setCurrentTabByTag(TAB_HOME);
                        tape="doc";
                        break;


                }
            }
        });
    }


    private void init()
    {
        // 实例化TabHost
        mth = this.getTabHost();
        TabHost.TabSpec ts1 = mth.newTabSpec(TAB_HOME).setIndicator(TAB_HOME);
        ts1.setContent(R.id.title);
        mth.addTab(ts1);// 往TabHost中第一个底部菜单添加界面
        TabHost.TabSpec ts2 = mth.newTabSpec(TAB_NEWS).setIndicator(TAB_NEWS);
        ts2.setContent(R.id.title);
        mth.addTab(ts2);
        mth.setCurrentTabByTag(TAB_NEWS);

    }
    public void click(View v)
    {
        switch (v.getId())
        {
            case  R.id.tv_wb:
                finish();
                break;
            case R.id.huoqu:
                if(phone.getText().toString().length()!=0) {
                    Toast.makeText(Register_main.this,"已经向你的手机发送验证码！",Toast.LENGTH_SHORT).show();
                    new Thread(new JsonThread()).start();
                }else {
                    Toast.makeText(Register_main.this,"手机号码不能为空！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.peo_login:
                phone_num=phone.getText().toString();
                phone_passwd=passwd.getText().toString();
                phone_passwd_1=passwd_1.getText().toString();
                if(phone_num.length()!=0&&phone_passwd.length()!=0&&phone_passwd_1.length()!=0&&phone_passwd.trim().equals(phone_passwd_1.trim())) {
                    if (result != null) {
                        if (port.getText().toString().trim().equals(result)) {
                            Toast.makeText(Register_main.this,"正在提交...！",Toast.LENGTH_SHORT).show();
                            new Thread(new RegisterThread()).start();
                        }
                    }else {
                        Toast.makeText(Register_main.this,"还未获取验证码！请先获取验证码！",Toast.LENGTH_SHORT).show();

                    }
                }else {
                    Toast.makeText(Register_main.this,"输入的信息有误或者两次输入的密码不一样！",Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }
    //用户注册
    class RegisterThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            String newInfoList=Register_submit_JsonHelp.getListPerson(register_url+tape+"&UserPhone="+phone_num+"&UserPasswd="+phone_passwd);
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

    //获取验证码的实现
    class JsonThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            List<Register_bean> newInfoList= Register_JsonHelp.getListPerson(urlPath+phone_num);
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
                case 2:
                    result_register=(String)msg.obj;
                    System.out.println("获取到的是否注册成功为："+result_register);
                    if (result_register.equals("sucess"))
                    {
                        Toast.makeText(Register_main.this,"用户注册成功！",Toast.LENGTH_SHORT).show();
                    }else if(result_register.equals("Alreadyexists")){
                        Toast.makeText(Register_main.this,"用户已经注册！",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Register_main.this,"用户注册失败！",Toast.LENGTH_SHORT).show();

                    }
                    break;


            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_main, menu);
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
