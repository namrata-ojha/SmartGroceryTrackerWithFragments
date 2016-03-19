package com.scu.smartgrocerytracker.historygrocery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.scu.smartgrocerytracker.R;
import com.scu.smartgrocerytracker.constants.Constants;

public class historyy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historyy);

        TextView textView = (TextView) findViewById(R.id.historyTitle);
        TextView textView1 = (TextView) findViewById(R.id.amountVeggie);
        TextView textView2 = (TextView) findViewById(R.id.amountFruits);
        TextView textView3 = (TextView) findViewById(R.id.amountDairy);
        TextView textView4 = (TextView) findViewById(R.id.amountUncategorised);

        Button button1 = (Button) findViewById(R.id.buttonVeggie);
        Button button2 = (Button) findViewById(R.id.buttonFruits);
        Button button3 = (Button) findViewById(R.id.buttonDairy);
        Button button4 = (Button) findViewById(R.id.buttonUncategorised);

        textView4.setText(Double.toString(Constants.Uncategorized_total));
        textView1.setText(Double.toString(Constants.Vegetables_total));
        textView2.setText(Double.toString(Constants.Fruits_total));
        textView3.setText(Double.toString(Constants.Dairy_total));





    }


    public void clearVeggiesTotal(View view) {
        TextView textView1 = (TextView) findViewById(R.id.amountVeggie);
        Constants.Vegetables_total = 0.0;
        textView1.setText(Double.toString(Constants.Vegetables_total));

    }


    public void clearFruitsTotal(View view) {
        TextView textView2 = (TextView) findViewById(R.id.amountFruits);
        Constants.Fruits_total = 0.0;
        textView2.setText(Double.toString(Constants.Fruits_total));

    }


    public void clearDairyTotal(View view) {
        TextView textView3 = (TextView) findViewById(R.id.amountDairy);
        Constants.Dairy_total = 0.0;
        textView3.setText(Double.toString(Constants.Dairy_total));

    }

    public void clearUncategorisedTotal(View view) {
        TextView textView4 = (TextView) findViewById(R.id.amountUncategorised);
        Constants.Uncategorized_total = 0.0;
        textView4.setText(Double.toString(Constants.Uncategorized_total));

    }
}




