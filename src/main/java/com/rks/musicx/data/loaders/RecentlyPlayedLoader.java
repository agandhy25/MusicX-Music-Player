package com.rks.musicx.data.loaders;

import android.content.Context;

import com.rks.musicx.data.model.Song;
import com.rks.musicx.database.RecentlyPlayed;

import java.util.List;

/**
 * Created by Coolalien on 12/16/2016.
 */

public class RecentlyPlayedLoader extends BaseAsyncTaskLoader<List<Song>> {

    private RecentlyPlayed recentlyPlayed;
    private int limit;

    /**
     * constructor
     * @param context
     * @param limit
     */
    public RecentlyPlayedLoader(Context context, int limit) {
        super(context);
        recentlyPlayed = new RecentlyPlayed(context);
        this.limit = limit;
    }

    /**
     * Load in bg
     * @return
     */
    @Override
    public List<Song> loadInBackground() {
        List<Song> songList = recentlyPlayed.readLimit(limit);
        recentlyPlayed.close();
        return songList;
    }

    /**
     * Clear Db
     */
    public void clearDb(){
        recentlyPlayed.removeAll();
    }
}
