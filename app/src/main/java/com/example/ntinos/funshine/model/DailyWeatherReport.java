package com.example.ntinos.funshine.model;

/**
 * Created by Konstantinos Tsiounis on 13/2/2017.
 */

public class DailyWeatherReport {
    private String cityName;
    private String country;
    private String convertedDate;
    private int maxTemp;
    private int minTemp;
    private int currentTemp;
    private String weatherType;

    public String convertDate (String date){
        String convertedDate = "1 May";

        //TODO convert the initial date
        return convertedDate;
    }

    public DailyWeatherReport(String country, String cityName, String date, int maxTemp, int minTemp, int currentTemp, String weatherType) {
        this.country = country;
        this.cityName = cityName;
        this.convertedDate = convertDate(date);
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.currentTemp = currentTemp;
        this.weatherType = weatherType;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }

    public String getDate() {
        return convertedDate;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }

    public String getWeatherType() {
        return weatherType;
    }
}
