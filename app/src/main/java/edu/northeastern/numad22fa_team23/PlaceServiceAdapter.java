package edu.northeastern.numad22fa_team23;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.northeastern.numad22fa_team23.model.Responses;

public class PlaceServiceAdapter extends RecyclerView.Adapter<ServiceViewHolder>{

    List<Responses.Places> list = new ArrayList<>();

    Context context;
    ClickListener listiner;

    public PlaceServiceAdapter(List<Responses.Places> list, Context context,ClickListener listiner) {
        this.list = list;
        this.context = context;
        this.listiner = listiner;
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout

        View photoView = inflater.inflate(R.layout.place_layout, parent, false);

        ServiceViewHolder viewHolder = new ServiceViewHolder(photoView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ServiceViewHolder viewHolder, final int position) {
        final int index = viewHolder.getAdapterPosition();
        viewHolder.placeName.setText(list.get(position).getPlacename());
        viewHolder.longitude.setText(list.get(position).getLongitude());
        viewHolder.state.setText(list.get(position).getState());
        viewHolder.sa.setText(list.get(position).getSa());
        viewHolder.latitude.setText(list.get(position).getLatitude());
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                listiner.click(index);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface ClickListener{

        void click(int index);

    }
}
