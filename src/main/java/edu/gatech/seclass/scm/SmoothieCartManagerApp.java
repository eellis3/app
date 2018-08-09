package edu.gatech.seclass.scm;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.scm.models.Customer;
import edu.gatech.seclass.scm.models.SaleItem;


/**
 * Created by James on 10/25/2015.
 */
public class SmoothieCartManagerApp extends Application {
    ArrayList<SaleItem> mCart;
    Customer cartCustomer;

    @Override
    public void onCreate() {
        super.onCreate();

        mCart = new ArrayList<>();
    }

    public void clearCart()
    {
        mCart.clear();
    }

    public void addToCart(SaleItem item)
    {
        mCart.add(item);
    }

    public List<SaleItem> getCartContents() {
        return this.mCart;
    }

    public Customer getCartCustomer() {
        return cartCustomer;
    }

    public void setCartCustomer(Customer cartCustomer) {
        this.cartCustomer = cartCustomer;
    }
}
