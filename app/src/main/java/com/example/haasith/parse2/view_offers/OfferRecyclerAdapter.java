package com.example.haasith.parse2.view_offers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haasith.parse2.R;
import com.example.haasith.parse2.current_session.CurrentSession;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;

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
    public void removeRow(int position)
    {
        data.remove(position);
        notifyItemRemoved(position);
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


            }

            @Override
            public void reject(int position) {

                data.get(position).RejectOffer();
                removeRow(position);

            }

            @Override
            public void accept(int position) {

                data.get(position).AcceptOffer();

                ParsePush parsePush = new ParsePush();
                ParseQuery pQuery = ParseInstallation.getQuery(); // <-- Installation query
                pQuery.whereEqualTo("clientId", data.get(position).getClientId()); // <-- you'll probably want to target someone that's not the current user, so modify accordingly
                parsePush.sendMessageInBackground("Only for special people", pQuery);

                Intent intent = new Intent(context, CurrentSession.class);
                intent.putExtra("sessionId", data.get(position).getSessionId());
                //intent.putExtra("username", data.get(position).getUsername());
                intent.putExtra("clientId", data.get(position).getClientId());
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
        OfferRecyclerInfo current = data.get(position);

        holder.userName.setText(current.getUsername());
        //holder.firstName.setText(current.getFirstName());
        holder.distance.setText(String.valueOf(current.getDistance())+"mi");
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
        ImageView yes;
        ImageView no;
        public MyViewHolderClicks mListener;

        // itemView will be my own custom layout View of the row
        public MyViewHolder(View itemView, MyViewHolderClicks listener) {
            super(itemView);

            mListener = listener;
            //Link the objects
            userName = (TextView) itemView.findViewById(R.id.username);
            distance = (TextView) itemView.findViewById(R.id.distance);
            yes = (ImageView) itemView.findViewById(R.id.yes);
            no = (ImageView) itemView.findViewById(R.id.no);
            offeredPrice = (TextView) itemView.findViewById(R.id.offeredPrice);
            itemView.setOnClickListener(this);
            yes.setOnClickListener(this);
            no.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.yes:
                    mListener.accept(getAdapterPosition());
                    break;
                case R.id.no:
                    mListener.reject(getAdapterPosition());
                    break;
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