package com.xfkj.mrxuyaohui.xfkjdemo.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.util.IpCofig;
import com.xfkj.mrxuyaohui.xfkjdemo.util.consultion.Bean;
import com.xfkj.mrxuyaohui.xfkjdemo.util.consultion.JsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.image.SmartImageView;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class Quick_phone extends Activity {
    //定义返回的listview
    private ListView listView;
    private TextView nullList;

    private static final String urlPath = IpCofig.getIP_SERVER()+"MyheartDoctot/DocActionAll?DocServe=2";
    private static final String TAG = "MainActivity";
    private static final String url_image=IpCofig.getIP_SERVER()+"MyheartDoctot/user_img/";
    private List<Bean> persons;
    //定义返回按钮
    private Button backButton;
    private Button btnSearch;

    private EditText etSearch;
    private ImageButton ivDeleteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_consultation);
        listView=(ListView)this.findViewById(R.id.list);
        nullList=(TextView)this.findViewById(R.id.nullEmp);
        listView.setEmptyView(nullList);

        backButton=(Button)this.findViewById(R.id.back_before);
        backButton.setText("电话咨询");
        new Thread(new  JsonThread() ).start();

        //搜索按钮
        etSearch=(EditText)this.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ivDeleteText.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Message msg = new Message();
                msg.what =2;
                Bundle data = new Bundle();
                data.putString("value", s.toString());
                msg.setData(data);
                handler.sendMessage(msg);

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

    }
    //点击按钮效果
    public void click(View v)
    {
        switch (v.getId())
        {
            case R.id.back_before:
                finish();
                break;
        }
    }
    class JsonThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            List<Bean> newInfoList = JsonHelp.getListPerson(urlPath);
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
                    System.out.println("已经走到这里了。。。。");
                    System.out.println("得到的数据是："+persons.get(0).getName());
                    MyAdapter adapter = new MyAdapter();
                    listView.setAdapter(adapter);
                    //设置监听事件
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent=new Intent();
                            intent.setClass(Quick_phone.this,Doctor_detail.class);

//                           intent.putExtra("name",persons.get(position).getName());
                            intent.putExtra("docId",persons.get(position).getDocId());
                            intent.putExtra("imageUrl",persons.get(position).getImageUrl());
                            intent.putExtra("docServe",persons.get(position).getDocServe());
                            startActivity(intent);

                        }
                    });
                    break;
                case 2:
                    refreshListView(msg.getData().get("value").toString());
                    break;
            }
        }
    };
    private void refreshListView(String value) {
        if (value == null || value.trim().length() == 0){
//            adapter = new ArrayAdapter<String>(this,
//                    android.R.layout.simple_list_item_1, listValue);
//            listView.setAdapter(adapter);
            new Thread(new  JsonThread() ).start();
            MyAdapter adapter = new MyAdapter();
            listView.setAdapter(adapter);
        }
        for (int i=0;i<persons.size();i++) {
            if (persons.get(i).getName().indexOf(value) < 0) {
                persons.remove(i);
            }
        }
        if (persons.size() == 0)
            return;
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.invalidateViews();
    }


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
        getMenuInflater().inflate(R.menu.menu_quick_phone, menu);
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
