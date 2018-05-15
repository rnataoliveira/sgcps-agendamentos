package com.example.renataoliveira.sgcps_agendamentos;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Details extends DebugActivity{
    private ListView lista;
    private List<Agendamento> agendamentos;
    private Agendamento agendamento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        Intent intent = getIntent();

        agendamento = (Agendamento) intent.getSerializableExtra("agendamento");
        TextView nomeAgendamento = findViewById(R.id.nome_agendamento);

        TextView horaAgendamento = findViewById(R.id.hora_agendamento);

        TextView dataAgendamento = findViewById(R.id.data_agendamento);

        TextView localAgendamento = findViewById(R.id.local_agendamento);

        nomeAgendamento.setText(agendamento.nome);
        horaAgendamento.setText(agendamento.hora);
        dataAgendamento.setText(agendamento.data);
        localAgendamento.setText(agendamento.local);


        Button botaoDeletar = (Button) findViewById(R.id.excluir_agendamento);
        botaoDeletar.setOnClickListener(clickDeletar(agendamento));
    }

    public View.OnClickListener clickDeletar(final Agendamento agendamento) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgendamentoDB agendamentoDB = new AgendamentoDB(Details.this);
                agendamentoDB.delete(agendamento);
//                Intent intent = new Intent(Details.this, TelaInicial2Activity.class);
//                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        };
    }

}
