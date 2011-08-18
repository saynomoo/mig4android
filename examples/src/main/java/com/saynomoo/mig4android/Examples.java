package com.saynomoo.mig4android;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Examples extends ListActivity{
    Class[] activities = new Class[]{QuickStart.class, Growing.class, BasicSizes.class, AbsolutePosition.class, LogicalPixels.class, AnimateLayout.class};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new BaseAdapter() {
            public int getCount() {
                return activities.length;
            }

            public Object getItem(int position) {
                return position;
            }

            public long getItemId(int position) {
                return position;
            }

            public View getView(final int position, View convertView, ViewGroup parent) {
                final TextView textView = new TextView(Examples.this);
                textView.setTextSize(24);
                textView.setPadding(5, 5, 0, 5);
                textView.setText(activities[position].getSimpleName());
                textView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent myIntent = new Intent(Examples.this, activities[position]);
                        startActivity(myIntent);
                    }
                });
                return textView;
            }
        });
    }
}
