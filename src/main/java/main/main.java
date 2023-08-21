package main;

import contatos.Contato;
import contatos.Contatos;
import persistencia.IPersistencia;
import persistencia.PersistenciaJSON;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        IPersistencia teste = new PersistenciaJSON();
        Contato contato = new Contato("Murilo", LocalDate.of(2003, 9, 12),
                "48988032892", "murilomarian.mm@gmail.com", teste);
        Contato contato2 = new Contato("Saloi", LocalDate.of(2003, 1, 12),
                "55555555555", "saloi@gmail.com", teste);
        contato.salvar();
        contato2.salvar();
        Contatos agenda = new Contatos(teste);
        agenda.puxarContatos();
        for (Contato agendaContato : agenda.getContatos()) {
            System.out.println(agendaContato);
        }
    }
}
