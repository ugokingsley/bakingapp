package com.example.android.bakingapp.FragmentClasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ugochukwu on 6/14/2017.
 */

public class CakeRecipeAdapter extends RecyclerView.Adapter<CakeRecipeAdapter.CakeRecipeAdapterViewHolder> {

    private Context mContext;
    private JSONArray mArray;
    private ClickOnVideosListener mClickOnVideosListener;


    public CakeRecipeAdapter(Context context, ClickOnVideosListener videosListener){
        mContext = context;
        mClickOnVideosListener = videosListener;
    }

    public interface ClickOnVideosListener{
        void onClickVideos(String Description, String videoURL, String sDescription, String thumbnailUrl);
    }

    @Override
    public CakeRecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.stepshortdescription;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean attachToRoot = false;

        View view = inflater.inflate(layout, parent, attachToRoot);

        return new CakeRecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CakeRecipeAdapterViewHolder holder, int position) {

        if (position == mArray.length()-1){
            holder.view.setVisibility(View.INVISIBLE);
        }

        String lDescription = null;
        String videoURL = null;
        String sDescription = null;

        try {
            JSONObject object = mArray.getJSONObject(position);
            sDescription = object.getString("shortDescription");
            lDescription = object.getString("description");
            videoURL = object.getString("videoURL");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.shortDescription.setText(sDescription);

    }

    @Override
    public int getItemCount() {
        if (mArray == null) return 0;

        return mArray.length();
    }

    public class CakeRecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView shortDescription;
        private View view;

        public CakeRecipeAdapterViewHolder(View itemView) {
            super(itemView);

            shortDescription = (TextView) itemView.findViewById(R.id.shortdescription);
            view = (View) itemView.findViewById(R.id.view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            String lDescription = null;
            String videoURL = null;
            String sDescription = null;
            String thumbnailUrl = null;

            try {
                JSONObject object = mArray.getJSONObject(getAdapterPosition());
                sDescription = object.getString("shortDescription");
                lDescription = object.getString("description");
                videoURL = object.getString("videoURL");
                thumbnailUrl = object.getString("thumbnailURL");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            mClickOnVideosListener.onClickVideos(lDescription, videoURL, sDescription, thumbnailUrl);
        }
    }

    public void swapArray(JSONArray array){
        mArray = array;
        notifyDataSetChanged();
    }
}
