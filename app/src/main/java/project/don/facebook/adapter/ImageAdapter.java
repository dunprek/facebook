package project.don.facebook.adapter;

import android.content.Context;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import project.don.facebook.R;
import project.don.facebook.model.ModelImages;

/**
 * Created by don on 9/11/2016.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private List<ModelImages> images;
    private int rowLayout;
    private Context context;

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        LinearLayout imagesLayout;
        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imagesLayout = (LinearLayout)itemView.findViewById(R.id.linear);
            imageView   = (ImageView)itemView.findViewById(R.id.iv);

        }
    }

    public ImageAdapter(List<ModelImages> imagesList, int rowLayout, Context context) {
        this.images = imagesList;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ImageAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ImageViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

//        Picasso.with(context).load(images.get(position).getImageUrl()).fit().into(holder);

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
