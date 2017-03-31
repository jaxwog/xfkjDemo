package com.xfkj.mrxuyaohui.xfkjdemo.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.util.Collection_check_JsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.Insert_collection_jsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.IpCofig;
import com.xfkj.mrxuyaohui.xfkjdemo.util.consultion.Bean;
import com.xfkj.mrxuyaohui.xfkjdemo.util.consultion.JsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.image.SmartImage;
import com.xfkj.mrxuyaohui.xfkjdemo.util.image.SmartImageView;
import com.xfkj.mrxuyaohui.xfkjdemo.util.judge.Judge_Bean;
import com.xfkj.mrxuyaohui.xfkjdemo.util.judge.Judge_JsonHelp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Doctor_detail extends Activity {
    private ListView listView;
    private ListView news_list;
    private Button back_before;
    String[] str={"图文咨询","电话咨询","门诊预约"};
    String[] descsStrings={"通过文字，图片，语音进行咨询","选择时间与医生进行15分钟电话咨询","预约专家到医院挂号就诊"};
    int[] id={R.drawable.pic_ct,R.drawable.phone_ct,R.drawable.yuyue_ct};
    String[] cost={"0","50","200"};

    private static final String url_image= IpCofig.getIP_SERVER()+"MyheartDoctot/user_img/";
    private static final String url_pingjia= IpCofig.getIP_SERVER()+"MyheartDoctot/Commentbyid?DocId=";

    private static final  String url_collet_check= IpCofig.getIP_SERVER()+"MyheartDoctot/CheckAll?UserId=";
    private static String url_insert_collection= IpCofig.getIP_SERVER()+"MyheartDoctot/InsertCollects?UserId=";
    private static String url_delect_collection= IpCofig.getIP_SERVER()+"MyheartDoctot/DelCollects?UserId=";


    private SmartImageView image;//医生头像
    private List<Judge_Bean> persons;

    //收藏按钮实现
    private ImageButton collection;//收藏的实现
    private int docId;//医生的ID
    private String result;//用户是否收藏
    //医生服务项
    private String docServe;
    private String[] items=new String[3] ;//医生的服务项
    private int[] nums=new int[items.length];

    private boolean flag;//判断用户是否登录
    private int userId;//用户的ID
    private String attending_str;
    private String adddress_str;
    private String name_str;
    private String docMessage_str;

    private TextView name,message,work_postion,main_cute;



    private List<Integer> nus=new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_detail);
        name=(TextView)findViewById(R.id.name);
        message=(TextView)findViewById(R.id.message);
        work_postion=(TextView)findViewById(R.id.work_postion);
        main_cute=(TextView)findViewById(R.id.main_cute);

        final Intent intent=getIntent();
        //获取医生的头像和DocId
        String imageUrl=intent.getStringExtra("imageUrl");
        docId=intent.getIntExtra("docId",-1);
        docServe=intent.getStringExtra("docServe");
//        intent.putExtra("attending",persons.get(position).getAttending());
//        intent.putExtra("address",persons.get(position).getAddress());
//        intent.putExtra("name_str",persons.get(position).getName());
//        intent.putExtra("docMessage",persons.get(position).getMassage());
        adddress_str=intent.getStringExtra("address");
        attending_str=intent.getStringExtra("attending");
        name_str=intent.getStringExtra("name");
        docMessage_str=intent.getStringExtra("docMessage");
        System.out.println("docServe得到的东西是:"+docServe);

        System.out.println("name得到的东西是:"+name_str);
        name.setText(name_str);
        message.setText(docMessage_str);
        work_postion.setText(adddress_str);
        main_cute.setText("主治："+attending_str);
        items=docServe.split(",");
        for(int i = 0;i<items.length;i++){
            System.out.println("docServe得到的东西是:"+items[i]);
        }
        for(int i = 0;i<items.length;i++){
            nums[i] = Integer.parseInt(items[i]);
        }
        for(int i = 0;i<nums.length;i++){
            System.out.println("nums得到的东西为:"+nums[i]);
        }
        for(int i=0;i<nums.length;i++)
        {
            if(nums[i]!=0)
            {
                nus.add(nums[i]);
            }
        }
        for(int i=0;i<nus.size();i++)
        {
            System.out.println("dedao:"+nus.get(i));
        }
        new Thread(new JsonThread()).start();


        image=(SmartImageView)this.findViewById(R.id.image);
        image.setImageUrl(url_image+imageUrl);


        news_list=(ListView)this.findViewById(R.id.list_2);
        listView=(ListView)this.findViewById(R.id.list_3);
        back_before=(Button)findViewById(R.id.back_before);
        back_before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final List<Map<String, Object>> listitems =new ArrayList<Map<String,Object>>();
        for(int i=0;i<nus.size();i++)
        {
            Map<String, Object> listIteMap=new HashMap<String,Object>();
            listIteMap.put("image",id[nus.get(i)-1]);
            listIteMap.put("name", str[nus.get(i)-1]);
            listIteMap.put("desc", descsStrings[nus.get(i)-1]);
            listIteMap.put("cost",cost[nus.get(i)-1]);
            listitems.add(listIteMap);
        }
        SimpleAdapter simpleAdapter=new SimpleAdapter(this, listitems, R.layout.doctor_detail_fuwu_listview, new String[]{"image","name","desc","cost"}, new int[]{R.id.image,R.id.name,R.id.image_desc,R.id.cost});
        news_list.setBackgroundResource(R.drawable.login_button_selector);
        news_list.setAdapter(simpleAdapter);
        //选择的服务
        news_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    //进入聊天界面
                }else if(position==1){
                    //进入电话咨询界面或者是门诊预约界面，直接到支付界面
                    Intent intent_fuwu = new Intent();
                    intent_fuwu.setClass(Doctor_detail.this, PayActivity.class);
                    intent_fuwu.putExtra("fuwu", (String) listitems.get(position).get("name"));
                    intent_fuwu.putExtra("zhifu","PhoneCall");
                    intent_fuwu.putExtra("price", (String) listitems.get(position).get("cost"));
                    intent_fuwu.putExtra("Billtapeid",position+1);
                    intent_fuwu.putExtra("docId", docId);
                    intent_fuwu.putExtra("xiang",1);
                    System.out.print("得到的name为：" + (String) listitems.get(position).get("name") + "price为：" + (String) listitems.get(position).get("cost"));
                    startActivity(intent_fuwu);
                }else if(position==2){
                    Intent intent_fuwu = new Intent();
                    intent_fuwu.setClass(Doctor_detail.this, PayActivity.class);
                    intent_fuwu.putExtra("zhifu","Appointment");
                    intent_fuwu.putExtra("fuwu", (String) listitems.get(position).get("name"));
                    intent_fuwu.putExtra("price", (String) listitems.get(position).get("cost"));
                    intent_fuwu.putExtra("Billtapeid",position+1);
                    intent_fuwu.putExtra("docId", docId);
                    intent_fuwu.putExtra("xiang",2);
                    System.out.print("得到的name为：" + (String) listitems.get(position).get("name") + "price为：" + (String) listitems.get(position).get("cost"));
                    startActivity(intent_fuwu);
                }



            }
        });

        //收藏按钮的实现
        collection=(ImageButton)this.findViewById(R.id.souchang);

        flag=((MyAppliction)getApplication()).getFlag();//登录状态
        if (flag)
        {
            new Thread(new CollectionThread()).start();
        }
        userId=((MyAppliction)getApplication()).getUserId();//登录用户ID

    }

    //按钮监听事件
    public void click(View v)
    {
        switch (v.getId())
        {
            case R.id.souchang:
                if(flag)   //如果用户已经登录
                {
                    if(result.equals("sucess"))
                    {
                        new Thread(new delectCollectionThread()).start();
                    }else {
                        new Thread(new insertCollectionThread()).start();
                        Toast.makeText(Doctor_detail.this,"你收藏了该医生！",Toast.LENGTH_SHORT).show();
                        collection.setImageDrawable(getResources().getDrawable(R.drawable.cancel_collection));
                    }
                }else {
                    Toast.makeText(Doctor_detail.this,"你还未登录，请先登录！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    //撤销收藏
    class delectCollectionThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            String result= Insert_collection_jsonHelp.getListPerson(url_delect_collection+userId+"&DocId="+docId);
            Message msg = new Message();
            if(result != null) {
                msg.what = 4;
                msg.obj = result;
            } else {
                msg.what = 0;
            }
            handler.sendMessage(msg);
        }
    }
    //用户插入收藏
    class insertCollectionThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            String result= Insert_collection_jsonHelp.getListPerson(url_insert_collection+userId+"&DocId="+docId);
            Message msg = new Message();
            if(result != null) {
                msg.what = 3;
                msg.obj = result;
            } else {
                msg.what = 0;
            }
            handler.sendMessage(msg);
        }
    }

    //获取用户的收藏状态
    class CollectionThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            String result= Collection_check_JsonHelp.getListPerson(url_collet_check+userId+"&DocId="+docId);
            Message msg = new Message();
            if(result != null) {
                msg.what = 2;
                msg.obj = result;
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
            List<Judge_Bean> newInfoList = Judge_JsonHelp.getListPerson(url_pingjia+docId);
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
//                            Intent intent=new Intent();
//                            intent.setClass(Doctor_detail.this,PayActivity.class);
//                            intent.putExtra("name","");
//                            intent.putExtra("docId",persons.get(position).getDocId());
//                            intent.putExtra("imageUrl",persons.get(position).getImageUrl());
//                            startActivity(intent);

                        }
                    });
                    break;
                case 2:
                    result=(String)msg.obj;
                    if(result.equals("sucess")){
                        collection.setImageDrawable(getResources().getDrawable(R.drawable.cancel_collection));
                    }
                    break;
                case 3:
                    result=(String)msg.obj;
                    if(result.equals("sucess")){

                        Toast.makeText(Doctor_detail.this,"你正在收藏该医生...",Toast.LENGTH_SHORT).show();
                        collection.setImageDrawable(getResources().getDrawable(R.drawable.cancel_collection));
                    }else {
                        Toast.makeText(Doctor_detail.this,"收藏失败！",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 4:
                    result=(String)msg.obj;
                    if(result.equals("sucess")){

                        Toast.makeText(Doctor_detail.this,"你取消了收藏该医生...",Toast.LENGTH_SHORT).show();
                        collection.setImageDrawable(getResources().getDrawable(R.drawable.souchang));
                    }else {
                        Toast.makeText(Doctor_detail.this,"取消收藏失败！",Toast.LENGTH_SHORT).show();
                    }
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

            ratingBar.setRating(newInfo.getCommentScore());
            System.out.println("得到的星星的个数："+newInfo.getCommentScore());
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
        getMenuInflater().inflate(R.menu.menu_doctor_detail, menu);
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
