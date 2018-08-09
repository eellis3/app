package edu.gatech.seclass.scm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.gatech.seclass.scm.models.SaleItem;
import edu.gatech.seclass.scm.R;

/**
 * Created by James on 10/25/2015.
 */
public class CartListAdapter extends ArrayAdapter<SaleItem> {

    private int resourceId;

    public CartListAdapter(Context context, int resource, List<SaleItem> objects) {
        super(context, resource, objects);

        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final SaleItem item = this.getItem(position);

        View listItemView = convertView;

        if(listItemView == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listItemView = inflater.inflate(this.resourceId, null);
        }

        // Smoothie (or other) field
        TextView itemNameView = (TextView) listItemView.findViewById(R.id.itemName);
        if(item.getmSmoothieTypeId() == 0)
        {
            itemNameView.setText("Orange Smoothie");
        }
        else if(item.getmSmoothieTypeId() == 1)
        {
            itemNameView.setText("Cherry Smoothie");
        }
        else if(item.getmSmoothieTypeId() == 2)
        {
            itemNameView.setText("Berry Smoothie");
        }
        else
        {
            itemNameView.setText("Custom Smoothie");
        }

        // Price field
        TextView priceView = (TextView) listItemView.findViewById(R.id.cartPriceValue);
        priceView.setText("$" + Double.toString(item.getItemPrice()));

        return listItemView;
    }
}
