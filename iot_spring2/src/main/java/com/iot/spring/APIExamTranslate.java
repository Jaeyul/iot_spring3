package com.iot.spring;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
public class APIExamTranslate {

    public static void main(String[] args) {
        String clientId = "WgitPcqXp5vWEq1bUQyn";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "mmW1UhRYF1";//애플리케이션 클라이언트 시크릿값";
        try {
        	String tt = "\r\n" + 
        			"### Error querying database.  Cause: java.sql.SQLSyntaxErrorException: (conn=183) Table 'dbconnector.abc' doesn't exist\r\n" + 
        			"### The error may exist in sql/sql.xml\r\n" + 
        			"### The error may involve sql.executeQuery-Inline\r\n" + 
        			"### The error occurred while setting parameters\r\n" + 
        			"### Cause: java.sql.SQLSyntaxErrorException: (conn=183) Table 'dbconnector.abc' doesn't exist";
            String text = URLEncoder.encode(tt, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/language/translate";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source=en&target=ko&text=" + text.substring(0, 100);
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes("source=en&target=ko&text=%25250D%25250A%252523%252523%252523%252BError%252Bquerying%252Bdatabase.%252B%252BCause%25253A%252Bj");
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}