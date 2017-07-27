package com.example.ntinos.funshine.Controllers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ntinos.funshine.Activities.WeatherActivity;
import com.example.ntinos.funshine.R;
import com.example.ntinos.funshine.model.DailyWeatherReport;

import java.util.ArrayList;

/**
 * Created by Ntinos on 27/7/2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherReportViewHolder>{

    private ArrayList<DailyWeatherReport> mDailyWeatherReport;
    private Context context;

    public WeatherAdapter(ArrayList<DailyWeatherReport> mDailyWeatherReport, Context current) {
        this.mDailyWeatherReport = mDailyWeatherReport;
        context = current;
    }

    @Override
    public void onBindViewHolder(WeatherReportViewHolder holder, int position) {
        DailyWeatherReport report = mDailyWeatherReport.get(position);
        holder.updateUI(report);
    }

    @Override
    public int getItemCount() {
        return mDailyWeatherReport.size();
    }

    @Override
    public WeatherReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_weather,parent,false);
        return new WeatherReportViewHolder(card, context);

    }
}
