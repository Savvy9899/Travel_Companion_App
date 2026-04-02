# Travel_Companion_App
Mobile Application Development Project 

# Travel Companion App

## 1. Overview

The Travel Companion App is an Android application developed in Java to assist international travellers with essential value conversions. The app allows users to convert between different units across multiple categories, including currency, fuel efficiency, liquid volume, distance, and temperature.

The application is designed to be simple, reliable, and user-friendly, ensuring that travellers can quickly perform conversions without confusion or errors.

---

## 2. Features

The application provides the following features:

* Conversion across multiple categories:

  * Currency
  * Fuel Efficiency
  * Liquid Volume
  * Distance
  * Temperature
* Dynamic user interface with dropdown menus (spinners)
* Input validation and error handling
* Real-time result display
* Prevention of invalid or unsupported conversions
* Automatic clearing of inputs when category changes

---

## 3. User Interface Design

The user interface includes the following components:

* Category selection spinner
* Source unit selection spinner
* Destination unit selection spinner
* Input field for entering the value
* Convert button to perform the conversion
* Result display area
* Optional image/icon to enhance visual appeal

The interface is designed using a vertical layout to ensure clarity and ease of use.

---

## 4. Technologies Used

* Programming Language: Java
* Platform: Android Studio
* UI Design: XML Layouts
* Minimum SDK: API Level 24 or higher

---

## 5. Conversion Logic

The application uses fixed conversion factors as specified in the assignment.

### 5.1 Currency Conversion (via USD)

* 1 USD = 1.55 AUD
* 1 USD = 0.92 EUR
* 1 USD = 148.50 JPY
* 1 USD = 0.78 GBP

All currency conversions are performed by first converting to USD and then to the target currency.

---

### 5.2 Fuel Efficiency and Volume

* 1 Mile per Gallon (mpg) = 0.425 Kilometers per Liter (km/L)
* 1 Gallon (US) = 3.785 Liters

---

### 5.3 Distance

* 1 Nautical Mile = 1.852 Kilometers

---

### 5.4 Temperature

* Fahrenheit = (Celsius × 1.8) + 32
* Celsius = (Fahrenheit − 32) / 1.8
* Kelvin = Celsius + 273.15

---

## 6. Validation and Error Handling

The application includes robust validation to ensure stability and prevent crashes.

* Prevents empty input values
* Prevents non-numeric input
* Handles identity conversions by returning the same value
* Prevents negative values for:

  * Currency
  * Fuel efficiency
  * Volume and distance
* Allows negative values for valid temperature units (Celsius and Fahrenheit)
* Prevents negative Kelvin values
* Displays error messages using Toasts and input field errors
* Handles unsupported conversions safely without crashing

---

## 7. Functional Behaviour

* When a category is selected, unit options update dynamically
* When the category changes, all input fields and results are cleared
* When the Convert button is pressed:

  * Input is validated
  * Conversion is performed using predefined logic
  * Result is displayed in a formatted manner

---

## 8. How to Run the Application

1. Open the project in Android Studio
2. Allow Gradle to sync
3. Connect an Android device or start an emulator
4. Click Run
5. Interact with the app by selecting a category, units, and entering a value

---

## 9. Example Test Cases

### Currency Conversion

Input: 10 USD → AUD
Output: 15.5000 AUD

### Fuel Efficiency

Input: 20 mpg → km/L
Output: 8.5000 km/L

### Volume Conversion

Input: 2 Gallons → Liters
Output: 7.5700 Liters

### Temperature Conversion

Input: 25 Celsius → Fahrenheit
Output: 77.0000 Fahrenheit

---

## 10. Project Structure

app/
└── src/
└── main/
├── java/com/example/travelcompanionapp/
│    └── MainActivity.java
├── res/layout/
│    └── activity_main.xml
├── res/drawable/
│    └── (icons/images)
└── AndroidManifest.xml

---

## 11. Limitations

* Conversion factors are fixed and not updated in real time
* Only predefined unit combinations are supported
* Internet-based currency updates are not included

---

## 12. Future Improvements

* Add real-time currency conversion using APIs
* Improve UI with modern design components
* Add more unit categories
* Store user preferences
* Add offline mode support

---

## 13. Conclusion

The Travel Companion App successfully demonstrates the implementation of a multi-category unit converter using Java and Android Studio. The application meets all assignment requirements, including UI design, conversion logic, validation, and error handling. It provides a reliable and user-friendly solution for travellers needing quick and accurate conversions.

---

