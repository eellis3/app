package edu.gatech.seclass.scm.models;

/**
 * Created by anoth on 10/24/2015.
 */
public class SaleItem {

    private long mId;
    private int mSizeId;
    private int mPurchaseId;
    private int mSmoothieTypeId;
    private double mPriceOverride = 0;
    private double mBasePrice = 2.99;

    public SaleItem() {

    }

    public SaleItem(int id) {
        this.mId = id;
    }

    public void setID(long id) {
        this.mId = id;
    }

    public long getID() {
        return this.mId;
    }

    public int getmSmoothieTypeId() {
        return mSmoothieTypeId;
    }

    public void setmSmoothieTypeId(int mSmoothieTypeId) {
        this.mSmoothieTypeId = mSmoothieTypeId;
    }

    public int getmSizeId() {
        return mSizeId;
    }

    public void setmSizeId(int mSizeId) {
        this.mSizeId = mSizeId;
    }

    public int getmPurchaseId() {
        return mPurchaseId;
    }

    public void setmPurchaseId(int mPurchaseId) {
        this.mPurchaseId = mPurchaseId;
    }

    public double getItemPrice() {
        double finalPrice = 0;
        //changed to >=0, $0 was defaulting to 2.99
        if (mPriceOverride >= 0) {
            finalPrice = mPriceOverride;
        }
        else {
            finalPrice = this.mBasePrice + getmSizeId();
        }
        return finalPrice;
    }

    public void setPriceOverride(double itemPrice) {
        this.mPriceOverride = itemPrice;
    }

}

