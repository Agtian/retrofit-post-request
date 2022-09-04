package com.example.retrofitpostrequest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    // Initialize variable
    JSONArray jsonArray;

    // Constructor
    public MainAdapter(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main, parent, false);

        // Return holder view
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            // Initialize json object
            JSONObject object = jsonArray.getJSONObject(position);
            // Set name on text view
            holder.tvName.setText(object.getString("name"));
            // Set name on text trip
            holder.tvName.setText(String.format("%s Trips", object.getString("trips")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        // Return array size
        return jsonArray.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // Initialize variable
        TextView tvName, tvTrip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Assign variable
            tvName = itemView.findViewById(R.id.tv_name);
            tvTrip = itemView.findViewById(R.id.tv_trip);
        }
    }
}
