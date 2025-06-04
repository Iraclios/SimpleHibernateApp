package org.example;

import org.example.service.AppService;
import org.example.utils.HibernateUtil;

public class Main {
    public static void main(String[] args) {
        AppService.enterLoop();
        HibernateUtil.shutdown();
    }
}