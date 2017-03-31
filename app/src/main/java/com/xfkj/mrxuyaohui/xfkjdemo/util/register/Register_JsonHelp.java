package com.xfkj.mrxuyaohui.xfkjdemo.util.register;

import com.xfkj.mrxuyaohui.xfkjdemo.util.news.News;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.xuyaohui on 2015/5/22.
 */
public class Register_JsonHelp {

    public static List<Register_bean> getListPerson(String urlPath) {
// String name, String address, String sex, String date, String password, String attending, String massage, float score, int scoreTime, int age, String imageUrl, String phone) {

        try{
            List<Register_bean> mlists = new ArrayList<Register_bean>();
            byte[] data = readParse(urlPath);
            JSONArray jsonArray1=new JSONArray(new String(data));
            for (int i = 0; i < jsonArray1.length(); i++) {
                JSONObject item = jsonArray1.getJSONObject(i);

                String ports=item.getString("ports");
                String phone=item.getString("phone");
                mlists.add(new Register_bean(ports,phone));
            }
            return mlists;}
        catch (Exception e) {
            e.printStackTrace();

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
