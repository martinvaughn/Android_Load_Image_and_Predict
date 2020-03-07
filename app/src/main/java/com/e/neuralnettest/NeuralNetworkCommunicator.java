package com.e.neuralnettest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.tensorflow.lite.Interpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class NeuralNetworkCommunicator implements Runnable {
    private Bitmap bitmap;
    private Interpreter tflite = null;
    private static final String MODEL_PATH = "final_cancer_model.tflite";
    private int DIMEN = 50;
    private Activity activity;
    private final Interpreter.Options tfliteOptions = new Interpreter.Options();


    public NeuralNetworkCommunicator(Bitmap bitmap, Activity activity) {
        this.bitmap = bitmap;
        this.activity = activity;
    }

    @Override
    public void run() {
       try {
           Log.d("NeuralNetClass", "Starting NeuralNet");
           tflite = new Interpreter(loadModelFile(this.activity), tfliteOptions);
       } catch (Exception e) {
           e.printStackTrace();

       }
       tflite.run(bitmap, bitmap);

        // bitmap = resizeBitmap(bitmap, DIMEN);
       System.out.println(Arrays.toString(tflite.getInputTensor(0).shape()));
       System.out.println(Arrays.toString(tflite.getOutputTensor(0).shape()));
       tflite.close();
       Log.d("NeuralNetClass", "NeuralNet Closed");
    }

    //maybe move back to Try
    // tfLite = new Interpreter(MODEL_PATH);
    // tfLite = new Interpreter(loadModelFile(this.activity));

    private MappedByteBuffer loadModelFile(Activity activity) throws IOException {
        Log.d("NeuralNetClass", "Starting LoadModel");

    AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(MODEL_PATH);

    FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());

    FileChannel fileChannel = inputStream.getChannel();
    long startOffSet = fileDescriptor.getStartOffset();
    long declaredLength = fileDescriptor.getDeclaredLength();

    return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffSet, declaredLength);
   }



    private Bitmap resizeBitmap(Bitmap image , int dimension ) {
        return Bitmap.createScaledBitmap( image , dimension , dimension , true ) ;
    }





    public void JavaImageIOTest()
    {
        try
        {
            // the line that reads the image file
            BufferedImage image = ImageIO.read(new File("/Users/al/some-picture.jpg"));

            // work with the image here ...
        }
        catch (IOException e)
        {
            // log the exception
            // re-throw if desired
        }
    }
}
