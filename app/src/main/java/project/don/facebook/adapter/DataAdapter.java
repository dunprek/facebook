package project.don.facebook.adapter;

import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import project.don.facebook.R;
import project.don.facebook.model.DataModel;
import project.don.facebook.ui.SecondActivity;


/**
 * Created by don on 9/17/2016.
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private Context context;
    private List<DataModel> data;

    public DataAdapter(Context context, List<DataModel> data) {
        this.context = context;
        this.data = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAlbum, tvPhotoCount;
        ImageView imageUrl;

        public ViewHolder(View view) {
            super(view);
            tvAlbum = (TextView) itemView.findViewById(R.id.tvAlbumName);
            tvPhotoCount = (TextView) itemView.findViewById(R.id.tvPhotoCount);
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
        viewHolder.tvAlbum.setText(data.get(position).getAlbumName());
        viewHolder.tvPhotoCount.setText(data.get(position).getPhotoCount());

        Picasso.with(context).load(data.get(position).getUrl()).fit().into(viewHolder.imageUrl);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
//                Toast.makeText(arg0.getContext(), "MY ALBUM ID IS......." + data.get(position).getId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra("id", data.get(position).getId().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);



            }
        });
    }

}
