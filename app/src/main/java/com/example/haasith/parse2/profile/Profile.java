package com.example.haasith.parse2.profile;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.haasith.parse2.R;
import com.example.haasith.parse2.payment.cardActivity;
import com.example.haasith.parse2.profile.ConfirmPaymentDialog;

public class Profile extends AppCompatActivity implements ConfirmPaymentCommunicator
{

    private TextView username;
    private TextView firstname;
    private Button confirmPayment;
    private String first;
    private String last;
    private String user;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            user = extras.getString("username");
            //email.setText(extras.getString("email"));
            first = extras.getString("firstname");
            last = extras.getString("lastname");
        }


        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getBackground().setAlpha(300);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setCollapsedTitleTextColor(0xFFffffff);
        collapsingToolbarLayout.setTitle(first + " " + last);
        collapsingToolbarLayout.setExpandedTitleColor(0xFFffffff);

        username = (TextView) findViewById(R.id.username);
        confirmPayment = (Button) findViewById(R.id.confirmpayment);
        //lastname = (TextView) findViewById(R.id.lastname);

        username.setText(user);
        //firstname.setText(first);
        //lastname.setText(last);

        confirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                ConfirmPaymentDialog myDialog = new ConfirmPaymentDialog();
                myDialog.show(fragmentManager, "Confirm Payment");
            }
        });



    }

    public void openpayment(View view) {
        Intent opencardpayment = new Intent(this, cardActivity.class);
        startActivity(opencardpayment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {


        switch (menuItem.getItemId()) {
            case R.id.home:
                onBackPressed();
                return true;
            default:
                onBackPressed();
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onDialogPayment() {
        Intent intent = new Intent(getApplicationContext(), Profile.class);
        //intent.putExtra("selectedId", data.get(position).getParseObjectId());
        startActivity(intent);
    }
}
