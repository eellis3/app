package edu.gatech.seclass.scm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.gatech.seclass.scm.models.SaleItem;
import edu.gatech.seclass.scm.R;
import edu.gatech.seclass.scm.SmoothieCartManagerApp;

public class OtherDetailActivity extends AppCompatActivity {

    public SaleItem saleItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saleItem = new SaleItem();

        // Set up add to cart button
        Button addToCartButton = (Button) findViewById(R.id.otherAddToCartButton);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set to -1 for custom smoothie
                saleItem.setmSmoothieTypeId(-1);
                // Add to cart
                SmoothieCartManagerApp app = (SmoothieCartManagerApp) getApplicationContext();
                app.addToCart(saleItem);

                Intent cartIntent = new Intent(OtherDetailActivity.this, CartActivity.class);
                startActivity(cartIntent);
            }
        });

        final EditText priceInput = (EditText) findViewById(R.id.enterPriceInput);
        priceInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Interested in this one

                // Attempt to convert text to a valid double
                //made xml set to decimal unsigned.  need to account for $0
                try
                {
                    double price = Double.parseDouble(priceInput.getText().toString());
                    saleItem.setPriceOverride(price);
                }
                catch (Exception e)
                {
                    // Needs some type of error handling
                }
            }
        });
    }

}
