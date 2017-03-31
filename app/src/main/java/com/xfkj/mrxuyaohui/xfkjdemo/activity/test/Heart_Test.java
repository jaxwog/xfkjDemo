package com.xfkj.mrxuyaohui.xfkjdemo.activity.test;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.Depression_Test_detail;
import com.xfkj.mrxuyaohui.xfkjdemo.self_define.CircleProgressBar;
import com.xfkj.mrxuyaohui.xfkjdemo.util.test.DBOpenHelper;
import com.xfkj.mrxuyaohui.xfkjdemo.util.test.MentalHealth;
import com.xfkj.mrxuyaohui.xfkjdemo.util.test.Pressuer_Bean;

import java.util.HashMap;
import java.util.Map;

public class Heart_Test extends Activity {
    private TextView title;//标题

    //设置进度条
    private CircleProgressBar progressBar;//进度条
    private int status=2;//初始值


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
    private Map<String, Integer> scoreMap = new HashMap<String, Integer>();

    private AlertDialog menuDialog ;
    private TextView the_testing;
    private int total=15;//定义总的题目数
    private int pageNo=1;//定义当前页

    SQLiteDatabase db = null;

    private Button next;
    private boolean flag=false;



    private static final String CREATE_TABLE_HEART_TEST = "create table IF NOT EXISTS tb_heart_test(mid integer primary key autoincrement,"
            + "subject varchar(200),answerAScore integer,"
            + "answerBScore integer,answerCScore integer)";
    private static final String DROP_HEART_TEST = "DROP TABLE IF EXISTS tb_heart_test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.depression_test);
        title=(TextView)this.findViewById(R.id.title);
        title.setText("心理健康自测");
        progressBar=(CircleProgressBar)this.findViewById(R.id.pb);
        progressBar.setProgress(status);

        question=(TextView)this.findViewById(R.id.desc);
        answerGroup = (RadioGroup) findViewById(R.id.answerGroup);
        answerGroup.setOnCheckedChangeListener(new AnswerGroupChangeListener());
        answerABtn = (RadioButton) findViewById(R.id.answerA);
        answerBBtn = (RadioButton) findViewById(R.id.answerB);
        answerCBtn = (RadioButton) findViewById(R.id.answerC);
        answerDBtn = (RadioButton) findViewById(R.id.answerD);
        answerDBtn.setVisibility(View.GONE);

        next=(Button)this.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answerABtn.isChecked()||answerBBtn.isChecked()||answerCBtn.isChecked()||answerDBtn.isChecked()) {
                    if (status <= 100) {
                        status += 7;
                        progressBar.setProgress(status);
                    } else {
                        progressBar.setProgress(100);
                    }
                    nextTest();
                }else {
                    Toast.makeText(Heart_Test.this,"请先选择答案！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        scoreMap.put("score",0);
        dbHelper=new DBOpenHelper(this,"demo.db",1);
        db = dbHelper.getWritableDatabase();
        db.execSQL(DROP_HEART_TEST);
        db.execSQL(CREATE_TABLE_HEART_TEST);
        insertPressuerData(dbHelper);
        initData();
        initView();
    }
    //插入压力测试数据
    public void insertPressuerData(DBOpenHelper myHelper){
        //获取数据库对象
        SQLiteDatabase db = myHelper.getWritableDatabase();
        //使用execSQL方法向表中插入数据
        try {
            System.out.println("创建了tb_pre_test表！");
            db.execSQL("INSERT INTO `tb_heart_test` VALUES ('1', '1、想事情或做事时常常无故走神，注意力难以集中，脑子里胡思乱想，记忆力下降','0', '1', '2')");
            db.execSQL("INSERT INTO `tb_heart_test` VALUES ('2', '2、出门后常想门或抽屉是否锁好;信寄出后总怀疑是否有差错;常强迫自己想或做一些无意义的事情，明知不必要却又无法控制','0', '1', '2')");
            db.execSQL("INSERT INTO `tb_heart_test` VALUES ('3', '3、常常失眠，或入睡困难，或浅睡多梦，频频醒来，次日感到精力不足', '0', '1', '2')");
            db.execSQL("INSERT INTO `tb_heart_test` VALUES ('4', '4、处于敏感、紧张、焦虑的心境之中，惧怕并尽可能回避某人、某地、某物，渐渐失去信心，心情抑郁','0', '1', '2')");
            db.execSQL("INSERT INTO `tb_heart_test` VALUES ('5', '5、最近看什么都不顺眼，心烦，动不动就发脾气、摔东西、骂人甚至想找人打一架', '0', '1', '2')");
            db.execSQL("INSERT INTO `tb_heart_test` VALUES ('6', '6、有时常常不明原因地感到疲劳，精力不足，浑身乏力，肢体有麻木感','0', '1', '2')");
            db.execSQL("INSERT INTO `tb_heart_test` VALUES ('7', '7、情绪低落，感到生活道路上有太多的挫折和困难，前途悲观，工作、学习、娱乐都提不起精神和兴趣','0', '1', '2')");
            db.execSQL("INSERT INTO `tb_heart_test` VALUES ('8', '8、感到身体有某种不适或疼痛，觉得身体有病，但检查结果说没病，怀疑是误诊或漏诊，心中老想着此事，非常烦恼', '0', '1', '2')");
            db.execSQL("INSERT INTO `tb_heart_test` VALUES ('9', '9、无法控制自己的情绪和行动，常不分时间和场合地发脾气，易兴奋，想到好笑的事就笑，控制不住;或心情不好时会晕倒，甚至突然失语、失明、胸闷、抽搐，发脾气后又后悔', '0', '1', '2')");
            db.execSQL("INSERT INTO `tb_heart_test` VALUES ('10', '10、对一些声音和强光等敏感，影响情绪和睡眠，怕出门，怕拥挤，认为自己是敏感多疑胆小怕事的人', '0', '1', '2')");
            db.execSQL("INSERT INTO `tb_heart_test` VALUES ('11', '11、好思虑，好幻想，做事过于认真，要求十全十美，喜欢诸事刨根问底，别人评价自己主观、固执、刻板。事情没做好便耿耿于怀', '0', '1', '2')");
            db.execSQL("INSERT INTO `tb_heart_test` VALUES ('12', '12、害怕高处、险处，害怕异物、血液、传染病，害怕亲人突然离去，害怕抛头露面', '0', '1', '2')");
            db.execSQL("INSERT INTO `tb_heart_test` VALUES ('13', '13、别人说自己有病，自己不对，但自己却认为是别人不理解自己;别人不对，都在嘲笑自己，故意与自己作对，事后虽然觉得可能是自己错了，但又不愿多反省','0', '1', '2')");
            db.execSQL("INSERT INTO `tb_heart_test` VALUES ('14', '14、独处时，竟听到了有人讲话的声音，大白天曾见过死去的人，认为周围的人跟踪自己，或想害自己，觉得自己想的事似乎别人都知道', '0', '1', '2')");
            db.execSQL("INSERT INTO `tb_heart_test` VALUES ('15', '15、最近什么也不想干，非常烦恼，但又没有明确原因，即使做一些事也常常不能使烦恼分散或减轻，烦恼如阴魂附身，难以摆脱', '0', '1', '2')");
            System.out.println("插入完成！");
            db.close();
        } catch (SQLException e) {
            System.out.println("插入失败！");
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
                default:
                    break;
            }
        }
    }
    //点击到下一页
    public void nextTest() {
        // 如果当前页与总数相等，表示完成时候的提交
        if (pageNo == total) {
            next.setText("完成");
            System.out.print("你的分数是："+scoreMap.get("score"));
            Intent intent =new Intent();
            intent.setClass(this,Heatr_test_detail.class);
            intent.putExtra("score","你的分数是："+scoreMap.get("score"));
            startActivity(intent);
            finish();
        } else {
            pageNo = (pageNo + 1) >= total ? total : (pageNo + 1);
            initData();
            initAll();
            initView();
            answerGroup.clearCheck();
            flag=false;
        }
    }
    //初始化数据
    public void initData() {
        mHealth = dbHelper.getHeartHealth(String.valueOf((pageNo - 1)));
        if (mHealth == null) {
            mHealth = new MentalHealth();
        }
    }
    public void initAll() {
        question.setText(mHealth.getSubject());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_heart__test, menu);
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
