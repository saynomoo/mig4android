package com.saynomoo.mig4android.test;

import android.R;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.saynomoo.mig4android.MigActivity;
import com.saynomoo.mig4android.MigLayout;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GrowingListView extends MigActivity {
    @Override
    public MigLayout createLayout() {
        return new GrowingListViewView();
    }
    class GrowingListViewView extends MigLayout {
        public GrowingListViewView() {
            super(GrowingListView.this);
            final ListView listView = new ListView(getContext());

            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(listView.getContext(), R.layout.simple_list_item_1, new ArrayList<String>());
            listView.setAdapter(adapter);

            postDelayed(new Runnable() {
                public void run() {
                    adapter.add("Hello, world");
                    postDelayed(this, 1000);
                }
            }, 1000);

//            addView(listView, "pos 20 20 300px null"); // Works as expected
        addView(listView, "pos 20 20 300px null, wmin 0"); // Didn't grow height
//          addView(listView, "pos 20 20 300px null, pad 0 0 0 -10"); // Didn't grow height
        }
    }
}
