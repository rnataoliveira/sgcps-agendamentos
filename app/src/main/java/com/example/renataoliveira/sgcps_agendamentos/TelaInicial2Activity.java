package com.example.renataoliveira.sgcps_agendamentos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TelaInicial2Activity extends DebugActivity{
    private List<Agendamento> agendamentos;
    private ListView lista;

    @Override
    protected void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial_2);

        Intent intent = getIntent();

        lista = (ListView)findViewById(R.id.listaElementos);

        final AgendamentoDB agendamentoDB = new AgendamentoDB(TelaInicial2Activity.this);
        agendamentos = agendamentoDB.findAll();
        lista.setAdapter(new AgendamentosAdapter(TelaInicial2Activity.this, agendamentos ));

            lista.setOnItemClickListener(new ListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
                    Intent it = new Intent(TelaInicial2Activity.this, Details.class);
                    Agendamento ag = agendamentos.get(index);
                    it.putExtra("agendamento", ag);
                    startActivityForResult(it, 1);
                }
            });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
                final AgendamentoDB agendamentoDB = new AgendamentoDB(TelaInicial2Activity.this);
                agendamentos = agendamentoDB.findAll();
                lista.setAdapter(new AgendamentosAdapter(TelaInicial2Activity.this, agendamentos));
        }
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
            Toast.makeText(TelaInicial2Activity.this,
                    "Atualizar",
                    Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_buscar) {
            Toast.makeText(TelaInicial2Activity.this,
                    "Buscar",
                    Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_adicionar) {
            Toast.makeText(TelaInicial2Activity.this,
                    "Buscar",
                    Toast.LENGTH_SHORT).show();
            Intent it = new Intent(TelaInicial2Activity.this, CadastroActivity.class);
            startActivityForResult(it, 1);
        } else if (id == R.id.action_config) {
            Toast.makeText(TelaInicial2Activity.this,
                    "Config",
                    Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_share) {
            Toast.makeText(TelaInicial2Activity.this,
                    "Compartilhar",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
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
        lista.setAdapter(new AgendamentosAdapter(TelaInicial2Activity.this,results ));
    }


    private Intent getDefautIntent() {
        // Intent ACTION_SEND. Qualquer app que responde essa intenção pode ser chamado
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/*");
        String textoShare = "Minha lista de agendamentos! \n";

        for (Agendamento agendamento: agendamentos) {
            textoShare += agendamento +"\n";
        }
        intent.putExtra(Intent.EXTRA_TEXT, textoShare);
        return intent;
    }

}
