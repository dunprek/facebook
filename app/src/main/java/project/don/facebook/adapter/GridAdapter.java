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

import java.util.List;

import project.don.facebook.R;
import project.don.facebook.model.DataModel;
import project.don.facebook.ui.SecondActivity;

/**
 * Created by don on 9/18/2016.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    private Context context;
    private List<DataModel> data;

    public GridAdapter(Context context, List<DataModel> data) {
        this.context = context;
        this.data = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageUrl;

        public ViewHolder(View view) {
            super(view);
            imageUrl = (ImageView) itemView.findViewById(R.id.ivCover);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        View viewItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Picasso.with(context).load(data.get(position).getUrl()).fit().into(viewHolder.imageUrl);
    }

}

