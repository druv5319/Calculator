package com.example.android.calculator;

import java.text.DecimalFormat;
import java.text.DecimalFormat.*;
import java.text.NumberFormat;
import java.text.NumberFormat.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    ArrayList<String> equationArrayList = new ArrayList<String>();
    String currentDisplayNumber = "";
    int maxlength = 9, clearcount = 0;
    boolean equationEvaluated = false;

    public void displayResult(String statement) {
        // The if statement is used to so the numbers dont continue to a next line or off the screen. The maximum the numbers the can
        // be inputted is 9
        if (statement.length() > maxlength) {
            statement = statement.substring(0, maxlength);
        }

        TextView resultView = (TextView) findViewById(R.id.viewresult);
        resultView.setText(String.valueOf(statement));
    }


    public void numberButtonClicked(View numberView){
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
        if (equationEvaluated && currentDisplayNumber.length() > 0) {
            equationArrayList.clear();
            currentDisplayNumber = "";
            displayResult(currentDisplayNumber);
            equationEvaluated = false;
        }

        if (tempNumber.matches(numberPattern)){
            currentDisplayNumber=tempNumber;
            displayResult(currentDisplayNumber);
            Button clearButton = (Button)findViewById(R.id.clear);
            if (currentDisplayNumber.length() > 0) {
                clearButton.setText("C");
            }
        }
    }
    
    public void clickPercent(View c){
        if (currentDisplayNumber != ""){
            double percentvalue = Double.parseDouble(currentDisplayNumber);
            percentvalue= percentvalue / 100;
            currentDisplayNumber = Double.toString(percentvalue);
            displayResult(currentDisplayNumber);
        }
    }

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

    public void operatorClicked (View operatorView) {
        equationEvaluated = false;
        Button operatorButton = (Button)operatorView;
        String operatorButtonString = operatorButton.getText().toString();
        if (!currentDisplayNumber.equalsIgnoreCase("")) {
            equationArrayList.add(currentDisplayNumber);
        }
        equationArrayList.add(operatorButtonString);
        if (equationArrayList.size() > 0) {
            boolean consecutiveOperatorsFound =
                    equationArrayList.get(equationArrayList.size()-1).contains("+") ||
                    equationArrayList.get(equationArrayList.size()-1).contains("-") ||
                    equationArrayList.get(equationArrayList.size()-1).contains("÷") ||
                    equationArrayList.get(equationArrayList.size()-1).contains("×");

            consecutiveOperatorsFound &=
                    equationArrayList.get(equationArrayList.size()-2).contains("+") ||
                    equationArrayList.get(equationArrayList.size()-2).contains("-") ||
                    equationArrayList.get(equationArrayList.size()-2).contains("÷") ||
                    equationArrayList.get(equationArrayList.size()-2).contains("×");

            consecutiveOperatorsFound &= equationArrayList.get(equationArrayList.size()-1).length() == 1 &&
                    equationArrayList.get(equationArrayList.size()-2).length() == 1;
            if (consecutiveOperatorsFound) {
                equationArrayList.remove(equationArrayList.get(equationArrayList.size()-2));
            }
        }
        currentDisplayNumber = "";
    }

    public void clickEquals(View c) {
        equationArrayList.add(currentDisplayNumber);
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
        if (currentDisplayNumber.matches("[-+]?[0-9]+[.][0]+")) {
            currentDisplayNumber = currentDisplayNumber.split("[.]")[0];
        }
        equationArrayList.remove(0);
        displayResult(currentDisplayNumber);
        equationEvaluated = true;
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
}