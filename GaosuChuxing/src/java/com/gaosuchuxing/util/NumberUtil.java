/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaosuchuxing.util;

import java.text.DecimalFormat;
import java.util.Random;

public class NumberUtil {
    public static final String DEFAULT_DECIMAL_FORMAT = "00";
    
    public static String format(int number, String decimalFormat, boolean bZeroVisible) {
        if ((number == 0 || number == -1) && !bZeroVisible) {
            return "";
        } else {
            return new DecimalFormat(decimalFormat).format(number);
        }
//        return new DecimalFormat(decimalFormat).format(number);
    }
    
    public static String format(int number, String decimalFormat) {
        return format(number, decimalFormat, true);
    }
    
    public static String format(int number) {
        return format(number, DEFAULT_DECIMAL_FORMAT);
    }
    
    public static String format(double number, String decimalFormat) {
        return new DecimalFormat(decimalFormat).format(number);
    }
    
    public static String format(double number) {
        return new DecimalFormat("#0.00").format(number);
    }
    
    public static int strToInt(String str) {
        return strToInt(str, -1);
    }
    
    public static int strToInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception ex) {}
        return defaultValue;
    }
    
    public static long strToLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception ex) {}
        return -1;
    }
    
    public static Double strToDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (Exception ex) {}
        return -1.0;
    }
    
    /*
     * generator random number 
     */
    public static int getRandomByRange(int min, int max) {
	Random rnd = new Random();
	
	int rangeInt = max - min + 1;
	if (rangeInt <= 0)
            rangeInt = 1;
        
	int random = min + rnd.nextInt(rangeInt);
	
	return random;
    }   
}
