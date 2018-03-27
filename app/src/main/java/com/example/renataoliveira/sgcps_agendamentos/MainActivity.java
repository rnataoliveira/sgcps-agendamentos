package com.example.renataoliveira.sgcps_agendamentos;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends DebugActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication);

        ImageView background = (ImageView) findViewById(R.id.backgroundImage);
        background.setImageResource(R.drawable.background);

        TextView fieldUser = (TextView) findViewById(R.id.userField);
        TextView fieldPassword = (TextView) findViewById(R.id.passwordField);

        String txtUser = fieldUser.toString();
        String txtPassword = fieldPassword.toString();

        Button buttonAccess = (Button)findViewById(R.id.buttonAuthentication);

        buttonAccess.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                startActivity(intent);

            }
        });


    }
}
