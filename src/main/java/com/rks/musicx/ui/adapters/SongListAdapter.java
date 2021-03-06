package com.rks.musicx.ui.adapters;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.appthemeengine.Config;
import com.rks.musicx.R;
import com.rks.musicx.data.model.Song;
import com.rks.musicx.misc.utils.ArtworkUtils;
import com.rks.musicx.misc.utils.Helper;
import com.rks.musicx.misc.utils.palette;
import com.rks.musicx.misc.widgets.CircleImageView;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.rks.musicx.R.id.artist;


public class SongListAdapter extends BaseRecyclerViewAdapter<Song,SongListAdapter.SongViewHolder> implements FastScrollRecyclerView.SectionedAdapter {

    private int layout = R.layout.song_list;
    private int duration = 500;
    private Interpolator interpolator = new LinearInterpolator();
    private int lastpos = -1;
    private ValueAnimator colorAnimation;

    public SongListAdapter(@NonNull Context context) {
        super(context);
    }

    public void setLayoutId(int layoutId) {
        layout = layoutId;
    }

    @Override
    public SongListAdapter.SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new SongViewHolder(itemView);
    }

    private Animator[] getAnimator(View view){
        return new Animator[]{
                ObjectAnimator.ofFloat(view,"translationY", view.getMeasuredHeight(),0)
        };
    }

    @Override
    public void onBindViewHolder(SongListAdapter.SongViewHolder holder, int position) {
        Song song = getItem(position);
        if(layout == R.layout.song_list) {
            holder.SongTitle.setText(song.getTitle());
            holder.SongArtist.setText(song.getArtist());
           // new getAlbumart(song,holder).execute();
            ArtworkUtils.ArtworkLoader(getContext(),song.getAlbumId(),holder.SongArtwork);
            holder.menu.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_menu));
            Drawable drawable = holder.menu.getDrawable();
            if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("dark_theme", false)) {
                drawable.setTint(Color.WHITE);
                holder.SongTitle.setTextColor(Color.WHITE);
                holder.SongArtist.setTextColor(ContextCompat.getColor(getContext(),R.color.darkthemeTextColor));
            }else {
                drawable.setTint(ContextCompat.getColor(getContext(),R.color.MaterialGrey));
                holder.SongTitle.setTextColor(Color.BLACK);
                holder.SongArtist.setTextColor(Color.DKGRAY);
            }
        }
        if(layout == R.layout.detail_list){
            holder.SongTitle.setText(song.getTitle());
            holder.SongArtist.setText(song.getArtist());
            holder.number.setText(position + 1 + ".");
            holder.menu.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_menu));
            Drawable drawable = holder.menu.getDrawable();
            if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("dark_theme", false)) {
                drawable.setTint(Color.WHITE);
                holder.SongTitle.setTextColor(Color.WHITE);
                holder.number.setTextColor(Color.WHITE);
                holder.SongArtist.setTextColor(ContextCompat.getColor(getContext(),R.color.darkthemeTextColor));
            }else {
                drawable.setTint(ContextCompat.getColor(getContext(),R.color.MaterialGrey));
                holder.SongTitle.setTextColor(Color.BLACK);
                holder.number.setTextColor(Color.BLACK);
                holder.SongArtist.setTextColor(Color.DKGRAY);
            }
        }
        if (layout == R.layout.item_grid_view){
            int pos = holder.getAdapterPosition();
            if (lastpos < pos){
                for (Animator animator : getAnimator(holder.songView)){
                    animator.setDuration(duration).start();
                    animator.setInterpolator(interpolator);
                }
            }
            holder.SongTitle.setText(song.getTitle());
            holder.SongArtist.setText(song.getArtist());
            ArtworkUtils.ArtworkLoaderPalette(getContext(), song.getAlbumId(), holder.songGridArtwork, new palette() {
                @Override
                public void palettework(Palette palette) {
                    final int[] colors = getAvailableColor(palette);
                    holder.songView.setBackgroundColor(colors[0]);
                    holder.SongTitle.setTextColor(ContextCompat.getColor(getContext(),R.color.text_transparent));
                    holder.SongArtist.setTextColor(ContextCompat.getColor(getContext(),R.color.text_transparent2));
                    animateViews(holder,colors[0]);
                }
            });
            holder.menu.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_menu));
            holder.menu.setVisibility(View.VISIBLE);
            Drawable drawable = holder.menu.getDrawable();
            if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("dark_theme", false)) {
                drawable.setTint(Color.WHITE);
            }
        }
        if (layout == R.layout.recent_list){
            int pos = holder.getAdapterPosition();
            if (lastpos < pos){
                for (Animator animator : getAnimator(holder.songView)){
                    animator.setDuration(duration).start();
                    animator.setInterpolator(interpolator);
                }
            }
            holder.SongTitle.setText(song.getTitle());
            holder.SongArtist.setText(song.getArtist());
            ArtworkUtils.ArtworkLoaderPalette(getContext(), song.getAlbumId(), holder.songGridArtwork, new palette() {
                @Override
                public void palettework(Palette palette) {
                    final int[] colors = getAvailableColor(palette);
                    holder.songView.setBackgroundColor(colors[0]);
                    holder.SongTitle.setTextColor(ContextCompat.getColor(getContext(),R.color.text_transparent));
                    holder.SongArtist.setTextColor(ContextCompat.getColor(getContext(),R.color.text_transparent2));
                    animateViews(holder,colors[0]);
                }
            });
            holder.menu.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_menu));
            holder.menu.setVisibility(View.VISIBLE);
            Drawable drawable = holder.menu.getDrawable();
            if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("dark_theme", false)) {
                drawable.setTint(Color.WHITE);
            }
        }
    }

    @Override
    public Song getItem(int position) throws ArrayIndexOutOfBoundsException {
        return super.getItem(position);
    }

    @Override
    public List<Song> getSnapshot() {
        return super.getSnapshot();
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return getItem(position).getTitle().substring(0,1);
    }

    public class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView SongTitle,SongArtist,number;
        private CircleImageView SongArtwork;
        private ImageButton menu;
        private LinearLayout songView;
        private ImageView songGridArtwork;

        public SongViewHolder(View itemView) {
            super(itemView);

            if(layout == R.layout.song_list){
                SongTitle = (TextView) itemView.findViewById(R.id.title);
                SongArtist = (TextView) itemView.findViewById(artist);
                SongArtwork = (CircleImageView) itemView.findViewById(R.id.artwork);
                menu = (ImageButton) itemView.findViewById(R.id.menu_button);
                songView = (LinearLayout) itemView.findViewById(R.id.item_view);
                itemView.setOnClickListener(this);
                menu.setOnClickListener(this);
            }
            if(layout == R.layout.detail_list){
                SongTitle = (TextView) itemView.findViewById(R.id.title);
                SongArtist = (TextView) itemView.findViewById(R.id.artist);
                number = (TextView) itemView.findViewById(R.id.number);
                songView = (LinearLayout) itemView.findViewById(R.id.item_view);
                menu = (ImageButton) itemView.findViewById(R.id.menu_button);
                menu.setOnClickListener(this);
                itemView.setOnClickListener(this);
            }
            if (layout == R.layout.item_grid_view){
                songGridArtwork = (ImageView) itemView.findViewById(R.id.album_artwork);
                SongTitle = (TextView) itemView.findViewById(R.id.album_name);
                SongArtist = (TextView) itemView.findViewById(R.id.artist_name);
                songView = (LinearLayout) itemView.findViewById(R.id.backgroundColor);
                menu = (ImageButton) itemView.findViewById(R.id.menu_button);
                menu.setOnClickListener(this);
                itemView.findViewById(R.id.item_view).setOnClickListener(this);
                songGridArtwork.setOnClickListener(this);
                itemView.setOnClickListener(this);
            }
            if (layout == R.layout.recent_list){
                songGridArtwork = (ImageView) itemView.findViewById(R.id.artwork);
                SongTitle = (TextView) itemView.findViewById(R.id.album_name);
                SongArtist = (TextView) itemView.findViewById(R.id.artist_name);
                songView = (LinearLayout) itemView.findViewById(R.id.backgroundColor);
                menu = (ImageButton) itemView.findViewById(R.id.menu_button);
                menu.setOnClickListener(this);
                itemView.findViewById(R.id.item_view).setOnClickListener(this);
                songGridArtwork.setOnClickListener(this);
                itemView.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            triggerOnItemClickListener(position, v);
        }
    }
    public void setFilter(List<Song> songList) {
        data = new ArrayList<>();
        data.addAll(songList);
        notifyDataSetChanged();
    }

    private void animateViews(SongViewHolder songViewHolder, int colorBg) {
        colorAnimation = setAnimator(0xffe5e5e5,
                colorBg);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                songViewHolder.songView.setBackgroundColor((Integer) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }

    private ValueAnimator setAnimator(int colorFrom, int colorTo) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        long duration = 800;
        colorAnimation.setDuration(duration);
        return colorAnimation;
    }

    private int[] getAvailableColor(Palette palette) {
        int[] temp = new int[3]; //array with size 3
        if (palette.getDarkVibrantSwatch() != null) {
            temp[0] = palette.getDarkVibrantSwatch().getRgb();
            temp[1] = palette.getDarkVibrantSwatch().getTitleTextColor();
            temp[2] = palette.getDarkVibrantSwatch().getBodyTextColor();
        } else if (palette.getDarkMutedSwatch() != null) {
            temp[0] = palette.getDarkMutedSwatch().getRgb();
            temp[1] = palette.getDarkMutedSwatch().getTitleTextColor();
            temp[2] = palette.getDarkMutedSwatch().getBodyTextColor();
        }else if (palette.getVibrantSwatch() != null) {
            temp[0] = palette.getVibrantSwatch().getRgb();
            temp[1] = palette.getVibrantSwatch().getTitleTextColor();
            temp[2] = palette.getVibrantSwatch().getBodyTextColor();
        }else if (palette.getDominantSwatch() != null) {
            temp[0] = palette.getDominantSwatch().getRgb();
            temp[1] = palette.getDominantSwatch().getTitleTextColor();
            temp[2] = palette.getDominantSwatch().getBodyTextColor();
        }else if (palette.getMutedSwatch() != null) {
            temp[0] = palette.getMutedSwatch().getRgb();
            temp[1] = palette.getMutedSwatch().getTitleTextColor();
            temp[2] = palette.getMutedSwatch().getBodyTextColor();
        }else {
            String atkey = Helper.getATEKey(getContext());
            int accent = Config.accentColor(getContext(),atkey);
            temp[0] = accent;
            temp[1] = 0xffe5e5e5;
            temp[2] = accent;
        }
        return temp;
    }



    /*public class getAlbumart extends AsyncTask<Void,Void,Void>{

        Call<Vag> vagCall;
        Song song;
        SongViewHolder songViewHolder;
        String Artwork;

        public getAlbumart(Song song, SongViewHolder songViewHolder){
            this.song = song;
            this.songViewHolder = songViewHolder;
        }

        @Override
        protected Void doInBackground(Void... params) {
            VagClient vagClient = new VagClient(getContext());
            VagServices vagServices = vagClient.createService(VagServices.class);
            vagCall = vagServices.getAlbumart(song.getArtist(),song.getTitle());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            vagCall.enqueue(new Callback<Vag>() {
                @Override
                public void onResponse(Call<Vag> call, Response<Vag> response) {
                    if (response.isSuccessful()){
                         final Vag vag = response.body();
                        try {
                            List<Alb> albs = vag.getAlb();
                            Artwork = albs.get(0).getImg();
                            Picasso.with(getContext())
                                    .load(Artwork)
                                    .error(R.mipmap.ic_launcher)
                                    .centerCrop()
                                    .resize(300,300)
                                    .placeholder(R.mipmap.ic_launcher)
                                    .into(songViewHolder.SongArtwork);
                        }catch (NullPointerException ex){}

                    }
                }

                @Override
                public void onFailure(Call<Vag> call, Throwable t) {

                }
            });
        }
    }*/
}
