package project.don.facebook.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import project.don.facebook.R;
import project.don.facebook.model.DataModel;

/**
 * Created by don on 9/18/2016.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    private ArrayList<DataModel> android;
    private Context context;

    public GridAdapter(Context context,ArrayList<DataModel> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public GridAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GridAdapter.ViewHolder viewHolder, int i) {

        Picasso.with(context).load(android.get(i).getUrl()).fit().into(viewHolder.img);
    }

    @Override
    public int getItemCount() {
        return android.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView img;
        public ViewHolder(View view) {
            super(view);


            img = (ImageView) view.findViewById(R.id.ivCover);
        }
    }

}