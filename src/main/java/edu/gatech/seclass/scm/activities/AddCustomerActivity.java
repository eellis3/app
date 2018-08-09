package edu.gatech.seclass.scm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import edu.gatech.seclass.scm.models.Customer;
import edu.gatech.seclass.scm.R;
import edu.gatech.seclass.scm.data.DbAccessProvider;

public class AddCustomerActivity extends AppCompatActivity {
    int invalidCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ValidateForm(v)) return;

                // Create new customer
                Customer newCustomer = new Customer();

                TextView QRCode = (TextView) findViewById(R.id.customerIDInput);
                String QRCodeActual =getRandom32HexString(32);
                QRCode.setText(QRCodeActual);
                newCustomer.setQrCode(QRCodeActual);

                EditText firstName = (EditText) findViewById(R.id.firstNameInput);
                newCustomer.setFirstName(firstName.getText().toString());

                EditText lastName = (EditText) findViewById(R.id.lastNameInput);
                newCustomer.setLastName(lastName.getText().toString());

                EditText address1 = (EditText) findViewById(R.id.address1Input);
                newCustomer.setBillingAddressFirstLine(address1.getText().toString());

                EditText address2 = (EditText) findViewById(R.id.address2Input);
                newCustomer.setBillingAddressSecondLine(address2.getText().toString());

                EditText email = (EditText) findViewById(R.id.emailInput);
                newCustomer.setEmail(email.getText().toString());

                EditText zip = (EditText) findViewById(R.id.zipInput);
                newCustomer.setmBillingAddressZip(Integer.parseInt(zip.getText().toString()));

                EditText city = (EditText) findViewById(R.id.cityInput);
                newCustomer.setBillingAddressCity(city.getText().toString());

                Spinner state = (Spinner) findViewById(R.id.stateDropdown);
                String stateText = state.getSelectedItem().toString();
                newCustomer.setmBillingAddressState(stateText);
                newCustomer.setmBillingAddressStateSpinnerID(state.getId());

                DbAccessProvider provider = new DbAccessProvider(AddCustomerActivity.this);
                provider.addCustomer(newCustomer);

                // Go to main activity
                Intent mainActivity = new Intent(AddCustomerActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
    }

    private boolean ValidateForm(View v) {
        ViewGroup vg = (ViewGroup)v.getRootView();
        if(!formIsValid(vg)) {
            Toast.makeText(getApplicationContext(), "Please complete all required fields", Toast.LENGTH_LONG).show();
            invalidCount = 0;
            return true;
        }
        return false;
    }

    public boolean formIsValid(ViewGroup vg) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View v = vg.getChildAt(i);
            if (v instanceof ViewGroup) {
                formIsValid((ViewGroup)v);
            } else if (v instanceof EditText) {
                if ((((EditText) v).length() == 0) && (v.getId() != R.id.address2Input)){
                   invalidCount++;
                }
            }
        }
        return invalidCount == 0;
    }

    private String getRandom32HexString(int num){
        DbAccessProvider provider = new DbAccessProvider(this);
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < num){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        String QR = sb.toString().substring(0, num);
        if(provider.getCustomerByQrCode(QR).isEmpty())
            return QR;
        else{
            return getRandom32HexString(num);
        }



    }

}
