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
import java.util.Arrays;

import edu.gatech.seclass.scm.models.Customer;
import edu.gatech.seclass.scm.R;
import edu.gatech.seclass.scm.data.DbAccessProvider;

public class EditCustomerActivity extends AppCompatActivity {
    int invalidCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the customer information

        long customerId = getIntent().getLongExtra("customer_id", -1);

        DbAccessProvider provider = new DbAccessProvider(this);

        ArrayList<Customer> customers = provider.getCustomers(customerId);
        final Customer customer = customers.get(0);

        final TextView QRcode = (TextView) findViewById(R.id.editCustomerIDInput);
        QRcode.setText(customer.getQrCode());

        final EditText firstName = (EditText) findViewById(R.id.editFirstNameInput);
        firstName.setText(customer.getFirstName());

        final EditText lastName = (EditText) findViewById(R.id.editLastNameInput);
        lastName.setText(customer.getLastName());

        final EditText email = (EditText) findViewById(R.id.editEmailInput);
        email.setText(customer.getEmail());

        final EditText address1 = (EditText) findViewById(R.id.editAddress1Input);
        address1.setText(customer.getBillingAddressFirstLine());

        final EditText address2 = (EditText) findViewById(R.id.editAddress2Input);
        address2.setText(customer.getBillingAddressSecondLine());

        final EditText zip = (EditText) findViewById(R.id.editZipInput);
        zip.setText(String.valueOf(customer.getmBillingAddressZip()));

        final EditText city = (EditText) findViewById(R.id.editCityInput);
        city.setText(customer.getBillingAddressCity());

        final Spinner state = (Spinner) findViewById(R.id.editStateDropdown);
        String[] states = getResources().getStringArray(R.array.state_array);
        String customerState = customer.getmBillingAddressState();
        state.setSelection(Arrays.asList(states).indexOf(customerState));

        // Submit button handler
        Button submitButton = (Button) findViewById(R.id.updateButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ValidateForm(v)) return;

                // Update customer
                customer.setFirstName(firstName.getText().toString());
                customer.setLastName(lastName.getText().toString());
                customer.setEmail(email.getText().toString());
                customer.setBillingAddressFirstLine(address1.getText().toString());
                customer.setBillingAddressSecondLine(address2.getText().toString());
                customer.setQrCode(QRcode.getText().toString());
                customer.setmBillingAddressZip(Integer.parseInt(zip.getText().toString()));
                customer.setBillingAddressCity(city.getText().toString());

                String stateStr = state.getSelectedItem().toString();
                customer.setmBillingAddressState(stateStr);
                
                DbAccessProvider dbAccessProvider = new DbAccessProvider(EditCustomerActivity.this);
                dbAccessProvider.updateCustomer(customer);

                // Return to Main Activity
                Intent mainActivity = new Intent(EditCustomerActivity.this, MainActivity.class);
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
                if (((EditText) v).length() == 0 && v.getId() != R.id.editAddress2Input){
                    invalidCount++;
                }
            }
        }
        return invalidCount == 0;
    }

}
