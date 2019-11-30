package com.example.diseasepredictor;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.util.*;
import java.lang.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editText1,editText2,editText3,editText4,editText5,editText6;
    Button button1,button2;

    private TensorFlowInferenceInterface tensorFlowInferenceInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tensorFlowInferenceInterface = new  TensorFlowInferenceInterface(getAssets(),"frozen_model.pb");

        editText1= (EditText)findViewById(R.id.rbc);
        editText2= (EditText)findViewById(R.id.sugar);
        editText3= (EditText)findViewById(R.id.acidity);
        editText4= (EditText)findViewById(R.id.colestrol);
        editText5= (EditText)findViewById(R.id.result);
        editText6= (EditText)findViewById(R.id.suggestion);

        button1 = (Button)findViewById(R.id.clear);
        button2 =(Button)findViewById(R.id.predict);
            // More code here
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.clear){
            editText1.setText(" ");
            editText2.setText(" ");
            editText3.setText(" ");
            editText4.setText(" ");
            editText5.setText(" ");
        }

        else if(v.getId()==R.id.predict){
            float n1,n2,n3,n4;
            n1 = Float.parseFloat(editText1.getText().toString());
            n2 = Float.parseFloat(editText2.getText().toString());
            n3 = Float.parseFloat(editText3.getText().toString());
            n4 = Float.parseFloat(editText4.getText().toString());

            float[] inp={n1,n2,n3,n4};

            float[] res={0,0,0,0};

            tensorFlowInferenceInterface.feed("my_input/X",inp,1,4);
            tensorFlowInferenceInterface.run(new String[] {"my_output/Softmax"});
            tensorFlowInferenceInterface.fetch("my_output/Softmax",res);

            //editText5.setText("["+Float.toString(res[0]) +" "+Float.toString(res[1]) +" "+Float.toString(res[2]) +" "+Float.toString(res[3]) +" "+"]");

            float max=res[0];
            int index=0;
            for(int i=0;i<4;i++){
                if(res[i]>max){
                    max=res[i];
                    index=i;
                }
            }

            if(index==0)

            //editText5.setText(""+Integer.toString(index)+"");

            if(index==1) {
              editText5.setText("Cancer");
              editText6.setText("["+"maidha"+"Coliflower"+"msdklsfngskl"+"]");
            }

            if(index==1) {
                editText5.setText("Diabeties");
                editText6.setText("["+"maidha"+"Coliflower"+"msdklsfngskl"+"]");
            }
            if(index==2) {
                editText5.setText("Stomach");
                editText6.setText("["+"maidha"+"Coliflower"+"msdklsfngskl"+"]");
            }
            if(index==3) {
                editText5.setText("Heart");
                editText6.setText("["+"FOODS NOT TO TAKE:"+"Coliflower,"+"LAIP"+"]");
            }

        }
    }
}
