package com.example.android.calculator;

import java.text.DecimalFormat;
import java.text.DecimalFormat.*;
import java.text.NumberFormat;
import java.text.NumberFormat.*;
import java.util.ArrayList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    double result, firstvalue, secondvalue, percentvalue;
    ArrayList<String> equationArrayList = new ArrayList<String>();
    String currentDisplayNumber = "";
    String statement = "", firststatement = "", secondstatement, command, strresult;
    int maxlength = 9, wholeresult, count, clearcount = 0;
    boolean equalsclickedlast = false, decimalpresent = false, negativepresent = false;


    public void displayResult(String statement) {
        // The if statement is used to so the numbers dont continue to a next line or off the screen. The maximum the numbers the can
        // be inputted is 9
        if (statement.length() > maxlength)
            statement = statement.substring(0, maxlength);


        TextView resultView = (TextView) findViewById(R.id.viewresult);
        resultView.setText(String.valueOf(statement));
    }


    public void numberButtonClicked(View numberView){
        if (equalsclickedlast == true) {
            equationArrayList.clear();
            currentDisplayNumber = "";
            displayResult(currentDisplayNumber);
        }
        equalsclickedlast = false;
        Button numberButton = (Button)numberView;
        String numberButtonString = numberButton.getText().toString();
        String tempNumber = "";
        if(numberButtonString.equalsIgnoreCase("+/-")) {
            if (currentDisplayNumber.substring(0,1).equalsIgnoreCase("-")) {
                tempNumber = currentDisplayNumber.substring(1, currentDisplayNumber.length());
            } else {
                tempNumber = "-" + currentDisplayNumber;
            }
        } else {
            tempNumber = currentDisplayNumber + numberButtonString;
        }
        String numberPattern = "[-+]?[0-9]+[.]?[0-9]*";
        if (tempNumber.matches(numberPattern)){
            currentDisplayNumber=tempNumber;
            displayResult(currentDisplayNumber);
            Button clearButton = (Button)findViewById(R.id.clear);
            if (currentDisplayNumber.length() > 0) {
                clearButton.setText("C");
            }
        }
    }

    /*public void clickPercent(View c){
        if (statement != ""){
            percentvalue = Double.parseDouble(statement);
            percentvalue= percentvalue / 100;
            statement = Double.toString(percentvalue);
            displayResult(statement);
        }
    }*/


    public void clickClear(View v) {
        Button buttonClear = (Button)findViewById(R.id.clear);
        buttonClear.setText("AC");

         TextView displayNumber = (TextView) findViewById(R.id.viewresult);
        currentDisplayNumber = "";
        displayNumber.setText("0");
        if (clearcount==1 && equationArrayList != null){
            equationArrayList.clear();
            clearcount = 0;
            buttonClear.setText("AC");
        }
        clearcount++;



    }




// The if else statements in all of the function methods are used if the user presses the wrong function button or
//changes their mind and decides to use a different function. Without these if else statements, it is not possible to change the
//function without clearing (clear button) everything and restarting again

    public void operatorClicked (View operatorView) {
        Button operatorButton = (Button)operatorView;
        String operatorButtonString = operatorButton.getText().toString();
        equationArrayList.add(currentDisplayNumber);
        currentDisplayNumber = "";
        equationArrayList.add(operatorButtonString);
    }

    public void clickEquals(View c) {
        equationArrayList.add(currentDisplayNumber);
        //TODO: Evaluate Equation
        for (int i = 0; i < equationArrayList.size(); i++) {
            if (equationArrayList.get(i).equalsIgnoreCase("×")) {
                evaluateExpressionInEquationArrayList(i);
                i-=2;
            }
        }

        for (int i = 0; i < equationArrayList.size(); i++) {
            if (equationArrayList.get(i).equalsIgnoreCase("÷")) {
                evaluateExpressionInEquationArrayList(i);
                i-=2;
            }
        }

        for (int i = 0; i < equationArrayList.size(); i++) {
            if (equationArrayList.get(i).equalsIgnoreCase("+")) {
                evaluateExpressionInEquationArrayList(i);
                i-=2;
            }
        }

        for (int i = 0; i < equationArrayList.size(); i++) {
            if (equationArrayList.get(i).equalsIgnoreCase("-")) {
                evaluateExpressionInEquationArrayList(i);
                i-=2;
            }
        }
        currentDisplayNumber = equationArrayList.get(0);
        equationArrayList.remove(0);
        equalsclickedlast = true;
        displayResult(currentDisplayNumber);
    }

    private void evaluateExpressionInEquationArrayList (int index) {
        Double leftNumber = Double.parseDouble(equationArrayList.get(index-1));
        Double rightNumber = Double.parseDouble(equationArrayList.get(index+1));
        String operator = equationArrayList.get(index);
        Double result;
        if (operator.equalsIgnoreCase("×")) {
            result = leftNumber*rightNumber;
        } else if (operator.equalsIgnoreCase("÷")) {
            result = leftNumber/rightNumber;
        } else if (operator.equalsIgnoreCase("+")) {
            result = leftNumber+rightNumber;
        } else {
            result = leftNumber-rightNumber;
        }
        equationArrayList.set(index-1, result.toString());
        equationArrayList.remove(equationArrayList.get(index+1));
        equationArrayList.remove(equationArrayList.get(index));
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