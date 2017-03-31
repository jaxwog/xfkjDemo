package com.xfkj.mrxuyaohui.xfkjdemo.activity.doc;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.MyAppliction;
import com.xfkj.mrxuyaohui.xfkjdemo.bean.Doc_ZiLiaoBean;
import com.xfkj.mrxuyaohui.xfkjdemo.util.IpCofig;
import com.xfkj.mrxuyaohui.xfkjdemo.util.doc.Doc_Ziliao_jsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.judge.Judge_Bean;
import com.xfkj.mrxuyaohui.xfkjdemo.util.judge.Judge_JsonHelp;

import java.util.List;

public class Doc_Pingjia extends Activity {


    private  String url_pingjia= IpCofig.getIP_SERVER()+"MyheartDoctot/Commentbyid?DocId=";
    private String urlPath= IpCofig.getIP_SERVER()+"MyheartDoctot/Docbyid?DocId=";

    private List<Judge_Bean> persons;
    private ListView listView;

    private List<Doc_ZiLiaoBean> ziliao;
    private RatingBar ratingBar;

    private Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc__pingjia);
        listView=(ListView)findViewById(R.id.content);
        ratingBar=(RatingBar)findViewById(R.id.rating);
        back=(Button)findViewById(R.id.back_before);
        new Thread(new JsonThread()).start();
        new Thread(new DocThread()).start();

    }
    public void click(View  v)
    {
        switch (v.getId())
        {
            case R.id.back_before:
                finish();
                break;
        }
    }
    //获取医生信息
    class DocThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            List<Doc_ZiLiaoBean> newInfoList= Doc_Ziliao_jsonHelp.getListPerson(urlPath+ ((MyAppliction)getApplication()).getDocId());
            Message msg = new Message();
            if(newInfoList != null) {
                msg.what = 2;
                msg.obj = newInfoList;
            } else {
                msg.what = 0;
            }
            handler.sendMessage(msg);
        }
    }
    //获取用户的评价信息
    class JsonThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            List<Judge_Bean> newInfoList = Judge_JsonHelp.getListPerson(url_pingjia+((MyAppliction)getApplication()).getDocId());
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
                    //得到的评论
                    persons = (List<Judge_Bean>) msg.obj;
                    MyAdapter adapter = new MyAdapter();
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    });
                    break;
                case 2:
                    ziliao = (List<Doc_ZiLiaoBean>) msg.obj;
                    String scoreTotal=ziliao.get(0).getDocScore();
                    String scoreTime=ziliao.get(0).getDocSoreTime();
                    System.out.println("得到的总分为："+scoreTotal+"评价的次数为："+scoreTime);
                    ratingBar.setRating(Integer.parseInt(scoreTotal)/Integer.parseInt(scoreTime));
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
                view = inflater.inflate(R.layout.doctor_detail_pingjia_listview, null);
                System.out.print("没有出现异常。。。");
            } else {
                view = convertView;
                System.out.print("出现异常。。。");

            }

            // 重新赋值, 不会产生缓存对象中原有数据保留的现象
            TextView name=(TextView)view.findViewById(R.id.phoneNum);
            TextView desc=(TextView)view.findViewById(R.id.desc);
            RatingBar ratingBar=(RatingBar)view.findViewById(R.id.rating);

            Judge_Bean newInfo = persons.get(position);
            //将得到的用户的手机号中间几位进行掩盖
            String phoneNum=newInfo.getUserPhone();
            for(int i=3;i<=7;i++)
            {
                phoneNum=phoneNum.replaceFirst(""+phoneNum.charAt(i),"*");
            }
            name.setText(phoneNum);
            desc.setText(newInfo.getCommentMessage());
//            ratingBar.setProgress(newInfo.getCommentScore());
            ratingBar.setRating(newInfo.getCommentScore());
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
        getMenuInflater().inflate(R.menu.menu_doc__pingjia, menu);
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
