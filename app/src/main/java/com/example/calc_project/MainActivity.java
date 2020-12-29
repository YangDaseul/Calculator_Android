package com.example.calc_project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    Stack<Double> numStack = new Stack<Double>();
    Stack<String> opStack = new Stack<String>();

    String data;
    double result = 0, num1, num2;
    String operator;
    boolean firstData = true;

    private final static String TAG = "calc";
    private final static String TAG1 = "num1";
    private final static String TAG2 = "num2";

    private TextView resultText;

    public static double calculate(double num1, double num2, String operator, double result) {
        if (operator.equals("+")) {
            result = num2 + num1;

        } else if (operator.equals("-")) {
            result = num2 - num1;

        } else if (operator.equals("×")) {
            result = num2 * num1;

        } else if (operator.equals("÷")) {
            result = num2 / num1;

        }
        return result;

    }

    public static int opCheck(String s) {
        int r = 0;

        if (s.equals("+") || s.equals("-")) {
            r = 1;
        } else if (s.equals("×") || s.equals("÷")) {
            r = 2;
        }
        return r;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = (TextView) findViewById(R.id.resultText);

    }

    public void numButtonClick(View v) { //숫자
        Button numButton = findViewById(v.getId());
        String numButtonText = numButton.getText().toString();



        if (firstData) { // TextView에 숫자 존재하지 않음
            resultText.setText(numButtonText);
            firstData = false;
        } else { // TextView에 이미 숫자 존재함
            resultText.append(numButtonText);
        }



    }

    public void opButtonClick(View v) { // 연산자
        numStack.push(Double.parseDouble(resultText.getText().toString()));
        Log.d(TAG, resultText.getText().toString());

        Button opButton = findViewById(v.getId());
        String opButtonText = opButton.getText().toString();

        if (opStack.isEmpty()) {
            opStack.push(opButtonText);
        } else {
            while (!opStack.isEmpty()) {
                operator = opStack.pop();

                if (opCheck(operator) < opCheck(opButtonText)) {
                    opStack.push(operator);
                    opStack.push(opButtonText);

                    break;
                } else if (opCheck(operator) >= opCheck(opButtonText)) {

                    num1 = numStack.pop();
                    num2 = numStack.pop();

                    result = calculate(num1, num2, operator, result);
                    resultText.setText(String.valueOf(result));

                    numStack.push(result);
                }
            }
        }
        if (opStack.isEmpty()) {
            opStack.push(opButtonText);
        }

        firstData = true;


    }

    public void acButtonClick(View v) {
        numStack.clear();
        opStack.clear();

        resultText.setText("");
    }

    public void resultButtonClick(View v) {
        numStack.push(Double.parseDouble(resultText.getText().toString()));

        while (!opStack.isEmpty()) {
            operator = opStack.pop();
            num1 = numStack.pop();
            //Log.d(TAG1, String.valueOf(num1));
            num2 = numStack.pop();
            //Log.d(TAG2, String.valueOf(num2));

            result = calculate(num1, num2, operator, result);

            numStack.push(result);
        }
        result = numStack.pop();

        resultText.setText(String.valueOf(result));


    }


}