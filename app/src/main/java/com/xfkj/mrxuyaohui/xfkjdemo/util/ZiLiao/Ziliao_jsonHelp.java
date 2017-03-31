package com.xfkj.mrxuyaohui.xfkjdemo.util.ZiLiao;

import com.xfkj.mrxuyaohui.xfkjdemo.bean.User_ziliao;
import com.xfkj.mrxuyaohui.xfkjdemo.util.consultion.Bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.xuyaohui on 2015/6/1.
 */
public class Ziliao_jsonHelp {
    public static List<User_ziliao> getListPerson(String urlPath) {
// String name, String address, String sex, String date, String password, String attending, String massage, float score, int scoreTime, int age, String imageUrl, String phone) {

        try{
            List<User_ziliao> mlists = new ArrayList<User_ziliao>();
            byte[] data = readParse(urlPath);

            JSONArray jsonArray1=new JSONArray(new String(data));
            JSONObject item1 = jsonArray1.getJSONObject(0);
            JSONObject item=item1.getJSONObject("list");

//            JSONArray array =item1.getJSONArray("list");
//            for (int i = 0; i < array.length(); i++) {
//                JSONObject item = array.getJSONObject(i);
                String alipayNum=item.getString("alipayNum");
                int userAge1=item.getInt("userAge");
                double userBalance=item.getDouble("userBalance");
                int userId=item.getInt("userId");
                String userMessage=item.getString("userMessage");
                String userName=item.getString("userName");
                String passwd=item.getString("userPasswd");
                String userPhmessage=item.getString("userPhmessage");
                String userPhone=item.getString("userPhone");
                String userSex=item.getString("userSex");
                mlists.add(new User_ziliao(alipayNum,userSex,userPhone, userPhmessage,passwd,userName,userMessage, userId,userBalance,userAge1));

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
