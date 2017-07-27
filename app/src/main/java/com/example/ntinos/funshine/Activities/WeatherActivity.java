package com.example.ntinos.funshine.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ntinos.funshine.Controllers.WeatherAdapter;
import com.example.ntinos.funshine.R;
import com.example.ntinos.funshine.model.DailyWeatherReport;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast";
    final String URL_COORD =  "/?lat=";//"/?lat=39.641894&lon=20.846556";
    final String URL_UNITS = "&units=metric";
    final String URL_API_KEY = "&APPID=62b39e024f54e19d3a85797aed2d0c18";

    private GoogleApiClient mGoogleApiClient;
    final int PERMISSION_LOCATION = 111;

    ArrayList<DailyWeatherReport> dailyReport = new ArrayList<>();

    private ImageView weatherIconMini;
    private ImageView weatherIcon;
    private TextView weatherDate;
    private TextView cityCountry;
    private TextView currentTemp;
    private TextView minTemp;
    private TextView weatherType;

    WeatherAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.ntinos.funshine.R.layout.activity_weather);

        weatherIcon = (ImageView) findViewById(R.id.weatherIcon);
        weatherIconMini = (ImageView) findViewById(R.id.weatherIconMini);
        currentTemp = (TextView)findViewById(R.id.currentTemp);
        minTemp = (TextView)findViewById(R.id.minTemp);
        weatherDate = (TextView)findViewById(R.id.weatherDate);
        cityCountry = (TextView)findViewById(R.id.city);
        weatherType = (TextView)findViewById(R.id.weatherType);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.content_weather_reports);

        mAdapter = new WeatherAdapter(dailyReport, this);

        recyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    public void downloadWeatherData(Location location){
        final String fullCoords = URL_COORD + location.getLatitude() + "&lon=" + location.getLongitude();
        final String url = BASE_URL + fullCoords + URL_UNITS + URL_API_KEY;

        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject city = response.getJSONObject("city");
                    String cityName = city.getString("name");
                    String country = city.getString("country");

                    JSONArray list = response.getJSONArray("list");

                    for(int i=0; i<7; i++){
                        JSONObject obj = list.getJSONObject(i);
                        JSONObject main = obj.getJSONObject("main");
                        Double currentTemp = main.getDouble("temp");
                        Double maxTemp = main.getDouble("temp_max");
                        Double minTemp = main.getDouble("temp_min");

                        JSONArray weatherArr = obj.getJSONArray("weather");
                        JSONObject weather = weatherArr.getJSONObject(0);
                        String weatherType = weather.getString("main");

                        String rawDate = obj.getString("dt_txt");

                        DailyWeatherReport report = new DailyWeatherReport(country, cityName, rawDate, maxTemp.intValue(), minTemp.intValue(), currentTemp.intValue(), weatherType);

                        dailyReport.add(report);
                    }

                }catch (JSONException e){
                    Log.v("JSONError","Error: " + e.getLocalizedMessage());
                }

                updateUI();
                mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("FUN", "Err: " + error.getLocalizedMessage());
            }
        });

        Volley.newRequestQueue(this).add(jsonRequest);
    }

    public void updateUI(){
        if(dailyReport.size()>0) {
            DailyWeatherReport report = dailyReport.get(0);

            switch (report.getWeatherType()){
                case DailyWeatherReport.WEATHER_TYPE_CLOUDS:
                    weatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
                    weatherIconMini.setImageDrawable(getResources().getDrawable(R.drawable.cloudy_mini));
                    break;
                case DailyWeatherReport.WEATHER_TYPE_RAIN:
                    weatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.rainy));
                    weatherIconMini.setImageDrawable(getResources().getDrawable(R.drawable.rainy_mini));
                    break;
                case DailyWeatherReport.WEATHER_TYPE_SNOW:
                    weatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.snow));
                    weatherIconMini.setImageDrawable(getResources().getDrawable(R.drawable.snow_mini));
                    break;
                default:
                    weatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.sunny));
                    weatherIconMini.setImageDrawable(getResources().getDrawable(R.drawable.sunny_mini));
            }

            weatherDate.setText("Today, " + report.getDay());
            currentTemp.setText(Integer.toString(report.getCurrentTemp()) + "°");
            minTemp.setText(Integer.toString(report.getMinTemp()) + "°");
            cityCountry.setText(report.getCityName() + ", " + report.getCountry());
            weatherType.setText(report.getWeatherType());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        downloadWeatherData(location);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION);
        }
        else{
            startLocationServices();
        }
    }

    public void startLocationServices(){
        try{
            LocationRequest req = LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,req,this);
        }catch (SecurityException exception){

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMISSION_LOCATION: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startLocationServices();
                }
                else{
                    //show a dialog saying something like, "I can't run your location dummy - you denied permission!"
                    Toast.makeText(this, "I can't run your location dummy - you denied permission", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
