package com.scu.smartgrocerytracker.pantry;

import android.os.Bundle;
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
import com.scu.smartgrocerytracker.constants.Constants;
import com.scu.smartgrocerytracker.constants.Unit;
import com.scu.smartgrocerytracker.items.Items;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddToPantryActivity extends AppCompatActivity {

    private Items item;
    private TextView itemNameView;
    private TextView categoryView;
    private Spinner unitSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_pantry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Get Item name and category from Shopping list activity
        Bundle bundle = getIntent().getExtras();
        item = (Items) bundle.getSerializable(Constants.ITEM_TABLE_NAME);
        int itemId = item.getId();
        String itemName = item.getItemName();
        String itemCategory = item.getItemCategory();

        itemNameView = (TextView)findViewById(R.id.itemNameTextView);
        itemNameView.setText(itemName);

        categoryView = (TextView) findViewById(R.id.categoryTextView);
        categoryView.setText(itemCategory);

        //Populate the Unit Spinner with possible values
        unitSpinner = (Spinner) findViewById(R.id.unitSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<Unit> adapter = new ArrayAdapter<Unit>(this,android.R.layout.simple_spinner_dropdown_item,Unit.values());
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        unitSpinner.setAdapter(adapter);


        //OnClick Listener for Save button
        findViewById(R.id.savebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PantryItem pantryItem = new PantryItem();
                pantryItem.setName(item.getItemName());
                pantryItem.setItemId(item.getId());
                pantryItem.setCategory(item.getItemCategory());

                EditText totalQuantityText = (EditText) findViewById(R.id.editText);
                double totalQuantity = Double.parseDouble(totalQuantityText.getText().toString());
                pantryItem.setTotalQuantity(totalQuantity);

                unitSpinner = (Spinner) findViewById(R.id.unitSpinner);
                String unitValue = unitSpinner.getSelectedItem().toString();
                Unit unit = Unit.getUnit(unitValue);
                pantryItem.setUnit(unit);

                EditText priceText = (EditText) findViewById(R.id.priceEditText);
                String priceValue = priceText.getText().toString();
                double price = Double.parseDouble(priceValue);
                pantryItem.setPrice(price);

                DateFormat dateFormat = new SimpleDateFormat("mm/dd/yy");
                Date expiryDate = null;
                try {
                    expiryDate = dateFormat.parse((((EditText) findViewById(R.id.expiryEditText)).getText().toString()));
                    pantryItem.setExpiryDate(expiryDate);
                } catch (ParseException e) {
                    Log.d("AddToPantryActivity", "Error parsing date");
                    e.printStackTrace();
                }

                //Add to Pantry
                SmartGroceryDBHelper.getInstance(getApplicationContext()).addToPantry(pantryItem);
                finish();

            }
        });


    }

}