package edu.gatech.seclass.scm.models;

import java.util.Date;

/**
 * Created by anoth on 10/25/2015.
 */
public class Customer {
    // Privte member variables
    private String mBillingAddressFirstLine;
    private String mBillingAddressSecondLine;
    private String mBillingAddressCity;
    private String mBillingAddressState;
    private int mBillingAddressStateSpinnerID;
    private Integer mBillingAddressZip;
    private String mEmail;
    private boolean mHasGoldStatus;
    private long mId;
    private String mQrCode;
    private String mFirstName;
    private String mLastName;
    private Date mRewardExpirationDate;
    private double mRewardBalance;
    private double mSpentToDate;

    public String getQrCode() {
        return mQrCode;
    }

    //manually setting the QR code for new customers.
    public void setQrCode(String mHexId) {
        this.mQrCode = mHexId;
    }


    /**
     * Function returns the amount the customer has spent to date.
     * @return The amount the customer has spent to date
     */
    public double getSpentToDate() {
        return mSpentToDate;
    }

    public void setSpentToDate(double spentToDate) {
        mSpentToDate = spentToDate;
    }

    // Generated Getters and Setters
    public String getBillingAddressFirstLine() {
        return mBillingAddressFirstLine;
    }

    public void setBillingAddressFirstLine(String billingAddressFirstLine) {
        this.mBillingAddressFirstLine = billingAddressFirstLine;
    }

    public String getBillingAddressSecondLine() {
        return mBillingAddressSecondLine;
    }

    public void setBillingAddressSecondLine(String billingAddressSecondLine) {
        this.mBillingAddressSecondLine = billingAddressSecondLine;
    }

    public String getmBillingAddressState() {
        return mBillingAddressState;
    }
    //need the ID of the spinner to show on the edit customer page.
    public int getmBillingAddressStateSpinnerID(){
        return  mBillingAddressStateSpinnerID;
    }

    // Generated Getters and Setters
    public String getBillingAddressCity(){
        return mBillingAddressCity;
    }

    public void setBillingAddressCity(String billingAddressCity){
        this.mBillingAddressCity = billingAddressCity;
    }

    public void setmBillingAddressState(String mBillingAddressState) {
        this.mBillingAddressState = mBillingAddressState;
    }
    public void setmBillingAddressStateSpinnerID(int mBillingAddressStateSpinnerID){
        this.mBillingAddressStateSpinnerID = mBillingAddressStateSpinnerID;
    }

    public Integer getmBillingAddressZip() {
        return mBillingAddressZip;
    }

    public void setmBillingAddressZip(Integer mBillingAddressZip) {
        this.mBillingAddressZip = mBillingAddressZip;
    }

    public boolean getmHasGoldStatus() {
        return mHasGoldStatus;
    }

    public void setmHasGoldStatus(int mHasGoldStatus) {
        this.mHasGoldStatus = (mHasGoldStatus == 1) ? true : false;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName = lastName;
    }

    public Date getRewardExpirationDate() {
        return mRewardExpirationDate;
    }

    public void setRewardExpirationDate(Date rewardExpirationDate) {
        this.mRewardExpirationDate = rewardExpirationDate;
    }

    public double getRewardsBalance() {
        return mRewardBalance;
    }

    public void setRewardsBalance(double rewardsBalance) {
        this.mRewardBalance = rewardsBalance;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }
}

