package com.example.ntinos.funshine.model;

/**
 * Created by Konstantinos Tsiounis on 13/2/2017.
 */

public class DailyWeatherReport {
    public static final String WEATHER_TYPE_CLOUDS = "Clouds";
    public static final String WEATHER_TYPE_CLEAR = "Clear";
    public static final String WEATHER_TYPE_RAIN = "Rain";
    public static final String WEATHER_TYPE_SNOW = "Snow";
    public static final String WEATHER_TYPE_WIND = "Wind";

    private String cityName;
    private String country;
    private String convertedDate;
    private int maxTemp;
    private int minTemp;
    private int currentTemp;
    private String weatherType;

    public String convertDate (String date){
        String convertedDate;
        String[] parts = date.split(" ");
        String[] splitedDate = parts[0].split("-");
        String[] splitedTime = parts[1].split(":");
        String day = splitedDate[2];
        String month = splitedDate[1];

        switch (month){
            case "01":
                month = "January";
                break;
            case "02":
                month = "February";
                break;
            case "03":
                month = "March";
                break;
            case "04":
                month = "April";
                break;
            case "05":
                month = "May";
                break;
            case "06":
                month = "June";
                break;
            case "07":
                month = "Jule";
                break;
            case "08":
                month = "August";
                break;
            case "09":
                month = "September";
                break;
            case "10":
                month = "October";
                break;
            case "11":
                month = "November";
                break;
            default:
                month = "December";
                break;
        }

        convertedDate = day + " " + month + "   " + splitedTime[0] + ":00";

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

    public String getDay(){
        String[] date = convertedDate.split(" ");
        return date[0] + " " + date[1];
    }
}
