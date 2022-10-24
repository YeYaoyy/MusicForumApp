package edu.northeastern.numad22fa_team23;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ServiceViewHolder extends RecyclerView.ViewHolder{

    TextView placeName;
    TextView longitude;
    TextView state;
    TextView sa;
    TextView latitude;
    TextView postcode;
    ImageView postcodeBtn;
    TextView postcodeName;
    ImageView saBtn;
    TextView saName;
    ImageView stateBtn;
    TextView stateName;

    View view;

    ServiceViewHolder(View itemView)
    {
        super(itemView);
        placeName = (TextView)itemView.findViewById(R.id.nameValue);
        longitude = (TextView)itemView.findViewById(R.id.longitudeValue);
        state = (TextView)itemView.findViewById(R.id.stateValue);
        sa = (TextView) itemView.findViewById(R.id.saValue);
        latitude = (TextView) itemView.findViewById(R.id.latitudeValue);
        postcode = (TextView) itemView.findViewById(R.id.postcodeValue);
        postcodeBtn = (ImageView) itemView.findViewById(R.id.postcodeIcon);
        postcodeName = (TextView) itemView.findViewById(R.id.postcode);
        saBtn = (ImageView) itemView.findViewById(R.id.saIcon);
        saName = (TextView) itemView.findViewById(R.id.sa);
        stateBtn = (ImageView) itemView.findViewById(R.id.stateIcon);
        stateName = (TextView) itemView.findViewById(R.id.state);
        view = itemView;
    }
}
