package com.example.haasith.parse2.current_session;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.haasith.parse2.R;

public class FinishUserSessionDialog extends DialogFragment implements View.OnClickListener {

    Button cancel;
    Button yes;
    FinishUserSessionCommunicator communicator;




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (FinishUserSessionCommunicator) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirm_payment, null);
        getDialog().setTitle("Please Rate Tutor");
        cancel = (Button) view.findViewById(R.id.cancel);
        yes = (Button) view.findViewById(R.id.yes);

        cancel.setOnClickListener(this);
        yes.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.cancel) {
            dismiss();
        } else if (view.getId() == R.id.yes) {
            dismiss();

            communicator.onDialogFinish();
        }


    }

}


interface FinishUserSessionCommunicator {

    public void onDialogFinish();
}
