package com.xfkj.mrxuyaohui.xfkjdemo;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.activity.Doctor_detail;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.KeShiActivity;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.MyAppliction;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.MyCenter;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.News_detail;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.News_main;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.Quick_phone;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.YiShengZiXun;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.ZhenDuan;
import com.xfkj.mrxuyaohui.xfkjdemo.service.PushSmsService;
import com.xfkj.mrxuyaohui.xfkjdemo.util.IpCofig;
import com.xfkj.mrxuyaohui.xfkjdemo.util.image.SmartImageView;
import com.xfkj.mrxuyaohui.xfkjdemo.util.news.MainNews_JsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.news.News;
import com.xfkj.mrxuyaohui.xfkjdemo.util.news.News_JsonHelp;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private Button zixun=null;//医生咨询按钮
    private Button phone=null;//快捷电话按钮
    private Button zhendun=null;//诊断按钮
    private Button mycenter=null;//个人中心按钮
    private Button news=null;//新闻按钮
    private Button yuyue=null;//预约按钮
    private Button qinggan=null;//情感按钮
    private Button work=null;//工作按钮
    private Button home=null;//家庭按钮
    private Button xueye=null;//学业按钮
    private Button yinger=null;//婴儿按钮
    private Button young=null;//青少年按钮
    private Button middle=null;//中青年按钮
    private Button agedness=null;//老年按钮
    private TabHost tabHost;





//    //动态广告自动播放的实现
//    private ViewPager viewPager;
//    private LinearLayout pointGroup;
//    private TextView iamgeDesc;
//    private MyPagerAdapter myPagerAdapter;
//    //动态显示新闻
//    private  String[] imageIds=new String[3];
//    //文字说明
//    private String[] imageDescriptions=new String[3];
//    private ArrayList<SmartImageView> imageList;
//    protected int lastPosition;
//动态广告自动播放的实现
    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;
    private LinearLayout pointGroup;
    private TextView iamgeDesc;
    //获取图片
    private  String[] imageIds = new String[3];
    //文字说明
    private String[] imageDescriptions=new String[3];
    private ArrayList<SmartImageView> imageList;
    protected int lastPosition;

    private static final String urlPath = IpCofig.getIP_SERVER()+"MyheartDoctot/SelectAllnewsView";
    private static final String url_image=IpCofig.getIP_SERVER()+"MyheartDoctot/news_img/";
    private List<News> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_main);
        initPictureMove();
        zixun=(Button)this.findViewById(R.id.but_zixun);
        phone=(Button)this.findViewById(R.id.but_tel);
        yuyue=(Button)this.findViewById(R.id.but_yuyue);
        qinggan=(Button)this.findViewById(R.id.but_love);
        work=(Button)this.findViewById(R.id.but_work);
        home=(Button)this.findViewById(R.id.but_home);
        xueye=(Button)this.findViewById(R.id.but_xueye);
        yinger=(Button)this.findViewById(R.id.but_yinger);
        young=(Button)this.findViewById(R.id.but_young);
        middle=(Button)this.findViewById(R.id.but_middle);
        agedness=(Button)this.findViewById(R.id.but_agedness);
        new Thread(new JsonThread()).start();
        //推送判断
        if (true) {
            //启动监听
            Intent intent = new Intent(this, PushSmsService.class);
            // 启动服务
            startService(intent);
        }else {
            Intent intent = new Intent(this, PushSmsService.class);
            // 启动服务
            stopService(intent);
        }

    }
    //新闻列表的实现
    class JsonThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            List<News> newInfoList = MainNews_JsonHelp.getListPerson(urlPath);
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
                    persons = (List<News>) msg.obj;
                    for(int i=0;i<3;i++)
                    {
                        imageIds[i]=url_image+persons.get(i).getNewsImg();
                        imageDescriptions[i]=persons.get(i).getNewsTitle();
                    }
                    myPagerAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    //对于动态广告图片的实现
    public void initPictureMove()
    {
        myPagerAdapter=new MyPagerAdapter();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pointGroup = (LinearLayout) findViewById(R.id.point_group);
        iamgeDesc = (TextView) findViewById(R.id.image_desc);
        iamgeDesc.setText(imageDescriptions[0]);


        imageList = new ArrayList<SmartImageView>();
        for (int i = 0; i <imageIds.length; i++) {


            //初始化图片资源
            SmartImageView image = new SmartImageView(this);
//            image.setBackgroundResource(imageIds[i]);
            image.setImageUrl(imageIds[i]);
            imageList.add(image);

            //添加指示点
            ImageView point =new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.rightMargin = 20;
            point.setLayoutParams(params);

            point.setBackgroundResource(R.drawable.point_bg);
            if(i==0){
                point.setEnabled(true);
            }else{
                point.setEnabled(false);
            }
            pointGroup.addView(point);
        }

        viewPager.setAdapter(new MyPagerAdapter());

        viewPager.setCurrentItem(Integer.MAX_VALUE/2 - (Integer.MAX_VALUE/2%imageIds.length)) ;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            /**
             * 页面切换后调用
             * position  新的页面位置
             */
            public void onPageSelected(int position) {

                position = position%imageIds.length;

                //设置文字描述内容
                iamgeDesc.setText(imageDescriptions[position]);

                //改变指示点的状态
                //把当前点enbale 为true
                pointGroup.getChildAt(position).setEnabled(true);
                //把上一个点设为false
                pointGroup.getChildAt(lastPosition).setEnabled(false);
                lastPosition = position;

            }

            @Override
            /**
             * 页面正在滑动的时候，回调
             */
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            /**
             * 当页面状态发生变化的时候，回调
             */
            public void onPageScrollStateChanged(int state) {

            }
        });
        isRunning = true;
        handler.sendEmptyMessageDelayed(0, 2000);
    }
    private boolean isRunning = true;

    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {

            //让viewPager 滑动到下一页
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            if(isRunning){
                handler.sendEmptyMessageDelayed(0, 2000);
            }
        };
    };

    protected void onDestroy() {
        super.onDestroy();
        isRunning = false;
    };

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        /**
         * 获得页面的总数
         */
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        /**
         * 获得相应位置上的view
         * container  view的容器，其实就是viewpager自身
         * position 	相应的位置
         */
        public Object instantiateItem(ViewGroup container, final int position) {
            for (int i = 0; i < imageIds.length; i++) {
                SmartImageView image = new SmartImageView(MainActivity.this);
                image.setImageUrl(imageIds[i]);
                imageList.set(i,image);
            }
            // 给 container 添加一个view
            container.addView(imageList.get(position%imageList.size()));
            imageList.get(position%imageIds.length).setOnClickListener(new View.OnClickListener() {
                //用户点击图片时触发
                @Override
                public void onClick(View v) {
                    //跳转
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, News_detail.class);
                    intent.putExtra("newId", persons.get(position%imageList.size()).getNewsId());
                    startActivity(intent);
                }
            });
            //返回一个和该view相对的object
            return imageList.get(position%imageList.size());
        }

        @Override
        /**
         * 判断 view和object的对应关系
         */
        public boolean isViewFromObject(View view, Object object) {
            if(view == object){
                return true;
            }else{
                return false;
            }
        }

        @Override
        /**
         * 销毁对应位置上的object
         */
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            object = null;
        }
    }

    //对于按钮增加监听事件
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.but_zixun://转到医生咨询页面
                intent.setClass(MainActivity.this, YiShengZiXun.class);
                intent.putExtra("docid","1");
                startActivity(intent);
                break;
            case R.id.but_tel://转到快捷电话页面
                intent.setClass(MainActivity.this, Quick_phone.class);
                startActivity(intent);
                break;
//            case R.id.zhendun://转到诊断电话页面
//                intent.setClass(MainActivity.this, ZhenDuan.class);
//                startActivity(intent);
//                break;
//            case R.id.wode://转到个人中心
//                intent.setClass(MainActivity.this, MyCenter.class);
//                startActivity(intent);
//                break;
//            case R.id.xinwen://转到新闻
//                intent.setClass(MainActivity.this,News_main.class);
//                startActivity(intent);
//                break;
            case R.id.but_yuyue://转到预约
                intent.setClass(MainActivity.this,YiShengZiXun.class);
                intent.putExtra("docid","3");
                startActivity(intent);
                break;
            case R.id.but_love://转到情感
                intent.setClass(MainActivity.this,KeShiActivity.class);
                intent.putExtra("docid","3");

                startActivity(intent);
                break;
            case R.id.but_work://转到工作
                intent.setClass(MainActivity.this,KeShiActivity.class);
                intent.putExtra("docid","2");

                startActivity(intent);
                break;
            case R.id.but_home://转到家庭
                intent.setClass(MainActivity.this,KeShiActivity.class);
                startActivity(intent);
                intent.putExtra("docid","1");

                break;
            case R.id.but_xueye://转到学业
                intent.setClass(MainActivity.this,KeShiActivity.class);
                intent.putExtra("docid","4");

                startActivity(intent);
                break;
            case R.id.but_yinger://转到婴儿
                intent.setClass(MainActivity.this,KeShiActivity.class);
                intent.putExtra("docid","5");

                startActivity(intent);
                break;
            case R.id.but_young://转到青少年
                intent.setClass(MainActivity.this,KeShiActivity.class);
                intent.putExtra("docid","6");

                startActivity(intent);
                break;
            case R.id.but_middle://转到中青年
                intent.setClass(MainActivity.this,KeShiActivity.class);
                intent.putExtra("docid","7");

                startActivity(intent);
                break;
            case R.id.but_agedness://转到老年
                intent.setClass(MainActivity.this,KeShiActivity.class);
                intent.putExtra("docid","8");

                startActivity(intent);
                break;
        }

    }
}
