package com.choliy.igor.flickrgallery.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.choliy.igor.flickrgallery.R;
import com.choliy.igor.flickrgallery.model.GalleryItem;
import com.choliy.igor.flickrgallery.util.PrefUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.PhotoHolder> {

    private Context mContext;
    private List<GalleryItem> mItems;
    private OnPhotoHolderListener mListener;

    public interface OnPhotoHolderListener {

        void onRequestedLastItem(int position);

        void onPhotoClicked(String photoId);
    }

    public GalleryAdapter(
            Context context,
            List<GalleryItem> items,
            OnPhotoHolderListener listener) {

        mContext = context;
        mItems = items;
        mListener = listener;
    }

    @Override
    public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(setupLayout(), parent, Boolean.FALSE);
        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateItems(List<GalleryItem> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private int setupLayout() {
        String savedStyle = PrefUtils.getStyleSettings(mContext);
        String simpleStyle = mContext.getString(R.string.pref_grid_style_value_1);
        String dividerStyle = mContext.getString(R.string.pref_grid_style_value_2);
        String cardStyle = mContext.getString(R.string.pref_grid_style_value_3);

        int layout;
        boolean newVersion = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        if (savedStyle.equals(simpleStyle) && newVersion)
            layout = R.layout.list_item_simple_new;
        else if (savedStyle.equals(simpleStyle) && !newVersion)
            layout = R.layout.list_item_simple_old;
        else if (savedStyle.equals(dividerStyle) && newVersion)
            layout = R.layout.list_item_divider_new;
        else if (savedStyle.equals(dividerStyle) && !newVersion)
            layout = R.layout.list_item_divider_old;
        else if (savedStyle.equals(cardStyle) && newVersion)
            layout = R.layout.list_item_card_new;
        else
            layout = R.layout.list_item_card_old;

        return layout;
    }

    class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.gallery_image_item) ImageView mPhotoImage;

        PhotoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String photoId = mItems.get(getAdapterPosition()).getId();
            mListener.onPhotoClicked(photoId);
        }

        private void bindItem(int position) {
            String url = mItems.get(position).getPictureUrl();
            boolean animationOn = PrefUtils.getAnimationSettings(mContext);
            if (animationOn) {
                Glide.with(mContext)
                        .load(url)
                        .animate(R.anim.anim_picture)
                        .into(mPhotoImage);
            } else Glide.with(mContext).load(url).into(mPhotoImage);

            // Callback on the end of the list
            if (position == getItemCount() - 1)
                mListener.onRequestedLastItem(getItemCount());
        }
    }
}