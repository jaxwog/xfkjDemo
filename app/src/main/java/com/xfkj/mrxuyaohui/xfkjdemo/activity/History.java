package com.xfkj.mrxuyaohui.xfkjdemo.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.MainActivity;
import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.bean.Peo_history_Bean;
import com.xfkj.mrxuyaohui.xfkjdemo.util.IpCofig;
import com.xfkj.mrxuyaohui.xfkjdemo.util.Peo_histoty_jsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.consultion.Bean;
import com.xfkj.mrxuyaohui.xfkjdemo.util.consultion.JsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.image.SmartImageView;

import org.w3c.dom.Text;

import java.util.List;

public class History extends TabActivity {
    public TabHost mth;
    public static final String TAB_HOME = "电话咨询";
    public static final String TAB_NEWS = "图文咨询";
    public static final String TAB_ABOUT = "门诊预约";
    public RadioGroup radioGroup;

    //返回按钮
    private Button backButton;
    //listview设置
    private ListView listView;//从服务器端获取数据
    private TextView empty;//为空时的显示
    private List<Peo_history_Bean> persons;
    public static final String urlPath= IpCofig.getIP_SERVER()+"MyheartDoctot/billsbyserveid?tape=user&userId=";
    private int userId;
    private int Billtapeid;

    private String content="电话咨询";//填充的评论东西
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peo_history);
        userId=((MyAppliction)getApplication()).getUserId();//登录用户ID

        //返回按钮
        backButton=(Button)this.findViewById(R.id.tv_wb);


        //listview的设置
        listView=(ListView)this.findViewById(R.id.list);
        empty=(TextView)this.findViewById(R.id.empty);
        listView.setEmptyView(empty);

        init();
        // 底部菜单点击事件
        clickevent();
    }
    //按钮监听事件
    public void click(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_wb:
                finish();
                break;
        }
    }

    private void clickevent() {
        this.radioGroup = (RadioGroup) findViewById(R.id.main_radio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 根据点击的按钮跳转到相应的界面
                switch (checkedId) {
                    case R.id.radio_button0:
                        mth.setCurrentTabByTag(TAB_HOME);
                        Billtapeid=2;
                        content="电话咨询";
                        new Thread(new JsonThread()).start();
                        break;
                    case R.id.radio_button1:
                        mth.setCurrentTabByTag(TAB_NEWS);
                        Billtapeid=1;
                        new Thread(new JsonThread()).start();
                        break;
                    case R.id.radio_button2:
                        mth.setCurrentTabByTag(TAB_ABOUT);
                        Billtapeid=3;
                        content="门诊预约";
                        new Thread(new JsonThread()).start();
                        break;


                }
            }
        });
    }
    private void init()
    {
        // 实例化TabHost
        mth = this.getTabHost();
        TabHost.TabSpec ts1 = mth.newTabSpec(TAB_HOME).setIndicator(TAB_HOME);
        ts1.setContent(R.id.title);
        mth.addTab(ts1);// 往TabHost中第一个底部菜单添加界面
        TabHost.TabSpec ts2 = mth.newTabSpec(TAB_NEWS).setIndicator(TAB_NEWS);
        ts2.setContent(R.id.title);
        mth.addTab(ts2);
        TabHost.TabSpec ts3 = mth.newTabSpec(TAB_ABOUT).setIndicator(TAB_ABOUT);
        ts3.setContent(R.id.title);
        mth.addTab(ts3);
        mth.setCurrentTabByTag(TAB_NEWS);
        Billtapeid=1;
        new Thread(new JsonThread()).start();
    }

    //动态获取数据
    class JsonThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            List<Peo_history_Bean> newInfoList = Peo_histoty_jsonHelp.getListPerson(urlPath+userId+"&Billtapeid="+Billtapeid);
            Message msg = new Message();
            if(newInfoList != null) {
                msg.what = 1;
                msg.obj = newInfoList;
            } else {
                msg.what = 0;
            }
            handler.sendMessage(msg);
        }
    }
     Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
                    persons = (List<Peo_history_Bean>) msg.obj;
                    MyAdapter adapte=new MyAdapter();
                    listView.setAdapter(adapte);
//                    //设置监听事件
//                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            Intent intent=new Intent();
//                            Toast.makeText(History.this,"你点击了"+position,Toast.LENGTH_SHORT).show();
//                            intent.setClass(History.this,PingJiaActivity.class);
//                            intent.putExtra("docId",persons.get(position).getDocId());
//                            startActivity(intent);
//
//                        }
//                    });
                    break;
            }
        }
    };



    class MyAdapter extends BaseAdapter {

        /**
         * 返回列表的总长度
         */
        @Override
        public int getCount() {
            return persons.size();
        }

        /**
         * 返回一个列表的子条目的布局
         */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = null;

            if(convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.peo_history_listview, null);
                System.out.print("没有出现异常。。。");
            } else {
                view = convertView;
                System.out.print("出现异常。。。");

            }
            TextView name=(TextView)view.findViewById(R.id.name);
            TextView time=(TextView)view.findViewById(R.id.time);
            Button pingjia=(Button)view.findViewById(R.id.detail);
            pingjia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent();
                    intent.setClass(History.this,PingJiaActivity.class);
                    intent.putExtra("docId",persons.get(position).getDocId());
                    startActivity(intent);
                }
            });

            Peo_history_Bean newInfo = persons.get(position);
            time.setText(newInfo.getBillTime());
            name.setText("对"+persons.get(position).getDocName()+content);
            System.out.println("历史记录中得到的docID"+persons.get(position).getDocId());
            return view;
        }


        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
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
