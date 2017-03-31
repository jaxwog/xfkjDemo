package com.xfkj.mrxuyaohui.xfkjdemo.activity;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.MainActivity;
import com.xfkj.mrxuyaohui.xfkjdemo.R;

public class FristActivity extends TabActivity {
    public TabHost mth;
    public static final String TAB_HOME = "首页";
    public static final String TAB_NEWS = "诊断";
    public static final String TAB_ABOUT = "新闻";
    public static final String TAB_SEARCH = "我的";
    public static final String TAB_DOC = "医生";

    public RadioGroup radioGroup;


    //退出程序
    private long exitTime = 0;//退出程序


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom);
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
                        mth.setCurrentTabByTag(TAB_HOME);
                        break;
                    case R.id.radio_button1:
                        mth.setCurrentTabByTag(TAB_NEWS);
                        break;
                    case R.id.radio_button2:
                        mth.setCurrentTabByTag(TAB_ABOUT);
                        break;
                    case R.id.radio_button3:
                            mth.setCurrentTabByTag(TAB_SEARCH);
                        break;

                }
            }
        });
    }

    /**
     * 实例化TabHost,往TabHost添加5个界面
     */
    private void init()
    {
        // 实例化TabHost
        mth = this.getTabHost();
         TabHost.TabSpec ts1 = mth.newTabSpec(TAB_HOME).setIndicator(TAB_HOME);
        ts1.setContent(new Intent(this, MainActivity.class));
        mth.addTab(ts1);// 往TabHost中第一个底部菜单添加界面
        TabHost.TabSpec ts2 = mth.newTabSpec(TAB_NEWS).setIndicator(TAB_NEWS);
        ts2.setContent(new Intent(this, ZhenDuan.class));
        mth.addTab(ts2);
        TabHost.TabSpec ts3 = mth.newTabSpec(TAB_ABOUT).setIndicator(TAB_ABOUT);
        ts3.setContent(new Intent(this, News_main.class));
        mth.addTab(ts3);
            TabHost.TabSpec ts4 = mth.newTabSpec(TAB_SEARCH).setIndicator(TAB_SEARCH);
            ts4.setContent(new Intent(this, MyCenter.class));
            mth.addTab(ts4);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frist, menu);
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

//    @Override
//    public boolean d(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
//            if((System.currentTimeMillis()-exitTime) > 2000){
//                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                exitTime = System.currentTimeMillis();
//            } else {
//                finish();
//                System.exit(0);
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
   @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
       if (event.getKeyCode()== KeyEvent.KEYCODE_BACK&&event.getAction() == KeyEvent.ACTION_DOWN) {
           if ((System.currentTimeMillis() - exitTime) > 2000) {
               Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
               exitTime = System.currentTimeMillis();
           } else {
               finish();
               System.exit(0);
           }
           return true;
       }
               return super.dispatchKeyEvent(event);
   }
}
