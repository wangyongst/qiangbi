package com.myweb.smvcip.account;

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
}

