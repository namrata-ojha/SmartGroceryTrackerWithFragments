package com.scu.smartgrocerytracker.pantry;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.scu.smartgrocerytracker.R;
import com.scu.smartgrocerytracker.SmartGroceryDBHelper;
import com.scu.smartgrocerytracker.constants.Unit;
import com.scu.smartgrocerytracker.db_helper.PantryDbUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdatePantryActivity extends AppCompatActivity {
    private PantryItem pantryItem;
    private TextView itemNameView;
    private TextView categoryView;
    private Spinner unitSpinner;
    private EditText priceText;
    private EditText totalQuantityText;
    private EditText expiryEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pantry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get Item name and category from Shopping list activity
        Bundle bundle = getIntent().getExtras();
        pantryItem = (PantryItem) bundle.getSerializable(PantryDbUtils.PANTRY_TABLE_NAME);
        int id = pantryItem.getId();
        int itemId = pantryItem.getItemId();
        String itemName = pantryItem.getName();
        String itemCategory = pantryItem.getCategory();

        itemNameView = (TextView)findViewById(R.id.itemNameTextView);
        itemNameView.setText(itemName);

        categoryView = (TextView) findViewById(R.id.categoryTextView);
        categoryView.setText(itemCategory);

        Unit unit = pantryItem.getUnit();
        //Populate the Unit Spinner with possible values
        unitSpinner = (Spinner) findViewById(R.id.unitSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<Unit> adapter = new ArrayAdapter<Unit>(this,android.R.layout.simple_spinner_dropdown_item,Unit.values());
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        unitSpinner.setAdapter(adapter);
        if (unit != null) {
            int spinnerPosition = adapter.getPosition(unit);
            unitSpinner.setSelection(spinnerPosition);
        }

        totalQuantityText = (EditText) findViewById(R.id.editText);
        totalQuantityText.setText(pantryItem.getTotalQuantity()+"");

        priceText = (EditText) findViewById(R.id.priceEditText);
        priceText.setText(pantryItem.getPrice() + "");

        expiryEditText =  (EditText)findViewById(R.id.expiryEditText);
        expiryEditText.setText(pantryItem.getExpiryDateString());


        //OnClick Listener for Save button
        findViewById(R.id.savebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pantryItem.setName(UpdatePantryActivity.this.pantryItem.getName());
                pantryItem.setItemId(UpdatePantryActivity.this.pantryItem.getId());
                pantryItem.setCategory(UpdatePantryActivity.this.pantryItem.getCategory());

                double totalQuantity = Double.parseDouble(totalQuantityText.getText().toString());
                pantryItem.setTotalQuantity(totalQuantity);

                unitSpinner = (Spinner) findViewById(R.id.unitSpinner);
                String unitValue = unitSpinner.getSelectedItem().toString();
                Unit unit = Unit.getUnit(unitValue);
                pantryItem.setUnit(unit);


                String priceValue = priceText.getText().toString();
                double price = Double.parseDouble(priceValue);
                pantryItem.setPrice(price);

                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
                Date expiryDate = null;
                try {
                    expiryDate = dateFormat.parse(expiryEditText.getText().toString());
                    pantryItem.setExpiryDate(expiryDate.getTime());
                } catch (ParseException e) {
                    Log.d("AddToPantryActivity", "Error parsing date");
                    e.printStackTrace();
                }

                //Update Pantry item
                SmartGroceryDBHelper.getInstance(getApplicationContext()).updatePantry(pantryItem);
                finish();
            }
        });

        findViewById(R.id.deletebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Delete from pantry
                SmartGroceryDBHelper.getInstance(getApplicationContext()).deletePantry(pantryItem);
                finish();

            }
        });


    }

}
