package com.example.weatherproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
ImageView mainImage;
TextView CurrentF;
TextView quote;
TextView QuoteAnswer;
TextView currentDate;
EditText edit;
JsonTask task;
TextView time1;
TextView time2;
TextView time3;
TextView time4;
TextView time5;
TextView high1;
TextView high2;
TextView high3;
TextView high4;
TextView high5;
TextView lo1;
TextView lo2;
TextView lo3;
TextView lo4;
TextView lo5;
ImageView image1;
ImageView image2;
ImageView image3;
ImageView image4;
ImageView image5;
Button button;
Button run;








    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         task = new JsonTask();

        edit = findViewById(R.id.edit);
        mainImage = findViewById(R.id.mainImage);
        CurrentF = findViewById(R.id.CurrentF);
        quote = findViewById(R.id.Quote);
        QuoteAnswer = findViewById(R.id.currentDate);
        currentDate = findViewById(R.id.QuoteAnswer);
        time1 = findViewById(R.id.time1);
        time2 = findViewById(R.id.time2);
        time3 = findViewById(R.id.time3);
        time4 = findViewById(R.id.time4);
        time5 = findViewById(R.id.time5);
        high1 = findViewById(R.id.high1);
        high2 = findViewById(R.id.high2);
        high3 = findViewById(R.id.high3);
        high4 = findViewById(R.id.high4);
        high5 = findViewById(R.id.high5);
        lo1 = findViewById(R.id.lo1);
        lo2 = findViewById(R.id.lo2);
        lo3 = findViewById(R.id.lo3);
        lo4 = findViewById(R.id.lo4);
        lo5 = findViewById(R.id.lo5);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image4 = findViewById(R.id.image4);
        image5 = findViewById(R.id.image5);
        button = findViewById(R.id.button);
        run = findViewById(R.id.button2);


        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.execute();


            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("TAG88","clicked");
                (new JsonTask()).execute();

            }
        });
//        task.execute();


    }



    public class JsonTask extends AsyncTask<Void, Void, Void> {
        JSONObject currentWeather;
        JSONObject hourlyWeather;


        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String zip = edit.getText().toString();

            //zip = "08852";



            try {
                URL url = new URL("https://api.openweathermap.org/data/2.5/weather?zip="+zip+"&appid=e70410f3ef2961132574cdf1f2bd873b");
                Log.d("TAG5",url.toString());

                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");

                }
                currentWeather = new JSONObject(buffer.toString());
                Log.d("Tag",currentWeather.getJSONArray("weather").toString());
                // return buffer.toString();

                url = new URL("https://api.openweathermap.org/data/2.5/forecast?zip="+zip+"&appid=e70410f3ef2961132574cdf1f2bd873b");
                Log.d("TAG60",url.toString());
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                buffer = new StringBuffer();
                line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");

                }
                hourlyWeather = new JSONObject(buffer.toString());
                Log.d("TAG1" ,hourlyWeather.toString());
                Log.d("TAG2" ,hourlyWeather.getJSONObject("city").toString());
                Log.d("TAG3" ,hourlyWeather.getJSONObject("city").getString("name").toString());
                Log.d("TAG4", hourlyWeather.getJSONArray("list").toString());
                Log.d("TAG5", hourlyWeather.getJSONArray("list").getJSONObject(2).getString("main").toString());



                //Log.d("TAG",currentWeather.getJSONObject("name").toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("TAG8",e.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("TAG8",e.toString());

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("TAG8",e.toString());

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);


            double K = 0;

           // currentWeather = post;


            try {
                Log.d("TAG",currentWeather.getJSONObject("main").toString());
                K = currentWeather.getJSONObject("main").getDouble("temp");

                //K = Integer.parseInt(myString);
            } catch (JSONException e) {
                e.printStackTrace();
            }



            int F = (int) (1.8 * (K - 273) + 32);


            CurrentF.setText(""+F+"°F");


            try {
                Log.d("temp",currentWeather.getJSONArray("weather").getJSONObject(0).getString("main").toString());
                //Log.d("tem",hourlyWeather.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp_min").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }


            //CURRENT WEATHER IMAGES
            try {
                if(currentWeather.getJSONArray("weather").getJSONObject(0).getString("main").equals("Rain")){
                    mainImage.setImageResource(R.drawable.rain);
                    quote.setText("Why did the man use ketchup in the rain?");
                    QuoteAnswer.setText("Because it was raining cats and hot dogs.");
                }
                if(currentWeather.getJSONArray("weather").getJSONObject(0).getString("main").equals("Thunderstorm")){
                    mainImage.setImageResource(R.drawable.thundercloud);
                    quote.setText("Why do nerds like to sit outside during a Thunderstorm");
                    QuoteAnswer.setText("They know it only takes one lightening strike to be the flash");
                }
                if(currentWeather.getJSONArray("weather").getJSONObject(0).getString("main").equals("Drizzle")){
                    mainImage.setImageResource(R.drawable.rain);
                    quote.setText("What does it do before it rains candy?");
                    QuoteAnswer.setText("It sprinkles!");
                }
                if(currentWeather.getJSONArray("weather").getJSONObject(0).getString("main").equals("Snow")){
                    mainImage.setImageResource(R.drawable.snow);
                    quote.setText("What is a snowman’s favorite snack?");
                    QuoteAnswer.setText("Ice krispies treats.");
                }
                if(currentWeather.getJSONArray("weather").getJSONObject(0).getString("main").equals("Clear")){
                    mainImage.setImageResource(R.drawable.clear);
                    quote.setText("Why is the sky blue?");
                    QuoteAnswer.setText("Because it is lonely up there");
                }
                if(currentWeather.getJSONArray("weather").getJSONObject(0).getString("main").equals("Clouds")){
                    mainImage.setImageResource(R.drawable.clouds);
                    quote.setText("How do you wrap a cloud");
                    QuoteAnswer.setText("With a rainbow");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //HOURLY WEATHER
            try {
                Log.d("poop", hourlyWeather.getJSONArray("list").getJSONObject(2).getString("main").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }


            double kelvin = 0;
            //hourly weather 1

            try {
               kelvin = hourlyWeather.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp_min");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int faren = (int) (1.8 * (kelvin - 273) + 32);
            lo1.setText(""+faren+"°F");
            Log.d("TAG55",lo1.getText().toString());

            try {
                kelvin = hourlyWeather.getJSONArray("list").getJSONObject(1).getJSONObject("main").getDouble("temp_min");
            } catch (JSONException e) {
                e.printStackTrace();
            }
             faren = (int) (1.8 * (kelvin - 273) + 32);
            lo2.setText(""+faren+"°F");
            Log.d("TAG55",lo2.getText().toString());

            try {
                kelvin = hourlyWeather.getJSONArray("list").getJSONObject(2).getJSONObject("main").getDouble("temp_min");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            faren = (int) (1.8 * (kelvin - 273) + 32);
            lo3.setText(""+faren+"°F");
            try {
                kelvin = hourlyWeather.getJSONArray("list").getJSONObject(3).getJSONObject("main").getDouble("temp_min");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            faren = (int) (1.8 * (kelvin - 273) + 32);
            lo4.setText(""+faren+"°F");
            try {
                kelvin = hourlyWeather.getJSONArray("list").getJSONObject(4).getJSONObject("main").getDouble("temp_min");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            faren = (int) (1.8 * (kelvin - 273) + 32);
            lo5.setText(""+faren+"°F");

//MAX_TEMP
            try {
                kelvin = hourlyWeather.getJSONArray("list").getJSONObject(0).getJSONObject("main").getDouble("temp_max");
            } catch (JSONException e) {
                e.printStackTrace();
            }
             faren = (int) (1.8 * (kelvin - 273) + 32);
            high1.setText(""+faren+"°F");
            Log.d("TAG70",high1.getText().toString());

            try {
                kelvin = hourlyWeather.getJSONArray("list").getJSONObject(1).getJSONObject("main").getDouble("temp_max");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            faren = (int) (1.8 * (kelvin - 273) + 32);
            high2.setText(""+faren+"°F");
            Log.d("TAG71",high2.getText().toString());

            try {
                kelvin = hourlyWeather.getJSONArray("list").getJSONObject(2).getJSONObject("main").getDouble("temp_max");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            faren = (int) (1.8 * (kelvin - 273) + 32);
            high3.setText(""+faren+"°F");
            Log.d("TAG72",high3.getText().toString());

            try {
                kelvin = hourlyWeather.getJSONArray("list").getJSONObject(3).getJSONObject("main").getDouble("temp_max");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            faren = (int) (1.8 * (kelvin - 273) + 32);
            high4.setText(""+faren+"°F");
            Log.d("TAG73",high4.getText().toString());

            try {
                kelvin = hourlyWeather.getJSONArray("list").getJSONObject(4).getJSONObject("main").getDouble("temp_max");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            faren = (int) (1.8 * (kelvin - 273) + 32);
            high5.setText(""+faren+"°F");
            Log.d("TAG74",high5.getText().toString());

            try {
                //SNOW
                if(hourlyWeather.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main").equals("Snow")){
                    image1.setImageResource(R.drawable.snow);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("main").equals("Snow")){
                    image2.setImageResource(R.drawable.snow);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("main").equals("Snow")){
                    image3.setImageResource(R.drawable.snow);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("main").equals("Snow")){
                    image4.setImageResource(R.drawable.snow);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(4).getJSONArray("weather").getJSONObject(0).getString("main").equals("Snow")){
                    image5.setImageResource(R.drawable.snow);
                }
                //RAIN
                //                    mainImage.setImageResource(R.drawable.rain);
                if(hourlyWeather.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main").equals("Rain")){
                    image1.setImageResource(R.drawable.rain);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("main").equals("Rain")){
                    image2.setImageResource(R.drawable.rain);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("main").equals("Rain")){
                    image3.setImageResource(R.drawable.rain);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("main").equals("Rain")){
                    image4.setImageResource(R.drawable.rain);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(4).getJSONArray("weather").getJSONObject(0).getString("main").equals("Rain")){
                    image5.setImageResource(R.drawable.rain);
                }


                //Thunderstorm
                if(hourlyWeather.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main").equals("Thunderstorm")){
                    image1.setImageResource(R.drawable.thundercloud);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("main").equals("Thunderstorm")){
                    image2.setImageResource(R.drawable.thundercloud);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("main").equals("Thunderstorm")){
                    image3.setImageResource(R.drawable.thundercloud);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("main").equals("Thunderstorm")){
                    image4.setImageResource(R.drawable.thundercloud);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(4).getJSONArray("weather").getJSONObject(0).getString("main").equals("Thunderstorm")){
                    image5.setImageResource(R.drawable.thundercloud);
                }

                //Drizzle
                if(hourlyWeather.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main").equals("Drizzle")){
                    image1.setImageResource(R.drawable.rain);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("main").equals("Drizzle")){
                    image2.setImageResource(R.drawable.rain);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("main").equals("Drizzle")){
                    image3.setImageResource(R.drawable.rain);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("main").equals("Drizzle")){
                    image4.setImageResource(R.drawable.rain);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(4).getJSONArray("weather").getJSONObject(0).getString("main").equals("Drizzle")){
                    image5.setImageResource(R.drawable.rain);
                }

                //Clear
                if(hourlyWeather.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main").equals("Clear")){
                    image1.setImageResource(R.drawable.clear);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("main").equals("Clear")){
                    image2.setImageResource(R.drawable.clear);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("main").equals("Clear")){
                    image3.setImageResource(R.drawable.clear);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("main").equals("Clear")){
                    image4.setImageResource(R.drawable.clear);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(4).getJSONArray("weather").getJSONObject(0).getString("main").equals("Clear")){
                    image5.setImageResource(R.drawable.clear);
                }

                //Clouds
                if(hourlyWeather.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main").equals("Clouds")){
                    image1.setImageResource(R.drawable.cloudy);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("main").equals("Clouds")){
                    image2.setImageResource(R.drawable.cloudy);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("main").equals("Clouds")){
                    image3.setImageResource(R.drawable.cloudy);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(3).getJSONArray("weather").getJSONObject(0).getString("main").equals("Clouds")){
                    image4.setImageResource(R.drawable.cloudy);
                }
                if(hourlyWeather.getJSONArray("list").getJSONObject(4).getJSONArray("weather").getJSONObject(0).getString("main").equals("Clouds")){
                    image5.setImageResource(R.drawable.cloudy);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //EPOC time
            //Date date = new Date(epocTime*1000L); // Convert seconds to milliseconds
            //        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            //        sdf.setTimeZone(TimeZone.getTimeZone("EST")); // Set timezone to EST
            //        String estTime = sdf.format(date);

            try {
                Log.d("time",hourlyWeather.getJSONArray("list").getJSONObject(0).getString("dt"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                //FIRST DATE
                long epocTime1 = Long.parseLong(hourlyWeather.getJSONArray("list").getJSONObject(0).getString("dt"));
                Date date1 = new Date(epocTime1*1000L); // Convert seconds to milliseconds
                SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
                SimpleDateFormat current1 = new SimpleDateFormat("yyyy-MM-dd");
                sdf1.setTimeZone(TimeZone.getTimeZone("EST")); // Set timezone to EST
                String estTime1 = sdf1.format(date1);
                String CD1 = current1.format(date1);
                currentDate.setText(""+CD1);
                time1.setText(""+estTime1);
                //Log.d("epoc",estTime);

                //DATE 2
                long epocTime2 = Long.parseLong(hourlyWeather.getJSONArray("list").getJSONObject(1).getString("dt"));
                Date date2 = new Date(epocTime2*1000L); // Convert seconds to milliseconds
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
                sdf2.setTimeZone(TimeZone.getTimeZone("EST")); // Set timezone to EST
                String estTime2 = sdf2.format(date2);
                time2.setText(""+estTime2);

                //DATE 3
                long epocTime3 = Long.parseLong(hourlyWeather.getJSONArray("list").getJSONObject(2).getString("dt"));
                Date date3 = new Date(epocTime3*1000L); // Convert seconds to milliseconds
                SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm:ss");
                sdf3.setTimeZone(TimeZone.getTimeZone("EST")); // Set timezone to EST
                String estTime3 = sdf3.format(date3);
                time3.setText(""+estTime3);

                //DATE 4
                long epocTime4 = Long.parseLong(hourlyWeather.getJSONArray("list").getJSONObject(3).getString("dt"));
                Date date4 = new Date(epocTime4*1000L); // Convert seconds to milliseconds
                SimpleDateFormat sdf4 = new SimpleDateFormat("HH:mm:ss");
                sdf4.setTimeZone(TimeZone.getTimeZone("EST")); // Set timezone to EST
                String estTime4 = sdf4.format(date4);
                time4.setText(""+estTime4);

                //DATE 5
                long epocTime5 = Long.parseLong(hourlyWeather.getJSONArray("list").getJSONObject(4).getString("dt"));
                Date date5 = new Date(epocTime5*1000L); // Convert seconds to milliseconds
                SimpleDateFormat sdf5 = new SimpleDateFormat("HH:mm:ss");
                sdf5.setTimeZone(TimeZone.getTimeZone("EST")); // Set timezone to EST
                String estTime5 = sdf5.format(date5);
                time5.setText(""+estTime5);
            } catch (JSONException e) {
                e.printStackTrace();
            }




        }



    }



}