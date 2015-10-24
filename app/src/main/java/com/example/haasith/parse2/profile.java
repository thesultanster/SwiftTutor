package com.example.haasith.parse2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class profile extends Activity
{

    private TextView username;
    private TextView firstname;
    private TextView lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Bundle extras = getIntent().getExtras();
        username = (TextView) findViewById(R.id.username);
        //email = (TextView) findViewById(R.id.email);
        firstname = (TextView) findViewById(R.id.firstname);
        lastname = (TextView) findViewById(R.id.lastname);

        if (extras != null)
        {
            username.setText(extras.getString("username"));
            //email.setText(extras.getString("email"));
            firstname.setText(extras.getString("firstname"));
            lastname.setText(extras.getString("lastname"));
        }

    }

    public void openpayment(View view) {
        Intent opencardpayment = new Intent(this, cardActivity.class);
        startActivity(opencardpayment);
    }
}
