package com.example.travelcompanion;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerCategory, spinnerFrom, spinnerTo;
    EditText etInputValue;
    Button btnConvert;
    TextView tvResult;

    String[] categories = {" ","Currency", "Fuel / Volume / Distance", "Temperature"};

    String[] currencyUnits = {" ","USD", "AUD", "EUR", "JPY", "GBP"};
    String[] fuelUnits = {" ","mpg", "km/L", "Gallon (US)", "Liters", "Nautical Mile", "Kilometers"};
    String[] temperatureUnits = {" ","Celsius", "Fahrenheit", "Kelvin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        etInputValue = findViewById(R.id.etInputValue);
        btnConvert = findViewById(R.id.btnConvert);
        tvResult = findViewById(R.id.result);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        updateUnitSpinners("Currency");

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categories[position];
                ImageView imgIcon = findViewById(R.id.imgIcon);

                switch (selectedCategory) {
                    case "Currency":
                        imgIcon.setImageResource(R.drawable.currency_icon);
                        break;
                    case "Fuel / Volume / Distance":
                        imgIcon.setImageResource(R.drawable.fuel_icon);
                        break;
                    case "Temperature":
                        imgIcon.setImageResource(R.drawable.temperature_icon);
                        break;
                    default:
                        imgIcon.setImageResource(R.drawable.travel_icon);
                }
                updateUnitSpinners(selectedCategory);
                clearInputs();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnConvert.setOnClickListener(v -> {
            String inputText = etInputValue.getText().toString().trim();

            if (inputText.isEmpty()) {
                etInputValue.setError("Please enter a value");
                Toast.makeText(MainActivity.this, "Input cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            double inputValue;
            try {
                inputValue = Double.parseDouble(inputText);
            } catch (NumberFormatException e) {
                etInputValue.setError("Please enter a valid numeric value");
                Toast.makeText(MainActivity.this, "Invalid input. Numbers only.", Toast.LENGTH_SHORT).show();
                return;
            }

            String category = spinnerCategory.getSelectedItem().toString();
            String fromUnit = spinnerFrom.getSelectedItem().toString();
            String toUnit = spinnerTo.getSelectedItem().toString();

            if (!isValidInput(category, fromUnit, inputValue)) {
                return;
            }

            if (fromUnit.equals(toUnit)) {
                tvResult.setText(String.format(Locale.getDefault(),
                        "No conversion needed: %.4f %s", inputValue, toUnit));
                Toast.makeText(MainActivity.this, "Source and destination are the same", Toast.LENGTH_SHORT).show();
                return;
            }

            double result = convertValue(category, fromUnit, toUnit, inputValue);

            if (Double.isNaN(result)) {
                tvResult.setText("Conversion not supported");
                Toast.makeText(MainActivity.this, "Conversion not supported", Toast.LENGTH_SHORT).show();
                return;
            }

            tvResult.setText(String.format(Locale.getDefault(),
                    "Converted Value: %.4f %s", result, toUnit));
        });
    }

    private boolean isValidInput(String category, String fromUnit, double value) {

        if (category.equals("Currency")) {
            if (value < 0) {
                Toast.makeText(this, "Currency value cannot be negative", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (category.equals("Fuel / Volume / Distance")) {
            if (value < 0) {
                Toast.makeText(this, "Fuel, volume, or distance value cannot be negative", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (category.equals("Temperature")) {
            if (fromUnit.equals("Kelvin") && value < 0) {
                Toast.makeText(this, "Kelvin cannot be negative", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    private void updateUnitSpinners(String category) {
        String[] units;

        switch (category) {
            case "Currency":
                units = currencyUnits;
                break;
            case "Fuel / Volume / Distance":
                units = fuelUnits;
                break;
            case "Temperature":
                units = temperatureUnits;
                break;
            default:
                units = new String[]{};
        }

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, units);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(unitAdapter);
        spinnerTo.setAdapter(unitAdapter);
    }

    private double convertValue(String category, String fromUnit, String toUnit, double inputValue) {
        switch (category) {
            case "Currency":
                return convertCurrency(fromUnit, toUnit, inputValue);

            case "Fuel / Volume / Distance":
                return convertFuelVolumeDistance(fromUnit, toUnit, inputValue);

            case "Temperature":
                return convertTemperature(fromUnit, toUnit, inputValue);

            default:
                return Double.NaN;
        }
    }

    private double convertCurrency(String fromUnit, String toUnit, double value) {
        double valueInUSD = 0.0;

        switch (fromUnit) {
            case "USD":
                valueInUSD = value;
                break;
            case "AUD":
                valueInUSD = value / 1.55;
                break;
            case "EUR":
                valueInUSD = value / 0.92;
                break;
            case "JPY":
                valueInUSD = value / 148.50;
                break;
            case "GBP":
                valueInUSD = value / 0.78;
                break;
        }

        switch (toUnit) {
            case "USD":
                return valueInUSD;
            case "AUD":
                return valueInUSD * 1.55;
            case "EUR":
                return valueInUSD * 0.92;
            case "JPY":
                return valueInUSD * 148.50;
            case "GBP":
                return valueInUSD * 0.78;
            default:
                return 0.0;
        }
    }

    private double convertFuelVolumeDistance(String fromUnit, String toUnit, double value) {

        if (fromUnit.equals("mpg") && toUnit.equals("km/L")) {
            return value * 0.425;
        }
        if (fromUnit.equals("km/L") && toUnit.equals("mpg")) {
            return value / 0.425;
        }

        if (fromUnit.equals("Gallon (US)") && toUnit.equals("Liters")) {
            return value * 3.785;
        }
        if (fromUnit.equals("Liters") && toUnit.equals("Gallon (US)")) {
            return value / 3.785;
        }

        if (fromUnit.equals("Nautical Mile") && toUnit.equals("Kilometers")) {
            return value * 1.852;
        }
        if (fromUnit.equals("Kilometers") && toUnit.equals("Nautical Mile")) {
            return value / 1.852;
        }

        Toast.makeText(this, "Invalid conversion in this category", Toast.LENGTH_SHORT).show();
        return 0.0;
    }

    private double convertTemperature(String fromUnit, String toUnit, double value) {

        if (fromUnit.equals("Celsius") && toUnit.equals("Fahrenheit")) {
            return (value * 1.8) + 32;
        }
        if (fromUnit.equals("Fahrenheit") && toUnit.equals("Celsius")) {
            return (value - 32) / 1.8;
        }
        if (fromUnit.equals("Celsius") && toUnit.equals("Kelvin")) {
            return value + 273.15;
        }
        if (fromUnit.equals("Kelvin") && toUnit.equals("Celsius")) {
            return value - 273.15;
        }

        Toast.makeText(this, "Invalid conversion in temperature category", Toast.LENGTH_SHORT).show();
        return 0.0;
    }

    private void clearInputs() {
        etInputValue.setText("");

        tvResult.setText("Converted value will appear here");

        spinnerFrom.setSelection(0);
        spinnerTo.setSelection(0);
    }
}