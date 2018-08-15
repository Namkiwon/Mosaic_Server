package com.angointeam.mosaic.utils.email;

import java.util.StringTokenizer;

public class EmailUtils {

    public static String getDomain(String email) {
        StringTokenizer tokenizer = new StringTokenizer(email,"@");

        if(tokenizer.countTokens() != 2) {
            return null;
        }

        tokenizer.nextToken();

        return tokenizer.nextToken();
    }
}
