package edu.gatech.seclass.scm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import edu.gatech.seclass.scm.R;

public class SelectPurchaseItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_purchase_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up click handlers
        RelativeLayout smoothieLayout = (RelativeLayout) findViewById(R.id.smoothieWrapper);
        smoothieLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smoothieDetailActivity = new Intent(SelectPurchaseItemActivity.this, SmoothieDetailActivity.class);
                startActivity(smoothieDetailActivity);
            }
        });

        RelativeLayout otherLayout = (RelativeLayout) findViewById(R.id.otherWrapper);
        otherLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherDetailActivity = new Intent(SelectPurchaseItemActivity.this, OtherDetailActivity.class);
                startActivity(otherDetailActivity);
            }
        });
    }

}
