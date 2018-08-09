package edu.gatech.seclass.scm.models;

/**
 * Created by anoth on 10/25/2015.
 */

import java.util.Date;
/**
 * Created by anoth on 10/25/2015.
 */

public class Purchase {
    private boolean mGoldCreditApplied;
    private Date mPurchaseDate;
    private double mRewardAmountRedeemed;
    private double mTotalPurchaseAmount;
    private double mTotalPurchaseAmountAfterDiscounts;
    private long mCustomerId;
    long mId;

    public long getmCustomerId() {
        return mCustomerId;
    }

    public void setmCustomerId(long mCustomerId) {
        this.mCustomerId = mCustomerId;
    }

    public Purchase(long customerId){
        mCustomerId = customerId;
    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public boolean isGoldCreditApplied() {
        return mGoldCreditApplied;
    }

    public void setGoldCreditApplied(int mGoldCreditApplied) {
        this.mGoldCreditApplied = (mGoldCreditApplied == 1) ? true : false;
    }

    public boolean getGoldCreditApplied() {
        return this.mGoldCreditApplied;
    }

    public Date getPurchaseDate() {
        return mPurchaseDate;
    }

    public void setPurchaseDate(Date mPurchaseDate) {
        this.mPurchaseDate = mPurchaseDate;
    }

    public double getRewardAmountRedeemed() {
        return mRewardAmountRedeemed;
    }

    public void setRewardAmountRedeemed(double mRewardAmountRedeemed) {
        this.mRewardAmountRedeemed = mRewardAmountRedeemed;
    }

    public double getTotalPurchaseAmount() {
        return mTotalPurchaseAmount;
    }

    public void setTotalPurchaseAmount(double mTotalPurchaseAmount) {
        this.mTotalPurchaseAmount = mTotalPurchaseAmount;
    }
    public double getmTotalPurchaseAmountAfterDiscounts(){
        return mTotalPurchaseAmountAfterDiscounts;
    }
    public void setmTotalPurchaseAmountAfterDiscounts(double mTotalPurchaseAmountAfterDiscounts){
        this.mTotalPurchaseAmountAfterDiscounts = mTotalPurchaseAmountAfterDiscounts;
    }
}


