package com.example.ntinos.funshine.Controllers;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ntinos.funshine.Activities.WeatherActivity;
import com.example.ntinos.funshine.R;
import com.example.ntinos.funshine.model.DailyWeatherReport;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ntinos on 27/7/2017.
 */

public class WeatherReportViewHolder extends RecyclerView.ViewHolder{
    private ImageView weatherIcon;
    private TextView weatherDate;
    private TextView weatherDescription;
    private TextView tempHigh;
    private TextView tempLow;
    private Context context;

    public WeatherReportViewHolder(View itemView, Context current) {
        super(itemView);

        weatherIcon = (ImageView)itemView.findViewById(R.id.weather_icon);
        weatherDate = (TextView)itemView.findViewById(R.id.weather_day);
        weatherDescription = (TextView)itemView.findViewById(R.id.weather_description);
        tempHigh = (TextView)itemView.findViewById(R.id.weather_temp_high);
        tempLow = (TextView)itemView.findViewById(R.id.weather_temp_low);
        context = current;
    }

    public void updateUI(DailyWeatherReport report){

        weatherDate.setText(report.getDate());
        weatherDescription.setText(report.getWeatherType());
        tempHigh.setText(Integer.toString(report.getMaxTemp()) + "°");
        tempLow.setText(Integer.toString(report.getMinTemp()) + "°");

        switch (report.getWeatherType()){
            case DailyWeatherReport.WEATHER_TYPE_CLOUDS:
                weatherIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.cloudy_mini));
                break;
            case DailyWeatherReport.WEATHER_TYPE_RAIN:
                weatherIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.rainy_mini));
                break;
            case DailyWeatherReport.WEATHER_TYPE_SNOW:
                weatherIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.snow_mini));
                break;
            default:
                weatherIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.sunny_mini));
        }


    }
}
