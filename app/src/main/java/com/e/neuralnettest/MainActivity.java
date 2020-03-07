package com.e.neuralnettest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    private Bitmap bitmap;
    private NeuralNetworkCommunicator CNN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view) {
        CNN = new NeuralNetworkCommunicator(bitmap, this);
        Log.d("Analysis Activity", "Starting CNN Thread");
        Thread t = new Thread(CNN);
        t.start();
    }

}
