package com.xfkj.mrxuyaohui.xfkjdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.util.IpCofig;
import com.xfkj.mrxuyaohui.xfkjdemo.util.consultion.Bean;
import com.xfkj.mrxuyaohui.xfkjdemo.util.consultion.JsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.image.SmartImage;
import com.xfkj.mrxuyaohui.xfkjdemo.util.image.SmartImageView;
import com.xfkj.mrxuyaohui.xfkjdemo.util.news.News;
import com.xfkj.mrxuyaohui.xfkjdemo.util.news.News_JsonHelp;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class News_main extends Activity {

    //新闻列表实现所需的控件
    private ListView listView;
    private TextView empty;


    private static final String urlPath = IpCofig.getIP_SERVER()+"MyheartDoctot/newsAll";
    private List<News> persons;
    private static final String url_image=IpCofig.getIP_SERVER()+"MyheartDoctot/news_img/";

    //退出程序
    private long exitTime = 0;//退出程序


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_main);
        initPictureMove();
        //新闻列表的实现
        listView=(ListView)this.findViewById(R.id.list);
        empty=(TextView)this.findViewById(R.id.empty);
        listView.setEmptyView(empty);
        new Thread(new JsonThread()).start();
    }
    //新闻列表的实现
    class JsonThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            List<News> newInfoList = News_JsonHelp.getListPerson(urlPath);
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
                    MyAdapter adapter = new MyAdapter();
                    listView.setAdapter(adapter);
                    //设置监听事件
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent();
                            intent.setClass(News_main.this, News_detail.class);
                            intent.putExtra("newId", persons.get(position).getNewsId());
                            intent.putExtra("name", persons.get(position).getNewsTitle());
                            intent.putExtra("desc", persons.get(position).getNewsMessage());
                            intent.putExtra("imageUrl", persons.get(position).getNewsImg());
                            intent.putExtra("time", persons.get(position).getNewsDate());
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
                view = inflater.inflate(R.layout.news_main_listview, null);
            } else {
                view = convertView;
                System.out.print("出现异常。。。");

            }

            // 重新赋值, 不会产生缓存对象中原有数据保留的现象
            SmartImageView sivIcon = (SmartImageView) view.findViewById(R.id.image);
            TextView name = (TextView) view.findViewById(R.id.name);
            TextView message = (TextView) view.findViewById(R.id.desc);
            for(int i=0;i<3;i++)
            {
                imageIds[i]=url_image+persons.get(i).getNewsImg();
                imageDescriptions[i]=persons.get(i).getNewsTitle();
            }
            myPagerAdapter.notifyDataSetChanged();
            News newInfo = persons.get(position);
            sivIcon.setImageUrl(url_image+newInfo.getNewsImg());
            System.out.println(newInfo.getNewsImg());
            name.setText(newInfo.getNewsTitle());
            message.setText(newInfo.getNewsMessage());
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
    //对于动态广告图片的实现
    public void initPictureMove() {
        myPagerAdapter=new MyPagerAdapter();
        viewPager = (ViewPager) findViewById(R.id.image_news);
        pointGroup = (LinearLayout) findViewById(R.id.new_point_group);
        iamgeDesc = (TextView) findViewById(R.id.new_image_desc);
        iamgeDesc.setText(imageDescriptions[0]);


        imageList = new ArrayList<SmartImageView>();
        for (int i = 0; i < imageIds.length; i++) {
            SmartImageView image=new SmartImageView(News_main.this);
            image.setImageUrl(imageIds[i]);

            imageList.add(image);



            //添加指示点
            ImageView point = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.rightMargin = 20;
            point.setLayoutParams(params);

            point.setBackgroundResource(R.drawable.point_bg);
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }
            pointGroup.addView(point);
        }

        viewPager.setAdapter(myPagerAdapter);

        viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageIds.length));

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
                SmartImageView image = new SmartImageView(News_main.this);
                image.setImageUrl(imageIds[i]);
                imageList.set(i,image);
            }
            container.addView(imageList.get(position%imageIds.length));
            imageList.get(position%imageIds.length).setOnClickListener(new View.OnClickListener() {
                //用户点击图片时触发
                @Override
                public void onClick(View v) {
                //跳转
                    Intent intent = new Intent();
                    intent.setClass(News_main.this, News_detail.class);
                    intent.putExtra("newId", persons.get(position%imageList.size()).getNewsId());
                    startActivity(intent);
                }
            });
            //返回一个和该view相对的object
            return imageList.get(position%imageIds.length);
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
