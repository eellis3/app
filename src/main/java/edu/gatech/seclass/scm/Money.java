package edu.gatech.seclass.scm;

import java.text.NumberFormat;

/**
 * Created by anoth on 11/11/2015.
 */
public class Money {
    public static String doubleToDollar(double amount){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(amount);
    }
}
