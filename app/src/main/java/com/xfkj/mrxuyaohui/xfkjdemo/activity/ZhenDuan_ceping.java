package com.xfkj.mrxuyaohui.xfkjdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.test.Frist_Depression_test;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.test.Frist_Heart_test;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.test.Frist_JiaoLv_test;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.test.Frist_pressuer_test;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.test.Heart_Test;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.test.Jiaolv_test;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.test.Pressuer_Test;

public class ZhenDuan_ceping extends Activity {

    private Button depression;//抑郁症测评
    //定义返回按钮
    private Button back_butn;
    //焦虑自测
    private Button anxiety;
    //压力水平自测
    private Button pressuer;
    private Button health;
    private Button genduo;//支付页面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhendun_xinliceping_detail);
        depression=(Button)this.findViewById(R.id.depression);
        back_butn=(Button)this.findViewById(R.id.back_before);
        anxiety=(Button)this.findViewById(R.id.anxiety);
        pressuer=(Button)this.findViewById(R.id.pressuer);
        health=(Button)this.findViewById(R.id.health);

    }
    public void click(View v)
    {
        Intent intent=new Intent();
        switch (v.getId())
        {
            case R.id.depression:
                intent.setClass(this,Frist_Depression_test.class);
                startActivity(intent);
                break;
            case R.id.back_before:
                finish();
                break;
            case R.id.anxiety:
                intent.setClass(this, Frist_JiaoLv_test.class);
                startActivity(intent);
                break;
            case R.id.pressuer:
                intent.setClass(this, Frist_pressuer_test.class);
                startActivity(intent);
                break;
            case R.id.health:
                intent.setClass(this, Frist_Heart_test.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_zhen_duan_ceping, menu);
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
