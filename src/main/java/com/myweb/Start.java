package com.myweb;

import com.myweb.smvcip.Account;
import com.myweb.smvcip.QiangBi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Start {
    private static final int MAX_THREADS = 10;
    // public static final int SLEEP = 2000;
    public static ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);

    public static void main(String[] args) throws Exception {
//        boolean isMainLogin = QiangBi.mainLogin("rlh003", "yhxt123456");
//        while (!isMainLogin) {
//            //isMainLogin = QiangBi.mainLogin("rlh003", "yhxt123456");
//            System.exit(1);
//        }
        Account.getAccount().forEach((k, v) -> {
            executorService.execute(new Thread(() -> {
                boolean out = QiangBi.refreshLogin(k, v);
                while (!out) {
                    out = QiangBi.refreshLogin(k, v);
                }
            }));
        });

        while (true) {
            if (executorService.isTerminated()) {
                Account.getAccount().forEach((k, v) -> {
                    executorService.execute(new Thread(() -> {
                        boolean out = QiangBi.refresh(k, v);
                        while (!out) {
                            out = QiangBi.refresh(k, v);
                        }
                        System.out.println("刷出来了！！！");
                    }));
                });
                break;
            }
        }
    }
}
