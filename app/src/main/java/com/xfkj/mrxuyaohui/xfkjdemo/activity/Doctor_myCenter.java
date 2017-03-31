package com.xfkj.mrxuyaohui.xfkjdemo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.doc.Doc_Pingjia;
import com.xfkj.mrxuyaohui.xfkjdemo.activity.doc.Doc_ZiLiao;
import com.xfkj.mrxuyaohui.xfkjdemo.util.Base64Coder;
import com.xfkj.mrxuyaohui.xfkjdemo.util.IpCofig;
import com.xfkj.mrxuyaohui.xfkjdemo.util.NetWorkUtil;
import com.xfkj.mrxuyaohui.xfkjdemo.util.ToRoundBitmapUtil;
import com.xfkj.mrxuyaohui.xfkjdemo.util.consultion.Bean;
import com.xfkj.mrxuyaohui.xfkjdemo.util.consultion.JsonHelp;
import com.xfkj.mrxuyaohui.xfkjdemo.util.image.SmartImage;
import com.xfkj.mrxuyaohui.xfkjdemo.util.image.SmartImageView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Doctor_myCenter extends Activity {

    private Button ziliao;
    private Button pingjia;
    private long exitTime = 0;//退出程序
    private Button logout;

    //医生头像
    private SmartImageView imageButton;
    private Intent dataIntent = null;
    private String url = IpCofig.getIP_SERVER()+"MyheartDoctot/UpdateUserimg";
    private String urlPath = IpCofig.getIP_SERVER()+"MyheartDoctot/DocActionAll?DocServe=";
    private static final String url_image=IpCofig.getIP_SERVER()+"MyheartDoctot/user_img/";
    private List<Bean> persons;
    private TextView name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_mycenter_main);
        ziliao=(Button)findViewById(R.id.ziliao);
        pingjia=(Button)findViewById(R.id.pingjia);
        logout=(Button)findViewById(R.id.logout);
        imageButton=(SmartImageView)findViewById(R.id.image);
        name=(TextView)findViewById(R.id.name);
        new Thread(new  JsonThread() ).start();
    }
    class JsonThread implements  Runnable
    {
        @Override
        public void run() {
            Looper.prepare();
            List<Bean> newInfoList = JsonHelp.getListPerson(urlPath+((MyAppliction)getApplication()).getUserId());
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
                    name.setText("欢迎" + persons.get(0).getName() + "医生");
                    if(persons.get(0).getImageUrl()!=null)
                    {
                        imageButton.setImageUrl(url_image+persons.get(0).getImageUrl());
                    }else {
                        Toast.makeText(Doctor_myCenter.this,"你还未上传头像...",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    public void click(View v)
    {
        switch (v.getId())
        {
            //医生头像设置
            case R.id.image:
                if(((MyAppliction)getApplication()).getFlag())
                {
                    imageButton.setClickable(true);
                    ShowPickDialog();
                }else {
                    imageButton.setClickable(false);
                }
                break;
            case R.id.ziliao:
                Intent intent=new Intent();
                intent.setClass(Doctor_myCenter.this, Doc_ZiLiao.class);
                startActivity(intent);
                break;
            case R.id.pingjia:
                Intent intent1=new Intent();
                intent1.setClass(Doctor_myCenter.this, Doc_Pingjia.class);
                startActivity(intent1);
                break;
            case R.id.logout:
                Intent logoutIntent = new Intent(this, FristActivity.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                ((MyAppliction)getApplication()).setFlag(false);
                startActivity(logoutIntent);
                break;
        }
    }
    private void ShowPickDialog() {
        new AlertDialog.Builder(this)
                .setTitle("设置头像...")
                .setPositiveButton("相册", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        /**
                         * 刚开始，我自己也不知道ACTION_PICK是干嘛的，后来直接看Intent源码，
                         * 可以发现里面很多东西，Intent是个很强大的东西，大家一定仔细阅读下
                         */
                        Intent intent = new Intent(Intent.ACTION_PICK, null);

                        /**
                         * 下面这句话，与其它方式写是一样的效果，如果：
                         * intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                         * intent.setType(""image/*");设置数据类型
                         * 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
                         * 这个地方小马有个疑问，希望高手解答下：就是这个数据URI与类型为什么要分两种形式来写呀？有什么区别？
                         */
                        intent.setDataAndType(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                "image/*");
                        startActivityForResult(intent, 1);

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        /**
                         * 下面这句还是老样子，调用快速拍照功能，至于为什么叫快速拍照，大家可以参考如下官方
                         * 文档，you_sdk_path/docs/guide/topics/media/camera.html
                         * 我刚看的时候因为太长就认真看，其实是错的，这个里面有用的太多了，所以大家不要认为
                         * 官方文档太长了就不看了，其实是错的，这个地方小马也错了，必须改正
                         */
//                        Intent intent = new Intent(
//                                MediaStore.ACTION_IMAGE_CAPTURE);
//                        //下面这句指定调用相机拍照后的照片存储的路径
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
//                                .fromFile(new File(Environment
//                                        .getExternalStorageDirectory(),
//                                        "xiaoma.jpg")));
//                        startActivityForResult(intent, 2);
                    }
                }).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // 如果是直接从相册获取
            case 1:
                startPhotoZoom(data.getData());
                break;
            // 如果是调用相机拍照时
            case 2:
                File temp = new File(Environment.getExternalStorageDirectory()
                        + "/xiaoma.jpg");
                startPhotoZoom(Uri.fromFile(temp));
                break;
            // 取得裁剪后的图片
            case 3:
                /**
                 * 非空判断大家一定要验证，如果不验证的话，
                 * 在剪裁之后如果发现不满意，要重新裁剪，丢弃
                 * 当前功能时，会报NullException，小马只
                 * 在这个地方加下，大家可以根据不同情况在合适的
                 * 地方做判断处理类似情况
                 *
                 */
                    if (data != null) {
                        dataIntent = data;
                        setPicToView(data);
                    }else {
                        Toast.makeText(Doctor_myCenter.this,"您还未选择头像...",Toast.LENGTH_SHORT).show();
                    }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
//            imageButton.setBackgroundDrawable(drawable);
//
            imageButton.setImageDrawable(drawable);
            uploadPic();
        }
    }

    /**
     * 裁剪图片方法实现
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
		/*
		 * 至于下面这个Intent的ACTION是怎么知道的，大家可以看下自己路径下的如下网页
		 * yourself_sdk_path/docs/reference/android/content/Intent.html
		 * 直接在里面Ctrl+F搜：CROP ，之前小马没仔细看过，其实安卓系统早已经有自带图片裁剪功能,
		 * 是直接调本地库的，小马不懂C C++  这个不做详细了解去了，有轮子就用轮子，不再研究轮子是怎么
		 * 制做的了...吼吼
		 */
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }
    private void uploadPic(){
        if(dataIntent != null){
            Bundle extras = dataIntent.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                Drawable drawable = new BitmapDrawable(photo);
                /**
                 * 下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上
                 * 传到服务器，QQ头像上传采用的方法跟这个类似
                 */
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                byte[] bytes = stream.toByteArray();
                // 将图片流以字符串形式存储下来

                final String picStr = new String(Base64Coder.encodeLines(bytes));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("picStr", picStr));
                        params.add(new BasicNameValuePair("picName",((MyAppliction)getApplication()).getPhone()));
                        final String result = NetWorkUtil.httpPost(url, params);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(Doctor_myCenter.this, result, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();

                //如果下载到的服务器的数据还是以Base64Coder的形式的话，可以用以下方式转换
                //为我们可以用的图片类型就OK啦...吼吼
                //Bitmap dBitmap = BitmapFactory.decodeFile(tp);
                //Drawable drawable = new BitmapDrawable(dBitmap);

            }
        }else {
            Toast.makeText(Doctor_myCenter.this, "图片不存在", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_doctor_my_center, menu);
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
