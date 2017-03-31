package com.xfkj.mrxuyaohui.xfkjdemo.util.welcome;

import com.xfkj.mrxuyaohui.xfkjdemo.util.Welcome;
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
 * Created by Administrator on 2015/6/5.
 */
public class WelcomeJsonHelp {
    public static List<Welcome> getListPerson(String urlPath) {
// String name, String address, String sex, String date, String password, String attending, String massage, float score, int scoreTime, int age, String imageUrl, String phone) {

        try{
            List<Welcome> mlists = new ArrayList<Welcome>();
            byte[] data = readParse(urlPath);

            JSONArray jsonArray1=new JSONArray(new String(data));
            JSONObject item1 = jsonArray1.getJSONObject(0);
            JSONArray array =item1.getJSONArray("list");


            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                int id=item.getInt("id");
                String imageurl=item.getString("startscreenImg");
                String desc=item.getString("startscreenTitle");

                mlists.add(new Welcome(id,imageurl,desc));
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
