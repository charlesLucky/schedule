/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dragon.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.apache.log4j.Logger;

public class IpPicker {

    private static Logger log = Logger.getLogger(IpPicker.class);

    public static List<String> getLocalIP() {
        List list = new ArrayList();
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    list.add(i.getHostAddress());
                }
            }
        } catch (Exception e) {
            log.error(e);
        }

        return list;
    }

    public static boolean isLocalIP(String ip) {
        List list = getLocalIP();
        return (list.contains(ip));
    }

}
