/**
 * ONI LUCA 20200008@student.act.edu
 * CS300-MOBILE APPLICATIONS PROJECT SUBMISSION
 * ItemAdapter is a custom adapter used to display menu items in a ListView.
 */

package com.example.vinniestrattoria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private List<MenuItem> menuItems;

    public ItemAdapter(Context context, List<MenuItem> menuItems) {
        this.context = context;
        this.menuItems = menuItems;
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return menuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_menu_item, parent, false);

            holder = new ViewHolder();
            holder.categoryHeaderTextView = convertView.findViewById(R.id.headerTextView);
            holder.itemNameTextView = convertView.findViewById(R.id.itemNameTextView);
            holder.priceTextView = convertView.findViewById(R.id.priceTextView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MenuItem menuItem = menuItems.get(position);

        // Set the category header text and visibility
        if (position == 0 || !menuItems.get(position - 1).getCategory().equals(menuItem.getCategory())) {
            holder.categoryHeaderTextView.setText(menuItem.getCategory());
            holder.categoryHeaderTextView.setVisibility(View.VISIBLE);
        } else {
            holder.categoryHeaderTextView.setVisibility(View.GONE);
        }

        // Set the item name and price text
        holder.itemNameTextView.setText(menuItem.getItemName());
        holder.priceTextView.setText(String.valueOf(menuItem.getPrice()));

        return convertView;
    }

    private static class ViewHolder {
        TextView categoryHeaderTextView;
        TextView itemNameTextView;
        TextView priceTextView;
    }

    public List<MenuItem> getItems() {
        return menuItems;
    }

    public void setItems(List<MenuItem> items) {
        this.menuItems = items;
        notifyDataSetChanged();
    }
}