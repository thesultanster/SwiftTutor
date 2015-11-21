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
import com.example.haasith.parse2.R;
import com.example.haasith.parse2.booking.Booking;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.compat.AsyncTask;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;
import com.stripe.model.Charge;

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

        sum = getArguments().getInt("sum");
        total.setText("$" + String.valueOf(sum));


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
            Intent intent = new Intent(getActivity(),Booking.class);
            startActivity(intent);
        }

    }

    private void chargeCard() {
        new AsyncTask<Void, Void, Void>() {

            //Charge charge;

            @Override
            protected Void doInBackground(Void... params) {

                CreditCard creditCard = form.getCreditCard();
                Card card = new Card(creditCard.getCardNumber(), creditCard.getExpMonth(), creditCard.getExpYear(), creditCard.getSecurityCode());

                boolean validation = card.validateCard();
                if (validation) {
                    try {
                        stripe = new Stripe(PUBLISHABLE_KEY);
                        stripe.createToken(
                                card,
                                new TokenCallback() {
                                    public void onSuccess(Token token) {
                                        // Send token to your server

                                        Log.d("stripe onsuccess", token.toString());
                                        final Map<String, Object> chargeParams = new HashMap<String, Object>();
                                        chargeParams.put("amount", sum * 100);
                                        chargeParams.put("currency", "usd");
                                        chargeParams.put("card", token.getId()); //Token obtained in onSuccess() TokenCallback method of
                                        new AsyncTask<Void, Void, Void>() {

                                            Charge charge;


                                            @Override
                                            protected Void doInBackground(Void... params) {
                                                try {
                                                    com.stripe.Stripe.apiKey = "sk_test_n8kyFez68piLJbbIOW7WW9yo";
                                                    charge = Charge.create(chargeParams);
                                                } catch (Exception e) {
                                                    // TODO Auto-generated catch block
                                                    e.printStackTrace();
                                                    /*showAlert("Exception while charging the card!",
                                                            e.getLocalizedMessage());*/
                                                }
                                                return null;
                                            }

                                            protected void onPostExecute(Void result) {
                                                //Log.d("stripe charge info","Card Charged : " + charge.getCreated() + "\nPaid : " + charge.getPaid() );
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
