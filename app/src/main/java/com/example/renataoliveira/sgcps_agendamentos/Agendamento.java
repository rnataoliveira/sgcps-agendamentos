package com.example.renataoliveira.sgcps_agendamentos;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Agendamento {
    public long id;
    public String nome;
    public String data;
    public String hora;
    public String local;
    public Integer lembrar;
    public Agendamento(){

    }

    public Agendamento(String nome, String data, String hora, String local, Integer lembrar) {
        this.nome = nome;
        this.data = data;
        this.hora = hora;
        this.local = local;
        this.lembrar = lembrar;
    }

    public static List<Agendamento> getAgendamentos(){
        List<Agendamento> agendamentos = new ArrayList<Agendamento>();
        agendamentos.add(new Agendamento("Visitar Cliente", "10/04/2018", "9:00", "Rua das Flores", 1));
        agendamentos.add(new Agendamento("Visitar Cliente", "10/04/2018", "9:00", "Rua das Flores", 0));
        agendamentos.add(new Agendamento("Visitar Cliente", "10/04/2018", "9:00", "Rua das Flores", 1));
        agendamentos.add(new Agendamento("Visitar Cliente", "10/04/2018", "9:00", "Rua das Flores", 1));

        return agendamentos;
    }
}
