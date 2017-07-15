package com.example.android.bakingapp.FragmentClasses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ugochukwu on 6/16/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientAdapterViewHolder> {

    private Context mContext;
    private JSONArray mArray;


    public IngredientAdapter(Context context){
        mContext = context;
    }

    @Override
    public IngredientAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = R.layout.ingridentitem;
        boolean attachToRoot = false;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layout, parent, attachToRoot);
        return new IngredientAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientAdapterViewHolder holder, int position) {

        if (position == mArray.length()-1){
            holder.view1.setVisibility(View.INVISIBLE);
        }
        String Ingredient = null;
        int quantity = 0;
        String measure = null;
        try {
            JSONObject object = mArray.getJSONObject(position);
            Ingredient = object.getString("ingredient");
            quantity = object.getInt("quantity");
            measure = object.getString("measure");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.mIngredient.setText(Ingredient);
        holder.mQuantity.setText(Integer.toString(quantity));
        holder.mMeasure.setText(measure);

    }

    @Override
    public int getItemCount() {
        if (mArray == null) return 0;
        return mArray.length();
    }

    public class IngredientAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView mQuantity, mMeasure, mIngredient;
        private View view1;
        public IngredientAdapterViewHolder(View itemView) {
            super(itemView);

            mQuantity = (TextView) itemView.findViewById(R.id.quantity);
            mMeasure = (TextView) itemView.findViewById(R.id.measure);
            mIngredient = (TextView) itemView.findViewById(R.id.ingerdient);
            view1 = (View) itemView.findViewById(R.id.view1);
        }
    }

    public void swapArray(JSONArray array){
        mArray = array;
        notifyDataSetChanged();
    }
}
