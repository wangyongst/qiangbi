package com.myweb;

import com.myweb.smvcip.QiangBi;
import com.myweb.smvcip.account.Account;
import com.myweb.smvcip.account.Accounts;
import com.myweb.smvcip.account.Server;
import com.myweb.smvcip.account.Test;
import com.myweb.smvcip.utils.Timer;

import java.util.List;

public class Start {
    public static void main(String[] args) {
        Test.makeAccount();
        while (true) {
            Accounts.getAccounts().forEach((k,v) -> {
                try {
                    Thread.sleep(30000/Accounts.getAccounts().size()/Test.getServer().size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!v.isLogined()) {
                   QiangBi.login(k,v.getPassword());
                } else {
                    QiangBi.refresh(k);
                }
            });
        }
    }
}
