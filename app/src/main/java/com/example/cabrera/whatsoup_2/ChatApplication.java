package com.example.cabrera.whatsoup_2;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import model.Message;

/**
 * Created by Cabrera on 11/8/15.
 */
public class ChatApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Calls parse to store the data
        Parse.enableLocalDatastore(this);
        // Instantiates a parse object with the message and stores it
        ParseObject.registerSubclass(Message.class);



    }

}