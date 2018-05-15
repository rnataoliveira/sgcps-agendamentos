package com.example.renataoliveira.sgcps_agendamentos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Renata Oliveira on 11/03/2018.
 */

public class Dashboard extends  DebugActivity {
    private String [] listadeAgendamentos = new String[]{"Entregar Contratos", "Receber Pagamento da Maria", "Ligar Para Planos de Saúde", "Fidelizar Clientes"};
    private List<Agendamento> agendamentos;
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        Intent intent = getIntent();
        Bundle params = intent.getExtras();

        // verificar se existe parâmetros
        if (params != null) {
            String nome = params.getString("nome");

            // Mostra o nome do usuário enviado no log e no Toast
            Log.d(DEBUG_TAG, "Nome do usuário: " + nome);
            Toast.makeText(Dashboard.this, "Nome do usuário: " + nome, Toast.LENGTH_LONG).show();

            // Altera o TextView da tela com o nome do usuário
            TextView texto = (TextView) findViewById(R.id.dashboardText);
            texto.setText(nome);
        }

        Button botaoVerAgendamentos = (Button) findViewById(R.id.buttonSeeSavedAppointments);
        botaoVerAgendamentos.setOnClickListener(clickAgendamentos());

        Button botaoAddAgendamento = (Button) findViewById(R.id.buttonAddAppointment);
        botaoAddAgendamento.setOnClickListener(clickAddAgendamento());

        // Recupera o botão de sair e vincula um evento de clique
        Button botaoSair = (Button) findViewById(R.id.buttonOut);
        botaoSair.setOnClickListener(clickSair());

        //Alterar texto da ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Início");

    }

    public View.OnClickListener clickAgendamentos() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent it = new Intent(Dashboard.this, TelaInicial2Activity.class);
                startActivity(it);
            }
        };
    }

//    @Override
//    public void onBackPressed(){
//        setContentView(R.layout.dashboard);
//    }

    public View.OnClickListener clickAddAgendamento() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent it = new Intent(Dashboard.this, CadastroActivity.class);
                startActivity(it);
            }
        };
    }

    // Tratamento do evento de clique no botao de sair
    public View.OnClickListener clickSair() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "Saída do sgcps");
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        };
    }

    // Recuperar resultado de CadastroActivity após ela ser finalizada
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                StringBuffer textoRetorno = new StringBuffer();
                textoRetorno.append(data.getStringExtra("nomeAgendamento"));
                textoRetorno.append("\n");
                textoRetorno.append(data.getStringExtra("dataAgendamento"));
                textoRetorno.append("\n");
                textoRetorno.append(data.getStringExtra("horaAgendamento"));
                textoRetorno.append("\n");
                textoRetorno.append(data.getStringExtra("localAgendamento"));
                textoRetorno.append("\n");

                TextView texto = (TextView) findViewById(R.id.dashboardText);
                texto.setText(textoRetorno.toString());

            }
        }
    }
    
}
