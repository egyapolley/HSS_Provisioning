package com.company;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestMain {

    public static void main(String[] args) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = simpleDateFormat.format(new Date());
        System.out.println(format);

    }
}
