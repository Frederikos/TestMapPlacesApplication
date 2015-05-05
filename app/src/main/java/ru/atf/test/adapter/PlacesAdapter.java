package ru.atf.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import ru.atf.test.R;
import ru.atf.test.models.PlaceModel;

public class PlacesAdapter extends ArrayAdapter<PlaceModel> {

    LayoutInflater layoutInflater;
    Context context;

    public PlacesAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void addItems(ArrayList<PlaceModel> items) {
        for (PlaceModel placeModel : items) {
            add(placeModel);
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        PlaceModel placeModel = getItem(position);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.place_list_item, null);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvTitle.setText(placeModel.title);
        viewHolder.tvFsValue.setText(placeModel.raiting.fsValue);
        viewHolder.tvCountVotes.setText(String.format(context.getString(R.string.votes_string), placeModel.raiting.count));
        viewHolder.tvDistance.setText(String.format("%.2f", placeModel.location.distance) + "mi * " + placeModel.location.location);
        ImageLoader.getInstance().displayImage(placeModel.imageUrl, viewHolder.ivImage);

        return convertView;
    }

    class ViewHolder {
        TextView tvTitle;
        TextView tvDistance;
        TextView tvFsValue;
        TextView tvCountVotes;
        ImageView ivImage;

        public ViewHolder(View view) {
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvDistance = (TextView) view.findViewById(R.id.tv_distance);
            tvFsValue = (TextView) view.findViewById(R.id.tv_fs_value);
            tvCountVotes = (TextView) view.findViewById(R.id.tv_votes_count);
            ivImage = (ImageView) view.findViewById(R.id.iv_place_image);
            view.setTag(this);
        }
    }

}
