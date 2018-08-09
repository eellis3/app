package edu.gatech.seclass.scm.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import edu.gatech.seclass.scm.Money;
import edu.gatech.seclass.scm.activities.CustomerInfoActivity;
import edu.gatech.seclass.scm.models.Customer;
import edu.gatech.seclass.scm.R;
import edu.gatech.seclass.scm.SmoothieCartManagerApp;
import edu.gatech.seclass.scm.activities.EditCustomerActivity;
import edu.gatech.seclass.scm.activities.PurchaseHistoryActivity;
import edu.gatech.seclass.scm.activities.SelectPurchaseItemActivity;

/**
 * Created by James on 10/22/2015.
 */
public class CustomerListAdapter extends ArrayAdapter<Customer> {

    private int resourceId;

    // String contains the positions of items to hide (supports list filtering as you type)
    private ArrayList<Integer> positionsToHide;

    // Add a position to hide
    public void addHidePosition(int position) {
        this.positionsToHide.add(position);
    }

    // Clear all registered hide positions
    public void clearHidePositions() {
        this.positionsToHide.clear();
    }

    public CustomerListAdapter(Context context, int resource, List<Customer> customers) {
        super(context, resource, customers);
        this.resourceId = resource;
        this.positionsToHide = new ArrayList<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Customer currentCustomer = this.getItem(position);

        View listItemView = convertView;

        if (listItemView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listItemView = inflater.inflate(this.resourceId, null);
        }

        if(this.positionsToHide.contains(position)) {
            listItemView.setLayoutParams(new AbsListView.LayoutParams(-1,1));
            listItemView.setVisibility(View.GONE);
            return listItemView;
        }
        else {

            listItemView.setVisibility(View.VISIBLE);
            listItemView.setLayoutParams(new AbsListView.LayoutParams(-1, -2));

            TextView customerNameView = (TextView) listItemView.findViewById(R.id.customerNameButton);
            customerNameView.setText(currentCustomer.getFirstName() + " " + currentCustomer.getLastName());

            TextView emailView = (TextView) listItemView.findViewById(R.id.customerEmail);
            emailView.setText(currentCustomer.getEmail());

            TextView spentView = (TextView) listItemView.findViewById(R.id.customerSpent);
            spentView.setText(Money.doubleToDollar(currentCustomer.getSpentToDate()));
            if(currentCustomer.getmHasGoldStatus())
                spentView.setTextColor(Color.YELLOW);
            else
                spentView.setTextColor(Color.parseColor("#696969"));

            TextView creditView = (TextView) listItemView.findViewById(R.id.customerCredit);
            creditView.setText(Money.doubleToDollar(currentCustomer.getRewardsBalance()));


            // Set up click handlers for each list item

            Button editButton = (Button) listItemView.findViewById(R.id.editButton);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent editCustomerIntent = new Intent(getContext(), EditCustomerActivity.class);
                    editCustomerIntent.putExtra("customer_id", currentCustomer.getId());
                    getContext().startActivity(editCustomerIntent);
                }
            });


            Button historyButton = (Button) listItemView.findViewById(R.id.historyButton);
            historyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent purchaseHistoryIntent = new Intent(getContext(), PurchaseHistoryActivity.class);
                    purchaseHistoryIntent.putExtra("customerId", currentCustomer.getId());
                    getContext().startActivity(purchaseHistoryIntent);
                }
            });

            Button purchaseButton = (Button) listItemView.findViewById(R.id.purchaseButton);
            purchaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Save current customer
                    SmoothieCartManagerApp app = (SmoothieCartManagerApp) getContext().getApplicationContext();
                    app.setCartCustomer(currentCustomer);
                    Intent purchaseItemIntent = new Intent(getContext(), SelectPurchaseItemActivity.class);
                    purchaseItemIntent.putExtra("customerId", currentCustomer.getId());
                    getContext().startActivity(purchaseItemIntent);
                }
            });
            Button infoButton = (Button) listItemView.findViewById(R.id.customerNameButton);
            infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent customerInfoIntent = new Intent(getContext(), CustomerInfoActivity.class);
                    customerInfoIntent.putExtra("customer_id",currentCustomer.getId());
                    getContext().startActivity(customerInfoIntent);
                }
            });

            return listItemView;
        }
    }
}
