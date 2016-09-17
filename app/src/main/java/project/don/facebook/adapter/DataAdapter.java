package project.don.facebook.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


import project.don.facebook.R;
import project.don.facebook.model.DataModel;

/**
 * Created by don on 9/17/2016.
 */
public class DataAdapter extends ArrayAdapter<DataModel> {

    ArrayList<DataModel> modelArrayList;
    int Resource;
    Context context;
    LayoutInflater vi;


    public DataAdapter(Context context, int resource, ArrayList<DataModel> objects) {
        super(context, resource, objects);

        modelArrayList = objects;
        Resource = resource;
        this.context = context;

        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = vi.inflate(Resource, null);
            holder = new ViewHolder();
            holder.tvAlbum = (TextView)convertView.findViewById(R.id.tvAlbumName);
            holder.tvCount = (TextView)convertView.findViewById(R.id.tvPhotoCount);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvAlbum.setText(modelArrayList.get(position).getAlbumName());
        holder.tvCount.setText(modelArrayList.get(position).getPhotoCount());

        return convertView;
    }

    static class ViewHolder {
         TextView tvAlbum;
         TextView tvCount;
    }

}
