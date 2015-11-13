package com.wbl.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by Simple_love on 2015/11/13.
 */
public class HttpRequestUtil {

        /*
        * ����GET����
        * @param url �����URL��ַ
        * @param paramMap �������
        * @return Զ����Ӧ���
        * */
        public static String doGetRequest(String url, Map<String,String> paramMap){
                //���󷵻ص���Ӧ���
                StringBuilder responseResult = new StringBuilder();

                //��ȡHttp�������Ӧ��������������������ʽ������Ӧ���
                BufferedReader reader = null;

                StringBuilder sb = new StringBuilder();

                //��װ����������
                String params = "";

                try {
                        //���������������������������з�װ
                        if (paramMap!=null && !paramMap.isEmpty()){
                                for (String name:paramMap.keySet()){
                                        //������������б��룬��������
                                        sb.append(name).append("=").append(java.net.URLEncoder.encode(paramMap.get(name),"UTF-8"));
                                        sb.append("&");
                                }
                                String temp_params = sb.toString();
                                params = temp_params.substring(0,temp_params.length()-1);
                        }

                        String full_url = url + "?" + params;

                        //����URL����
                        java.net.URL connectUrl= new URL(full_url);

                        //��URL����
                        java.net.HttpURLConnection httpURLConnection = (HttpURLConnection) connectUrl.openConnection();

                        //����http����ͷ�е�����
                        httpURLConnection.setRequestProperty("Accept","*/*");
                        httpURLConnection.setRequestProperty("Connection","keep-alive");
                        httpURLConnection.setRequestProperty("User Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36");

                        httpURLConnection.connect();

                        //����http��Ӧ��ͷ��
                        Map<String,List<String>> responseHeader = httpURLConnection.getHeaderFields();

                        for (String key:responseHeader.keySet()){
                                System.out.println(key + " : " + responseHeader.get(key));
                        }

                        //����BufferedReader����������ȡURL����Ӧ,�����ñ��뷽ʽ
                        reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));
                        String line;
                        while ((line = reader.readLine())!=null){
                                responseResult.append(line);
                        }
                }catch (Exception e){
                        e.printStackTrace();
                }finally {
                       try {
                               if (reader != null)
                                       reader.close();
                       }catch (Exception e){
                               e.printStackTrace();
                       }
                }
                return responseResult.toString();
        }

        /*
        * ����POST����
        *
        *@param url �����URL��ַ
        * @param paramMap �������
        * @return Զ����Ӧ���
        * */
        public static String doPostRequest(String url, Map<String,String> paramMap){
                StringBuilder responseResult = new StringBuilder();
                BufferedReader in = null;
                PrintWriter out = null;
                StringBuilder sb = new StringBuilder();
                String params = "";

                try {
                        if (paramMap != null && !paramMap.isEmpty()){
                                for (String name: paramMap.keySet()){
                                        sb.append(name).append("=").append(java.net.URLEncoder.encode(paramMap.get(name),"UTF-8"));
                                }
                                String temp_params = sb.toString();
                                params = temp_params.substring(0,temp_params.length() - 1);
                        }

                        URL connectUrl = new URL(url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) connectUrl.openConnection();

                        //����http����ͷ�е�����
                        httpURLConnection.setRequestProperty("Accept","*/*");
                        httpURLConnection.setRequestProperty("Connection", "keep-alive");
                        httpURLConnection.setRequestProperty("User Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36");

                        /*
                        * URL ���ӿ����������/��������������ʹ�� URL ���ӽ������룬�� DoInput ��־����Ϊ true��
                        * �������ʹ�� URL ���ӽ���������� DoOutput ��־����Ϊ true��
                        * */
                        httpURLConnection.setDoInput(true);
                        httpURLConnection.setDoOutput(true);

                        //// ��ȡHttpURLConnection�����Ӧ�������
                        out = new PrintWriter(httpURLConnection.getOutputStream());
                        /*
                        * POST��ʽ�ύ���������������������з��͵ģ����Ǻ�GET������
                        * */
                        //�����������
                        out.write(params);

                        out.flush();

                        httpURLConnection.connect();

                        //����BufferedReader����������ȡURL����Ӧ�����ñ��뷽ʽ
                        in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));
                        String line;

                        //// ��ȡ���ص�����
                        while ((line = in.readLine()) != null){
                                responseResult.append(line);
                        }
                }catch (Exception e){
                        e.printStackTrace();
                }finally {
                        try {
                                if (out != null)
                                        out.close();
                                if (in != null)
                                        in.close();
                        }catch (Exception e){
                                e.printStackTrace();
                        }
                }
                return responseResult.toString();
        }

        public static void main(String[] args) {
                System.out.println(HttpRequestUtil.doGetRequest("http://www.baidu.com", null));
                System.out.println(HttpRequestUtil.doPostRequest("http://www.baidu.com",null));
        }
}
