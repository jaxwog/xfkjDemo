package com.xfkj.mrxuyaohui.xfkjdemo.util.news;

import com.xfkj.mrxuyaohui.xfkjdemo.util.judge.Judge_Bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.xuyaohui on 2015/4/27.
 */
public class News_JsonHelp {

    public static List<News> getListPerson(String urlPath) {
// String name, String address, String sex, String date, String password, String attending, String massage, float score, int scoreTime, int age, String imageUrl, String phone) {

        try{
            List<News> mlists = new ArrayList<News>();
            byte[] data = readParse(urlPath);
            JSONArray jsonArray1=new JSONArray(new String(data));
            JSONObject item1 = jsonArray1.getJSONObject(0);
            JSONArray array =item1.getJSONArray("list");
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                String newsDate=item.getString("newsDate");
                int newsId=item.getInt("newsId");
                String newsImg=item.getString("newsImg");
                String newsMessage=item.getString("newsMessage");
                String newsTitle=item.getString("newsTitle");
                mlists.add(new News(newsDate,newsId,newsImg,newsMessage,newsTitle));
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
