package tech.minthura.carecovid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tech.minthura.carecovid.R;
import tech.minthura.carecovid.support.HomeListener;
import tech.minthura.caresdk.model.Post;

public class PostsRecyclerViewAdapter extends RecyclerView.Adapter<PostsRecyclerViewAdapter.PostViewHolder> {

    private Context mContext;
    private ArrayList<Post> mPosts;
    private HomeListener mHomeListener;
    public PostsRecyclerViewAdapter(Context context, ArrayList<Post> posts, HomeListener homeListener) {
        mContext = context;
        mPosts = posts;
        mHomeListener = homeListener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item_view, parent,false);
        return new PostViewHolder(view);
    }

    public void addPosts(ArrayList<Post> posts) {
        mPosts.clear();
        mPosts.addAll(posts);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = mPosts.get(position);
        holder.txtTitle.setText(post.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHomeListener.onPostDetails(post.getType(), post.getDescription());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        PostViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_post_title);
        }
    }

}
