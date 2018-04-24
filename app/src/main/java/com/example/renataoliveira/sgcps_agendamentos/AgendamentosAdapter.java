package com.example.renataoliveira.sgcps_agendamentos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AgendamentosAdapter extends BaseAdapter {
    private final Context context;
    private final List<Agendamento> listaDeAgendamentos;

    public AgendamentosAdapter(Context context, List<Agendamento> listaDeAgendamentos) {
        this.context = context;
        this.listaDeAgendamentos = listaDeAgendamentos;
    }

    @Override
    public int getCount() {
        return listaDeAgendamentos != null? listaDeAgendamentos.size():0;
    }

    @Override
    public Object getItem(int posicao) {
        return listaDeAgendamentos.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return posicao;
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {
        // Infla a View
        View viewText = LayoutInflater.from(context).inflate(R.layout.tela_inicial_itens, viewGroup, false);

        // Procura elementos de tela para atualizar
        TextView t = (TextView)viewText.findViewById(R.id.textItemList);

        // atualiza valores da view
        Agendamento agendamento = listaDeAgendamentos.get(posicao);
        t.setText(agendamento.nome);
        return viewText;
    }


}
