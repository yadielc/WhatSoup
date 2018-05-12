package com.example.cabrera.whatsoup_2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.os.Handler;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import Util.ProgressGenerator;
import data.ChatAdapter;
import model.Message;

import static android.app.ProgressDialog.show;

/*This class is for the conversation window */

public class ChatActivity extends ActionBarActivity {

    //Attributes
    private EditText message;
    private Button sendMessageButton;
    private ProgressGenerator progressGenerator;
    public static final String USER_ID_KEY = "userId";
    private String currentUserId;
    private ListView listview;
    private ArrayList<Message> mMessages;
    private ChatAdapter mAdapter;
    private Handler handler = new Handler ();

    //Maximum messages to show on window
    private static final int MAX_CHAT_MSG_TO_SHOW = 70;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getCurrentUser();
        handler.postDelayed(runnable, 100);
    }

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            refreshMessages();
            handler.postDelayed(this, 100);
        }
    };

    public void getCurrentUser() {
        currentUserId = ParseUser.getCurrentUser().getObjectId();
        messagePosting();
    }

    // To send the message of the user
    private void messagePosting() {
        message = (EditText) findViewById(R.id.etMessage);
        sendMessageButton = (Button) findViewById(R.id.buttonSend);
        listview = (ListView) findViewById(R.id.listview_chat);
        mMessages = new ArrayList<Message>();
        mAdapter = new ChatAdapter(ChatActivity.this, currentUserId, mMessages);
        listview.setAdapter(mAdapter);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                if (!message.getText().toString().equals("")) {
                    // Create the message
                    Message msg = new Message();
                    msg.setUserId(currentUserId);
                    msg.setBody(message.getText().toString());
                    msg.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            receiveMessage();

                        }
                    });
                    message.setText("");
                } else {
                    // If the user tries to send a empty message, show a prompt
                    Toast.makeText(getApplication(), "Empty Message", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    //When receive messages
    private void receiveMessage() {
        //Does a query to parse
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        //Making sure the limit is 70 messages
        query.setLimit(MAX_CHAT_MSG_TO_SHOW);
        query.orderByAscending("createdAt");

        query.findInBackground(new FindCallback<Message>() {
            @Override
            public void done(List<Message> messages, ParseException e) {
                if (e == null) {
                    mMessages.clear();
                    mMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged();
                    listview.invalidate();

                } else {
                    Log.v("Error:", "Error:" + e.getMessage());

                }
            }
        });
    }

    private void refreshMessages () {
        receiveMessage();
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {

                    startActivity(new Intent(ChatActivity.this, MainActivity.class));
                    } else {

                    }
                }
        });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
  }


