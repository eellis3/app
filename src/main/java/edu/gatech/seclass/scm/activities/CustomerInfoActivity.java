package edu.gatech.seclass.scm.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import edu.gatech.seclass.scm.Money;
import edu.gatech.seclass.scm.SmoothieCartManagerApp;
import edu.gatech.seclass.scm.models.Customer;
import edu.gatech.seclass.scm.R;
import edu.gatech.seclass.scm.data.DbAccessProvider;

public class CustomerInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the customer information
        long customerId = getIntent().getLongExtra("customer_id", -1);

        DbAccessProvider provider = new DbAccessProvider(this);

        ArrayList<Customer> customers = provider.getCustomers(customerId);
        final Customer customer = customers.get(0);

        final TextView QRcode = (TextView) findViewById(R.id.showCustomerID);
        QRcode.setText(customer.getQrCode());

        final TextView showFirstName = (TextView) findViewById(R.id.showFirstName);
        showFirstName.setText(customer.getFirstName());

        final TextView showLastName = (TextView) findViewById(R.id.showLastName);
        showLastName.setText(customer.getLastName());

        final TextView showEmail = (TextView) findViewById(R.id.showEmail);
        showEmail.setText(customer.getEmail());

        final TextView showAddress1 = (TextView) findViewById(R.id.showAddress1);
        showAddress1.setText(customer.getBillingAddressFirstLine());

        final TextView showAddress2 = (TextView) findViewById(R.id.showAddress2);
        showAddress2.setText(customer.getBillingAddressSecondLine());

        final TextView showZip = (TextView) findViewById(R.id.showZip);
        showZip.setText(customer.getmBillingAddressZip().toString());

        final TextView showState = (TextView) findViewById(R.id.showState);
        showState.setText(customer.getmBillingAddressState());

        final TextView showCity = (TextView) findViewById(R.id.showCity);
        showCity.setText(customer.getBillingAddressCity());

        final TextView customerCredit = (TextView) findViewById(R.id.customerCredit);
        customerCredit.setText(Money.doubleToDollar(customer.getRewardsBalance()));

        final TextView customerSpent = (TextView) findViewById(R.id.customerSpent);
        customerSpent.setText(Money.doubleToDollar(customer.getSpentToDate()));

        final TextView goldStatus = (TextView) findViewById(R.id.goldStatus);
        goldStatus.setText(String.valueOf(customer.getmHasGoldStatus()));
        if(customer.getmHasGoldStatus())
            goldStatus.setTextColor(Color.YELLOW);
        else
            goldStatus.setTextColor(Color.parseColor("#696969"));

        //hide rewards date if no credit.
        final TextView CreditExpires = (TextView) findViewById(R.id.CreditExpires);
        if(customer.getRewardsBalance()>0) {
            CreditExpires.setVisibility(View.VISIBLE);
            CreditExpires.setText(customer.getRewardExpirationDate().toString());
        }
        else
            CreditExpires.setVisibility(View.INVISIBLE);


        // Set up click handlers for each list item
        Button editButton = (Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editCustomerIntent = new Intent(CustomerInfoActivity.this, EditCustomerActivity.class);
                editCustomerIntent.putExtra("customer_id", customer.getId());
                startActivity(editCustomerIntent);
            }
        });

        Button historyButton = (Button) findViewById(R.id.historyButton);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent purchaseHistoryIntent = new Intent(CustomerInfoActivity.this, PurchaseHistoryActivity.class);
                purchaseHistoryIntent.putExtra("customerId", customer.getId());
                startActivity(purchaseHistoryIntent);
            }
        });

        Button purchaseButton = (Button) findViewById(R.id.purchaseButton);
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save current customer
                SmoothieCartManagerApp app = (SmoothieCartManagerApp) getApplicationContext();
                app.setCartCustomer(customer);
                Intent purchaseItemIntent = new Intent(CustomerInfoActivity.this, SelectPurchaseItemActivity.class);
                purchaseItemIntent.putExtra("customerId", customer.getId());
                startActivity(purchaseItemIntent);
            }
        });

    }




}
