package com.example.haasith.parse2.view_offers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haasith.parse2.R;
import com.example.haasith.parse2.find_tutor.FindTutor;
import com.example.haasith.parse2.profile.Profile;

import java.util.Collections;
import java.util.List;

/**
 * Created by Haasith on 10/3/2015.
 */
public class OfferRecyclerAdapter extends RecyclerView.Adapter<OfferRecyclerAdapter.MyViewHolder> {

    // emptyList takes care of null pointer exception
    List<OfferRecyclerInfo> data = Collections.emptyList();
    LayoutInflater inflator;
    Context context;
    //List<OfferRecyclerInfo>mDataSet;

    public OfferRecyclerAdapter(ViewOffers context, List<OfferRecyclerInfo> data)
    {
        this.context = context;
        inflator = LayoutInflater.from(context);
        this.data=data;
    }


    /*picture = (Bitmap) ex.get("data");
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    picture.compress(Bitmap.CompressFormat.JPEG, 100, stream);
    // get byte array here
    bytearray= stream.toByteArray();*/

    public void addRow(OfferRecyclerInfo row)
    {
        data.add(row);
        notifyItemInserted(getItemCount() - 1);
    }

    // Called when the recycler view needs to create a new row
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        final View view = inflator.inflate(R.layout.row_offer, parent, false);
        MyViewHolder holder = new MyViewHolder(view, new MyViewHolder.MyViewHolderClicks()
        {

            public void rowClick(View caller, int position)
            {
                android.util.Log.d("rowClick", "rowClicks");

                Intent intent = new Intent(context, Profile.class);
                intent.putExtra("selectedId", data.get(position).getParseObjectId());
                intent.putExtra("username", data.get(position).getUsername());
                intent.putExtra("tutorId",data.get(position).getParseObjectId());
                view.getContext().startActivity(intent);
            }

            @Override
            public void reject(int position) {

            }

            @Override
            public void accept(int position) {

            }


        });
        return holder;
    }

    // Setting up the data for each row
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {

        // This gives us current information list object
        OfferRecyclerInfo current = data.get(position);

        holder.userName.setText(current.getUsername());
        //holder.firstName.setText(current.getFirstName());
        holder.distance.setText(String.valueOf(current.getDistance()));
        holder.offeredPrice.setText(String.valueOf("$" + current.getPrice()));

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    // Created my custom view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView userName;
        TextView distance;
        TextView offeredPrice;
        public MyViewHolderClicks mListener;

        // itemView will be my own custom layout View of the row
        public MyViewHolder(View itemView, MyViewHolderClicks listener) {
            super(itemView);

            mListener = listener;
            //Link the objects
            userName = (TextView) itemView.findViewById(R.id.username);
            distance = (TextView) itemView.findViewById(R.id.distance);
            offeredPrice = (TextView) itemView.findViewById(R.id.offeredPrice);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                default:
                    mListener.rowClick(v, getAdapterPosition());
                    break;
            }
        }
        public interface MyViewHolderClicks
        {
            void rowClick(View caller, int position);
            void reject(int position);
            void accept(int position);

        }
    }
}