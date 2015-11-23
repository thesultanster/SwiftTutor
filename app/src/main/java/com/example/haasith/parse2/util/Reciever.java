package com.example.haasith.parse2.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.haasith.parse2.tutor_list.TutorList;
import com.parse.ParsePushBroadcastReceiver;

public class Reciever extends ParsePushBroadcastReceiver {

    @Override
    public void onPushOpen(Context context, Intent intent) {
        Log.e("Push", "Clicked");
        Intent i = new Intent(context, TutorList.class);
        i.putExtras(intent.getExtras());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}