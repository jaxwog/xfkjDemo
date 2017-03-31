package com.xfkj.mrxuyaohui.xfkjdemo.activity.test;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.xfkj.mrxuyaohui.xfkjdemo.R;

public class JiaoLv_test_detail extends Activity {
    private TextView score;

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depression__test_detail);

        textView=(TextView)findViewById(R.id.desc);
        textView.setText("评分：\n" +
                "    总粗分、标准分（最后得分=总粗分*1.25后取整）\n" +
                "    按照中国常模结果，SAS标准的分界为50分，其中50-59分为轻度焦虑，60-69分为重度焦虑，69分以上为重度焦虑。\n"
               +"    关于焦虑症状的临床分级，除参考量表分值外，主要还应根据临床症状，特别是要害症状的程度划分，量表总分值仅能作为一项参考指标而非绝对标准。\n");


        score=(TextView)this.findViewById(R.id.title);
        Intent intent=getIntent();
        String text=intent.getStringExtra("score");
        score.setText(text);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jiao_lv_test_detail, menu);
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
