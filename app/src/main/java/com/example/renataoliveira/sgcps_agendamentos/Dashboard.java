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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Renata Oliveira on 11/03/2018.
 */

public class Dashboard extends  DebugActivity {
    private String [] listaDeCompromissos = new String[]{"Entregar Contratos", "Receber Pagamento da Maria", "Ligar Para Planos de Saúde", "Fidelizar Clientes"};
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

        // Recupera o botão de sair e vincula um evento de clique
        Button botaoSair = (Button) findViewById(R.id.buttonOut);
        botaoSair.setOnClickListener(clickSair());

        //Alterar texto da ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Início");
    }

    // Tratamento do evento de clique no botao de sair
    public View.OnClickListener clickSair() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "Saída do BrewerApp");
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
                textoRetorno.append(data.getStringExtra("nomeCompromisso"));
                textoRetorno.append("\n");
                textoRetorno.append(data.getStringExtra("dataCompromisso"));
                textoRetorno.append("\n");
                textoRetorno.append(data.getStringExtra("horaCompromisso"));
                textoRetorno.append("\n");
                textoRetorno.append(data.getStringExtra("localCompromisso"));
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
                Toast.makeText(Dashboard.this, query, Toast.LENGTH_SHORT).show();
                String results = "";
                for (String compromisso: listaDeCompromissos) {
                    if(compromisso.toLowerCase().contains(query)){
                        results += compromisso +"\n";
                    }
                }
                TextView texto = (TextView) findViewById(R.id.dashboardText);
                texto.setText(results);
                return false;
            }

            @Override
            // Tratamento do evento enquanto ainda está escrevendo
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(Dashboard.this, newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        };
    }


    private Intent getDefautIntent() {
        // Intent ACTION_SEND. Qualquer app que responde essa intenção pode ser chamado
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/*");
        String textoShare = "Minha lista de compromissos! \n";

        for (String compromisso: listaDeCompromissos) {
            textoShare += compromisso +"\n";
        }
        intent.putExtra(Intent.EXTRA_TEXT, textoShare);
        return intent;
    }
}
