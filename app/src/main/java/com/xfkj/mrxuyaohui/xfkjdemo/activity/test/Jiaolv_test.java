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
import android.widget.ImageView;
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

public class Jiaolv_test extends Activity {
    private TextView title;//标题

    //设置进度条
    private CircleProgressBar progressBar;//进度条
    private int status=5;//初始值


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
    private int total=20;//定义总的题目数
    private int pageNo=1;//定义当前页

    SQLiteDatabase db = null;

    private Button next;
    private boolean flag=false;



    private static final String CREATE_TABLE_JIAOLV_TEST = "create table IF NOT EXISTS tb_jiaolv_test(mid integer primary key autoincrement,"
            + "subject varchar(200),answerAScore integer,"
            + "answerBScore integer,answerCScore integer,answerDScore integer)";
    private static final String DROP_PRE_TEST = "DROP TABLE IF EXISTS tb_jiaolv_test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.depression_test);
        title=(TextView)this.findViewById(R.id.title);
        title.setText("焦虑水平自测");
        progressBar=(CircleProgressBar)this.findViewById(R.id.pb);
        progressBar.setProgress(status);

        question=(TextView)this.findViewById(R.id.desc);
        answerGroup = (RadioGroup) findViewById(R.id.answerGroup);
        answerGroup.setOnCheckedChangeListener(new AnswerGroupChangeListener());
        answerABtn = (RadioButton) findViewById(R.id.answerA);
        answerBBtn = (RadioButton) findViewById(R.id.answerB);
        answerCBtn = (RadioButton) findViewById(R.id.answerC);
        answerDBtn = (RadioButton) findViewById(R.id.answerD);

        next=(Button)this.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answerABtn.isChecked()||answerBBtn.isChecked()||answerCBtn.isChecked()||answerDBtn.isChecked()) {
                    if (status <= 100) {
                        status += 5;
                        progressBar.setProgress(status);
                    } else {
                        progressBar.setProgress(100);
                    }
                    nextTest();
                }else {
                    Toast.makeText(Jiaolv_test.this, "请先选择答案！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        scoreMap.put("score",0);
        dbHelper=new DBOpenHelper(this,"demo.db",1);
        db = dbHelper.getWritableDatabase();
        db.execSQL(DROP_PRE_TEST);
        db.execSQL(CREATE_TABLE_JIAOLV_TEST);
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
            db.execSQL("INSERT INTO `tb_jiaolv_test` VALUES ('1', '1、我觉得比平常容易紧张和着急','1', '2', '3','4')");
            db.execSQL("INSERT INTO `tb_jiaolv_test` VALUES ('2', '2、我无缘无故感到害怕','1', '2', '3','4')");
            db.execSQL("INSERT INTO `tb_jiaolv_test` VALUES ('3', '3、我容易心烦意乱或感到惊恐 ','1', '2', '3','4')");
            db.execSQL("INSERT INTO `tb_jiaolv_test` VALUES ('4', '4、我觉得我可能将要发疯 ','1', '2', '3','4')");
            db.execSQL("INSERT INTO `tb_jiaolv_test` VALUES ('5', '5、我感到事事都很顺利，不会有倒霉的事情发生', '4','3','2','1')");
            db.execSQL("INSERT INTO `tb_jiaolv_test` VALUES ('6', '6、我手脚发抖打颤 ','1', '2', '3','4')");
            db.execSQL("INSERT INTO `tb_jiaolv_test` VALUES ('7', '7、我因头痛、颈痛和背痛而烦恼 ','1', '2', '3','4')");
            db.execSQL("INSERT INTO `tb_jiaolv_test` VALUES ('8', '8、我感到无力而且容易疲劳', '1', '2', '3','4')");
            db.execSQL("INSERT INTO `tb_jiaolv_test` VALUES ('9', '9、我感到平静，能安静坐下来', '4','3','2','1')");
            db.execSQL("INSERT INTO `tb_jiaolv_test` VALUES ('10', '10、我感到我的心跳得很快','1', '2', '3','4')");
            db.execSQL("INSERT INTO `tb_jiaolv_test` VALUES ('11', '11、我因阵阵的眩晕而不舒服','1', '2', '3','4')");
            db.execSQL("INSERT INTO `tb_jiaolv_test` VALUES ('12', '12、我有阵阵要晕倒的感觉 ', '1', '2', '3','4')");
            db.execSQL("INSERT INTO `tb_jiaolv_test` VALUES ('13', '13、我呼吸时进气和出气都不费力', '4','3','2','1')");
            db.execSQL("INSERT INTO `tb_jiaolv_test` VALUES ('14', '14、我的手指和脚趾感到麻木和刺痛', '1', '2', '3','4')");
            db.execSQL("INSERT INTO `tb_jiaolv_test` VALUES ('15', '15、我因胃痛和消化不良而苦恼','1', '2', '3','4')");
            db.execSQL("INSERT INTO `tb_jiaolv_test` VALUES ('16', '16、我常常要小便 ', '1', '2', '3','4')");
            db.execSQL("INSERT INTO `tb_jiaolv_test` VALUES ('17', '17、我的手脚常常是干燥温暖的', '4','3','2','1')");
            db.execSQL("INSERT INTO `tb_jiaolv_test` VALUES ('18', '18、我觉得脸红发热', '1', '2', '3','4')");
            db.execSQL("INSERT INTO `tb_jiaolv_test` VALUES ('19', '19、我容易入睡，晚上休息很好', '4','3','2','1')");
            db.execSQL("INSERT INTO `tb_jiaolv_test` VALUES ('20', '20、我做恶梦','1', '2', '3','4')");
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
            next.setText("完成");
            System.out.print("你的分数是："+scoreMap.get("score"));
            Intent intent =new Intent();
            intent.setClass(this,JiaoLv_test_detail.class);
            double haha=scoreMap.get("score")*1.25;
            int wuwu=(int)haha;
            intent.putExtra("score","你的分数是："+wuwu);
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
        mHealth = dbHelper.getJiaoLvHealth(String.valueOf((pageNo - 1)));
        if (mHealth == null) {
            mHealth = new MentalHealth();
        }
    }
    public void initAll() {
        question.setText( mHealth.getSubject());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jiaolv_test, menu);
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
