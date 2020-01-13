package ru.unfortunately.school.abstractionsunittestsandmore.presentation.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SpinnerSortAdapter extends BaseAdapter {

    List<String> mItems;

    SpinnerSortAdapter(List<String> items){
        mItems = new ArrayList<>(items);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public String getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(
                    parent.getContext()).inflate(
                    android.R.layout.simple_list_item_1, parent, false);
            ViewHolder viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.mTextView.setText(getItem(position));
        return convertView;
    }


    private class ViewHolder{
        private final TextView mTextView;

        ViewHolder(View view){
            mTextView = view.findViewById(android.R.id.text1);
        }
    }
}
