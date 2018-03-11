package com.example.renataoliveira.sgcps_agendamentos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView userField = (TextView) findViewById(R.id.userField);
        TextView passwordField = (TextView) findViewById(R.id.passwordField);

        String userText = userField.toString();
        String passwordText = passwordField.toString();

        Button buttonAccess = (Button)findViewById(R.id.buttonAuthentication);

        buttonAccess.setOnClickListener(onClickLogin());
    }

    private View.OnClickListener onClickLogin() {
        return new View.OnClickListener() {
            public void onClick(View v) {
                TextView userField = (TextView) findViewById(R.id.userField);
                TextView passwordField = (TextView) findViewById(R.id.passwordField);

                String userText = userField.getText().toString();
                String passwordText = passwordField.getText().toString();

                Toast.makeText(MainActivity.this, "Usuário: " + userText + "; Senha: " + passwordText, Toast.LENGTH_LONG).show();

            }
        };
    }
}
