package edu.gatech.seclass.scm.models;

/**
 * Created by anoth on 10/28/2015.
 */
public class CostCalculator {

    public static double getItemPrice2(SaleItem item){
        int sizeAdjustment = 0;
        double basePrice;
        switch (item.getmSmoothieTypeId())
        {
            default:
                basePrice = 3.99;
                break;
        }
        switch (item.getmSizeId())
        {
            case 1:
                sizeAdjustment = 0;
                break;
            case 2:
                sizeAdjustment = 1;
                break;
            case 3:
                sizeAdjustment = 2;
                break;
        }
        return basePrice + sizeAdjustment;
    }
}

