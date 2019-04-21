package com.example.jj.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button startGame = (Button) findViewById(R.id.buttonStartGame);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText difficultyField = (EditText) findViewById(R.id.editTextDifficulty);
                int difficulty = Integer.parseInt(difficultyField.getText().toString());

                if(difficulty > 30) {
                    Toast.makeText(getApplicationContext(), "Don't flood the screen with dots", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent connectDotsActivity = new Intent(MainActivity.this, ConnectDotsActivity.class);
                    connectDotsActivity.putExtra("difficulty", difficulty);
                    startActivity(connectDotsActivity);
                }
            }
        });
    }

}
