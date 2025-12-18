package com.example.androidtest1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidtest1.R;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class DiagramActivity extends AppCompatActivity {

    private BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagram);

        chart = findViewById(R.id.barChart);

        setupChart();
    }

    private void setupChart() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 10));
        entries.add(new BarEntry(1, 25));
        entries.add(new BarEntry(2, 15));
        entries.add(new BarEntry(3, 30));
        entries.add(new BarEntry(4, 90));

        BarDataSet dataSet = new BarDataSet(entries, "numbers");
        dataSet.setColor(Color.parseColor("#00ff00"));
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.parseColor("#ff0000"));

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.5f);

        chart.setData(barData);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawGridLines(false);

        chart.getDescription().setEnabled(false);
        chart.setFitBars(true);
        chart.animateY(1000);

        chart.invalidate();
    }
}
