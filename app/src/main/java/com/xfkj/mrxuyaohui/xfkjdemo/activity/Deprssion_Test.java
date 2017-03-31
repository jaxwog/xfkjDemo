package com.xfkj.mrxuyaohui.xfkjdemo.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.self_define.CircleProgressBar;
import com.xfkj.mrxuyaohui.xfkjdemo.util.test.DBOpenHelper;
import com.xfkj.mrxuyaohui.xfkjdemo.util.test.MentalHealth;

import java.util.HashMap;
import java.util.Map;

public class Deprssion_Test extends Activity {

    //设置进度条
    private CircleProgressBar progressBar;//进度条
    private int status=5;//初始值
    private ImageView circlePointImg;


    private DBOpenHelper dbHelper ;
    private MentalHealth mHealth;
    private RadioGroup answerGroup;
    private RadioButton answerABtn;
    private RadioButton answerBBtn;
    private RadioButton answerCBtn;
    private RadioButton answerDBtn;
    //问题是
    private TextView question;
    //剩余题目的个数
    private TextView remainQuestion;
    private Map<String, Integer> scoreMap = new HashMap<String, Integer>();

    private AlertDialog menuDialog ;
    private TextView the_testing;
    private int total=21;//定义总的题目数
    private int pageNo=1;//定义当前页
    private Button next;
    private boolean flag=false;

    SQLiteDatabase db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.depression_test);
        progressBar=(CircleProgressBar)this.findViewById(R.id.pb);
        progressBar.setProgress(status);

        question=(TextView)this.findViewById(R.id.desc);
        answerGroup = (RadioGroup) findViewById(R.id.answerGroup);
        answerGroup.setOnCheckedChangeListener(new AnswerGroupChangeListener());
        answerABtn = (RadioButton) findViewById(R.id.answerA);
        answerBBtn = (RadioButton) findViewById(R.id.answerB);
        answerCBtn = (RadioButton) findViewById(R.id.answerC);
        answerDBtn=(RadioButton)findViewById(R.id.answerD);

        next=(Button)this.findViewById(R.id.next);

//        scoreMap.put("score",0);
        scoreMap.put("score",0);
        dbHelper=new DBOpenHelper(this,"demo.db",1);
        DBOpenHelper.insertAndUpdateData(dbHelper);
        initData();
        initView();
    }

    public void click(View v)
    {
        switch (v.getId())
        {
            case R.id.next:
                if(answerABtn.isChecked()||answerBBtn.isChecked()||answerCBtn.isChecked()||answerDBtn.isChecked()) {
                    if (status <= 100) {
                        status += 5;
                        progressBar.setProgress(status);
                    } else {
                        progressBar.setProgress(100);
                    }
                    nextTest();
                }else {
                    Toast.makeText(Deprssion_Test.this,"请先选择答案！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    //设置监听事件
    public void initView(){
        question.setText(mHealth.getSubject());
    }
    class AnswerGroupChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int arg1) {
            int result = 0;
            switch (group.getCheckedRadioButtonId()) {
                case R.id.answerA:
                    if(flag!=true){
                    result = scoreMap.get("score")+mHealth.getAnswerAScore();
                    scoreMap.put("score", result);
                    }
                    flag=true;

                    break;
                case R.id.answerB:
                    if(flag!=true) {
                        result = scoreMap.get("score") + mHealth.getAnswerBScore();
                        scoreMap.put("score", result);
                    }
                    flag=true;
                    break;
                case R.id.answerC:
                    if(flag!=true) {
                        result = scoreMap.get("score") + mHealth.getAnswerCScore();
//                    Toast.makeText(MainActivity.this,mHealth.getSubject()+"得到的分数为："+mHealth.getAnswerCScore(),Toast.LENGTH_SHORT).show();

                        scoreMap.put("score", result);
                    }
                    flag=true;
                    break;
                case R.id.answerD:
                    if(flag!=true) {
                        result = scoreMap.get("score") + mHealth.getAnswerDScore();
                        scoreMap.put("score", result);
                    }
                    flag=true;
                    break;
                default:
                    break;
            }
        }
    }
    //点击到下一页
    public void nextTest() {
        // 如果当前页与总数相等，表示完成时候的提交
        if (pageNo == total) {
            System.out.print("你的分数是："+scoreMap.get("score"));
        } else {
            pageNo = (pageNo + 1) >= total ? total : (pageNo + 1);
            initData();
            initAll();
            answerGroup.clearCheck();
            flag=false;
            if (pageNo == total) {
                next.setText("完成");
                Intent intent =new Intent();
                intent.setClass(this,Depression_Test_detail.class);
                double haha=scoreMap.get("score")*1.25;
                int wuwu=(int)haha;
                intent.putExtra("score","分数是："+wuwu);
                startActivity(intent);
                finish();

            }
        }
    }
    //初始化数据
    public void initData() {
        mHealth = dbHelper.getMentalHealth(String.valueOf((pageNo-1)));
        if (mHealth == null) {
            mHealth = new MentalHealth();
        }
    }
    public void initAll() {
        question.setText( mHealth.getSubject());
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
