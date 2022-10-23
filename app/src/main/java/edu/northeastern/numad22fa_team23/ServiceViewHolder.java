package edu.northeastern.numad22fa_team23;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ServiceViewHolder extends RecyclerView.ViewHolder{

    TextView placeName;
    TextView longitude;
    TextView state;
    TextView sa;
    TextView latitude;
    View view;

    ServiceViewHolder(View itemView)
    {
        super(itemView);
        placeName = (TextView)itemView.findViewById(R.id.nameValue);
        longitude = (TextView)itemView.findViewById(R.id.longitudeValue);
        state = (TextView)itemView.findViewById(R.id.stateValue);
        sa = (TextView) itemView.findViewById(R.id.saValue);
        latitude = (TextView) itemView.findViewById(R.id.latitudeValue);
        view = itemView;
    }
}
