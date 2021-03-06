package com.rks.musicx.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rks.musicx.R;
import com.rks.musicx.data.model.Song;
import com.rks.musicx.misc.utils.ArtworkUtils;
import com.rks.musicx.misc.utils.SimpleItemTouchHelperCallback;
import com.rks.musicx.misc.widgets.CircleImageView;

import java.util.Collections;

import static com.rks.musicx.R.id.artist;

/**
 * Created by Coolalien on 6/21/2016.
 */
public class QueueAdapter extends BaseRecyclerViewAdapter<Song,QueueAdapter.QueueViewHolder> implements SimpleItemTouchHelperCallback.ItemTouchHelperAdapter {


    private int mLayoutId = R.layout.queue_songlist;
    private final SimpleItemTouchHelperCallback.OnStartDragListener mDragStartListener;
    private int mSelectedItemPosition = -1;

    public QueueAdapter(@NonNull Context context, SimpleItemTouchHelperCallback.OnStartDragListener mDragStartListener) {
        super(context);
        this.mDragStartListener = mDragStartListener;
    }

    public void setLayoutId(int layoutId) {
        mLayoutId = layoutId;
    }

    @Override
    public QueueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        return new QueueViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QueueViewHolder holder, int position) {
        Song song = getItem(position);
        if(mLayoutId == R.layout.song_list) {
            if (position == mSelectedItemPosition) {
                holder.itemView.setSelected(true);
            } else {
                holder.itemView.setSelected(false);

            }
            holder.SongTitle.setText(song.getTitle());
            holder.SongArtist.setText(song.getArtist());
            ArtworkUtils.ArtworkLoader(getContext(),song.getAlbumId(),holder.queueArtwork);
            holder.SongTitle.setTextColor(Color.WHITE);
            holder.SongArtist.setTextColor(Color.WHITE);
            holder.menu.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_menu));
            holder.queueArtwork.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        mDragStartListener.onStartDrag(holder);
                    }
                    return false;
                }
            });
        }
        if (mLayoutId == R.layout.queue_songlist) {
            if (position == mSelectedItemPosition) {
                holder.itemView.setSelected(true);
            } else {
                holder.itemView.setSelected(false);
            }
            holder.SongTitle.setText(song.getTitle());
            holder.SongArtist.setText(song.getArtist());
            holder.number.setText(position + 1 + ".");
            holder.menu.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_menu));
            holder.reorder.getDrawable().setTint(ContextCompat.getColor(getContext(),R.color.white));
            holder.menu.getDrawable().setTint(ContextCompat.getColor(getContext(),R.color.white));
            holder.reorder.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        mDragStartListener.onStartDrag(holder);
                    }
                    return false;
                }
            });
            if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("dark_theme", false)) {
                holder.reorder.getDrawable().setTint(Color.WHITE);
                holder.SongTitle.setTextColor(Color.WHITE);
                holder.number.setTextColor(Color.WHITE);
                holder.SongArtist.setTextColor(ContextCompat.getColor(getContext(),R.color.darkthemeTextColor));
                holder.menu.getDrawable().setTint(Color.WHITE);
            }else {
                holder.reorder.getDrawable().setTint(ContextCompat.getColor(getContext(),R.color.MaterialGrey));
                holder.menu.getDrawable().setTint(ContextCompat.getColor(getContext(),R.color.MaterialGrey));
                holder.SongTitle.setTextColor(Color.BLACK);
                holder.number.setTextColor(Color.BLACK);
                holder.SongArtist.setTextColor(Color.DKGRAY);
            }
        }
        if(mLayoutId == R.layout.gridqueue){
            if (position == mSelectedItemPosition) {
                holder.itemView.setSelected(true);
            } else {
                holder.itemView.setSelected(false);

            }
            ArtworkUtils.ArtworkLoader(getContext(),song.getAlbumId(),holder.queueArtworkgrid);
        }
    }

    public void setSelection(int position) {
        int oldSelection = mSelectedItemPosition;
        mSelectedItemPosition = position;

        if (oldSelection >= 0 && oldSelection < data.size()) {
            notifyItemChanged(oldSelection);
        }

        if (mSelectedItemPosition >= 0 && mSelectedItemPosition < data.size()) {
            notifyItemChanged(mSelectedItemPosition);
        }
    }

    @Override
    public void onItemDismiss(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < 0 || fromPosition >= data.size() || toPosition < 0 || toPosition >= data.size()) {
            return false;
        }
        Collections.swap(data, fromPosition, toPosition);

        if (mSelectedItemPosition == fromPosition) {
            mSelectedItemPosition = toPosition;
        } else if (mSelectedItemPosition == toPosition) {
            mSelectedItemPosition = fromPosition;
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public Song getItem(int position) throws ArrayIndexOutOfBoundsException {
        return super.getItem(position);
    }

    public class QueueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,SimpleItemTouchHelperCallback.ItemTouchHelperViewHolder {

        private TextView SongTitle,SongArtist,number;
        View itemView;
        LinearLayout DragDrop;
        private ImageButton menu;
        private ImageView reorder;
        private ImageView queueArtworkgrid;
        private CircleImageView queueArtwork;

        public QueueViewHolder(View itemView) {
            super(itemView);

            if(mLayoutId == R.layout.song_list){
                SongTitle = (TextView) itemView.findViewById(R.id.title);
                SongArtist = (TextView) itemView.findViewById(artist);
                queueArtwork = (CircleImageView) itemView.findViewById(R.id.artwork);
                menu = (ImageButton) itemView.findViewById(R.id.menu_button);
                this.itemView = itemView;
                itemView.setOnClickListener(this);
                menu.setOnClickListener(this);
            }
            if (mLayoutId == R.layout.queue_songlist) {
                SongTitle = (TextView) itemView.findViewById(R.id.title);
                SongArtist = (TextView) itemView.findViewById(R.id.artist);
                number = (TextView) itemView.findViewById(R.id.number);
                reorder = (ImageView) itemView.findViewById(R.id.reorder);
                DragDrop = (LinearLayout) itemView.findViewById(R.id.item_view);
                this.itemView = itemView;
                menu = (ImageButton) itemView.findViewById(R.id.menu_button);
                itemView.setOnClickListener(this);
                menu.setOnClickListener(this);
            }
            if (mLayoutId == R.layout.gridqueue){
                queueArtworkgrid = (ImageView) itemView.findViewById(R.id.queue_artwork);
                itemView.findViewById(R.id.item_view).setOnClickListener(this);
                itemView.setOnClickListener(this);
                this.itemView = itemView;
            }
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            triggerOnItemClickListener(position, v);
        }

        @Override
        public void onItemSelected() {
            if (mLayoutId == R.layout.song_list) {
                itemView.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.bgcolor));
            }
            if (mLayoutId == R.layout.queue_songlist) {
                itemView.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.bgcolor));
            }

        }

        @Override
        public void onItemClear() {
            if (mLayoutId == R.layout.queue_songlist) {
                itemView.setBackgroundColor(0);
            }
            if (mLayoutId == R.layout.song_list) {
                itemView.setBackgroundColor(0);
            }
        }
    }
}
