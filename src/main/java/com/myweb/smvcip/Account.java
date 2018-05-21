package com.myweb.smvcip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account {
    private static Map<String ,String> accounts = new HashMap<>();
    public static Map<String ,String> getAccount(){
        if(accounts.size()>1) return accounts;
        accounts.put("zhiping9","asdf123456");
        accounts.put("hongying8","asdf123456");
        accounts.put("haiyan8","asdf123456");
        accounts.put("hexiu8","asdf123456");
        accounts.put("lingling9","asdf123456");
        accounts.put("quanhai8","asdf123456");
        accounts.put("quanhai6","asdf123456");
        accounts.put("quanhai9","asdf123456");
        accounts.put("jianlian8","asdf123456");
        return accounts;
    }
//
//    zhiping9----asdf123456
//    hongying8----asdf123456
//    haiyan8----asdf123456
//    hexiu8----asdf123456
//    lingling9----asdf123456
//    quanhai8----asdf123456
//    quanhai6----asdf123456
//    quanhai9----asdf123456
//    jianlian8----asdf123456
//    suqin8----asdf123456
//    xiuying88----asdf123456
//    xiuying99----asdf123456
//    xiuying100----asdf123456
//    lixiang8----asdf123456
}

