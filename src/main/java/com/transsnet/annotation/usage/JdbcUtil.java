package com.transsnet.annotation.usage;

public class JdbcUtil {

    @DbInfo(url = "jdbc:mysql://localhost:3306/transsnet",username = "yinqi",pw = "123456")
    public static String getConnection(String url,String userName,String pw){
        System.out.println(url);
        System.out.println(userName);
        System.out.println(pw);
        return null;
    }

}
