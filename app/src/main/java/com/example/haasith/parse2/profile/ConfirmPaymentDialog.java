package com.example.haasith.parse2.profile;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devmarvel.creditcardentry.library.CreditCard;
import com.devmarvel.creditcardentry.library.CreditCardForm;
import com.example.haasith.parse2.ApplicationData;
import com.example.haasith.parse2.R;
import com.example.haasith.parse2.booking.Booking;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.compat.AsyncTask;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;

import java.util.HashMap;
import java.util.Map;

public class ConfirmPaymentDialog extends DialogFragment implements View.OnClickListener {


    public static final String PUBLISHABLE_KEY = "pk_test_6dbsxJGJT00t1Vd17ecIZyXi";
    private CreditCardForm form;

    TextView cancel;
    TextView yes;
    TextView editBooking;
    TextView total;
    ConfirmPaymentCommunicator communicator;
    int sum;

    Customer customer;


    Stripe stripe = null;

    public static ConfirmPaymentDialog newInstance(int sum) {
        ConfirmPaymentDialog f = new ConfirmPaymentDialog();
        Bundle args = new Bundle();
        args.putInt("sum", sum);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (ConfirmPaymentCommunicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirm_payment, null);
        getDialog().setTitle("Confirm Payment");
        cancel = (TextView) view.findViewById(R.id.cancel);
        yes = (TextView) view.findViewById(R.id.yes);
        editBooking = (TextView) view.findViewById(R.id.editBooking);
        total = (TextView) view.findViewById(R.id.total);
        form = (CreditCardForm) view.findViewById(R.id.credit_card_form);

        sum = getArguments().getInt("sum")*100;
        total.setText("$" + String.valueOf(sum/100));


        //price   = (EditText) view.findViewById(R.id.price);

        cancel.setOnClickListener(this);
        yes.setOnClickListener(this);
        editBooking.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.cancel) {
            dismiss();
        } else if (view.getId() == R.id.yes) {
            chargeCard();
        } else if (view.getId() == R.id.editBooking) {
            Intent intent = new Intent(getActivity(), Booking.class);
            startActivity(intent);
        }

    }


    private void chargeCard() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {


                String customerId = ParseUser.getCurrentUser().getString("customerId");

                // If Id exists then do payment it
                if (customerId != null) {

                    Log.d("stripe customerId exists", customerId);

                    final Map<String, Object> chargeParams = new HashMap<String, Object>();
                    chargeParams.put("amount", sum);
                    chargeParams.put("currency", "usd");
                    chargeParams.put("customer", customerId);
                    chargeParams.put("destination", "acct_179kg2E42B5njx1Y");
                    chargeParams.put("application_fee", 200);

                    new AsyncTask<Void, Void, Void>() {

                        Charge charge;


                        @Override
                        protected Void doInBackground(Void... params) {
                            try {
                                com.stripe.Stripe.apiKey = "sk_test_n8kyFez68piLJbbIOW7WW9yo";
                                charge = Charge.create(chargeParams);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        protected void onPostExecute(Void result) {
                            //Log.d("stripe charge info","Card Charged : " + charge.getCreated() + "\nPaid : " + charge.getPaid() );
                        }

                    }.execute();

                    dismiss();
                    communicator.onDialogPayment();
                }


                CreditCard creditCard = form.getCreditCard();
                Card card = new Card(creditCard.getCardNumber(), creditCard.getExpMonth(), creditCard.getExpYear(), creditCard.getSecurityCode());

                final Map<String, Object> defaultCardParams = new HashMap<String, Object>();
                defaultCardParams.put("number", creditCard.getCardNumber());
                defaultCardParams.put("exp_month", creditCard.getExpMonth());
                defaultCardParams.put("exp_year", creditCard.getExpYear());
                defaultCardParams.put("cvc", creditCard.getSecurityCode());

                boolean validation = card.validateCard();
                if (validation) {
                    try {
                        stripe = new Stripe(PUBLISHABLE_KEY);
                        stripe.createToken(
                                card,
                                new TokenCallback() {
                                    public void onSuccess(final Token token) {
                                        // Send token to your server

                                        new AsyncTask<Void, Void, Void>() {

                                            String customerId;

                                            @Override
                                            protected Void doInBackground(Void... params) {
                                                try {

                                                    Log.d("stripe after customerId", "creating customer");
                                                    com.stripe.Stripe.apiKey = "sk_test_n8kyFez68piLJbbIOW7WW9yo";
                                                    Map<String, Object> defaultCustomerParams = new HashMap<String, Object>();
                                                    defaultCustomerParams.put("card", defaultCardParams);
                                                    defaultCustomerParams.put("description", "Description of Customer");
                                                    customer = Customer.create(defaultCustomerParams);

                                                } catch (AuthenticationException e) {
                                                    e.printStackTrace();
                                                } catch (InvalidRequestException e) {
                                                    e.printStackTrace();
                                                } catch (APIConnectionException e) {
                                                    e.printStackTrace();
                                                } catch (CardException e) {
                                                    e.printStackTrace();
                                                } catch (APIException e) {
                                                    e.printStackTrace();
                                                }
                                                return null;
                                            }

                                            protected void onPostExecute(Void result) {


                                                customerId = customer.getId();
                                                Log.d("stripe after customerId", customerId);

                                                ParseUser.getCurrentUser().put("customerId", customerId);
                                                ParseUser.getCurrentUser().saveInBackground();

                                                Log.d("stripe onsuccess", token.toString());
                                                final Map<String, Object> chargeParams = new HashMap<String, Object>();
                                                chargeParams.put("amount", sum);
                                                chargeParams.put("currency", "usd");
                                                chargeParams.put("customer", customerId);
                                                chargeParams.put("destination", "acct_179kg2E42B5njx1Y");
                                                chargeParams.put("application_fee", sum*0.20);

                                                //chargeParams.put("card", token.getId()); //Token obtained in onSuccess() TokenCallback method of

                                                new AsyncTask<Void, Void, Void>() {

                                                    Charge charge;

                                                    @Override
                                                    protected Void doInBackground(Void... params) {
                                                        try {
                                                            com.stripe.Stripe.apiKey = ApplicationData.SECRET_KEY;
                                                            charge = Charge.create(chargeParams);
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                        return null;
                                                    }

                                                    protected void onPostExecute(Void result) {
                                                        //Log.d("stripe charge info","Card Charged : " + charge.getCreated() + "\nPaid : " + charge.getPaid() );
                                                    }

                                                }.execute();
                                            }

                                        }.execute();

                                    }

                                    public void onError(Exception error) {
                                        // Show localized error message

                                    }
                                }
                        );
                    } catch (AuthenticationException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            protected void onPostExecute(Void result) {

                dismiss();
                communicator.onDialogPayment();

            }

        }.execute();
    }

}


interface ConfirmPaymentCommunicator {

    public void onDialogPayment();
}
