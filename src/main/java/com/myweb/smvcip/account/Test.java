package com.myweb.smvcip.account;

import com.myweb.smvcip.utils.Timer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static Map<String, String> getUNAPW() {
        Map<String, String> unapw = new HashMap<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("c://account/6789.txt"));
            String str = null;
            while((str = br.readLine()) != null) {
                unapw.put(str.split("----")[0], str.split("----")[1]);
            }
        } catch (Exception e) {
            System.out.println("账号密码文件读取错误！！");
        }
//
//
//        unapw.put("zhiping9", "asdf123456");
//        unapw.put("hongying8", "asdf123456");
//        unapw.put("haiyan8", "asdf123456");
//        unapw.put("hexiu8", "asdf123456");
//        unapw.put("lingling9", "asdf123456");
//        unapw.put("quanhai8", "asdf123456");
//        unapw.put("quanhai6", "asdf123456");
//        unapw.put("quanhai9", "asdf123456");
//        unapw.put("jianlian8", "asdf123456");
//            unapw.put("zsheng7", "yhxt123456");
//            unapw.put("xyongq7", "yhxt123456");
//            unapw.put("xiajianqiu8", "yhxt123456");
//            unapw.put("zhangjian7", "yhxt123456");
//            unapw.put("zhangjian8", "yhxt123456");
//            unapw.put("zhangjian9", "yhxt123456");
//            unapw.put("zhangjian11", "yhxt123456");
//            unapw.put("lxianhao7", "yhxt123456");
//            unapw.put("lxianhao12", "yhxt123456");
//            unapw.put("lxianhao13", "yhxt123456");
//            unapw.put("zsheng9", "yhxt123456");
//            unapw.put("zhanggy8", "yhxt123456");
//            unapw.put("zhanggy9", "yhxt123456");
//            unapw.put("zhanggy13", "yhxt123456");
//            unapw.put("zhanggy12", "yhxt123456");
//            unapw.put("zhouy9", "yhxt123456");
//            unapw.put("zhouy11", "yhxt123456");
//            unapw.put("zhouy7", "yhxt123456");
        return unapw;
    }


    public static List<Server> getServer() {
        List<Server> servers = new ArrayList<>();
        Server server = new Server();
        server.setIp("127.0.0.1");
        server.setPort(8888);
        servers.add(server);
        Server server2 = new Server();
        server2.setIp("103.98.17.216");
        server2.setPort(8888);
        servers.add(server2);
        return servers;
    }

    public static void makeAccount() {
        Timer count = new Timer();
        count.setCount(0);
        final List<Server> servers = Test.getServer();
        Test.getUNAPW().forEach((k, v) -> {
            Account account = new Account();
            account.setUsername(k);
            account.setPassword(v);
            account.setIp(servers.get(count.getCount()).getIp());
            account.setPort(servers.get(count.getCount()).getPort());
            account.setLogined(false);
            Accounts.putAccounts(account);
            count.setCount(count.getCount() + 1);
            if (count.getCount() > 1) count.setCount(0);
        });
    }
}
