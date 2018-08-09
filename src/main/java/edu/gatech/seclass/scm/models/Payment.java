package edu.gatech.seclass.scm.models;

import java.util.Date;

/**
 * Created by anoth on 10/24/2015.
 */
public class Payment {

    private long _id;
    private double mPaymentAmount;
    private long mCardAccountNumber;
    private Date mCardExpirationDate;
    private String mCardHolderName;
    private int mCardSecurityCode;
    private Date mPaymentDate;
    private long mPurchaseId;

    public Payment(int id) {
        this._id = id;
    }

    public Payment() {
    }

    public void setID(long id) {
        this._id = id;
    }

    public long getID() {
        return this._id;
    }

    public double getmPaymentAmount() {
        return mPaymentAmount;
    }

    public void setmPaymentAmount(double mPaymentAmount) {
        this.mPaymentAmount = mPaymentAmount;
    }

    public long getmCardAccountNumber() {
        return mCardAccountNumber;
    }

    public void setmCardAccountNumber(long mCardAccountNumber) {
        this.mCardAccountNumber = mCardAccountNumber;
    }

    public Date getmCardExpirationDate() {
        return mCardExpirationDate;
    }

    public void setmCardExpirationDate(Date mCardExpirationDate) {
        this.mCardExpirationDate = mCardExpirationDate;
    }

    public String getmCardHolderName() {
        return mCardHolderName;
    }

    public void setmCardHolderName(String mCardHolderName) {
        this.mCardHolderName = mCardHolderName;
    }

    public int getmCardSecurityCode() {
        return mCardSecurityCode;
    }

    public void setmCardSecurityCode(int mCardSecurityCode) {
        this.mCardSecurityCode = mCardSecurityCode;
    }

    public Date getmPaymentDate() {
        return mPaymentDate;
    }

    public void setmPaymentDate(Date mPaymentDate) {
        this.mPaymentDate = mPaymentDate;
    }

    public long getmPurchaseId() {
        return mPurchaseId;
    }

    public void setmPurchaseId(long mPurchaseId) {
        this.mPurchaseId = mPurchaseId;
    }

}


