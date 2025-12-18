package com.example.androidtest1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RVActivity extends AppCompatActivity {

    private TextView totalText;
    private List<Gift> gifts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv);

        totalText = findViewById(R.id.total);
        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        gifts = new ArrayList<>();
        gifts.add(new Gift(R.drawable.ic_headphones, "Headphones", 100));
        gifts.add(new Gift(R.drawable.ic_book, "Book", 200));
        gifts.add(new Gift(R.drawable.ic_mouse, "Mouse", 300));
        gifts.add(new Gift(R.drawable.ic_keyboard, "Keyboard", 400));
        gifts.add(new Gift(R.drawable.ic_headphones, "Headphones", 100));
        gifts.add(new Gift(R.drawable.ic_book, "Book", 200));
        gifts.add(new Gift(R.drawable.ic_mouse, "Mouse", 300));
        gifts.add(new Gift(R.drawable.ic_keyboard, "Keyboard", 400));
        gifts.add(new Gift(R.drawable.ic_headphones, "Headphones", 100));
        gifts.add(new Gift(R.drawable.ic_book, "Book", 200));
        gifts.add(new Gift(R.drawable.ic_mouse, "Mouse", 300));
        gifts.add(new Gift(R.drawable.ic_keyboard, "Keyboard", 400));
        gifts.add(new Gift(R.drawable.ic_headphones, "Headphones", 100));
        gifts.add(new Gift(R.drawable.ic_book, "Book", 200));
        gifts.add(new Gift(R.drawable.ic_mouse, "Mouse", 300));
        gifts.add(new Gift(R.drawable.ic_keyboard, "Keyboard", 400));
        gifts.add(new Gift(R.drawable.ic_headphones, "Headphones", 100));
        gifts.add(new Gift(R.drawable.ic_book, "Book", 200));
        gifts.add(new Gift(R.drawable.ic_mouse, "Mouse", 300));
        gifts.add(new Gift(R.drawable.ic_keyboard, "Keyboard", 400));
        gifts.add(new Gift(R.drawable.ic_headphones, "Headphones", 100));
        gifts.add(new Gift(R.drawable.ic_book, "Book", 200));
        gifts.add(new Gift(R.drawable.ic_mouse, "Mouse", 300));
        gifts.add(new Gift(R.drawable.ic_keyboard, "Keyboard", 400));
        gifts.add(new Gift(R.drawable.ic_headphones, "Headphones", 100));
        gifts.add(new Gift(R.drawable.ic_book, "Book", 200));
        gifts.add(new Gift(R.drawable.ic_mouse, "Mouse", 300));
        gifts.add(new Gift(R.drawable.ic_keyboard, "Keyboard", 400));
        gifts.add(new Gift(R.drawable.ic_headphones, "Headphones", 100));
        gifts.add(new Gift(R.drawable.ic_book, "Book", 200));
        gifts.add(new Gift(R.drawable.ic_mouse, "Mouse", 300));
        gifts.add(new Gift(R.drawable.ic_keyboard, "Keyboard", 400));
        gifts.add(new Gift(R.drawable.ic_headphones, "Headphones", 100));
        gifts.add(new Gift(R.drawable.ic_book, "Book", 200));
        gifts.add(new Gift(R.drawable.ic_mouse, "Mouse", 300));
        gifts.add(new Gift(R.drawable.ic_keyboard, "Keyboard", 400));
        gifts.add(new Gift(R.drawable.ic_headphones, "Headphones", 100));
        gifts.add(new Gift(R.drawable.ic_book, "Book", 200));
        gifts.add(new Gift(R.drawable.ic_mouse, "Mouse", 300));
        gifts.add(new Gift(R.drawable.ic_keyboard, "Keyboard", 400));
        gifts.add(new Gift(R.drawable.ic_headphones, "Headphones", 100));
        gifts.add(new Gift(R.drawable.ic_book, "Book", 200));
        gifts.add(new Gift(R.drawable.ic_mouse, "Mouse", 300));
        gifts.add(new Gift(R.drawable.ic_keyboard, "Keyboard", 400));
        gifts.add(new Gift(R.drawable.ic_headphones, "Headphones", 100));
        gifts.add(new Gift(R.drawable.ic_book, "Book", 200));
        gifts.add(new Gift(R.drawable.ic_mouse, "Mouse", 300));
        gifts.add(new Gift(R.drawable.ic_keyboard, "Keyboard", 400));
        gifts.add(new Gift(R.drawable.ic_headphones, "Headphones", 100));
        gifts.add(new Gift(R.drawable.ic_book, "Book", 200));
        gifts.add(new Gift(R.drawable.ic_mouse, "Mouse", 300));
        gifts.add(new Gift(R.drawable.ic_keyboard, "Keyboard", 400));
        gifts.add(new Gift(R.drawable.ic_headphones, "Headphones", 100));
        gifts.add(new Gift(R.drawable.ic_book, "Book", 200));
        gifts.add(new Gift(R.drawable.ic_mouse, "Mouse", 300));
        gifts.add(new Gift(R.drawable.ic_keyboard, "Keyboard", 400));
        gifts.add(new Gift(R.drawable.ic_headphones, "Headphones", 100));
        gifts.add(new Gift(R.drawable.ic_book, "Book", 200));
        gifts.add(new Gift(R.drawable.ic_mouse, "Mouse", 300));
        gifts.add(new Gift(R.drawable.ic_keyboard, "Keyboard", 400));
        gifts.add(new Gift(R.drawable.ic_headphones, "Headphones", 100));
        gifts.add(new Gift(R.drawable.ic_book, "Book", 200));
        gifts.add(new Gift(R.drawable.ic_mouse, "Mouse", 300));
        gifts.add(new Gift(R.drawable.ic_keyboard, "Keyboard", 400));


        GiftAdapter adapter = new GiftAdapter(gifts, this::updateTotal);
        recycler.setAdapter(adapter);
    }

    private void updateTotal() {
        int sum = 0;
        for (Gift g : gifts) {
            if (g.checked) sum += g.price;
        }
        totalText.setText("Total: " + sum + " грн");
    }
}

class Gift {
    public int imageRes;
    public String name;
    public int price;
    public boolean checked;

    public Gift(int imageRes, String name, int price) {
        this.imageRes = imageRes;
        this.name = name;
        this.price = price;
        this.checked = false;
    }
}


class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.ViewHolder> {
    public interface OnCheckedChange {
        void onChange();
    }

    private List<Gift> gifts;
    private OnCheckedChange listener;

    public GiftAdapter(List<Gift> gifts, OnCheckedChange listener) {
        this.gifts = gifts;
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, price;
        CheckBox check;

        ViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.img);
            name = view.findViewById(R.id.name);
            price = view.findViewById(R.id.price);
            check = view.findViewById(R.id.check);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_gift, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        Gift g = gifts.get(i);

        holder.img.setImageResource(g.imageRes);
        holder.name.setText(g.name);
        holder.price.setText(g.price + " грн");

        holder.check.setOnCheckedChangeListener(null);
        holder.check.setChecked(g.checked);

        holder.check.setOnCheckedChangeListener((b, checked) -> {
            g.checked = checked;
            listener.onChange();
        });
    }

    @Override
    public int getItemCount() {
        return gifts.size();
    }
}
