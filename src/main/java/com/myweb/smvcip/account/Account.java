package com.myweb.smvcip.account;

import java.util.HashMap;
import java.util.Map;

public class Account {
    private Map<String, String> accounts = new HashMap<>();

    private Map<String, Boolean> logined = new HashMap<>();

    private static Account account = new Account();

    private Account() {
        if (account == null) {
            accounts.put("zhiping9", "asdf123456");
            accounts.put("hongying8", "asdf123456");
            accounts.put("haiyan8", "asdf123456");
            accounts.put("hexiu8", "asdf123456");
            accounts.put("lingling9", "asdf123456");
            accounts.put("quanhai8", "asdf123456");
            accounts.put("quanhai6", "asdf123456");
            accounts.put("quanhai9", "asdf123456");
            accounts.put("jianlian8", "asdf123456");
        }
    }

    public static Account getAccount() {
        return account;
    }

    public static Map<String, String> getAccounts() {
        return getAccount().accounts;
    }

    public static Map<String, Boolean> getLogined() {
        return getAccount().logined;
    }

    public static boolean setLogined(String username) {
        getAccount().logined.put(username, true);
        return true;
    }

    public static boolean outLogined(String username) {
        getAccount().logined.put(username,false);
        return true;
    }
}

