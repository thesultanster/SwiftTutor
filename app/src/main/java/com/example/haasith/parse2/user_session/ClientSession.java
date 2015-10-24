package com.example.haasith.parse2.user_session;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.haasith.parse2.R;
import com.example.haasith.parse2.profile.ConfirmPaymentDialog;
import com.example.haasith.parse2.util.NavigationDrawerFramework;

public class ClientSession extends NavigationDrawerFramework implements FinishUserSessionCommunicator{

    private Toolbar toolbar;
    Button finishSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_session);

        finishSession = (Button) findViewById(R.id.finishButton);

        finishSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FinishUserSessionDialog myDialog = new FinishUserSessionDialog();
                myDialog.show(fragmentManager, "Please Rate Tutor");
            }
        });


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

    }
}
