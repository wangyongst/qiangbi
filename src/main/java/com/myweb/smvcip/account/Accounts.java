package com.myweb.smvcip.account;

import java.util.HashMap;
import java.util.Map;

public class Accounts {
    private Map<String, Account> accounts = new HashMap<>();

    private static Accounts account = new Accounts();

    public static Accounts getAccount() {
        return account;
    }

    public static Account getAccount(String username) {
        return getAccount().accounts.get(username);
    }

    public static Map<String, Account> getAccounts() {
        return getAccount().accounts;
    }

    public static boolean putAccounts(Account account) {
        getAccount().accounts.put(account.getUsername(), account);
        return true;
    }

    public static boolean setLogined(String username) {
        Account acc = getAccount().accounts.get(username);
        acc.setLogined(true);
        getAccount().accounts.put(username,acc);
        return true;
    }

    public static boolean outLogined(String username) {
        Account acc = getAccount().accounts.get(username);
        acc.setLogined(false);
        getAccount().accounts.put(username,acc);
        return true;
    }
}

