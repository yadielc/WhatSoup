package com.example.cabrera.whatsoup_2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


/* Where all the action happens, displays the login and create account button*/
public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    //Attributes
    private Button loginButton;
    private Button createAccountButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the login and create account buttons to values
        createAccountButton = (Button) findViewById(R.id.createButtonId);
        loginButton = (Button) findViewById(R.id.loginButton);

        createAccountButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        // Enable Local Datastore.
        // This stores all the data entered by the user in Parse
        Parse.enableLocalDatastore(this);

        //Initializes the parse backend
        Parse.initialize(this, "lco9D7kiZ42aatBttHsHWLGbUtFWhE3Q3xpVlyQy", "xGAZsq39gL7c0dJXzrxyVrE9x87TqgZQcP9HEccz");
        ParseAnalytics.trackAppOpened(getIntent());
        ParseObject testObject = new ParseObject("AndroidCourseTest");
        testObject.put("Ola", "Mundo");
        testObject.saveInBackground();

     //   ParseUser user = new ParseUser();
       // user.setUsername("HolaUsuarior");
        //user.setPassword("hala");
       // user.setEmail("pal@soy.com");

      //  user.put("phone", "650-253-0000");
       // user.put("food", "Pizza");
       // user.signUpInBackground(new SignUpCallback() {
        //    @Override
          //  public void done(ParseException e) {
          //      if (e == null) {
                    //we are good
                 //   Toast.makeText(getApplicationContext(), "Yeah! All is good", Toast.LENGTH_LONG).show();
               // } else {
                    //something went wrong
             //   }
           // }
        //});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginButton:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.createButtonId:
                startActivity(new Intent(MainActivity.this, CreateAccount.class));


        }

    }
}
