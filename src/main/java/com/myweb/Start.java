package com.myweb;

import com.myweb.smvcip.account.Account;
import com.myweb.smvcip.QiangBi;
import com.myweb.smvcip.refresh.Refresh;
import com.myweb.smvcip.refresh.RefreshApi;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Start {
    private static final int MAX_THREADS = 10;
    public  static final int SLEEP = 100;
    private static int time = 0;
    private static RefreshApi refreshApi = null;

    public static void main(String[] args) {

        //boolean isMainLogin = QiangBi.mainLogin("rlh003", "yhxt123456");
        long start = System.currentTimeMillis();
        ExecutorService executors = Executors.newFixedThreadPool(MAX_THREADS);
        CountDownLatch countDownLatch = new CountDownLatch(MAX_THREADS);
        Account.getAccount().forEach((k, v) -> {
            try {
                System.out.println(QiangBi.login(k,v));
            } catch (Exception e) {
                e.printStackTrace();
            }
//            executors.execute(new Thread(() -> {
//                try {
//                    System.out.println(QiangBi.login(k,v));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }));
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executors.shutdown();
    }
}
