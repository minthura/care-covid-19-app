package tech.minthura.carecovid.ui.tips;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tech.minthura.carecovid.R;
import tech.minthura.carecovid.adapter.PostsRecyclerViewAdapter;
import tech.minthura.carecovid.support.DialogUtils;
import tech.minthura.carecovid.support.HomeListener;
import tech.minthura.carecovid.ui.base.BaseFragment;
import tech.minthura.caresdk.model.Post;
import tech.minthura.caresdk.service.ErrorResponse;

public class TipsFragment extends BaseFragment {

    private TipsViewModel tipsViewModel;
    private HomeListener mHomeListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeListener) {
            mHomeListener = (HomeListener) context;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tipsViewModel = new ViewModelProvider(this).get(TipsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_tips, container, false);
        RecyclerView recyclerViewPosts = view.findViewById(R.id.recyclerViewPosts);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        PostsRecyclerViewAdapter postsRecyclerViewAdapter = new PostsRecyclerViewAdapter(getContext(), new ArrayList<>(), mHomeListener);
        recyclerViewPosts.setAdapter(postsRecyclerViewAdapter);
        tipsViewModel.getPostsLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<Post>>() {
            @Override
            public void onChanged(ArrayList<Post> posts) {
                DialogUtils.dismiss();
                postsRecyclerViewAdapter.addPosts(posts);
            }
        });
        tipsViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), new Observer<ErrorResponse>() {
            @Override
            public void onChanged(ErrorResponse errorResponse) {
                DialogUtils.dismiss();
            }
        });
        DialogUtils.showLoadingDialog(getContext(), getString(R.string.app_loading));
        tipsViewModel.getPosts();
        return view;
    }

    /*private View.OnClickListener onCardViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cv_item_1:
                    mHomeListener.onImageListFragment(1);
                    break;
                case R.id.cv_item_2:
                    mHomeListener.onImageListFragment(2);
                    break;
                case R.id.cv_item_3:
                    mHomeListener.onImageListFragment(3);
                    break;
                case R.id.cv_item_4:
                    mHomeListener.onImageListFragment(4);
                    break;
                case R.id.cv_item_5:
                    mHomeListener.onImageListFragment(5);
                    break;
                case R.id.cv_item_6:
                    mHomeListener.onImageListFragment(6);
                    break;
                default:
                    break;
            }
        }
    };*/

}
