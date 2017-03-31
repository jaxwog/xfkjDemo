package com.xfkj.mrxuyaohui.xfkjdemo.util.test;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Mr.xuyaohui on 2015/4/20.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private Context context;
    private static String DATABASENAME = "demo.db";
    private static int DATABASEVERSION = 1;
    SQLiteDatabase db = null;
    private static final String DROP_TABLE_TEST = "DROP TABLE IF EXISTS tb_mental_health";
    private static final String DROP_PRE_TEST = "DROP TABLE IF EXISTS tb_pre_test";
    public static String MENTAL_HEALTH_ID = "mid";
    public static String MENTAL_HEALTH_SUBJECT = "subject";
    public static String MENTAL_HEALTH_ANSWERASCORE = "answerAScore";
    public static String MENTAL_HEALTH_ANSWERBSCORE = "answerBScore";
    public static String MENTAL_HEALTH_ANSWERCSCORE = "answerCScore";
    public static String MENTAL_HEALTH_ANSWERDSCORE = "answerDScore";
    public static String MENTAL_HEALTH_ANSWERDA = "answerA";
    public static String MENTAL_HEALTH_ANSWERDB = "answerB";
    public static String MENTAL_HEALTH_ANSWERDC = "answerC";



    private static final String CREATE_TABLE_MENTAL_HEALTH = "create table IF NOT EXISTS tb_mental_health(mid integer primary key autoincrement,"
            + "subject varchar(200),answerAScore integer,"
            + "answerBScore integer,answerCScore integer,answerDScore integer)";
    private static final String CREATE_TABLE_ANXIETY_TEST = "create table IF NOT EXISTS tb_anxiety_test(mid integer primary key autoincrement,"
            + "subject varchar(200),answerAScore integer,"
            + "answerBScore integer,answerCScore integer,answerDScore integer)";
    private static final String CREATE_TABLE_PRE_TEST = "create table IF NOT EXISTS tb_pre_test(mid integer primary key autoincrement,"
            + "subject varchar(200),answerAScore integer,"
            + "answerBScore integer,answerCScore integer,answerA varchar(20),answerB varchar(20),answerC varchar(20))";
    private static final String CREATE_TABLE_JIAOLV_TEST = "create table IF NOT EXISTS tb_jiaolv_test(mid integer primary key autoincrement,"
            + "subject varchar(200),answerAScore integer,"
            + "answerBScore integer,answerCScore integer,answerDScore integer)";
    private static final String CREATE_TABLE_HEART_TEST = "create table IF NOT EXISTS tb_heart_test(mid integer primary key autoincrement,"
            + "subject varchar(200),answerAScore integer,"
            + "answerBScore integer,answerCScore integer)";
    private static final String DROP_HEART_TEST = "DROP TABLE IF EXISTS tb_heart_test";





    private static final String DROP_jiaolv_TEST = "DROP TABLE IF EXISTS tb_jiaolv_test";



    public DBOpenHelper(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MENTAL_HEALTH);
        db.execSQL(CREATE_TABLE_ANXIETY_TEST);
        db.execSQL(CREATE_TABLE_PRE_TEST);
        db.execSQL(CREATE_TABLE_JIAOLV_TEST);
        db.execSQL(CREATE_TABLE_HEART_TEST);
        System.out.println("创建了三张表！");
    }
    public DBOpenHelper(Context context, String name, int version) {
        super(context, name, null, version);
        this.context = context;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_TEST);
        db.execSQL(DROP_PRE_TEST);
        db.execSQL(DROP_jiaolv_TEST);
        db.execSQL(DROP_HEART_TEST);
    }
    //插入压力测试数据
    public static void insertAndUpdatePressuerData(DBOpenHelper myHelper){
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
    //心理健康自测
    public static void insertAndUpdateXinLiData(DBOpenHelper myHelper){
        //获取数据库对象
        SQLiteDatabase db = myHelper.getWritableDatabase();
        //使用execSQL方法向表中插入数据
        try {
            db.execSQL("INSERT INTO `tb_anxiety_test` VALUES ('1', '1.我觉得比平时容易紧张或着急','1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_anxiety_test` VALUES ('2', '2.我无缘无故在感到害怕','1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_anxiety_test` VALUES ('3', '3.我容易心里烦乱或感到惊恐 ', '1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_anxiety_test` VALUES ('4', '4.我觉得我可能将要发疯 ', '1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_anxiety_test` VALUES ('5', '5.我觉得一切都很好',  '4', '3', '2', '1')");
            db.execSQL("INSERT INTO `tb_anxiety_test` VALUES ('6', '6.我手脚发抖打颤 ','1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_anxiety_test` VALUES ('7', '7.我因为头疼、颈痛和背痛而苦恼   ','1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_anxiety_test` VALUES ('8', '8.我觉得容易衰弱和疲乏  ',  '1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_anxiety_test` VALUES ('9', '9.我觉得心平气和，并且容易安静坐着',  '4', '3', '2', '1')");
            db.execSQL("INSERT INTO `tb_anxiety_test` VALUES ('10', '10.我觉得心跳得很快',  '1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_anxiety_test` VALUES ('11', '11.我因为一阵阵头晕而苦恼',  '1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_anxiety_test` VALUES ('12', '12.我有晕倒发作，或觉得要晕倒似的 ',  '1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_anxiety_test` VALUES ('13', '13.我吸气呼气都感到很容易','4', '3', '2', '1')");
            db.execSQL("INSERT INTO `tb_anxiety_test` VALUES ('14', '14.我的手脚麻木和刺痛 ', '1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_anxiety_test` VALUES ('15', '15.我因为胃痛和消化不良而苦恼  ',  '1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_anxiety_test` VALUES ('16', '16.我常常要小便',  '1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_anxiety_test` VALUES ('17', '17.我的手脚常常是干燥温暖的', '4', '3', '2', '1')");
            db.execSQL("INSERT INTO `tb_anxiety_test` VALUES ('18', '18.我脸红发热    ', '1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_anxiety_test` VALUES ('19', '19.我容易入睡并且一夜睡得很好',  '4', '3', '2', '1')");
            db.execSQL("INSERT INTO `tb_anxiety_test` VALUES ('20', '20.我作恶梦', '1', '2', '3', '4')");
            db.close();
        } catch (SQLException e) {
            System.out.println("插入失败！");
        }
    }
    //抑郁指数自测数据库
    public static void insertAndUpdateData(DBOpenHelper myHelper){
        //获取数据库对象
        SQLiteDatabase db = myHelper.getWritableDatabase();
        //使用execSQL方法向表中插入数据
        try {
            db.execSQL("INSERT INTO `tb_mental_health` VALUES ('1', '1、平常感兴趣的事我现在仍然感兴趣：', '4', '3', '2', '1')");
            System.out.println("1插入成功！");
            db.execSQL("INSERT INTO `tb_mental_health` VALUES ('2', '2、我认为如果我死了，别人会生活得好些','1', '2', '3', '4')");
            System.out.println("2插入成功！");
            db.execSQL("INSERT INTO `tb_mental_health` VALUES ('3', '3、我的生活现在过得很有意思',  '4', '3', '2', '1')");
            db.execSQL("INSERT INTO `tb_mental_health` VALUES ('4', '4、我觉得自己是个有用的人，有人需要我', '4', '3', '2', '1')");
            db.execSQL("INSERT INTO `tb_mental_health` VALUES ('5', '5、我觉得做出决定是容易的',  '4', '3', '2', '1')");
            db.execSQL("INSERT INTO `tb_mental_health` VALUES ('6', '6、我比平常容易生气激动','1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_mental_health` VALUES ('7', '7、我对将来抱有希望','4', '3', '2', '1')");
            System.out.println("中间插入数据成功！");
            db.execSQL("INSERT INTO `tb_mental_health` VALUES ('8', '8、我觉得不安而平静不下来',  '1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_mental_health` VALUES ('9', '9、我觉得经常做的事情并没有困难',  '4', '3', '2', '1')");
            db.execSQL("INSERT INTO `tb_mental_health` VALUES ('10', '10、我的头脑和平常一样清楚',  '4', '3', '2', '1')");
            db.execSQL("INSERT INTO `tb_mental_health` VALUES ('11', '11、我无缘无故感到疲乏',  '1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_mental_health` VALUES ('12', '12、我心跳比平时快',  '1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_mental_health` VALUES ('13', '13、我有便秘的苦恼', '1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_mental_health` VALUES ('14', '14、我发觉我的体重在下降', '1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_mental_health` VALUES ('15', '15、我与异性密切接触和以往一样感到愉快',  '4', '3', '2', '1')");
            db.execSQL("INSERT INTO `tb_mental_health` VALUES ('16', '16、我吃的跟平时一样多',  '4', '3', '2', '1')");
            db.execSQL("INSERT INTO `tb_mental_health` VALUES ('17', '17、我晚上睡眠不好', '1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_mental_health` VALUES ('18', '18、我一阵阵地哭出来或觉得想哭', '1', '2', '3', '4')");
            db.execSQL("INSERT INTO `tb_mental_health` VALUES ('19', '19、我觉得一天之中早晨最好',  '4', '3', '2', '1')");
            db.execSQL("INSERT INTO `tb_mental_health` VALUES ('20', '20、我觉得闷闷不乐，情绪低沉', '1', '2', '3', '4')");

            System.out.println("3插入成功！");
            db.close();
        } catch (SQLException e) {
            Log.i("err", "insert failed");
            System.out.println("插入失败！");
        }
    }
    //心理健康自测
    public MentalHealth getHeartHealth(String offset) {
        MentalHealth health = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery(
                "select * from tb_heart_test limit 1 offset ? ",
                new String[] { offset }

        );
        if (cursor != null && cursor.moveToNext()) {
            health = new MentalHealth();
            health.setId(cursor.getInt(cursor.getColumnIndex(MENTAL_HEALTH_ID)));
            health.setSubject(cursor.getString(cursor
                    .getColumnIndex(MENTAL_HEALTH_SUBJECT)));
            health.setAnswerAScore(cursor.getInt(cursor
                    .getColumnIndex(MENTAL_HEALTH_ANSWERASCORE)));
            health.setAnswerBScore(cursor.getInt(cursor
                    .getColumnIndex(MENTAL_HEALTH_ANSWERBSCORE)));
            health.setAnswerCScore(cursor.getInt(cursor
                    .getColumnIndex(MENTAL_HEALTH_ANSWERCSCORE)));

            System.out.println("成功获取到数据："+health.getSubject());


        }
        if (cursor != null) {
            cursor.close();
        }
        return health;
    }

    //压力水平自测
    public Pressuer_Bean getPressuer_Bean(String offset) {
        Pressuer_Bean health = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery(
                "select * from tb_pre_test limit 1 offset ? ",
                new String[] { offset }

        );
        if (cursor != null && cursor.moveToNext()) {
            health = new Pressuer_Bean();
            health.setId(cursor.getInt(cursor.getColumnIndex(MENTAL_HEALTH_ID)));
            health.setSubject(cursor.getString(cursor
                    .getColumnIndex(MENTAL_HEALTH_SUBJECT)));
            health.setAnswerAScore(cursor.getInt(cursor
                    .getColumnIndex(MENTAL_HEALTH_ANSWERASCORE)));
            health.setAnswerBScore(cursor.getInt(cursor
                    .getColumnIndex(MENTAL_HEALTH_ANSWERBSCORE)));
            health.setAnswerCScore(cursor.getInt(cursor
                    .getColumnIndex(MENTAL_HEALTH_ANSWERCSCORE)));
            health.setAnswerA(cursor.getString(cursor
                    .getColumnIndex(MENTAL_HEALTH_ANSWERDA)));
            health.setAnswerB(cursor.getString(cursor
                    .getColumnIndex(MENTAL_HEALTH_ANSWERDB)));
            health.setAnswerC(cursor.getString(cursor
                    .getColumnIndex(MENTAL_HEALTH_ANSWERDC)));
            System.out.println("成功获取到数据："+cursor.getString(cursor
                    .getColumnIndex(MENTAL_HEALTH_ANSWERDC)));
            System.out.println("成功获取到数据："+cursor.getString(cursor
                    .getColumnIndex(MENTAL_HEALTH_ANSWERDB)));



        }
        if (cursor != null) {
            cursor.close();
        }
        return health;
    }
    //焦虑自测用的函数
    public MentalHealth getJiaoLvHealth(String offset) {
        MentalHealth health = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery(
                "select * from tb_jiaolv_test limit 1 offset ? ",
                new String[] { offset }

        );
        if (cursor != null && cursor.moveToNext()) {
            health = new MentalHealth();
            health.setId(cursor.getInt(cursor.getColumnIndex(MENTAL_HEALTH_ID)));
            health.setSubject(cursor.getString(cursor
                    .getColumnIndex(MENTAL_HEALTH_SUBJECT)));
            health.setAnswerAScore(cursor.getInt(cursor
                    .getColumnIndex(MENTAL_HEALTH_ANSWERASCORE)));
            health.setAnswerBScore(cursor.getInt(cursor
                    .getColumnIndex(MENTAL_HEALTH_ANSWERBSCORE)));
            health.setAnswerCScore(cursor.getInt(cursor
                    .getColumnIndex(MENTAL_HEALTH_ANSWERCSCORE)));
            health.setAnswerDScore(cursor.getInt(cursor
                    .getColumnIndex(MENTAL_HEALTH_ANSWERDSCORE)));

            System.out.println("成功获取到数据："+health.getSubject());


        }
        if (cursor != null) {
            cursor.close();
        }
        return health;
    }
    //抑郁指数自测
    public MentalHealth getMentalHealth(String offset) {
        MentalHealth health = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery(
                "select * from tb_mental_health limit 1 offset ? ",
                new String[] { offset }

        );
        if (cursor != null && cursor.moveToNext()) {
            health = new MentalHealth();
            health.setId(cursor.getInt(cursor.getColumnIndex(MENTAL_HEALTH_ID)));
            health.setSubject(cursor.getString(cursor
                    .getColumnIndex(MENTAL_HEALTH_SUBJECT)));
            health.setAnswerAScore(cursor.getInt(cursor
                    .getColumnIndex(MENTAL_HEALTH_ANSWERASCORE)));
            health.setAnswerBScore(cursor.getInt(cursor
                    .getColumnIndex(MENTAL_HEALTH_ANSWERBSCORE)));
            health.setAnswerCScore(cursor.getInt(cursor
                    .getColumnIndex(MENTAL_HEALTH_ANSWERCSCORE)));
            health.setAnswerDScore(cursor.getInt(cursor
                    .getColumnIndex(MENTAL_HEALTH_ANSWERDSCORE)));

            System.out.println("成功获取到数据："+health.getSubject());


        }
        if (cursor != null) {
            cursor.close();
        }
        return health;
    }

}
