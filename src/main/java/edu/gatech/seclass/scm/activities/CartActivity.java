package edu.gatech.seclass.scm.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Callable;

import edu.gatech.seclass.scm.Money;
import edu.gatech.seclass.scm.models.Customer;
import edu.gatech.seclass.scm.models.Payment;
import edu.gatech.seclass.scm.models.Purchase;
import edu.gatech.seclass.scm.models.SaleItem;
import edu.gatech.seclass.scm.R;
import edu.gatech.seclass.scm.SmoothieCartManagerApp;
import edu.gatech.seclass.scm.adapters.CartListAdapter;
import edu.gatech.seclass.scm.data.DbAccessProvider;
import edu.gatech.seclass.services.CreditCardService;
import edu.gatech.seclass.services.EmailService;
import edu.gatech.seclass.services.PaymentService;

import static edu.gatech.seclass.services.EmailService.sendMail;
import static edu.gatech.seclass.services.PaymentService.processTransaction;

public class CartActivity extends AppCompatActivity {

    private ProgressDialog progressSpinner;

    private void returnToMain() {
        // Clear cart
        final SmoothieCartManagerApp app = (SmoothieCartManagerApp) getApplicationContext();
        app.clearCart();

        progressSpinner.setMessage("Success!");
        try {
            Thread.sleep(300);
        }
        catch (Exception e) {

        }

        // Notify user
        Toast.makeText(CartActivity.this, "Payment process complete.", Toast.LENGTH_LONG).show();

        // Close spinner
        progressSpinner.dismiss();

        // Return to home
        Intent mainActivity = new Intent(CartActivity.this, MainActivity.class);
        startActivity(mainActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        // Set up cart item list
        final SmoothieCartManagerApp app = (SmoothieCartManagerApp) getApplicationContext();
        CartListAdapter adapter = new CartListAdapter(this, R.layout.cart_item, app.getCartContents());
        ListView cartListView = (ListView) findViewById(R.id.cartList);
        cartListView.setAdapter(adapter);

        // Set up summary fields
        List<SaleItem> cartContents = app.getCartContents();
        //these 3 values to be shown on transaction history
        double totalBefore = 0;

        for(SaleItem item : cartContents)
        {
            totalBefore += item.getItemPrice();
        }
        TextView totalBeforeView = (TextView) findViewById(R.id.totalBeforeValue);
        totalBeforeView.setText(Money.doubleToDollar(totalBefore));

        final Customer currentCustomer = app.getCartCustomer();
        TextView goldStatusView = (TextView) findViewById(R.id.goldPercentValue);
        if(currentCustomer.getmHasGoldStatus())
        {
            goldStatusView.setText("5% = " + Money.doubleToDollar(.95 * totalBefore));
        }
        else
        {
            goldStatusView.setText("0%");
        }

        //setup calendar
        final Calendar calReset = Calendar.getInstance();
        final Calendar cal = Calendar.getInstance();

        //if credit expired, reset to $0 and delete date.
        if(calReset.after(currentCustomer.getRewardExpirationDate())){
            currentCustomer.setRewardsBalance(0);
            //reset date.
            calReset.set(1969, 11, 31, 19, 0, 0);
            Date reset = calReset.getTime();
            currentCustomer.setRewardExpirationDate(reset);
            DbAccessProvider provider2 = new DbAccessProvider(CartActivity.this);
            provider2.updateCustomer(currentCustomer);
        }

        //displays credit amount
        TextView creditAvailableView = (TextView) findViewById(R.id.creditAvailableValue);
        creditAvailableView.setText(Money.doubleToDollar(currentCustomer.getRewardsBalance()));


        final TextView finalTotalView = (TextView) findViewById(R.id.finalTotalValue);
        double finalTotal = totalBefore;
        double creditApplied = 0;


        if(currentCustomer.getmHasGoldStatus())
        {
            finalTotal = totalBefore * 0.95;
        }

        if(currentCustomer.getRewardsBalance() > 0)
        {
            if(finalTotal >= currentCustomer.getRewardsBalance())
            {
                // More purchase than credit
                finalTotal = finalTotal - currentCustomer.getRewardsBalance();
                creditApplied = currentCustomer.getRewardsBalance();
            }
            else
            {
                // More credit than purchase
                creditApplied = finalTotal;
                finalTotal = 0;
            }

        }

        finalTotalView.setText(Money.doubleToDollar(finalTotal));


        Button purchaseButton = (Button) findViewById(R.id.purchaseButton);
        final double finalTotal1 = finalTotal;
        final double totalBefore1 = totalBefore;
        final double finalCreditApplied = creditApplied;
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set up db access
                DbAccessProvider provider = new DbAccessProvider(CartActivity.this);

                // Initialize and populate purchase
                final Purchase newPurchase = new Purchase(app.getCartCustomer().getId());
                //need to set purchase to total before discounts
                newPurchase.setTotalPurchaseAmount(totalBefore1);

                //set purchase amount after rewards
                newPurchase.setmTotalPurchaseAmountAfterDiscounts(finalTotal1);

                // Set rest of fields
                newPurchase.setGoldCreditApplied((app.getCartCustomer().getmHasGoldStatus()) ? 1 : 0);

                newPurchase.setRewardAmountRedeemed(finalCreditApplied);

                newPurchase.setPurchaseDate(new Date());

                // Save purchase to db
                long purchaseId = provider.addPurchase(newPurchase);

                // Save individual purchase items to db
                for (SaleItem item : app.getCartContents()) {
                    item.setmPurchaseId((int) purchaseId);
                    provider.addSaleItem(item);
                }

                // Update customer credit amount
                if(finalCreditApplied > 0)
                {
                    currentCustomer.setRewardsBalance(currentCustomer.getRewardsBalance() - finalCreditApplied);
                }

                // Update spent to date
                currentCustomer.setSpentToDate(currentCustomer.getSpentToDate() + finalTotal1);

                // Check for an earned credit
                boolean creditEarned = false;
                if(finalTotal1 >= 50)
                {
                    creditEarned = true;
                    currentCustomer.setRewardsBalance(currentCustomer.getRewardsBalance() + 5);
                    //set expiration date to 1 year later
                    cal.setTime(new Date());
                    cal.add(Calendar.DATE, 366); //minus number would decrement the days
                    Date earnedRewardsExpiryDate = cal.getTime();
                    currentCustomer.setRewardExpirationDate(earnedRewardsExpiryDate);
                }

                // Check for new gold status
                boolean goldStatusEarned = false;
                if(currentCustomer.getSpentToDate() >= 500 && currentCustomer.getmHasGoldStatus() == false)
                {
                    goldStatusEarned = true;
                    currentCustomer.setmHasGoldStatus(1);
                }

                // Actually update customer in database
                provider.updateCustomer(currentCustomer);


                // Now, read customer card
                final boolean finalGoldStatusEarned = goldStatusEarned;
                final boolean finalCreditEarned = creditEarned;
                Callback cardReadCb = new Callback() {
                    @Override
                    public void call() {

                        //dont charge credit card if free
                        if(finalTotal1 == 0) {
                            Toast.makeText(CartActivity.this, "Free Smoothies!  Not being charged!", Toast.LENGTH_LONG).show();
                            returnToMain();
                        }
                        final String cardReadResult = (String) this.mArgs;
                        if(cardReadResult.equals("ERR")) {
                            // Repeat operation
                            progressSpinner.setMessage("An error occured while reading card.  Reattempting..");
                            CardReadTask cardReadTask = new CardReadTask(this);
                            cardReadTask.execute();
                        }
                        else {
                            // Done with card read
                            progressSpinner.setMessage("Card successfully read, processing transaction..");
                            final String[] cardInfo = parseCardInfo(cardReadResult);

                            DateFormat format = new SimpleDateFormat("mmddyyyy", Locale.ENGLISH);
                            Date expiryDate = null;
                            try {
                                expiryDate = format.parse(cardInfo[3]);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            final Date finalExpiryDate = expiryDate;
                            ProcessPaymentTask task = new ProcessPaymentTask(cardInfo[0],
                                    cardInfo[1], cardInfo[2], expiryDate, cardInfo[4], finalTotal1,
                                    new Callback() {
                                @Override
                                public void call() {
                                    Boolean paymentResult = (Boolean) this.mArgs;

                                    if(!paymentResult)
                                    {
                                        // Retry
                                        progressSpinner.setMessage("An error occured while processing transaction.  Reattempting..");

                                        ProcessPaymentTask task = new ProcessPaymentTask(cardInfo[0],
                                                cardInfo[1], cardInfo[2], finalExpiryDate, cardInfo[4], finalTotal1, this);
                                        task.execute();
                                    }
                                    else
                                    {
                                        // Done processing payment
                                        progressSpinner.setMessage("Transaction successfully processed!");

                                        // Display success momentarily
                                        try {
                                            Thread.sleep(300);
                                        }
                                        catch (Exception e)
                                        {

                                        }

                                        // Save customer payment info
                                        Payment p = new Payment();
                                        p.setmCardHolderName(cardInfo[0] + " " + cardInfo[1]);
                                        p.setmCardAccountNumber(Long.parseLong(cardInfo[2]));
                                        p.setmCardExpirationDate(finalExpiryDate);
                                        p.setmCardSecurityCode(Integer.parseInt(cardInfo[4]));
                                        p.setmPaymentDate(new Date());
                                        p.setmPaymentAmount(newPurchase.getmTotalPurchaseAmountAfterDiscounts());
                                        p.setmPurchaseId(newPurchase.getId());

                                        DbAccessProvider dbAccessProvider = new DbAccessProvider(CartActivity.this);

                                        dbAccessProvider.addPayment(p);

                                        // Send credit and reward emails if necessary
                                        if(finalCreditEarned || finalGoldStatusEarned)
                                        {
                                            if(finalCreditEarned)
                                            {
                                                progressSpinner.setMessage("Sending earned credit notification...");
                                                SendEmailTask sendCreditEmail = new SendEmailTask(currentCustomer.getEmail(), "Congratulations, you have earned a credit.", "You have earned a $5 credit.", new Callback() {
                                                    @Override
                                                    public void call() {
                                                        Boolean emailSuccess = (Boolean) this.mArgs;
                                                        if(!emailSuccess)
                                                        {
                                                            progressSpinner.setMessage("An error occured while sending reward notification.  Reattempting..");
                                                            SendEmailTask task = new SendEmailTask(currentCustomer.getEmail(), "Congratulations, you have earned a credit.", "You have earned a $5 credit.", this);
                                                            task.execute();
                                                        }
                                                        if(emailSuccess) {
                                                            // Done!
                                                            progressSpinner.setMessage("Earned credit email sent.");

                                                            // Check for gold status achievement, and currently not in gold status
                                                            if(finalGoldStatusEarned)
                                                            {
                                                                // Notify of new gold status
                                                                progressSpinner.setMessage("Sending gold status notification email...");
                                                                SendEmailTask sendGoldStatusEmail = new SendEmailTask(currentCustomer.getEmail(), "Congratulations, gold status achieved!", "You have spent more than $500 this year and have achieved gold status", new Callback() {
                                                                    @Override
                                                                    public void call() {
                                                                        Boolean emailResult = (Boolean) this.mArgs;

                                                                        if(emailResult) {
                                                                            // Go back to main
                                                                            returnToMain();
                                                                        }
                                                                        else {
                                                                            progressSpinner.setMessage("An error occured while sending gold status notification.  Reattempting..");
                                                                            SendEmailTask task = new SendEmailTask(currentCustomer.getEmail(), "Congratulations, gold status achieved!", "You have spent more than $500 this year and have achieved gold status", this);
                                                                            task.execute();
                                                                        }
                                                                    }
                                                                });
                                                                sendGoldStatusEmail.execute();
                                                            }
                                                            else
                                                            {
                                                                // Done, go to main activity
                                                                returnToMain();
                                                            }
                                                        }
                                                    }
                                                });
                                                sendCreditEmail.execute();
                                            }
                                            else
                                            {
                                                if(finalGoldStatusEarned)
                                                {
                                                    progressSpinner.setMessage("Sending gold status message...");
                                                    SendEmailTask sendGoldStatusEmail = new SendEmailTask(currentCustomer.getEmail(), "Congratulations, gold status achieved!", "You have spent more than $500 this year and have achieved gold status", new Callback() {
                                                        @Override
                                                        public void call() {
                                                            Boolean emailResult = (Boolean) this.mArgs;

                                                            if(emailResult) {
                                                                // Go back to main
                                                                returnToMain();
                                                            }
                                                            else {
                                                                progressSpinner.setMessage("An error occured while sending gold status notification.  Reattempting..");
                                                                SendEmailTask task = new SendEmailTask(currentCustomer.getEmail(), "Congratulations, gold status achieved!", "You have spent more than $500 this year and have achieved gold status", this);
                                                                task.execute();
                                                            }
                                                        }
                                                    });
                                                    sendGoldStatusEmail.execute();
                                                }
                                                else
                                                {
                                                    returnToMain();
                                                }
                                            }

                                        }
                                        else
                                        {
                                            returnToMain();
                                        }
                                    }
                                }
                            });
                            task.execute();
                        }
                    }
                };

                // Read customer card
                CardReadTask cardReadTask = new CardReadTask(cardReadCb);

                // Set up progress spinner
                progressSpinner = new ProgressDialog(CartActivity.this, ProgressDialog.STYLE_SPINNER);
                progressSpinner.setCancelable(false);
                progressSpinner.setTitle("Executing purchase");
                progressSpinner.setMessage("Reading customer card...");
                progressSpinner.show();
                cardReadTask.execute();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
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
            Intent addItemIntent = new Intent(CartActivity.this, SelectPurchaseItemActivity.class);
            startActivity(addItemIntent);
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    private String[] parseCardInfo(String cardStr) {
        String[] cardInfo = cardStr.split("#");
        return cardInfo;
    }

    //----------------------------------------------------------------------------------------------

    // Utility class to create callback functions.
    private abstract class Callback {
        protected Object mArgs;

        public void setArgs(Object args) {
            mArgs = args;
        }

        public abstract void call();
    }

    // AsyncTask wrappers for cartservices functions (to make them more realistic and enable the
    // design of a practical UI)

    private class SendEmailTask extends AsyncTask<Void, Void, Boolean> {
        private Callback mCallback;
        private String mRecipient;
        private String mSubject;
        private String mBody;

        public SendEmailTask(String recipient, String subject, String body, Callback callback) {
            this.mRecipient = recipient;
            this.mSubject = subject;
            this.mBody = body;
            this.mCallback = callback;
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            long duration = (long) (Math.random() * (3000 - 1000)) + 1000;
            try {
                Thread.sleep(duration);
                return EmailService.sendMail(this.mRecipient, this.mSubject, this.mBody);
            }
            catch (Exception e) {
                return EmailService.sendMail(this.mRecipient, this.mSubject, this.mBody);
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            this.mCallback.setArgs(aBoolean);
            this.mCallback.call();
        }
    }

    private class ProcessPaymentTask extends AsyncTask<Void, Void, Boolean> {
        private Callback mCallback;
        private String mFirstName;
        private String mLastName;
        private String mCCNumber;
        private Date mExpirationDate;
        private String mSecurityCode;
        private double mAmount;


        public ProcessPaymentTask(String firstName, String lastName, String ccNumber,
                                   Date expirationDate, String securityCode, double amount,
                                   Callback callback) {
            this.mFirstName = firstName;
            this.mLastName = lastName;
            this.mCCNumber = ccNumber;
            this.mExpirationDate = expirationDate;
            this.mSecurityCode = securityCode;
            this.mAmount = amount;
            this.mCallback = callback;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // Generate the card read time in milliseconds (between 0.5 and 2 seconds)
            long duration = (long) (Math.random() * (3000 - 1000)) + 1000;
            try {
                Thread.sleep(duration);
                return PaymentService.processTransaction(this.mFirstName, this.mLastName,
                        this.mCCNumber, this.mExpirationDate, this.mSecurityCode, this.mAmount);
            }
            catch (Exception e) {
                return PaymentService.processTransaction(this.mFirstName, this.mLastName,
                        this.mCCNumber, this.mExpirationDate, this.mSecurityCode, this.mAmount);
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            this.mCallback.setArgs(aBoolean);
            this.mCallback.call();
        }
    }

    private class CardReadTask extends AsyncTask<Void, Void, String> {
        private Callback mCallback;

        public CardReadTask(Callback callback) {
            mCallback = callback;
        }

        @Override
        protected String doInBackground(Void... params) {
            // Generate the card read time in milliseconds (between 1 and 3 seconds)
            long duration = (long) (Math.random() * (3000 - 1000)) + 1000;
            try {
                Thread.sleep(duration);
                return CreditCardService.readCard();
            }
            catch (Exception e) {
                return CreditCardService.readCard();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            mCallback.setArgs(s);
            mCallback.call();
        }
    }

}
