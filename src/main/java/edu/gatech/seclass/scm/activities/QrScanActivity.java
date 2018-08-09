package edu.gatech.seclass.scm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import edu.gatech.seclass.scm.R;
import edu.gatech.seclass.scm.data.DbAccessProvider;
import edu.gatech.seclass.scm.models.Customer;
import edu.gatech.seclass.services.*;


public class QrScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Set up mock scan button
        Button qrCodeButton = (Button) findViewById(R.id.getQrCodeButton);
        qrCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = QRCodeService.scanQRCode();

                // Quick and dirty solution to obtain customer id, should actually be a sql query
                DbAccessProvider provider = new DbAccessProvider(QrScanActivity.this);
                ArrayList<Customer> customers = provider.getCustomerByQrCode(code);

                if(customers.size() > 0) {
                    // Should only be one match in most cases
                    Intent customerInfoActivity = new Intent(QrScanActivity.this, CustomerInfoActivity.class);
                    customerInfoActivity.putExtra("customer_id", customers.get(0).getId());
                    startActivity(customerInfoActivity);
                }
                else {
                    // Notify user of no matching QR code
                    Toast.makeText(QrScanActivity.this, "No customer found for QR code returned", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
