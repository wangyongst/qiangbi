package com.myweb.smvcip.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    public static Map<String, String> getUNAPW() {
        Map<String, String> unapw = new HashMap<>();
        unapw.put("zhiping9", "asdf123456");
        unapw.put("hongying8", "asdf123456");
        unapw.put("haiyan8", "asdf123456");
        unapw.put("hexiu8", "asdf123456");
        unapw.put("lingling9", "asdf123456");
        unapw.put("quanhai8", "asdf123456");
        unapw.put("quanhai6", "asdf123456");
        unapw.put("quanhai9", "asdf123456");
        unapw.put("jianlian8", "asdf123456");
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
        server.setIp("localhost");
        server.setPort(8888);
        servers.add(server);
        Server server2 = new Server();
        server2.setIp("128.14.227.165");
        server2.setPort(8888);
        servers.add(server2);
        return servers;
    }
}
