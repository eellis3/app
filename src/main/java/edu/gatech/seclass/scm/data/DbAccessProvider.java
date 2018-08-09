package edu.gatech.seclass.scm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.Date;

import edu.gatech.seclass.scm.models.Customer;
import edu.gatech.seclass.scm.models.Payment;
import edu.gatech.seclass.scm.models.Purchase;
import edu.gatech.seclass.scm.models.SaleItem;

/**
 * Created by anoth on 10/24/2015.
 * from: https://github.com/jgilfelt/android-sqlite-asset-helper
 * copies existing database file into data/data folder
 */

public class DbAccessProvider extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "SmoothieCartDB.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CUSTOMER = "customer";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_QRCODE = "qr_code";
    public static final String COLUMN_BILLINGADDRESSFIRSTLINE = "billing_address_first_line";
    public static final String COLUMN_BILLINGADDRESSSTATE= "billing_address_state";
    public static final String COLUMN_BILLINGADDRESSZIP= "billing_address_zip";
    public static final String COLUMN_BILLINGADDRESSSECONDLINE = "billing_address_second_line";
    public static final String COLUMN_BILLINGADDRESSCITY = "billing_address_city";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_HASGOLDSTATUS = "has_gold_status";
    public static final String COLUMN_FIRSTNAME = "first_name";
    public static final String COLUMN_LASTNAME = "last_name";
    public static final String COLUMN_REWARDEXPIRATIONDATE = "reward_expiration_date";
    public static final String COLUMN_REWARDBALANCE = "reward_balance";

    private static final String TABLE_PURCHASE = "purchase";
    public static final String COLUMN_GOLDCREDITAPPLIED = "gold_credit_applied";
    public static final String COLUMN_PURCHASEDATE = "purchase_date";
    public static final String COLUMN_REWARDAMOUNTREDEEMED = "reward_amount_redeemed";
    public static final String COLUMN_TOTALPURCHASEAMOUNT = "total_purchase_amount";
    public static final String COLUMN_CUSTOMERID = "customer_id";
    public static final String COLUMN_TOTAL_PURCHASEAMOUNTAFTERDISCOUNTS = "total_purchase_amt_after_discounts";

    private static final String TABLE_PAYMENT = "payment";
    public static final String COLUMN_PAYMENTAMOUNT = "amount";
    public static final String COLUMN_CARDACCOUNTNUMBER = "card_account_number";
    public static final String COLUMN_CARDEXPIRATIONDATE = "card_expiration_date";
    public static final String COLUMN_CARDHOLDERNAME = "cardholder_name";
    public static final String COLUMN_CARDSECURITYCODE = "card_security_code";
    public static final String COLUMN_PAYMENTDATE = "payment_date";
    public static final String COLUMN_PURCHASEID = "purchase_id";

    private static final String TABLE_SALE_ITEM= "sale_item";
    public static final String COLUMN_PRICEPAID = "price_paid";
    public static final String COLUMN_SIZEID = "size_id";
    public static final String COLUMN_SMOOTHIETYPEID = "smoothie_type_id";

    public DbAccessProvider(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        //The job of SQLiteAssetHelper is to copy an existing database from the Assets folder to the
        //data/data/databases folder on the on the destination device.  The assets folder is not accessed once the
        //app has been deployed.  While testing, deleting the database will refresh it with the latest version from the
        //assets folder.  If you remove the deleteDatabase line, it will persist across deployments & start/stop of app.
        //context.deleteDatabase(DATABASE_NAME);

        //setForcedUpgrade doesn't appear to work...
        //setForcedUpgrade(0);
    }

    public long addCustomer(Customer customer) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_QRCODE, customer.getQrCode());
        values.put(COLUMN_BILLINGADDRESSFIRSTLINE, customer.getBillingAddressFirstLine());
        values.put(COLUMN_BILLINGADDRESSSECONDLINE, customer.getBillingAddressSecondLine());
        values.put(COLUMN_BILLINGADDRESSCITY, customer.getBillingAddressCity());
        values.put(COLUMN_BILLINGADDRESSSTATE, customer.getmBillingAddressState());
        values.put(COLUMN_BILLINGADDRESSZIP, customer.getmBillingAddressZip());
        values.put(COLUMN_EMAIL, customer.getEmail());
        values.put(COLUMN_HASGOLDSTATUS, customer.getmHasGoldStatus());
        values.put(COLUMN_FIRSTNAME, customer.getFirstName());
        values.put(COLUMN_LASTNAME, customer.getLastName());
        if (customer.getRewardExpirationDate() != null) {
            values.put(COLUMN_REWARDEXPIRATIONDATE, customer.getRewardExpirationDate().getTime());
        }
        values.put(COLUMN_REWARDBALANCE, customer.getRewardsBalance());

        SQLiteDatabase db = this.getWritableDatabase();

        long id = db.insert(TABLE_CUSTOMER, null, values);
        customer.setId(id);
        db.close();

        return id;
    }

    public void deleteCustomer(long id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CUSTOMER, COLUMN_ID + "=" + id, null);
        db.close();
    }

    public long updateCustomer(Customer customer) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_QRCODE, customer.getQrCode());
        values.put(COLUMN_BILLINGADDRESSFIRSTLINE, customer.getBillingAddressFirstLine());
        values.put(COLUMN_BILLINGADDRESSSECONDLINE, customer.getBillingAddressSecondLine());
        values.put(COLUMN_BILLINGADDRESSCITY, customer.getBillingAddressCity());
        values.put(COLUMN_BILLINGADDRESSSTATE, customer.getmBillingAddressState());
        values.put(COLUMN_BILLINGADDRESSZIP, customer.getmBillingAddressZip());
        values.put(COLUMN_EMAIL, customer.getEmail());
        values.put(COLUMN_HASGOLDSTATUS, customer.getmHasGoldStatus());
        values.put(COLUMN_FIRSTNAME, customer.getFirstName());
        values.put(COLUMN_LASTNAME, customer.getLastName());
        if (customer.getRewardExpirationDate() != null) {
            values.put(COLUMN_REWARDEXPIRATIONDATE, customer.getRewardExpirationDate().getTime());
        }
        values.put(COLUMN_REWARDBALANCE, customer.getRewardsBalance());

        SQLiteDatabase db = this.getWritableDatabase();

        long id = db.update(TABLE_CUSTOMER, values, COLUMN_ID + "=" + customer.getId(), null);
        db.close();

        return id;
    }

    public long addPurchase(Purchase purchase) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_GOLDCREDITAPPLIED, (purchase.getGoldCreditApplied() == true ? 1 : 0));
        if (purchase.getPurchaseDate() != null) {
            values.put(COLUMN_PURCHASEDATE, purchase.getPurchaseDate().getTime());
        }
        values.put(COLUMN_REWARDAMOUNTREDEEMED, purchase.getRewardAmountRedeemed());
        values.put(COLUMN_TOTALPURCHASEAMOUNT, purchase.getTotalPurchaseAmount());
        values.put(COLUMN_CUSTOMERID, purchase.getmCustomerId());
        values.put(COLUMN_TOTAL_PURCHASEAMOUNTAFTERDISCOUNTS, purchase.getmTotalPurchaseAmountAfterDiscounts());

        SQLiteDatabase db = this.getWritableDatabase();

        long id = db.insert(TABLE_PURCHASE, null, values);
        purchase.setId(id);
        db.close();

        return id;
    }

    public long updatePurchase(Purchase purchase) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_GOLDCREDITAPPLIED, (purchase.getGoldCreditApplied() == true ? 1 : 0));
        if (purchase.getPurchaseDate() != null) {
            values.put(COLUMN_PURCHASEDATE, purchase.getPurchaseDate().getTime());
        }
        values.put(COLUMN_REWARDAMOUNTREDEEMED, purchase.getRewardAmountRedeemed());
        values.put(COLUMN_TOTALPURCHASEAMOUNT, purchase.getTotalPurchaseAmount());
        values.put(COLUMN_CUSTOMERID, purchase.getmCustomerId());
        values.put(COLUMN_TOTAL_PURCHASEAMOUNTAFTERDISCOUNTS, purchase.getmTotalPurchaseAmountAfterDiscounts());

        SQLiteDatabase db = this.getWritableDatabase();

        long id = db.update(TABLE_CUSTOMER, values, COLUMN_ID + "=" + purchase.getId(), null);
        purchase.setId(id);
        db.close();

        return id;
    }

    public long addPayment(Payment payment) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_PAYMENTAMOUNT, payment.getmPaymentAmount());
        values.put(COLUMN_CARDACCOUNTNUMBER, payment.getmCardAccountNumber());
        if (payment.getmCardExpirationDate() != null) {
            values.put(COLUMN_CARDEXPIRATIONDATE, payment.getmCardExpirationDate().getTime());
        }
        values.put(COLUMN_CARDHOLDERNAME, payment.getmCardHolderName());
        values.put(COLUMN_CARDSECURITYCODE, payment.getmCardSecurityCode());
        if (payment.getmPaymentDate() != null) {
            values.put(COLUMN_PAYMENTDATE, payment.getmPaymentDate().getTime());
        }
        values.put(COLUMN_PURCHASEID, payment.getmPurchaseId());

        SQLiteDatabase db = this.getWritableDatabase();

        long id = db.insert(TABLE_PAYMENT, null, values);
        payment.setID(id);
        db.close();

        return id;
    }

    public long addSaleItem(SaleItem saleItem) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRICEPAID, saleItem.getItemPrice());
        values.put(COLUMN_SIZEID, saleItem.getmSizeId());
        values.put(COLUMN_PURCHASEID, saleItem.getmPurchaseId());
        values.put(COLUMN_SMOOTHIETYPEID, saleItem.getmSmoothieTypeId());

        SQLiteDatabase db = this.getWritableDatabase();

        long id = db.insert(TABLE_SALE_ITEM, null, values);
        saleItem.setID(id);

        db.close();

        return id;
    }

    // Returns an ArrayList since QR codes can be entered manually (no guarantee of uniqueness)
    public ArrayList<Customer> getCustomerByQrCode(String qr_code) {
        String query = "SELECT * FROM " + TABLE_CUSTOMER + " WHERE " + COLUMN_QRCODE + " = '" + qr_code + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Customer> customerList = new ArrayList<>();
        while(cursor.moveToNext()) {
            Customer customer = new Customer();
            customerList.add(customer);
            customer.setId(Integer.parseInt((cursor.getString(cursor.getColumnIndex(COLUMN_ID)))));
            customer.setQrCode((cursor.getString(cursor.getColumnIndex(COLUMN_QRCODE))));
            customer.setBillingAddressFirstLine((cursor.getString(cursor.getColumnIndex(COLUMN_BILLINGADDRESSFIRSTLINE))));
            customer.setBillingAddressSecondLine((cursor.getString(cursor.getColumnIndex(COLUMN_BILLINGADDRESSSECONDLINE))));
            customer.setBillingAddressCity((cursor.getString(cursor.getColumnIndex(COLUMN_BILLINGADDRESSCITY))));
            customer.setmBillingAddressState((cursor.getString(cursor.getColumnIndex(COLUMN_BILLINGADDRESSSTATE))));
            customer.setmBillingAddressZip((cursor.getInt(cursor.getColumnIndex(COLUMN_BILLINGADDRESSZIP))));
            customer.setEmail((cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))));
            customer.setFirstName((cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME))));
            customer.setLastName((cursor.getString(cursor.getColumnIndex(COLUMN_LASTNAME))));
            customer.setmHasGoldStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_HASGOLDSTATUS))));
            customer.setRewardExpirationDate(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_REWARDEXPIRATIONDATE))));
            customer.setRewardsBalance((cursor.getDouble(cursor.getColumnIndex(COLUMN_REWARDBALANCE))));
        }
        cursor.close();
        db.close();
        return customerList;
    }

    public  ArrayList<Customer> getCustomers(long customer_id) {
        String query = "";

        if (customer_id == -1) {
            query = "Select * FROM " + TABLE_CUSTOMER;
        }
        else {
            query = "Select * FROM " + TABLE_CUSTOMER + " WHERE " + COLUMN_ID + " = " + customer_id;
        }
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Customer> customerList = new ArrayList<Customer>();

        while (cursor.moveToNext()) {
            Customer customer = new Customer();
            customerList.add(customer);
            customer.setId(Integer.parseInt((cursor.getString(cursor.getColumnIndex(COLUMN_ID)))));
            customer.setQrCode((cursor.getString(cursor.getColumnIndex(COLUMN_QRCODE))));
            customer.setBillingAddressFirstLine((cursor.getString(cursor.getColumnIndex(COLUMN_BILLINGADDRESSFIRSTLINE))));
            customer.setBillingAddressSecondLine((cursor.getString(cursor.getColumnIndex(COLUMN_BILLINGADDRESSSECONDLINE))));
            customer.setBillingAddressCity((cursor.getString(cursor.getColumnIndex(COLUMN_BILLINGADDRESSCITY))));
            customer.setmBillingAddressState((cursor.getString(cursor.getColumnIndex(COLUMN_BILLINGADDRESSSTATE))));
            customer.setmBillingAddressZip((cursor.getInt(cursor.getColumnIndex(COLUMN_BILLINGADDRESSZIP))));
            customer.setEmail((cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))));
            customer.setFirstName((cursor.getString(cursor.getColumnIndex(COLUMN_FIRSTNAME))));
            customer.setLastName((cursor.getString(cursor.getColumnIndex(COLUMN_LASTNAME))));
            customer.setmHasGoldStatus(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_HASGOLDSTATUS))));
            customer.setRewardExpirationDate(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_REWARDEXPIRATIONDATE))));
            customer.setRewardsBalance((cursor.getDouble(cursor.getColumnIndex(COLUMN_REWARDBALANCE))));

            String totalPurchasesQuery = "Select total(" + COLUMN_TOTAL_PURCHASEAMOUNTAFTERDISCOUNTS + ") AS ALL_PURCHASES FROM "
                    + TABLE_PURCHASE + " where " + COLUMN_CUSTOMERID + " = " + customer.getId() +
                    " and strftime('%Y', " + COLUMN_PURCHASEDATE + "/1000, 'unixepoch') >= strftime('%Y', 'now')";
            Cursor totalPurchasesCursor = db.rawQuery(totalPurchasesQuery, null);
            totalPurchasesCursor.moveToFirst();
            customer.setSpentToDate(Double.parseDouble((totalPurchasesCursor.getString(totalPurchasesCursor.getColumnIndex("ALL_PURCHASES")))));
            totalPurchasesCursor.close();
        }
        cursor.close();
        db.close();
        return customerList;
    }

    public  ArrayList<Purchase> getPurchases(long customer_id) {

        String query = query = "Select * FROM " + TABLE_PURCHASE + " WHERE " + COLUMN_CUSTOMERID + " = " + customer_id;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Purchase> purchaseList = new ArrayList<Purchase>();

        while (cursor.moveToNext()) {
            Purchase purchase = new Purchase(customer_id);
            purchaseList.add(purchase);
            purchase.setGoldCreditApplied(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_GOLDCREDITAPPLIED))));
            purchase.setRewardAmountRedeemed(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_REWARDAMOUNTREDEEMED))));
            purchase.setTotalPurchaseAmount(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_TOTALPURCHASEAMOUNT))));
            purchase.setPurchaseDate(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_PURCHASEDATE))));
            purchase.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
            purchase.setmTotalPurchaseAmountAfterDiscounts(cursor.getFloat(cursor.getColumnIndex(COLUMN_TOTAL_PURCHASEAMOUNTAFTERDISCOUNTS)));
        }
        cursor.close();
        db.close();

        return purchaseList;
    }

    public  Purchase getPurchase(long purchase_id) {

        String query = query = "Select * FROM " + TABLE_PURCHASE + " WHERE " + COLUMN_ID + " = " + purchase_id;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Purchase purchase = null;
        while (cursor.moveToNext()) {
            purchase = new Purchase(cursor.getInt(((cursor.getColumnIndex(COLUMN_CUSTOMERID)))));
            purchase.setGoldCreditApplied(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_GOLDCREDITAPPLIED))));
            purchase.setRewardAmountRedeemed(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_REWARDAMOUNTREDEEMED))));
            purchase.setTotalPurchaseAmount(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_TOTALPURCHASEAMOUNT))));
            purchase.setPurchaseDate(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_PURCHASEDATE))));
            purchase.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
            purchase.setmTotalPurchaseAmountAfterDiscounts(cursor.getFloat(cursor.getColumnIndex(COLUMN_TOTAL_PURCHASEAMOUNTAFTERDISCOUNTS)));
        }
        cursor.close();
        db.close();

        return purchase;
    }

    public  ArrayList<Payment> getPayments(long paymentId) {

        String query = query = "Select * FROM " + TABLE_PAYMENT + " WHERE " + COLUMN_ID + " = " + paymentId;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        ArrayList<Payment> paymentList = new ArrayList<Payment>();

        while (cursor.moveToNext()) {
            Payment payment = new Payment();
            paymentList.add(payment);
            payment.setmPaymentAmount(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENTAMOUNT))));
            payment.setmCardAccountNumber(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_CARDACCOUNTNUMBER))));
            payment.setmCardExpirationDate(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_CARDEXPIRATIONDATE))));
            payment.setmPaymentDate(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_PAYMENTDATE))));
            payment.setmCardHolderName(cursor.getString(cursor.getColumnIndex(COLUMN_CARDHOLDERNAME)));
            payment.setmCardSecurityCode(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_CARDSECURITYCODE))));
            payment.setID(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
        }
        cursor.close();
        db.close();

        return paymentList;
    }
}
