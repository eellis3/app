package edu.gatech.seclass.scm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import edu.gatech.seclass.scm.models.Customer;
import edu.gatech.seclass.scm.R;
import edu.gatech.seclass.scm.adapters.CustomerListAdapter;
import edu.gatech.seclass.scm.data.DbAccessProvider;
import edu.gatech.seclass.services.QRCodeService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Manually set title
        getSupportActionBar().setTitle("Home");

        // Set up ListView
        ListView customerList = (ListView) findViewById(R.id.customerList);
        DbAccessProvider provider = new DbAccessProvider(this);
        final CustomerListAdapter adapter = new CustomerListAdapter(this, R.layout.customer_list_item, provider.getCustomers(-1));
        customerList.setAdapter(adapter);

        // Handle case where we are returning from QrScanActivity
        //String qrCode = getIntent().getStringExtra("qrCode");
       /* if(qrCode != null)
        {
            // Hide all customers except the one matching the provided qr code
            adapter.clearHidePositions();
            for(int i = 0; i < adapter.getCount(); i++)
            {
                Customer temp = adapter.getItem(i);
                String tempQrCode = temp.getQrCode();
                if(!(qrCode).toUpperCase().contains(tempQrCode.toUpperCase()))
                {
                    adapter.addHidePosition(i);
                }
            }
                // TODO: Check if customer id code matches qr code.  If it doesn't, hide the position
            }
            adapter.notifyDataSetChanged();
*/

        // Disable initial keyboard opening
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Set up automatic searching
        EditText searchEditText = (EditText) findViewById(R.id.search_input);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Interested in this function
                adapter.clearHidePositions();
                String searchString = s.toString();

                for(int i = 0; i < adapter.getCount(); i++)
                {
                    Customer temp = adapter.getItem(i);
                    String name = temp.getFirstName() + " " + temp.getLastName();
                    if(!(name).toUpperCase().contains(searchString.toUpperCase()))
                    {
                        adapter.addHidePosition(i);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        // Set up QR code button
        ImageView QRButton = (ImageView) findViewById(R.id.scan_customer_card);
        QRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent qr = new Intent(MainActivity.this, QrScanActivity.class);
                startActivity(qr);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            // Go to add customer activity
            Intent addCustomerIntent = new Intent(MainActivity.this, AddCustomerActivity.class);
            startActivity(addCustomerIntent);
            return false;
        }

        return super.onOptionsItemSelected(item);
    }
}
