package com.blackinc.browniecounter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public int numbers;
    private String MyPREFENCES = "MyPrefs";
    public static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(MyPREFENCES, MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView stuff = (TextView) findViewById(R.id.quantity_text_view);
        numbers = sharedPreferences.getInt("numberOfTimes", 0);
        stuff.setText("" + numbers);
    }

    public void increaseNumber(View view) {

        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        numbers = numbers + 1;
        quantityTextView.setText("" + numbers);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("numberOfTimes", numbers);
        editor.commit();
    }

    public void decreaseNumber(View view) {

        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        numbers = numbers - 1;
        quantityTextView.setText("" + numbers);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("numberOfTimes", numbers);
        editor.commit();
    }
}
