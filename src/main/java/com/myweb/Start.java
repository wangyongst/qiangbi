package com.myweb;

import com.myweb.smvcip.Account;
import com.myweb.smvcip.QiangBi;
import com.myweb.smvcip.refresh.Refresh;
import com.myweb.smvcip.refresh.RefreshApi;
import com.myweb.smvcip.utils.Proxys;
import com.myweb.smvcip.utils.Timer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Start {
    private static final int MAX_THREADS = 1;
    public  static final int SLEEP = 100;
    public  static ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);
    private static int time = 0;
    private static RefreshApi refreshApi = null;

    public static void main(String[] args) {

        boolean isMainLogin = QiangBi.mainLogin("rlh003", "yhxt123456");

//        Account.getAccount().forEach((k, v) -> {
//            if (time >= Proxys.size) time = 0;
//            Timer timer = new Timer();
//            timer.setTime(time);
//            executorService.execute(new Thread(() -> {
//                Refresh refresh = new Refresh(k,v,timer.getTime());
//               new QiangBi().letGo(refresh);
//            }));
//        });
    }
}
