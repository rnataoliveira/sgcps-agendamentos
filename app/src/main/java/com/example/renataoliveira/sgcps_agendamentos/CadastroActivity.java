package com.example.renataoliveira.sgcps_agendamentos;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

public class CadastroActivity extends AppCompatActivity {
    private Agendamento agendamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button botao =(Button)findViewById(R.id.botaoCadastro);
        botao.setOnClickListener(clickCadastro());

        CheckBox check = (CheckBox) findViewById(R.id.checkLembrar);
        check.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(CadastroActivity.this, "Favorita: " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });


        // recuperar Intent vinda da alteração
        Intent cadastroIt = getIntent();
        Serializable agendamentoS = cadastroIt.getSerializableExtra("agendamento");
        if(agendamentoS != null) {
            agendamento = (Agendamento)agendamentoS;
            EditText nomeAgendamento = (EditText) findViewById(R.id.nomeCompromisso);
            EditText localAgendamenot = (EditText) findViewById(R.id.localCompromisso);
            EditText horaAgendamento = (EditText) findViewById(R.id.horaCompromisso);
            EditText dataAgendamento = (EditText) findViewById(R.id.DataCompromisso);
            CheckBox paraLembrar = (CheckBox)findViewById(R.id.checkLembrar);

            nomeAgendamento.setText(agendamento.nome);
            horaAgendamento.setText(agendamento.hora);
            dataAgendamento.setText(agendamento.data);
            localAgendamenot.setText(agendamento.local);

            paraLembrar.setChecked(agendamento.lembrar==1);

        }
    }



    public View.OnClickListener clickCadastro() {
        return new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                EditText nomeDoCompromisso = (EditText)findViewById(R.id.nomeCompromisso);
                EditText dataDoCompromisso = (EditText)findViewById(R.id.DataCompromisso);
                EditText horaDoCompromisso = (EditText)findViewById(R.id.horaCompromisso);
                EditText localDoCompromisso = (EditText)findViewById(R.id.localCompromisso);
                CheckBox lembrar = (CheckBox)findViewById(R.id.checkLembrar);
                boolean paraLembrar = lembrar.isChecked();

                String textNomeCompromisso = nomeDoCompromisso.getText().toString();
                String textDataCompromisso = dataDoCompromisso.getText().toString();
                String textHoraCompromisso = horaDoCompromisso.getText().toString();
                String textLocalCompromisso = localDoCompromisso.getText().toString();

                Intent returnIntent = new Intent();
//                returnIntent.putExtra("nomeCompromisso",textNomeCompromisso);
//                returnIntent.putExtra("dataCompromisso",textDataCompromisso);
//                returnIntent.putExtra("horaCompromisso",textHoraCompromisso);
//                returnIntent.putExtra("localComprommisso",textLocalCompromisso);

                if (agendamento == null){
                    agendamento = new Agendamento();
                }
                agendamento.nome = textNomeCompromisso;
                agendamento.local = textLocalCompromisso;
                agendamento.lembrar = paraLembrar ? 1 : 0;
                agendamento.hora = textHoraCompromisso;
                agendamento.data = textDataCompromisso;

                AgendamentoDB agendamentoDB = new AgendamentoDB(CadastroActivity.this);

                agendamentoDB.save(agendamento);

                setResult(Activity.RESULT_OK, returnIntent);
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
