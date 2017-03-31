package com.xfkj.mrxuyaohui.xfkjdemo.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

import com.xfkj.mrxuyaohui.xfkjdemo.R;

public class Depression_Test_detail extends Activity {

    private TextView score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depression__test_detail);
        score=(TextView)this.findViewById(R.id.title);
        Intent intent=getIntent();
        String text=intent.getStringExtra("score");
        score.setText(text);
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){

            new AlertDialog.Builder(this)
                    .setTitle("退出测试？")
                    .setMessage("正在测试，是否要退出？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
        }
        return super.onKeyDown(keyCode, event);
    }
    private void exit() {

        ActivityManager actMgr = (ActivityManager) this .getSystemService(Context.ACTIVITY_SERVICE);
        actMgr.restartPackage(getPackageName());
        System.out.println("exit2");
    }
}
