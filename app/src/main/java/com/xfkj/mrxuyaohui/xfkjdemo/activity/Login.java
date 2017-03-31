package com.xfkj.mrxuyaohui.xfkjdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.bean.Doc_Login;
import com.xfkj.mrxuyaohui.xfkjdemo.bean.User_Login;
import com.xfkj.mrxuyaohui.xfkjdemo.util.HttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends Activity {

    private ImageButton forget_button;//忘记按钮
    private Button register;//注册按钮
    private EditText phoneNum;//手机号码
    private EditText password;//手机密码
    private Button login;//登录按钮

    private Button peo_miss;//忘记密码

    //医生登录按钮
    private Button doc_login;
    private String result;

    //判断登录的是用户还是医生
    private String tape=null;


    //使用SharedPreferences储存用户信息
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    //返回的用户信息
    private List<User_Login> user_list;
    private List<Doc_Login> doc_list;

    //记住密码实现
    private CheckBox passwd_rember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_login);
        register = (Button) this.findViewById(R.id.peo_zhuce);
        phoneNum = (EditText) this.findViewById(R.id.peo_phone);
        password = (EditText) this.findViewById(R.id.peo_pas);
        login = (Button) this.findViewById(R.id.peo_login);
        //忘记密码的实现
        peo_miss=(Button)this.findViewById(R.id.peo_miss);

        //医生登录
        doc_login=(Button)this.findViewById(R.id.doc_login);

        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();//获取编辑器
        if(sharedPreferences.getBoolean("isEmpty",false))
        {
            phoneNum.setText(sharedPreferences.getString("userPhone",null));
            password.setText(sharedPreferences.getString("userPasswd",null));
        }

    }

    public void clickListener(View v)
    {
        Intent intent=new Intent();
        switch (v.getId())
        {
            case R.id.peo_zhuce:
                intent.setClass(this,Register_main.class);
                startActivity(intent);
                break;
            case R.id.peo_login:
                tape="user";
                if(check()) {
                    Toast.makeText(Login.this, "正在登陆...", Toast.LENGTH_SHORT).show();
                    new Thread(new LoginThread()).start();
                }else {
                    Toast.makeText(Login.this, "请补全信息！", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.doc_login:
                tape="doc";
                if(check()) {
                    Toast.makeText(Login.this, "正在登陆...", Toast.LENGTH_SHORT).show();
                    new Thread(new DocLoginThread()).start();

                }else {
                    Toast.makeText(Login.this, "请补全信息！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.peo_miss://转到忘记密码界面
                Intent intentForget=new Intent();
                intentForget.setClass(Login.this,forget_select_shenfen.class);
                startActivity(intentForget);
                break;

        }
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 1:
                    //返回到个人中心，且带有两个数据：用户名和注销按钮
                    Toast.makeText(Login.this,"登录成功！",Toast.LENGTH_SHORT).show();
                    Intent zhuceIntent = new Intent();
                    Bundle myBundle = new Bundle();
                    myBundle.putString("username", phoneNum.getText().toString().trim());
                    myBundle.putString("logout","退出登录");
                    zhuceIntent.putExtras(myBundle);
                    setResult(1, zhuceIntent);
                    finish();
                    break;
                case 2:
                    Toast.makeText(Login.this,"登录成功！",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent();
                    intent.setClass(Login.this,Doctor_myCenter.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
    //医生登陆开启线程
    class DocLoginThread implements  Runnable
    {

        @Override
        public void run() {
            Looper.prepare();
            Message msg=handler.obtainMessage();
                if (DocLogin())
                {
                    msg.what=2;
                    handler.sendMessage(msg);
                }else{
                    Toast.makeText(Login.this,"输入的密码或账号有误！",Toast.LENGTH_SHORT).show();
                }

        }
    }
    //用户登陆开启线程
    class LoginThread implements  Runnable
    {

        @Override
        public void run() {
            Looper.prepare();
            Message msg=handler.obtainMessage();
                if (login())
                {
                    msg.what=1;
                    handler.sendMessage(msg);
                }else{
                    Toast.makeText(Login.this,"输入的密码或账号有误！",Toast.LENGTH_SHORT).show();
                }
        }
    }


    public boolean check()
    {
        if (phoneNum.getText().toString().trim()==null||password.getText().toString().trim()==null)
        {
            Toast.makeText(this,"请填写账号和密码！",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
    //医生登陆验证
    public boolean DocLogin(){
        boolean flag=false;
        String phone=phoneNum.getText().toString().trim();
        String passWd=password.getText().toString().trim();
        System.out.println("登陆前的flag为："+((MyAppliction)getApplication()).getFlag());
        try{
            doc_list=HttpUtil.docLoginServer(phone, passWd, tape);
            String result=doc_list.get(0).getSuccess();
            System.out.println("获取到的result为："+result);
            int docId_login=doc_list.get(0).getDocId();
            String userPhone=doc_list.get(0).getDocphone();
            if (result.equals("sucess")) {
                flag=true;
                Toast.makeText(Login.this,"正在登陆...",Toast.LENGTH_SHORT).show();
                editor.putString("userPasswd", password.getText().toString().trim());
                System.out.println("存储的密码为："+password.getText().toString().trim());

                editor.putString("userPhone", phoneNum.getText().toString().trim());
                editor.putInt("userId", docId_login);
                editor.putBoolean("isEmpty", true);
                editor.commit();//提交修改
                ((MyAppliction)getApplication()).setFlag(true);
                ((MyAppliction)getApplication()).setUserId(docId_login);
                ((MyAppliction)getApplication()).setUserId(1);
                ((MyAppliction)getApplication()).setDocId(docId_login);
                ((MyAppliction)getApplication()).setPhone(phoneNum.getText().toString().trim());
                ((MyAppliction)getApplication()).setPassword(password.getText().toString().trim());
                System.out.println("存储的UserID为："+ ((MyAppliction)getApplication()).getUserId());

                return flag;
            }else if(result.equals("error")){
                Toast.makeText(Login.this,"密码输入错误...",Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(Login.this,"输入信息有误！请重新输入...",Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(Login.this,"登陆失败！检查输入信息，请重试！",Toast.LENGTH_SHORT).show();


        }
        return flag;
    }
    //用户登录验证
    public boolean login(){
        boolean flag=false;
        String phone=phoneNum.getText().toString().trim();
        String passWd=password.getText().toString().trim();
        System.out.println("得到的密码为："+password.getText().toString().trim());

        try{
            user_list=HttpUtil.loginServer(phone,passWd,tape);
            String result=user_list.get(0).getResult();
            System.out.println("获取到的result为："+result);
            int userId=user_list.get(0).getUserId();
            String userPhone=user_list.get(0).getPhone();
            if (result.equals("sucess")) {
                flag=true;
                editor.putString("userPasswd", password.getText().toString().trim());
                System.out.println("存储的密码为："+password.getText().toString().trim());
                editor.putString("userPhone", phoneNum.getText().toString().trim());
                editor.putInt("userId", userId);
                editor.putBoolean("isEmpty", true);
                editor.commit();//提交修改
                ((MyAppliction)getApplication()).setFlag(true);
                ((MyAppliction)getApplication()).setUserId(userId);
                ((MyAppliction)getApplication()).setPassword(password.getText().toString().trim());
                System.out.println("存储的UserID为："+ ((MyAppliction)getApplication()).getUserId());
                System.out.println("存储的password为："+ ((MyAppliction)getApplication()).getPassword());
                return flag;
            }else if(result.equals("error")){
                Toast.makeText(Login.this,"密码输入错误...",Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(Login.this,"登陆失败...",Toast.LENGTH_SHORT).show();

            }

        }catch (Exception e)
        {
            Toast.makeText(Login.this,"登陆失败！检查输入信息，请重试！",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            System.out.println("没有查询到！");
        }
        return flag;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
