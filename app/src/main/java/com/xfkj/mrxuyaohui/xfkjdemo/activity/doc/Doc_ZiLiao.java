package com.xfkj.mrxuyaohui.xfkjdemo.activity.doc;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.Doctor_myCenter;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.MyAppliction;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.XiuGai_Pass_Activity;
import com.xfkj.mrxuyaohui.xfkjdemo.bean.Doc_ZiLiaoBean;
import com.xfkj.mrxuyaohui.xfkjdemo.util.IpCofig;
import com.xfkj.mrxuyaohui.xfkjdemo.util.doc.Doc_Ziliao_jsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.register.Register_submit_JsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.time.DateTimePickDialogUtil;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Doc_ZiLiao extends Activity {

    private EditText name;//姓名
    private Spinner sex;
    private EditText desc;
    private EditText age;
    private EditText address;
    private EditText attending;
    private Spinner keshi_1;
    private Spinner keshi_2;
    private TextView phone;
    private Button xiugai;
    private CheckBox tuwen,dianhua,menzhen;
    private CheckBox qinggan,gongzuo,jiating,xueye,yingyouer,qingshaonian,zhongqingnian,zhonglaonian;

    private EditText birthday;
    private String initStartDateTime = "2015年9月3日 14:44"; // 初始化开始时间
    private String urlPath= IpCofig.getIP_SERVER()+"MyheartDoctot/Docbyid?DocId=1";
    private List<Doc_ZiLiaoBean> persons;

    //数据提交
    //http://localhost:8080/MyheartDoctot/UpdateDocMessage?DocId=2&DocName=aaa&DocSex=%E7%94%B7&DocDate=2015-01-03&DocAge=11&DocPhone=11000000000&DocAddress=%E9%83%91%E5%B7%9E%E9%87%91%E6%B0%B4&Attending=dfag&DocMassage=%E4%BD%A0%E6%98%AF%E5%82%BB%E9%80%BC&DocServe=1%EF%BC%8C2&Db_secondId=2
    private String tijiao_urlpath=IpCofig.getIP_SERVER()+"MyheartDoctot/UpdateDocMessage?DocId=";


    private Button sub;

    private String[] items=new String[3] ;//医生的服务项
    private int[] nums=new int[items.length];

    //医生的分类
    private String[] fenlei=new String[8] ;//医生的服务项
    private int[] fenlei_nums=new int[fenlei.length];

    private String result_register;
    private Button title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc__zi_liao);

        tuwen=(CheckBox)findViewById(R.id.tuwen);
        dianhua=(CheckBox)findViewById(R.id.dianhua);
        menzhen=(CheckBox)findViewById(R.id.menzhen);

        qinggan=(CheckBox)findViewById(R.id.qinggan);
        gongzuo=(CheckBox)findViewById(R.id.gongzuo);
        jiating=(CheckBox)findViewById(R.id.jiating);
        xueye=(CheckBox)findViewById(R.id.xueye);
        yingyouer=(CheckBox)findViewById(R.id.yingyouer);
        qingshaonian=(CheckBox)findViewById(R.id.qingshaonian);
        zhongqingnian=(CheckBox)findViewById(R.id.zhongqingnian);
        zhonglaonian=(CheckBox)findViewById(R.id.zhonglaonian);

        sub=(Button)findViewById(R.id.sub);
        birthday=(EditText)findViewById(R.id.birthday);
        name=(EditText)findViewById(R.id.name);
        desc=(EditText)findViewById(R.id.desc);
        age=(EditText)findViewById(R.id.age);
        address=(EditText)findViewById(R.id.address);
        attending=(EditText)findViewById(R.id.attending);
        phone=(TextView)findViewById(R.id.phone);
        sex=(Spinner)findViewById(R.id.sex);
        xiugai=(Button)findViewById(R.id.xiugai);
        new Thread(new JsonThread()).start();
        title=(Button)findViewById(R.id.title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        birthday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        Doc_ZiLiao.this, initStartDateTime);
                dateTimePicKDialog.dateTimePicKDialog(birthday);
            }
        });
    }
    public void click(View v)
    {
        switch (v.getId())
        {
            case R.id.sub :
                if(check()) {
                    Toast.makeText(Doc_ZiLiao.this, "正在提交数据...", Toast.LENGTH_SHORT).show();
                    new Thread(new RegisterThread()).start();
                }
                break;
            case R.id.xiugai:
                Intent intent=new Intent();
                intent.setClass(Doc_ZiLiao.this, XiuGai_Pass_Activity.class);
                intent.putExtra("tape","doc");
                startActivity(intent);
                break;
        }
    }
    public boolean check()
    {
        if(name.getText().toString().length()==0||desc.getText().toString().length()==0||age.getText().toString().length()==0||address.getText().toString().length()==0||attending.getText().toString().length()==0||birthday.getText().toString().length()==0)
        {
            Toast.makeText(Doc_ZiLiao.this,"请补全您的信息！",Toast.LENGTH_SHORT).show();
        }else {
            return true;
        }
        return false;
    }
    //用户提交信息
    class RegisterThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            String tuwen_str=null;
            String dianhua_str=null;
            String menzhen_str=null;
            String qinggan_str=null;
            String gongzuo_str=null;
            String jiating_str=null;
            String xueyue_str=null;
            String yingyouer_str=null;
            String qingshaonian_str=null;
            String zhongqingnian_str=null;
            String zhonglaonian_str=null;

            String sexString=null;
            String name_space=name.getText().toString().trim();
            String address_str=address.getText().toString().trim();
            String attending_str=attending.getText().toString().trim();
            String docmessage_str=desc.getText().toString().trim();
            if(sex.getSelectedItemPosition()==0){
                sexString="男";
            }else {
                sexString="女";
            }


            //拼接字符串
            StringBuffer fuwu=new StringBuffer();
            //医生服务
            if(tuwen.isChecked())
            {
                if (fuwu.length()==0)
                {
                    fuwu.append(1);
                }else {
                    fuwu.append(",1");
                }
            }
            if(dianhua.isChecked())
            {
                if (fuwu.length()==0)
                {
                    fuwu.append(2);
                }else {
                    fuwu.append(",2");
                }
            }
            if(menzhen.isChecked())
            {
                if (fuwu.length()==0)
                {
                    fuwu.append(3);
                }else {
                    fuwu.append(",3");
                }
            }
            //拼接科室字符串
            StringBuffer keshi=new StringBuffer();

            //科室分类
            if(qinggan.isChecked())
            {
                if (keshi.length()==0)
                {
                    keshi.append(1);
                }else {
                    keshi.append(",1");
                }
            }
            if(gongzuo.isChecked())
            {
                if (keshi.length()==0)
                {
                    keshi.append(2);
                }else {
                    keshi.append(",2");
                }
            }
            if(jiating.isChecked())
            {
                if (keshi.length()==0)
                {
                    keshi.append(3);
                }else {
                    keshi.append(",3");
                }
            }
            if(xueye.isChecked())
            {
                if (keshi.length()==0)
                {
                    keshi.append(4);
                }else {
                    keshi.append(",4");
                }
            }
            if(yingyouer.isChecked())
            {
                if (keshi.length()==0)
                {
                    keshi.append(5);
                }else {
                    keshi.append(",5");
                }
            }
            if(qingshaonian.isChecked())
            {
                if (keshi.length()==0)
                {
                    keshi.append(6);
                }else {
                    keshi.append(",6");
                }
            }
            if(zhongqingnian.isChecked())
            {
                if (keshi.length()==0)
                {
                    keshi.append(7);
                }else {
                    keshi.append(",7");
                }
            }
            if(zhonglaonian.isChecked())
            {
                if (keshi.length()==0)
                {
                    keshi.append(8);
                }else {
                    keshi.append(",8");
                }
            }
            try{
                name_space = URLEncoder.encode(name_space, "utf-8") ;
                sexString = URLEncoder.encode(sexString, "utf-8") ;
                address_str = URLEncoder.encode(address_str, "utf-8") ;
                attending_str = URLEncoder.encode(attending_str, "utf-8") ;
                docmessage_str = URLEncoder.encode(docmessage_str, "utf-8") ;

            }catch (Exception e)
            {
                e.printStackTrace();
            }
            String newInfoList= Register_submit_JsonHelp.getListPerson(tijiao_urlpath +((MyAppliction)getApplication()).getDocId()+"&DocName="+name_space+"&DocSex="+sexString+"&DocDate="+birthday.getText().toString().replace(" ","%20")+"&DocAge="+age.getText().toString().trim()+"&DocAddress="+address_str+"&Attending="+attending_str+"&DocMassage="+docmessage_str+"&DocServe="+fuwu+"&Db_secondId="+keshi);
            Message msg = new Message();
            if(newInfoList != null) {
                msg.what = 2;
                msg.obj = newInfoList;
            } else {
                msg.what = 0;
            }
            shandler.sendMessage(msg);
        }
    }
    //获取医生信息
    class JsonThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            List<Doc_ZiLiaoBean> newInfoList=Doc_Ziliao_jsonHelp.getListPerson(urlPath);
            Message msg = new Message();
            if(newInfoList != null) {
                msg.what = 1;
                msg.obj = newInfoList;
            } else {
                msg.what = 0;
            }
            shandler.sendMessage(msg);
        }
    }
    Handler shandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    persons = (List<Doc_ZiLiaoBean>) msg.obj;
//                    private EditText name;//姓名
//                    private Spinner sex;
//                    private EditText desc;
//                    private EditText age;
//                    private EditText address;
//                    private EditText attending;
//                    private Spinner keshi_1;
//                    private Spinner keshi_2;
//                    private EditText phone;
//                    private Button xiugai;
                      name.setText(persons.get(0).getDocName());
                      if(persons.get(0).getDocSex().equals("男")) {
                          sex.setSelection(0);
                      }else {
                          sex.setSelection(1);
                      }
                    desc.setText(persons.get(0).getDocMassage());
                    age.setText(""+persons.get(0).getDocAge());
                    address.setText(persons.get(0).getDocAddress());
                    attending.setText(persons.get(0).getAttending());
                    birthday.setText(persons.get(0).getDocDate());
                    phone.setText(persons.get(0).getDocPhone());
                    //获取docServe
                    String docServe=persons.get(0).getDocServe();
                    items=docServe.split(",");
                    for(int i = 0;i<items.length;i++){
                        nums[i] = Integer.parseInt(items[i]);
                    }
                    for(int i = 0;i<nums.length;i++){
                        System.out.println("nums得到的东西为:"+nums[i]);
                    }
                    for(int i=0;i<nums.length;i++)
                    {
                        if(nums[i]==1)
                        {
                            tuwen.setChecked(true);
                        }
                        if(nums[i]==2)
                        {
                            dianhua.setChecked(true);
                        }
                        if(nums[i]==3)
                        {
                            menzhen.setChecked(true);
                        }
                    }
                    String db_secondId=persons.get(0).getDb_secondId();
                    System.out.print("得到的db_secondId为："+db_secondId);
                    fenlei=db_secondId.split(",");
                    for(int i = 0;i<fenlei.length;i++){
                        fenlei_nums[i] = Integer.parseInt(fenlei[i]);
                    }
                    for(int i=0;i<fenlei_nums.length;i++)
                    {
                        if(fenlei_nums[i]==1)
                        {
                            qinggan.setChecked(true);
                        }
                        if(fenlei_nums[i]==2)
                        {
                            gongzuo.setChecked(true);
                        }
                        if(fenlei_nums[i]==3)
                        {
                            jiating.setChecked(true);
                        }
                        if(fenlei_nums[i]==4)
                        {
                            xueye.setChecked(true);
                        }
                        if(fenlei_nums[i]==5)
                        {
                            yingyouer.setChecked(true);
                        }
                        if(fenlei_nums[i]==6)
                        {
                            qingshaonian.setChecked(true);
                        }
                        if(fenlei_nums[i]==7)
                        {
                            zhongqingnian.setChecked(true);
                        }
                        if(fenlei_nums[i]==8)
                        {
                            zhonglaonian.setChecked(true);
                        }
                    }
                    break;
                case 2:
                    result_register=(String)msg.obj;
                    System.out.println("获取到的是否提交成功为："+result_register);
                    if (result_register.equals("sucess"))
                    {
                        Toast.makeText(Doc_ZiLiao.this, "信息提交成功！", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Doc_ZiLiao.this,"信息提交失败！",Toast.LENGTH_SHORT).show();

                    }
            }
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doc__zi_liao, menu);
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
