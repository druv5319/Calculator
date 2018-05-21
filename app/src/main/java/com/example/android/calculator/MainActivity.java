package com.example.android.calculator;

import java.text.DecimalFormat;
import java.text.DecimalFormat.*;
import java.text.NumberFormat;
import java.text.NumberFormat.*;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    double result, firstvalue, secondvalue, percentvalue;
    String statement = "", firststatement = "", secondstatement, command, strresult;
    int maxlength = 9, wholeresult, count;
    boolean equalsclickedlast = false, decimalpresent = false, negativepresent = false;


    public void displayResult(String statement) {
        // The if statement is used to so the numbers dont continue to a next line or off the screen. The maximum the numbers the can
        // be inputted is 9
        if (statement.length() > maxlength)
            statement = statement.substring(0, maxlength);


        TextView resultView = (TextView) findViewById(R.id.viewresult);
        resultView.setText(String.valueOf(statement));
    }

    public void clickOne(View v) {
        clearResult();
        statement += "1";
        displayResult(statement);
    }

    public void clickTwo(View v) {
        clearResult();
        statement += "2";
        displayResult(statement);
    }

    public void clickThree(View v) {
        clearResult();
        statement += "3";
        displayResult(statement);
    }

    public void clickFour(View v) {
        clearResult();
        statement += "4";
        displayResult(statement);
    }

    public void clickFive(View v) {
        clearResult();
        statement += "5";
        displayResult(statement);
    }

    public void clickSix(View v) {
        clearResult();
        statement += "6";
        displayResult(statement);
    }

    public void clickSeven(View v) {
        clearResult();
        statement += "7";
        displayResult(statement);
    }

    public void clickEight(View v) {
        clearResult();
        statement += "8";
        displayResult(statement);
    }

    public void clickNine(View v) {
        clearResult();
        statement += "9";
        displayResult(statement);
    }

    public void clickZero(View v) {
        clearResult();
        statement += "0";
        displayResult(statement);
    }

    public void clickDecimal(View v) {
        clearResult();
        decimalCheck();
        if (decimalpresent == false) {

            decimalpresent = false;
            statement += '.';
            displayResult(statement);
        }
    }

    public void clickPosNeg(View c){
        negativeCheck();
        if (statement != "") {
            if (negativepresent == false) {
                statement = "-" + statement;
                displayResult(statement);
            }
            else {
                statement = statement.substring(1);
                displayResult(statement);
            }
        }
    }

    public void clickPercent(View c){
        if (statement != ""){
            percentvalue = Double.parseDouble(statement);
            percentvalue= percentvalue / 100;
            statement = Double.toString(percentvalue);
            displayResult(statement);
        }
    }

    public void clickClear(View v) {
        statement = "";
        result = 0;
        command = "";
        displayResult("0");
    }
// The if else statements in all of the function methods are used if the user presses the wrong function button or
//changes their mind and decides to use a different function. Without these if else statements, it is not possible to change the
//function without clearing (clear button) everything and restarting again

    public void clickDivide(View c) {
        if (statement != "") {
            if (firststatement != "")
                clickEquals(c);
            firststatement = statement;
            statement = "";
            command = "divide";
        }
        else
            command = "divide";
    }

    public void clickMultiply(View c) {
        if (statement != "") {
            if (firststatement != "")
                clickEquals(c);
            firststatement = statement;
            statement = "";
            command = "multiply";
        }
        else
            command = "multiply";
    }

    public void clickSubtract(View c) {
        if (statement != "") {
            if (firststatement != "")
                clickEquals(c);
            firststatement = statement;
            statement = "";
            command = "subtract";

        }
        else
            command = "subtract";
    }

    public void clickAdd(View c) {
        if (statement != "") {
            if (firststatement != "")
                clickEquals(c);
            firststatement = statement;
            statement = "";
            command = "add";
        }
        else
            command = "add";
    }

    public void clickEquals(View c) {
        if (firststatement != "" & statement != "") {

            secondstatement = statement;
            firstvalue = Double.parseDouble(firststatement);
            secondvalue = Double.parseDouble(secondstatement);

            //Tells the program which function to compute according to which function button is pressed
            switch (command) {
                case "divide":
                    result = firstvalue / secondvalue;
                    break;
                case "multiply":
                    result = firstvalue * secondvalue;
                    break;
                case "subtract":
                    result = firstvalue - secondvalue;
                    break;
                case "add":
                    result = firstvalue + secondvalue;
                    break;

            }

            //If the answer is a  whole number, the calcultor will display an Int instead of a double so the decimal values will
            // not be shown
            if (result % 1 == 0) {
                wholeresult = (int) result;
                strresult = Integer.toString(wholeresult);
            }

            // If the answer is a non-whole number, the calculator will display the answer with the decimal values
            else
                strresult = Double.toString(result);
            //If the result is too long to fit the app, this will shorten it into scientific notation
            if (strresult.length() >9)
                strresult = String.format("%.3E", result);
            command = "";
            decimalpresent = false;
            statement = strresult;
            firststatement = "";
            equalsclickedlast = true;
            displayResult(strresult);
        }

    }
    // This method to clear the current result/view on the screen when a number button is pushed
    // right after when the equals button was pushed
    public void clearResult(){
        if (equalsclickedlast == true) {
            statement = "";
            equalsclickedlast = false;
        }
    }
    //This confirms to not insert a decimal point in a number if it already has one
    public void decimalCheck(){
        for (int i = 0; i < statement.length(); i++) {
            if (statement.charAt(i) == '.') {
                decimalpresent = true;
            }
        }
    }

    public void negativeCheck(){
        if (statement.charAt(0) == '-')
            negativepresent = true;
        else
            negativepresent = false;

    }



}
