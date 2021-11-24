package com.rishav.calculator;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CardView zero,one,two,three,four,five,six,seven,eight,nine,add,div,sub,mul,back,point,equal,clear;
    TextView operation,result;
    String input="",output;
    ArrayList<String> operands = new ArrayList<String>();
    double finalResult=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //defining the hooks
        defineHooks();
        //set Listener
        setListener();

        result.setVisibility(View.GONE);
        operation.setText("0");

    }

    private void setListener() {
        zero.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        add.setOnClickListener(this);
        div.setOnClickListener(this);
        mul.setOnClickListener(this);
        sub.setOnClickListener(this);
        point.setOnClickListener(this);
        equal.setOnClickListener(this);
        clear.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void defineHooks() {
        zero = findViewById(R.id.zero);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        add = findViewById(R.id.add);
        div = findViewById(R.id.divide);
        sub = findViewById(R.id.minus);
        mul = findViewById(R.id.multiply);
        point = findViewById(R.id.point);
        back = findViewById(R.id.back);
        equal = findViewById(R.id.equals);
        clear = findViewById(R.id.AC);
        operation = findViewById(R.id.operation);
        result = findViewById(R.id.result);
    }

    @Override
    public void onClick(View view) {
        if(view == zero){
            operation("0");
        }else if(view == one){
            operation("1");
        }else if(view == two){
            operation("2");
        }else if(view == three){
            operation("3");
        }else if(view == four){
            operation("4");
        }else if(view == five){
            operation("5");
        }else if(view == six){
            operation("6");
        }else if(view == seven){
            operation("7");
        }else if(view == eight){
            operation("8");
        }else if(view == nine){
            operation("9");
        }else if(view == point){
            operation(".");
        }else if(view == add){
            operation(" + ");
        }else if(view == sub){
            operation(" - ");
        }else if(view == mul){
            operation(" X ");
        }else if(view == div){
            operation(" % ");
        }else if(view == clear){
            operation("AC");
        }else if(view == back){
            operation("<-");
        }
        else if(view == equal){
            operation("result");
        }
    }

    private void operation(String no) {
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(80);
        if (no.equals("AC")){
            input="";
            result.setText("");
        }else if(no.equals("<-")){
            if(!input.equals("")) {
                Character last = input.charAt(input.length()-1);
                if(last.equals(' ')){
                    input = input.substring(0, input.length() - 2);
                }
                input = input.substring(0, input.length() - 1);
            }
        }else if(no.equals("result")){
            getResult();
        }else{
            boolean isTwiceOperator =false;
            String prev ="";
            if(input.length()>1) {
                prev = input.substring(input.length() - 1);
            }
            if((prev.equals(" ") || prev.equals("."))&& (no.equals(" + ") || no.equals(" - ") ||no.equals(" % ") ||no.equals(" X ") ||no.equals(".") )) {
                isTwiceOperator = true;
            }
            if(input.equals("") && (no.equals(" + ") || no.equals(" - ") ||no.equals(" % ") ||no.equals(" X ") ||no.equals(".") )){
                isTwiceOperator=true;
            }
            if (!isTwiceOperator) {
                input = input + no;
            }
        }
        operation.setText(input);
    }

    private void getResult() {
        result.setVisibility(View.VISIBLE);
        operands.clear();
        String dup =input;
        dup=dup+" ";
        int len = dup.length();
        String value="";
        Character ch;
        for(int i=0 ; i<len;i++){
            ch=dup.charAt(i);
            if(ch.equals(' ')){
                value = value.trim();
                operands.add(value);
                value="";
            }
            value = value+ch;
        }

        getAns("%");
        getAns("X");
        getAns("+");
        getAns("-");

        finalResult=Double.parseDouble(operands.get(0));
        DecimalFormat formatter = new DecimalFormat("#.##########");
        result.setText(String.valueOf(formatter.format(finalResult)));

    }

    private void getAns(String operator) {
        int len = operands.size();
        for(int j=0;j<len;j++){
            int size = operands.size();
        for(int i=0;i<size;i++) {
            if (operands.get(i).equals(operator)) {
                if (operands.get(i).equals("%")) {
                    //perform division
                    finalResult = Double.parseDouble(operands.get(i - 1)) / Double.parseDouble(operands.get(i + 1));
                } else if (operands.get(i).equals("X")) {
                    //perform multiplication
                    finalResult = Double.parseDouble(operands.get(i - 1)) * Double.parseDouble(operands.get(i + 1));
                } else if (operands.get(i).equals("+")) {
                    //perform addition
                    finalResult = Double.parseDouble(operands.get(i - 1)) + Double.parseDouble(operands.get(i + 1));
                } else if (operands.get(i).equals("-")) {
                    //perform subtraction
                    finalResult = Double.parseDouble(operands.get(i - 1)) - Double.parseDouble(operands.get(i + 1));
                }
                operands.remove(i - 1);
                operands.add(i-1,String.valueOf(finalResult));
                operands.remove(i + 1);
                operands.remove(i);
                break;
            }
        }
        }
    }

}
