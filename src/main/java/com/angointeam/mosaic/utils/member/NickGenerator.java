package com.angointeam.mosaic.utils.member;


import org.apache.commons.lang3.RandomStringUtils;

public class NickGenerator {
    public static String gen() {
        return RandomStringUtils.random(10,true,true);
    }
}
