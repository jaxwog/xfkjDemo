package com.xfkj.mrxuyaohui.xfkjdemo.util.judge;

/**
 * Created by Mr.xuyaohui on 2015/4/19.
 */

import com.xfkj.mrxuyaohui.xfkjdemo.util.consultion.Bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/*******
 *
 * Json的数据解析
 *
 * 注意的问题是:
 *
 * 1.清单文件网络权限
 *
 * 2.需要添加json外部包
 *
 * ********/

public class Judge_JsonHelp {

    public static List<Judge_Bean> getListPerson(String urlPath) {
// String name, String address, String sex, String date, String password, String attending, String massage, float score, int scoreTime, int age, String imageUrl, String phone) {

        try{
            List<Judge_Bean> mlists = new ArrayList<Judge_Bean>();
            byte[] data = readParse(urlPath);



//    JSONArray array1 = new JSONArray(new String(data));
//    JSONArray array=array1.getJSONArray(0);

            JSONArray jsonArray1=new JSONArray(new String(data));
            JSONObject item1 = jsonArray1.getJSONObject(0);
            JSONArray array =item1.getJSONArray("list");


            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
//        private int commentId;
//        private String commentMessage;
//        private int commentScore;
//        private int commentTime;
//        private int docId;
//        private int userId;
//        private String userPhone;
                int commentId=item.getInt("commentId");
                String commentMessage=item.getString("commentMessage");
                int commentScore=item.getInt("commentScore");
                String commentTime=item.getString("commentTime");
                int docId=item.getInt("docId");
                int userId=item.getInt("userId");
                String userPhone=item.getString("userPhone");
                mlists.add(new Judge_Bean(commentId,commentMessage,commentScore,commentTime,docId,userId,userPhone));






            }
            return mlists;}
        catch (Exception e) {
            e.printStackTrace();
//    } finally {
//        if(client != null) {
//            client.getConnectionManager().shutdown();		// 关闭和释放资源
//        }
        }
        return null;
    }
    /**
     * 从指定的url中获取字节数组
     *
     * @param urlPath
     * @return 字节数组
     * @throws Exception
     */
    public static byte[] readParse(String urlPath) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }
}

