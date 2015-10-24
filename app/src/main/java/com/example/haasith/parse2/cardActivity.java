package com.example.haasith.parse2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.compat.AsyncTask;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;
import com.stripe.model.Charge;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class cardActivity extends Activity {

    public static final String PUBLISHABLE_KEY = "pk_test_RjBx9Nq6KSJboLDdbCw7D51r";

    Button enterCreditCard;
    Stripe stripe = null;
    EditText price;

    URL url;
    HttpURLConnection client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        price   = (EditText)findViewById(R.id.price);

        enterCreditCard = (Button) findViewById(R.id.enterCreditCard);
        enterCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chargeCard();
            }
        });
    }

    private void chargeCard() {
        new AsyncTask<Void, Void, Void>() {

            //Charge charge;

            @Override
            protected Void doInBackground(Void... params) {
                Card card = new Card("4242-4242-4242-4242", 12, 2016, "123");

                boolean validation = card.validateCard();
                if (validation) {
                    try {
                        stripe = new Stripe(PUBLISHABLE_KEY);
                        stripe.createToken(
                                card,
                                new TokenCallback() {
                                    public void onSuccess(Token token) {
                                        // Send token to your server
                                        //Toast.makeText(getBaseContext(), token.toString(), Toast.LENGTH_LONG).show();
                                        final Map<String, Object> chargeParams = new HashMap<String, Object>();
                                        chargeParams.put("amount", Integer.parseInt(price.getText().toString()));
                                        chargeParams.put("currency", "usd");
                                        chargeParams.put("card", token.getId()); //Token obtained in onSuccess() TokenCallback method of
                                        new AsyncTask<Void, Void, Void>() {

                                            Charge charge;

                                            @Override
                                            protected Void doInBackground(Void... params) {
                                                try {
                                                    com.stripe.Stripe.apiKey = "sk_test_FAeJGRuRLIU14yUTriLfkYHG";
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
                                                Toast.makeText(cardActivity.this,
                                                        "Card Charged : " + charge.getCreated() + "\nPaid : " + charge.getPaid(),
                                                        Toast.LENGTH_LONG
                                                ).show();
                                            };

                                        }.execute();

                                        //stripe.createToken() asynchronous call
                                        /*
                                        try {
                                            url = new URL("");
                                            client = (HttpURLConnection) url.openConnection();
                                            client.setDoOutput(true);
                                            client.setDoInput(true);
                                            client.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                                            client.setRequestMethod("POST");
                                            //client.setFixedLengthStreamingMode(request.toString().getBytes("UTF-8").length);
                                            client.connect();

                                            OutputStreamWriter writer = new OutputStreamWriter(client.getOutputStream());
                                            String output = token.toString();
                                            writer.write(output);
                                            writer.flush();
                                            writer.close();

                                            InputStream input = client.getInputStream();
                                            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                                            StringBuilder result = new StringBuilder();
                                            String line;

                                            while ((line = reader.readLine()) != null) {
                                                result.append(line);
                                            }
                                            JSONObject response = new JSONObject(result.toString());
                                        } catch (JSONException e){
                                        } catch (IOException e) {
                                        } finally {
                                            client.disconnect();
                                        }
                                        */
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
                /*Toast.makeText(MainActivity.this,
                        "Card Charged : " + charge.getCreated() + "\nPaid : " + charge.getPaid(),
                        Toast.LENGTH_LONG
                ).show();*/
            }

        }.execute();
    }
}
