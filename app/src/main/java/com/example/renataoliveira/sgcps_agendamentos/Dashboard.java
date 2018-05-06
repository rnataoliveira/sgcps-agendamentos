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
                setContentView(R.layout.activity_tela_inicial_2);
                lista = (ListView)findViewById(R.id.listaElementos);

                agendamentos = Agendamento.getAgendamentos();
                lista.setAdapter(new AgendamentosAdapter(Dashboard.this,agendamentos ));

                lista.setOnItemClickListener(new ListView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {

                        Toast.makeText(Dashboard.this, "Selecionado "+ agendamentos.get(index).nome, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };
    }

    public View.OnClickListener clickAddAgendamento() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent it = new Intent(Dashboard.this, CadastroActivity.class);
                startActivityForResult(it, 1);
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

    @Override
    // Método para colocar o menu na ActionBar
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflar o menu na view
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // Recuperar SearchView
        MenuItem item = menu.findItem(R.id.action_buscar);
        SearchView searchView = (SearchView) item.getActionView();
        // Listener que espera a ação de buscar
        searchView.setOnQueryTextListener(onSearch());

        // Recuperar  botão de compartilhar
        MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider share = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        //Listener que espera a ação de compartilhar
        share.setShareIntent(getDefautIntent());
        return true;
    }

    // TRatar os eventos dos botões do menu
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_atualizar) {
            Toast.makeText(Dashboard.this,
                    "Atualizar",
                    Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_buscar) {
            Toast.makeText(Dashboard.this,
                    "Buscar",
                    Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_adicionar) {
            Toast.makeText(Dashboard.this,
                    "Buscar",
                    Toast.LENGTH_SHORT).show();
            Intent it = new Intent(Dashboard.this, CadastroActivity.class);
            startActivityForResult(it, 1);
        } else if (id == R.id.action_config) {
            Toast.makeText(Dashboard.this,
                    "Config",
                    Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_share) {
            Toast.makeText(Dashboard.this,
                    "Compartilhar",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
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


    // Funcao para retornar o tratamento do evento no SearchView
    private SearchView.OnQueryTextListener onSearch() {
        return new SearchView.OnQueryTextListener() {
            @Override
            // Tratamento do evento quando termina de escrever
            public boolean onQueryTextSubmit(String query) {
                query = query.toLowerCase();
//                Toast.makeText(Dashboard.this, query, Toast.LENGTH_SHORT).show();
//                String results = "";
//                for (String agendamento: listaDeAgendamentos) {
//                    if(agendamento.toLowerCase().contains(query)){
//                        results += agendamento +"\n";
//                    }
//                }
//                TextView texto = (TextView) findViewById(R.id.dashboardText);
//                texto.setText(results);
                buscaAgendamento(query);
                return false;
            }

            @Override
            // Tratamento do evento enquanto ainda está escrevendo
            public boolean onQueryTextChange(String newText) {
//                Toast.makeText(Dashboard.this, newText, Toast.LENGTH_SHORT).show();
                buscaAgendamento(newText);
                return false;
            }
        };
    }

    private void buscaAgendamento(String query) {
        List<Agendamento> results = new ArrayList<Agendamento>();
        for (Agendamento agendamento: agendamentos) {
            if(agendamento.nome.toLowerCase().contains(query)){
                results.add(agendamento);
            }
        }
        lista.setAdapter(new AgendamentosAdapter(Dashboard.this,results ));
    }


    private Intent getDefautIntent() {
        // Intent ACTION_SEND. Qualquer app que responde essa intenção pode ser chamado
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/*");
        String textoShare = "Minha lista de agendamentos! \n";

        for (String agendamento: listadeAgendamentos) {
            textoShare += agendamento +"\n";
        }
        intent.putExtra(Intent.EXTRA_TEXT, textoShare);
        return intent;
    }
}
