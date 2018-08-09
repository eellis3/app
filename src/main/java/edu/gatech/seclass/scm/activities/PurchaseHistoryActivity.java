package edu.gatech.seclass.scm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.List;

import edu.gatech.seclass.scm.models.Purchase;
import edu.gatech.seclass.scm.R;
import edu.gatech.seclass.scm.adapters.PurchaseHistoryListAdapter;
import edu.gatech.seclass.scm.data.DbAccessProvider;

public class PurchaseHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the customer id passed in the intent
        Intent intent = getIntent();
        long customerId = intent.getLongExtra("customerId", -1);

        DbAccessProvider provider = new DbAccessProvider(this);

        List<Purchase> customerPurchases = provider.getPurchases(customerId);
        PurchaseHistoryListAdapter adapter = new PurchaseHistoryListAdapter(this, R.layout.purchase_hist_list_item, customerPurchases);
        ListView historyListView = (ListView) findViewById(R.id.purchaseHistoryList);
        historyListView.setAdapter(adapter);
    }
}
