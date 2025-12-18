package com.example.androidtest1;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SAActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sa);

        ListView listView = findViewById(R.id.listView);
        List<Map<String, Object>> data = new ArrayList<>();

        data.add(createChat(R.drawable.avatar1, "USER 1", "Hello", "14:35", 2, true, false));
        data.add(createChat(0, "USER 2", "World!!", "10:19", 190, true, true));
        data.add(createChat(0, "USER 3", "QWERTY!!", "01:12", 11, true, true));
        data.add(createChat(0, "USER 4", "53421123", "11:11", 22, true, false));

        String[] from = {"avatar", "name", "last_message", "time", "unread_count", "status_icon"};
        int[] to = {R.id.avatar, R.id.name, R.id.last_message, R.id.time, R.id.unread, R.id.status};
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.sa_item, from, to);

        adapter.setViewBinder((view, value, text) -> {
            if (view.getId() == R.id.unread) {
                int count = (int) value;
                view.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
                ((TextView) view).setText(String.valueOf(count));
                return true;
            }
            if (view.getId() == R.id.status) {
                ((ImageView) view).setImageResource((int) value);
                return true;
            }
            return false;
        });

        listView.setAdapter(adapter);
    }

    private Map<String, Object> createChat(int avatar, String name, String last_message, String time, int unreadCount, boolean isSent, boolean isRead) {
        Map<String, Object> map = new HashMap<>();
        map.put("avatar", avatar == 0 ? R.drawable.avatar_placeholder : avatar);
        map.put("name", name);
        map.put("last_message", last_message);
        map.put("time", time);
        map.put("unread_count", unreadCount);

        int statusIcon = 0;

        if (isSent && isRead)
            statusIcon = R.drawable.ic_read;
        else if (isSent)
            statusIcon = R.drawable.ic_sent;

        map.put("status_icon", statusIcon);
        return map;
    }
}
