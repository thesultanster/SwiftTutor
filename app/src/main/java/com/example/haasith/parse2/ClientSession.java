package com.example.haasith.parse2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;

import com.example.haasith.parse2.util.NavigationDrawerFramework;

public class ClientSession extends NavigationDrawerFramework {

    private Toolbar toolbar;
    Button finishSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_session);

        finishSession = (Button) findViewById(R.id.finishButton);


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


}
