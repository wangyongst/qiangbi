package com.myweb.smvcip.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Proxys {
    private List<ProxyUtils> proxyUtilsList = new ArrayList<>();
    public static int size = 1;
    private static Proxys proxy = new Proxys();

    private Proxys() {
        for (int i = 0; i < size; i++) {
            ProxyUtils proxyUtils = new ProxyUtils(i);
            proxyUtilsList.add(proxyUtils);
        }
    }

    public static ProxyUtils getProxy(int i) {
        return proxy.proxyUtilsList.get(i);
    }
}
