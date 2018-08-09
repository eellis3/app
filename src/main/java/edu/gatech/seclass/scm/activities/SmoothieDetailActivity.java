package edu.gatech.seclass.scm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import edu.gatech.seclass.scm.models.CostCalculator;
import edu.gatech.seclass.scm.models.SaleItem;
import edu.gatech.seclass.scm.R;
import edu.gatech.seclass.scm.SmoothieCartManagerApp;

public class SmoothieDetailActivity extends AppCompatActivity {

    private SaleItem smoothie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smoothie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        smoothie = new SaleItem();

        // Set up size checkboxes
        final CheckBox smallBox = (CheckBox) findViewById(R.id.smallCheckBox);
        final CheckBox mediumBox = (CheckBox) findViewById(R.id.mediumCheckBox);
        final CheckBox largeBox = (CheckBox) findViewById(R.id.largeCheckBox);



        // Set up add to cart button
        Button addToCartButton = (Button) findViewById(R.id.smoothieAddToCartButton);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //save flavor on click
                Spinner flavor = (Spinner) findViewById(R.id.flavorDropdown);
                int smoothieTypeId = flavor.getSelectedItemPosition();
                smoothie.setmSmoothieTypeId(smoothieTypeId);
                // Save item to cart
                SmoothieCartManagerApp app = (SmoothieCartManagerApp) getApplicationContext();
                app.addToCart(smoothie);

                Intent cartIntent = new Intent(SmoothieDetailActivity.this, CartActivity.class);
                startActivity(cartIntent);
            }
        });

        final TextView priceView = (TextView) findViewById(R.id.priceValue);

        smallBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Two cases: already checked and not checked
                if (!isChecked) {
                    smallBox.setChecked(false);
                } else {
                    smallBox.setChecked(true);
                    mediumBox.setChecked(false);
                    largeBox.setChecked(false);

                    smoothie.setmSizeId(1);

                    smoothie.setPriceOverride(CostCalculator.getItemPrice2(smoothie));

                    priceView.setText(Double.toString(smoothie.getItemPrice()));

                }
            }
        });

        mediumBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    mediumBox.setChecked(false);
                } else {
                    mediumBox.setChecked(true);
                    smallBox.setChecked(false);
                    largeBox.setChecked(false);

                    smoothie.setmSizeId(2);

                    smoothie.setPriceOverride(CostCalculator.getItemPrice2(smoothie));

                    priceView.setText(Double.toString(smoothie.getItemPrice()));
                }
            }
        });

        largeBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    largeBox.setChecked(false);
                } else {
                    largeBox.setChecked(true);
                    smallBox.setChecked(false);
                    mediumBox.setChecked(false);

                    smoothie.setmSizeId(3);

                    smoothie.setPriceOverride(CostCalculator.getItemPrice2(smoothie));

                    priceView.setText(Double.toString(smoothie.getItemPrice()));
                }
            }
        });
    }

}
