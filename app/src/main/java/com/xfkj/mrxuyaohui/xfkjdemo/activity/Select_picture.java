package com.xfkj.mrxuyaohui.xfkjdemo.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xfkj.mrxuyaohui.xfkjdemo.R;
import com.xfkj.mrxuyaohui.xfkjdemo.util.sd_exist;

import java.io.File;

public class Select_picture extends Activity {
    //按钮
    private Button takePhoto;
    private Button pickPhoto;
    private Button cancel;

    private Uri photoUri;
    private String path;
    private Intent lastIntent ;
    public static final String KEY_PHOTO_PATH = "photo_path";
    /* 头像名称 */
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";
    /*** 
     * 使用照相机拍照获取图片 
     */
    public static final int CAMERA_REQUEST_CODE = 0;
    /*** 
     * 使用相册中的图片 
     */
    public static final int IMAGE_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_picture_dialog);
        takePhoto=(Button)this.findViewById(R.id.takePhoto);
        pickPhoto=(Button)this.findViewById(R.id.pickPhoto);
        cancel=(Button)this.findViewById(R.id.cancel);
    }
    public void click(View v)
    {
        switch (v.getId())
        {
            case R.id.takePhoto:
                Intent intentFromCapture = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);
                // 判断存储卡是否可以用，可用进行存储
                if (sd_exist.hasSdcard()) {
                    intentFromCapture.putExtra(
                            MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(Environment
                                    .getExternalStorageDirectory(),
                                    IMAGE_FILE_NAME)));
                }
                startActivityForResult(intentFromCapture,
                        CAMERA_REQUEST_CODE);
                break;
            case R.id.pickPhoto:
                Intent intentFromGallery = new Intent();
                intentFromGallery.setType("image/*"); // 设置文件类型
                intentFromGallery
                        .setAction(Intent.ACTION_GET_CONTENT);
                System.out.println("选择图片正确》》》》》");
                startActivityForResult(intentFromGallery,
                        IMAGE_REQUEST_CODE);
                break;
            case R.id.cancel:
                finish();
                break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //结果码不等于取消时候
        if (resultCode != RESULT_CANCELED) {

            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    System.out.println("选择图片后启动裁剪图片》》》》》");
                    System.out.println("选择图片后的地址是》》》》》"+data.getData());
                    try {
                        ContentResolver resolver = getContentResolver();
                        Uri originalUri = data.getData(); //获得图片的uri
                        System.out.println("得到的图片地址为dshjfasdfhjoiashiosd"+" "+originalUri);

// 这里开始的第二部分，获取图片的路径：
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor cursor = managedQuery(originalUri, proj, null, null, null);
//按我个人理解 这个是获得用户选择的图片的索引值
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
//最后根据索引值获取图片路径
                        path = cursor.getString(column_index);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    if (sd_exist.hasSdcard()) {
                        File tempFile = new File(
                                Environment.getExternalStorageDirectory()
                                        + IMAGE_FILE_NAME);
                        System.out.println("照相后启动裁剪图片》》》》》");
                        System.out.println("照相后得到的图片地址》》》》》"+Uri.fromFile(tempFile));
                        startPhotoZoom(Uri.fromFile(tempFile));
                    } else {
                        Toast.makeText(Select_picture.this, "未找到存储卡，无法存储照片！",
                                Toast.LENGTH_LONG).show();
                    }

                    break;
                case RESULT_REQUEST_CODE:
                    Bundle myBundle=data.getExtras();



                    System.out.println("得到的图片的地址为"+path);
                    Intent myIntent=new Intent();
                    myIntent.putExtras(myBundle);
                    myIntent.putExtra("uri",path);
                    setResult(RESULT_REQUEST_CODE, myIntent);
                    finish();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        System.out.println("开始裁剪图片》》》》》");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 2);
    }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_picture, menu);
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
