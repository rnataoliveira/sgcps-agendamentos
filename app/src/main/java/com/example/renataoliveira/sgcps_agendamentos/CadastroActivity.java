package com.example.renataoliveira.sgcps_agendamentos;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button botao =(Button)findViewById(R.id.botaoCadastro);
        botao.setOnClickListener(clickCadastro());
    }

    public View.OnClickListener clickCadastro() {
        return new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                EditText nomeDoCompromisso = (EditText)findViewById(R.id.nomeCompromisso);
                EditText dataDoCompromisso = (EditText)findViewById(R.id.DataCompromisso);
                EditText horaDoCompromisso = (EditText)findViewById(R.id.horaCompromisso);
                EditText localDoCompromisso = (EditText)findViewById(R.id.localCompromisso);

                String textNomeCompromisso = nomeDoCompromisso.getText().toString();
                String textDataCompromisso = dataDoCompromisso.getText().toString();
                String textHoraCompromisso = horaDoCompromisso.getText().toString();
                String textLocalCompromisso = localDoCompromisso.getText().toString();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("nomeCompromisso",textNomeCompromisso);
                returnIntent.putExtra("dataCompromisso",textDataCompromisso);
                returnIntent.putExtra("horaCompromisso",textHoraCompromisso);
                returnIntent.putExtra("localComprommisso",textLocalCompromisso);

                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            finish();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
