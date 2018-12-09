package com.opendownloader.opendownloadersample.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.opendownloader.library.lib.OpenLoader;
import com.opendownloader.opendownloadersample.R;
import com.opendownloader.opendownloadersample.entity.CustomImage;

import java.util.List;

public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ViewHolder> {

    private List<CustomImage> images;
    private Activity mActivity;

    public ImageRecyclerAdapter(List<CustomImage> images, Activity mActivity) {
        this.images = images;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.img_grid_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String imageUrl = images.get(i).getUrl();
        String imageSmallUrl = images.get(i).getSmallUrl();

        OpenLoader.get().loadImage(imageSmallUrl, viewHolder.imgSmall);
        OpenLoader.get().loadImage(imageUrl, viewHolder.img);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private ImageView imgSmall;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            imgSmall = itemView.findViewById(R.id.img_small);
        }
    }
}
