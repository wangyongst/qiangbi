package com.myweb;

import com.myweb.smvcip.Account;
import com.myweb.smvcip.QiangBi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Start {
    private static final int MAX_THREADS = 100;
    // public static final int SLEEP = 2000;
    public static ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);

    public static void main(String[] args) throws Exception {

        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "ERROR");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "ERROR");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.headers", "ERROR");

        boolean isMainLogin = QiangBi.mainLogin("rlh003", "yhxt123456");
        while (!isMainLogin) {
            isMainLogin = QiangBi.mainLogin("rlh003", "yhxt123456");
        }
        Account.getAccount().forEach((k, v) -> {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            executorService.execute(new Thread(() -> {
                QiangBi.refresh(k, v);
            }));
        });
    }
}
