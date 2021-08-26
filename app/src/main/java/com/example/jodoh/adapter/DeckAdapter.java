package com.example.jodoh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jodoh.R;
import com.example.jodoh.entity.UserEntity;

import java.util.List;

public class DeckAdapter extends BaseAdapter {
    List<UserEntity> calon;
    Context context;

    public DeckAdapter(List<UserEntity> calon, Context context) {
        this.calon = calon;
        this.context = context;
    }

    @Override
    public int getCount() {
        return calon.size();
    }

    @Override
    public Object getItem(int position) {
        return calon.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.idNameCalon)).setText(calon.get(position).getName());
        ((TextView) convertView.findViewById(R.id.idUmur)).setText(calon.get(position).getUmur());
        Glide.with(context).load(""+calon.get(position).getImage()).into((ImageView) convertView.findViewById(R.id.idGambarCalon));
        return convertView;
    }
}
