package com.xfkj.mrxuyaohui.xfkjdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.util.IpCofig;
import com.xfkj.mrxuyaohui.xfkjdemo.util.Peo_Collects_All_jsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.consultion.Bean;
import com.xfkj.mrxuyaohui.xfkjdemo.util.consultion.JsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.image.SmartImageView;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Collection extends Activity {
    private ListView listView;
    private TextView empty;
    private static final String urlPath = IpCofig.getIP_SERVER()+"MyheartDoctot/CollectsbyidAction?UserId=";
    private List<Bean> persons;
    private static final String url_image=IpCofig.getIP_SERVER()+"MyheartDoctot/user_img/";

    private boolean flag;//判断用户是否登录
    private int userId;//用户的ID

    private ImageButton backbut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peo_collection);
        listView=(ListView)this.findViewById(R.id.list);
        empty=(TextView)this.findViewById(R.id.list_empty);
        listView.setEmptyView(empty);

        backbut=(ImageButton)this.findViewById(R.id.backbut);
        backbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        flag=((MyAppliction)getApplication()).getFlag();
        System.out.println("得到的flag为："+flag);
        if(flag) {
            System.out.println("开启线程");
            new Thread(new JsonThread()).start();
            userId= ((MyAppliction)getApplication()).getUserId();
            System.out.println("收藏中的userID为："+ userId);
        }

    }
    class JsonThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            userId=((MyAppliction)getApplication()).getUserId();
            System.out.println("得到的UserId为："+userId);
            List<Bean> newInfoList = Peo_Collects_All_jsonHelp.getListPerson(urlPath + userId);
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
                    persons = (List<Bean>) msg.obj;
                    MyAdapter adapter = new MyAdapter();
                    listView.setAdapter(adapter);
                    //设置监听事件
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent=new Intent();
                            intent.setClass(Collection.this,Doctor_detail.class);
                            intent.putExtra("docId",persons.get(position).getDocId());
                            intent.putExtra("imageUrl",persons.get(position).getImageUrl());
                            intent.putExtra("docServe",persons.get(position).getDocServe());
                            intent.putExtra("attending",persons.get(position).getAttending());
                            intent.putExtra("address",persons.get(position).getAddress());
                            intent.putExtra("name",persons.get(position).getName());
                            intent.putExtra("docMessage",persons.get(position).getMassage());
                            startActivity(intent);

                        }
                    });
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
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;

            if(convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.doctor_consultation_listview, null);
                System.out.print("没有出现异常。。。");
            } else {
                view = convertView;
                System.out.print("出现异常。。。");

            }

            // 重新赋值, 不会产生缓存对象中原有数据保留的现象
            SmartImageView sivIcon = (SmartImageView) view.findViewById(R.id.tupian);
            TextView name = (TextView) view.findViewById(R.id.name);
            TextView message = (TextView) view.findViewById(R.id.zhiwu);
            TextView address = (TextView) view.findViewById(R.id.gongzuo);
            TextView attending = (TextView) view.findViewById(R.id.zhuzhi);
            RatingBar ratingBar=(RatingBar)view.findViewById(R.id.rating);

            Bean newInfo = persons.get(position);
            String scoreTotal=newInfo.getScore();
            String scoreTime=newInfo.getScoreTime();
//            ratingBar.setNumStars((int)scoreTotal/scoreTime);
            ratingBar.setRating(Integer.parseInt(scoreTotal)/Integer.parseInt(scoreTime));
            sivIcon.setImageUrl(url_image+newInfo.getImageUrl());		// 设置图片
            name.setText(newInfo.getName());
            address.setText(newInfo.getAddress());
            message.setText(newInfo.getMassage());
            attending.setText(newInfo.getAttending());
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
        getMenuInflater().inflate(R.menu.menu_collection, menu);
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
