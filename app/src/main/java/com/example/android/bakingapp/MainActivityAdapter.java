package com.example.android.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ugochukwu on 6/12/2017.
 */

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.MainActivityAdapterViewHolder> {

    private Context mContext;
    public  JSONArray mJsonArray;
    private ClickRecyclerView mClickRecyclerView;
    private static final int[] mImages = {R.drawable.nutellapie1, R.drawable.brownies1, R.drawable.yellowcake1, R.drawable.cheesecake1};

    public MainActivityAdapter(Context context, ClickRecyclerView clickRecyclerView){
        mContext = context;
        mClickRecyclerView = clickRecyclerView;
    }

    public interface ClickRecyclerView{
       void onClick(int position, String name);
    }

    @Override
    public MainActivityAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int mLayout = R.layout.activity_main;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean attachTParentImmediately = false;

        View view = inflater.inflate(mLayout, parent, attachTParentImmediately);

        return new MainActivityAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainActivityAdapterViewHolder holder, int position) {

        String name = null;
        String image = null;
        int servings = 0;
        try {
            JSONObject jsonObject1 = mJsonArray.getJSONObject(position);
            name = jsonObject1.getString("name");
            image = jsonObject1.getString("image");
            servings = jsonObject1.getInt("servings");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (position == 0){
            holder.mCakeTypename.setText(R.string.pielabel);
        }else {
            holder.mCakeTypename.setText(R.string.cakelabel);
        }

        //Handle image load from server if url is present
        if (image != "") {
            holder.mImageView.setImageResource(mImages[position]);
        }else if(image == ""){
            Glide.with(mContext).asBitmap().load(image).into(holder.mImageView);
        }
        holder.mCakeType.setText(name);
        holder.mServings.setText(Integer.toString(servings));
    }

    @Override
    public int getItemCount() {
        if (mJsonArray == null) return 0;
        return mJsonArray.length();
    }

    public class MainActivityAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mCakeType, mCakeTypename, mServings;
        public ImageView mImageView;

        public MainActivityAdapterViewHolder(View itemView) {
            super(itemView);

            mCakeType = (TextView) itemView.findViewById(R.id.cakeType);
            mImageView = (ImageView) itemView.findViewById(R.id.imgIcon);
            mCakeTypename = (TextView) itemView.findViewById(R.id.desc);
            mServings = (TextView) itemView.findViewById(R.id.servings);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //handle click here
            String name = null;
            try {
                JSONObject jsonObject1 = mJsonArray.getJSONObject(getAdapterPosition());
                name = jsonObject1.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mClickRecyclerView.onClick(getAdapterPosition(), name);

        }
    }

    public void swapValue(JSONArray jsonArray){

        mJsonArray = jsonArray;
        notifyDataSetChanged();
    }
}
