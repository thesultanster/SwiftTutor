package com.example.haasith.parse2.search_list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haasith.parse2.profile.Profile;
import com.example.haasith.parse2.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Haasith on 10/3/2015.
 */
public class TutorListRecyclerAdapter extends RecyclerView.Adapter<TutorListRecyclerAdapter.MyViewHolder> {

    // emptyList takes care of null pointer exception
    List<TutorListRecyclerInfo> data = Collections.emptyList();
    LayoutInflater inflator;
    Context context;
    //List<TutorListRecyclerInfo>mDataSet;

    public TutorListRecyclerAdapter(FindTutor context, List<TutorListRecyclerInfo> data)
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

    public void addRow(TutorListRecyclerInfo row)
    {
        data.add(row);
        notifyItemInserted(getItemCount() - 1);
    }

    // Called when the recycler view needs to create a new row
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        final View view = inflator.inflate(R.layout.list_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view, new MyViewHolder.MyViewHolderClicks()
        {

            public void rowClick(View caller, int position)
            {
                android.util.Log.d("rowClick", "rowClicks");

                Intent intent = new Intent(context, Profile.class);
                intent.putExtra("selectedId", data.get(position).getParseObjectId());
                intent.putExtra("username", data.get(position).getUsername());
                intent.putExtra("firstname", data.get(position).getFirstName());
                intent.putExtra("lastname", data.get(position).getLastName());
                view.getContext().startActivity(intent);
            }
            

        });
        return holder;
    }

    // Setting up the data for each row
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {

        // This gives us current information list object
        TutorListRecyclerInfo current = data.get(position);

        holder.userName.setText(current.getUsername());
        //holder.firstName.setText(current.getFirstName());
        //holder.lastName.setText(String.valueOf(current.getDistance()));
        holder.distance.setText(String.valueOf(current.getDistance()) + "mi");
        holder.lowestPrice.setText(String.valueOf("$" + current.getLowestPrice()));

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    // Created my custom view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView userName;
        TextView firstName;
        TextView lastName;
        TextView distance;
        TextView lowestPrice;
        public MyViewHolderClicks mListener;

        // itemView will be my own custom layout View of the row
        public MyViewHolder(View itemView, MyViewHolderClicks listener) {
            super(itemView);

            mListener = listener;
            //Link the objects
            userName = (TextView) itemView.findViewById(R.id.username);
            lastName = (TextView) itemView.findViewById(R.id.lastname);
            firstName = (TextView) itemView.findViewById(R.id.firstname);
            distance = (TextView) itemView.findViewById(R.id.distance);
            lowestPrice = (TextView) itemView.findViewById(R.id.lowestPrice);
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

        }
    }
}