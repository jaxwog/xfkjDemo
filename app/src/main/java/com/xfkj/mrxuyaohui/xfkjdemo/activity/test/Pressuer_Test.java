package com.xfkj.mrxuyaohui.xfkjdemo.activity.test;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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

public class Pressuer_Test extends Activity {
    private TextView title;//标题

    //设置进度条
    private CircleProgressBar progressBar;//进度条
    private int status=4;//初始值


    private DBOpenHelper dbHelper ;
    private Pressuer_Bean mHealth;
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
    private int total=25;//定义总的题目数
    private int pageNo=1;//定义当前页

    SQLiteDatabase db = null;

    private Button next;
    private boolean flag=false;



    private static final String CREATE_TABLE_PRE_TEST = "create table IF NOT EXISTS tb_pre_test(mid integer primary key autoincrement,"
            + "subject varchar(200),answerAScore integer,"
            + "answerBScore integer,answerCScore integer,answerA varchar(20),answerB varchar(20),answerC varchar(20))";
    private static final String DROP_PRE_TEST = "DROP TABLE IF EXISTS tb_pre_test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.depression_test);
        title=(TextView)this.findViewById(R.id.title);
        title.setText("压力水平自测");
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
                        status += 4;
                        progressBar.setProgress(status);
                    } else {
                        progressBar.setProgress(100);
                    }
                    nextTest();
                }else {
                    Toast.makeText(Pressuer_Test.this, "请先选择答案！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        scoreMap.put("score",0);
        dbHelper=new DBOpenHelper(this,"demo.db",1);
        db = dbHelper.getWritableDatabase();
        db.execSQL(DROP_PRE_TEST);
        db.execSQL(CREATE_TABLE_PRE_TEST);
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
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('1', '1、 觉得手上工作太多，无法应对','0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('2', '2、 觉得时间不够，所以要分秒必争。例如过马路时闯灯，走路和说话的节奏很快速。','0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('3', '3、 觉得没有时间消遣，终日记挂着工作 ', '0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('4', '4、 遇到挫败时很容易发脾气','0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('5', '5、 担心别人对自己工作表现的评价', '0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('6', '6、 觉得上司和家人都不欣赏自己','0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('7', '7、 担心自己的经济状况','0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('8', '8、 有头痛、胃痛、背痛的毛病，难于治愈', '0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('9', '9、 需要借烟酒、药物、零食等抑制不安的情绪', '0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('10', '10、 需要借助安眠药去协助入睡', '0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('11', '11、 与家人、朋友、同事的相处令你发脾气', '0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('12', '12、 与人倾谈时，打断对方的话题 ', '0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('13', '13、 上床后觉得思潮起伏，很多事情牵挂，难以入睡','0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('14', '14、 太多工作，不能每件事做到尽善尽美', '0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('15', '15、 当空闲时轻松一下也会觉得内疚', '0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('16', '16、 做事急躁任性，而事后感到内疚', '0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('17', '17、 觉得自己不应该享乐', '0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('18', '18、 感觉比以前更悲观 ', '0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('19', '19、 周末睡懒觉会产生负罪感', '0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('20', '20、 与同事、家人、朋友的冲突没有及时解决','0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('21', '21、 责备自己，觉得自己不够好','0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('22', '22、 对工作或家庭生活压卷','0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('23', '23、 觉得自己的能力或表现没有被正确评价 ', '0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('24', '24、 你怀疑自己是否走在人生目标的正确轨道上', '0', '1', '2','从未发生','偶尔发生','经常发生')");
            db.execSQL("INSERT INTO `tb_pre_test` VALUES ('25', '25、 每天工作完成后，对自己的成绩不满意', '0', '1', '2','从未发生','偶尔发生','经常发生')");
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
            System.out.print("你的分数是："+scoreMap.get("score"));
            Intent intent =new Intent();
            intent.setClass(this,Pressure_test_detail.class);
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
        mHealth = dbHelper.getPressuer_Bean(String.valueOf((pageNo - 1)));
        if (mHealth == null) {
            mHealth = new Pressuer_Bean();
        }
    }
    public void initAll() {
        question.setText( mHealth.getSubject());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pressuer__test, menu);
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
