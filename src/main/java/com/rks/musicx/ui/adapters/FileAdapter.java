package com.rks.musicx.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rks.musicx.R;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;


/**
 * Created by Coolalien on 8/6/2016.
 */

public class FileAdapter extends BaseRecyclerViewAdapter<File,FileAdapter.Fileviewholder> implements FastScrollRecyclerView.SectionedAdapter {

    private FileModelComparator fileModelComparator = new FileModelComparator();

    public FileAdapter(@NonNull Context context) {
        super(context);
    }

    @Override
    public FileAdapter.Fileviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fp_item, parent, false);
        return new Fileviewholder(rootView);
    }

    @Override
    public void onBindViewHolder(FileAdapter.Fileviewholder holder, int position) {
        File file = getItem(position);
        Collections.sort(data, fileModelComparator);
        if (file.isDirectory()) {
            holder.thumbnail.setImageResource(R.drawable.ic_folder);
            holder.filename.setText(file.getName());
            if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("dark_theme", false)) {
                holder.filename.setTextColor(Color.WHITE);
            }
        }
    }

    @Override
    public File getItem(int position) throws ArrayIndexOutOfBoundsException {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public String getSectionName(int position) {
        return getItem(position).getName().substring(0,1);
    }

    public class Fileviewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView thumbnail;
        private TextView filename;

        public Fileviewholder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            filename = (TextView) itemView.findViewById(R.id.filename);
            itemView.setOnClickListener(this);
            itemView.findViewById(R.id.folder_view).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            triggerOnItemClickListener(position, v);
        }
    }

    public class FileModelComparator implements Comparator<File> {

        @Override
        public int compare(File file, File t1) {
            if (file.equals(t1)) {
                return 0;
            }

            if (file.isDirectory() && t1.isFile()) {
                // show directories above files
                return -1;
            }

            if (file.isFile() && t1.isDirectory()) {
                // show files below directories
                return 1;
            }

            // sort alphabetically, ignoring case
            return file.getName().compareToIgnoreCase(t1.getName());
        }
    }
}
