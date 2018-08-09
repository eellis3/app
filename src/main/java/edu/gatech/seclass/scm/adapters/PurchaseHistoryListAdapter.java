package edu.gatech.seclass.scm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.gatech.seclass.scm.Money;
import edu.gatech.seclass.scm.models.Purchase;
import edu.gatech.seclass.scm.R;

/**
 * Created by James on 10/23/2015.
 */
public class PurchaseHistoryListAdapter extends ArrayAdapter<Purchase> {

    private int resourceId;

    public PurchaseHistoryListAdapter(Context context, int resource, List<Purchase> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Purchase currentPurchase = this.getItem(position);

        View listItemView = convertView;

        if(listItemView == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listItemView = inflater.inflate(this.resourceId, null);
        }

        // Fill in data
        TextView purchaseDateView = (TextView) listItemView.findViewById(R.id.purchaseDate);
        purchaseDateView.setText(currentPurchase.getPurchaseDate().toString());

        TextView amountView = (TextView) listItemView.findViewById(R.id.purchaseAmount);
        amountView.setText(Money.doubleToDollar(currentPurchase.getTotalPurchaseAmount()));

        TextView goldValue = (TextView) listItemView.findViewById(R.id.goldValue);
        if(currentPurchase.getGoldCreditApplied())
        {
            // Left over must be gold percentage
            double goldDiscountAmount = currentPurchase.getTotalPurchaseAmount() - currentPurchase.getmTotalPurchaseAmountAfterDiscounts() - currentPurchase.getRewardAmountRedeemed();
            goldValue.setText("YES: " + Money.doubleToDollar(goldDiscountAmount));
        }
        else
        {
            goldValue.setText("NO");

        }

        TextView rewardAmountView = (TextView) listItemView.findViewById(R.id.rewardAmount);
        rewardAmountView.setText(Money.doubleToDollar(currentPurchase.getRewardAmountRedeemed()));

        TextView finalPurchaseAmount = (TextView) listItemView.findViewById(R.id.finalAmountValue);
        finalPurchaseAmount.setText(Money.doubleToDollar(currentPurchase.getmTotalPurchaseAmountAfterDiscounts()));

        return listItemView;
    }
}
