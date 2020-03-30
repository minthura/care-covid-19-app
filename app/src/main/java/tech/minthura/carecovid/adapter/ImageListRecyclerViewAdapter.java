package tech.minthura.carecovid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tech.minthura.carecovid.R;
import tech.minthura.carecovid.support.HomeListener;

public class ImageListRecyclerViewAdapter extends RecyclerView.Adapter<ImageListRecyclerViewAdapter.ImageListViewHolder> {

    private Context mContext;
    private ArrayList<String> mImageUrls;
    private HomeListener mHomeListener;
    public ImageListRecyclerViewAdapter(Context context, ArrayList<String> imageUrls, HomeListener homeListener) {
        mContext = context;
        mImageUrls = imageUrls;
        mHomeListener = homeListener;
    }

    @NonNull
    @Override
    public ImageListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_list_item_view, parent,false);
        return new ImageListViewHolder(view);
    }

    public void addImages(ArrayList<String> urls) {
        mImageUrls.clear();
        mImageUrls.addAll(urls);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ImageListViewHolder holder, int position) {
        String url = mImageUrls.get(position);
        Picasso.get().load(url).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeListener.onFullScreenImageView(url);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    static class ImageListViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageListViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }

}
