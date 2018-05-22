package com.myweb;

import com.myweb.smvcip.Account;
import com.myweb.smvcip.QiangBi;
import com.myweb.smvcip.refresh.Refresh;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Start {
    private static final int MAX_THREADS = 1;
    public static final int SLEEP = 200;
    public static ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);

    public static void main(String[] args) throws Exception {
//        boolean isMainLogin = QiangBi.mainLogin("rlh003", "yhxt123456");
//        while (!isMainLogin) {
//            //isMainLogin = QiangBi.mainLogin("rlh003", "yhxt123456");
//            System.exit(1);
//        }
        Account.getAccount().forEach((k, v) -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executorService.execute(new Thread(() -> {
                QiangBi.letGo(k, v);
            }));
        });
    }
}
