package com.ayibonte.twitter.utils;

import java.util.Arrays;

/**
 *
 * Created by alfred on 6/3/15.
 */
public class TextUtil {

    public static String[] splitCharacters(String tweet){
        if(tweet.length() > 140 ){
            return tweet.split("(?<=\\G.{140})");
        }
        return new String[]{tweet};
    }
}
