package com.xfkj.mrxuyaohui.xfkjdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.xfkj.mrxuyaohui.xfkjdemo.R;


public class forget_select_shenfen extends Activity {
    private Button user;
    private Button doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_select_shenfen);
        user=(Button)findViewById(R.id.user);
        doc=(Button)findViewById(R.id.doc);
    }
    public void click(View v)
    {
        Intent intent=new Intent();
        switch (v.getId())
        {
            case R.id.user:
                intent.setClass(forget_select_shenfen.this,Forget_passwd_main.class);
                intent.putExtra("tape","user");
                startActivity(intent);
                finish();
                break;
            case R.id.doc :
                intent.setClass(forget_select_shenfen.this,Forget_passwd_main.class);
                intent.putExtra("tape","doc");
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forget_select_shenfen, menu);
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
