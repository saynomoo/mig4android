package com.saynomoo.mig4android;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class MigLayoutInListView extends MigActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new ListView(this){{
            setAdapter(new BaseAdapter() {
                public int getCount() {
                    return 10;
                }

                public Object getItem(int position) {
                    return position;
                }

                public long getItemId(int position) {
                    return position;
                }

                public View getView(final int position, View convertView, ViewGroup parent) {
                    return new MigLayout(getContext()) {{
                        addLabel(this, "Item " + position, "");
                    }};
                }
            });
        }});
    }

    @Override
    public MigLayout createLayout() {
        return new MigLayout(this);
    }
}
