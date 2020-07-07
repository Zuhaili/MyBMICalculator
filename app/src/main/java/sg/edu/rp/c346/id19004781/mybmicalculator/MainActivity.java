package sg.edu.rp.c346.id19004781.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button btnCalculate, btnResetData;
    TextView tvDate, tvBMI, tvBMIResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnResetData = findViewById(R.id.buttonResetData);
        tvDate = findViewById(R.id.textViewDate);
        tvBMI = findViewById(R.id.textViewBMI);
        tvBMIResult = findViewById(R.id.textViewBMIResult);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strWeight = etWeight.getText().toString();
                String strHeight = etHeight.getText().toString();

                float weight = Float.parseFloat(strWeight);
                float height = Float.parseFloat(strHeight);
                float bmi = weight / (height * height);

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                tvDate.setText(datetime);
                tvBMI.setText(String.format("%.3f", bmi));
                etWeight.setText("");
                etHeight.setText("");
//                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//                SharedPreferences.Editor prefEdit = prefs.edit();
//                prefEdit.putInt("weight", intWeight);
//                prefEdit.putInt("height", intHeight);
//
//                prefEdit.commit();

                //Enhancement
                if (bmi == 0.0){
                    tvBMIResult.setText("");
                }
                else if (bmi > 0.0 && bmi < 18.5) {
                    tvBMIResult.setText("underweight");
                }
                else if (bmi >= 18.5 && bmi <= 24.9) {
                    tvBMIResult.setText("normal");
                }
                else if (bmi >= 25.0 && bmi <= 29.9) {
                    tvBMIResult.setText("overweight");
                }
                else if (bmi >= 30) {
                    tvBMIResult.setText("obese");
                }
                else {
                    tvBMIResult.setText("Error in calculation");
                }

            }
        });

        btnResetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDate.setText("Last Calculated Date:");
                tvBMI.setText("Last Calculated BMI:");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Step 1a: Get the user input from the EditText and store it in a variable
        String dateTimeString = tvDate.getText().toString();
        float bmi = Float.parseFloat(tvBMI.getText().toString());


        // Step 1b: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Step 1c: Obtain an instance of the SharedPreference Editor for update later
        SharedPreferences.Editor prefEdit = prefs.edit();

        // Step 1d: Add the key-value pair
        prefEdit.putString("datetime", "Last Calculated Date: " + dateTimeString);
        prefEdit.putFloat("lastBMIValue", bmi);

        // Step 1e: Call commit() to save the changes into SharedPreferences
        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Step 2a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Step 2b: Retrieve the saved data from the SharedPreferences object
        String datetimeString = prefs.getString("datetime", "");
        float lastBMIValue = prefs.getFloat("lastBMIValue", 0);


        // Step 2c: Update the UI element with the value
        tvDate.setText(datetimeString);
        tvBMI.setText(lastBMIValue + "");
    }
}
