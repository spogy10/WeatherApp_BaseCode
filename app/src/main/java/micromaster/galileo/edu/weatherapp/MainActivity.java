package micromaster.galileo.edu.weatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import micromaster.galileo.edu.weatherapp.API.WeatherInterface;
import micromaster.galileo.edu.weatherapp.model.WeatherData;
import micromaster.galileo.edu.weatherapp.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final static String BASE_URL = "http://api.wunderground.com/api/";
    private final static String API_KEY = "cb8278363f0b144e";
    @BindView(R.id.pressure)
    TextView pressure;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.countryName)
    TextView countryName;
    @BindView(R.id.temperature)
    TextView temperature;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.humidity)
    TextView humidity;
    @BindView(R.id.weather)
    TextView weather;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        new Task().execute();

    }

    private class Task extends AsyncTask<Void, Void, WeatherResponse> {


        @Override
        protected WeatherResponse doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            WeatherInterface weatherInterface = retrofit.create(WeatherInterface.class);
            Call<WeatherResponse> call = weatherInterface.getWeatherFromSanFrancisco(API_KEY);
            WeatherResponse weatherResponse = null;
            try {
                return call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(WeatherResponse weatherResponse) {

            //Log.d("Paul", weatherResponse.getWeatherData().getHumidity());
            WeatherData data = weatherResponse.getWeatherData();


            pressure.setText(String.valueOf(data.getPressure()));
            weather.setText(data.getWeather());
            countryName.setText(data.getDisplayLocation().getCityName());
            humidity.setText(data.getHumidity());
            temperature.setText(data.getTemp());

        }
    }


}
