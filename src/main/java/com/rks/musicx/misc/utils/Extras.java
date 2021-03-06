package com.rks.musicx.misc.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.rks.musicx.data.loaders.SortOrder;

import static com.rks.musicx.misc.utils.Constants.ALBUM_SORT_ORDER;
import static com.rks.musicx.misc.utils.Constants.ARTIST_SORT_ORDER;
import static com.rks.musicx.misc.utils.Constants.FloatingView;
import static com.rks.musicx.misc.utils.Constants.GridViewAlbum;
import static com.rks.musicx.misc.utils.Constants.GridViewArtist;
import static com.rks.musicx.misc.utils.Constants.GridViewSong;
import static com.rks.musicx.misc.utils.Constants.HIDE_LOCKSCREEEN;
import static com.rks.musicx.misc.utils.Constants.HIDE_NOTIFY;
import static com.rks.musicx.misc.utils.Constants.REORDER_TAB;
import static com.rks.musicx.misc.utils.Constants.RESTORE_LASTTAB;
import static com.rks.musicx.misc.utils.Constants.SAVE_DATA;
import static com.rks.musicx.misc.utils.Constants.SAVE_EQ;
import static com.rks.musicx.misc.utils.Constants.SONG_SORT_ORDER;
import static com.rks.musicx.misc.utils.Constants.STORAGE_SELECTION;
import static com.rks.musicx.misc.utils.Constants.SaveHeadset;
import static com.rks.musicx.misc.utils.Constants.SaveLyrics;
import static com.rks.musicx.misc.utils.Constants.SaveTelephony;
import static com.rks.musicx.misc.utils.Constants.TextFonts;
import static com.rks.musicx.misc.utils.Constants.Zero;
import static com.rks.musicx.misc.utils.Constants.sInstance;

/**
 * Created by Coolalien on 2/18/2016.
 */
public class Extras {

    public SharedPreferences mPreferences;
    private Context mcontext;

    public Extras(Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.mcontext = context;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public static int px2Dp(int dp, Context c) {
        DisplayMetrics displayMetrics = c.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.ydpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static void init(Context context) {
        sInstance = new Extras(context);
    }

    public static Extras getInstance() {
        return sInstance;
    }

    public void setOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        mPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public SharedPreferences getmPreferences() {
        return mPreferences;
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getSongSortOrder() {
        return mPreferences.getString(SONG_SORT_ORDER, SortOrder.SongSortOrder.SONG_A_Z);
    }

    public void setSongSortOrder(String value) {
        putString(SONG_SORT_ORDER, value);
    }

    public String getArtistSortOrder() {
        return mPreferences.getString(ARTIST_SORT_ORDER, SortOrder.ArtistSortOrder.ARTIST_A_Z);
    }

    public void setArtistSortOrder(String value) {
        putString(ARTIST_SORT_ORDER, value);
    }

    public String getAlbumSortOrder() {
        return mPreferences.getString(ALBUM_SORT_ORDER, SortOrder.AlbumSortOrder.ALBUM_A_Z);
    }

    public void setAlbumSortOrder(String value) {
        putString(ALBUM_SORT_ORDER, value);
    }

    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * setTabIndex
     * @param index
     */
    public void setTabIndex(final int index) {
        final SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(REORDER_TAB, String.valueOf(index));
        editor.apply();
    }

    /**
     * TabIndex
     * @return
     */
    public String getTabIndex(){
        return mPreferences.getString(REORDER_TAB, "0");
    }

    /**
     * save lyrics option
     * @return
     */
    public boolean saveLyrics(){
        return mPreferences.getBoolean(SaveLyrics,true);
    }

    /**
     * grid or list Song View
     * @return
     */
    public boolean songView(){
        return mPreferences.getBoolean(GridViewSong,false);
    }

    /**
     * grid or list Album View
     * @return
     */
    public boolean albumView(){
        return mPreferences.getBoolean(GridViewAlbum,false);
    }

    /**
     * grid or list Artist View
     * @return
     */
    public boolean artistView(){
        return mPreferences.getBoolean(GridViewArtist,false);
    }

    /**
     * hide or show Floating Widget
     * @return
     */
    public boolean floatingWidget(){
        return mPreferences.getBoolean(FloatingView,true);
    }

    /**
     * Font Config
     * @return
     */
    public String fontConfig(){
        return mPreferences.getString(TextFonts, "11");
    }

    /**
     * Headset Config
     * @return
     */
    public boolean headsetConfig(){
        return mPreferences.getBoolean(SaveHeadset,true);
    }

    /**
     * Phonecall Config
     * @return
     */
    public boolean phonecallConfig(){
        return mPreferences.getBoolean(SaveTelephony,true);
    }

    /**
     * save Eq
     */
    public SharedPreferences saveEq(){
        return mcontext.getSharedPreferences(SAVE_EQ, Context.MODE_PRIVATE);
    }

    /**
     * Save Data
     * @return
     */
    public boolean saveData(){
        return mPreferences.getBoolean(SAVE_DATA, true);
    }

    /**
     * Hide Notification
     * @return
     */
    public boolean hideNotify(){
        return mPreferences.getBoolean(HIDE_NOTIFY,false);
    }

    /**
     * HideLockscreen
     * @return
     */
    public boolean hideLockscreen(){
        return mPreferences.getBoolean(HIDE_LOCKSCREEEN,false);
    }

    /**
     * Restore lastTab
     * @return
     */
    public boolean restoreLastTab(){
        return mPreferences.getBoolean(RESTORE_LASTTAB,false);
    }

    /**
     * Folder Storage selection
     * @return
     */
    public String storageConfig(){
        return mPreferences.getString(STORAGE_SELECTION, Zero);
    }

}
