package project.don.facebook.adapter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


import project.don.facebook.R;
import project.don.facebook.model.DataModel;

/**
 * Created by don on 9/17/2016.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {

    private List<DataModel> DataModelsList;
    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView albumName, photoCount;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            albumName = (TextView) view.findViewById(R.id.tvAlbumName);
            photoCount = (TextView) view.findViewById(R.id.tvPhotoCount);
            imageView = (ImageView) view.findViewById(R.id.ivCover);

        }
    }


    public DataAdapter(Context context, List<DataModel> DataModelsList) {
        this.DataModelsList = DataModelsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DataModel DataModel = DataModelsList.get(position);
        holder.albumName.setText(DataModel.getAlbumName());
        holder.photoCount.setText(DataModel.getPhotoCount());
        Picasso.with(context).load(DataModel.getUrl()).fit().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return DataModelsList.size();
    }
}
