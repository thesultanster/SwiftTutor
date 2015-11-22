package com.example.haasith.parse2.current_session;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.maps.SupportMapFragment;

/*
    In this class, we intercept the touch event by using TouchableWrapper class .
    There is also a custom listener OnTouchListener to dispatch the touch event to the activity
    current session that handles the map. When touch event occured, dispatchTouchEvent will be called and
    the listener mListener will handle it.
 */
public class CurrentSessionMapFragment extends SupportMapFragment {

    private OnTouchListener mListener;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstance) {

        View layout = super.onCreateView(layoutInflater, viewGroup, savedInstance);
        TouchableWrapper frameLayout = new TouchableWrapper(getActivity());
        frameLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        ((ViewGroup) layout).addView(frameLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return layout;
    }


    public void setListener(OnTouchListener listener) {
        mListener = listener;
    }


    public interface OnTouchListener {

        public abstract void onTouch();

    }

    public class TouchableWrapper extends FrameLayout {


        public TouchableWrapper(Context context) {
            super(context);
        }


        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mListener.onTouch();
                    break;
                case MotionEvent.ACTION_UP:
                    mListener.onTouch();
                    break;
            }
            return super.dispatchTouchEvent(event);
        }

    }
}