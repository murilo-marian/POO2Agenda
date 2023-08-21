package main;

import contatos.Contato;
import contatos.Contatos;
import persistencia.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        IPersistencia json = new PersistenciaJSON();
        IPersistencia xml = new PersistenciaXML();
        IPersistencia csv = new PersistenciaCSV();
        IPersistencia mySql = new PersistenciaMySQL();

        Contato contato = new Contato("Murilo", LocalDate.of(2003, 9, 12),
                "48988032892", "murilomarian.mm@gmail.com", json);
        Contato contato2 = new Contato("Saloi", LocalDate.of(2003, 1, 12),
                "55555555555", "saloi@gmail.com", xml);
        Contato contato3 = new Contato("Rodrigo", LocalDate.of(2002, 4, 2), "333333333", "rodrigo@gmail.com", csv);
        contato.salvar();
        contato2.salvar();
        contato3.salvar();


        System.out.println("JSON");
        Contatos agenda = new Contatos(json);
        agenda.puxarContatos();
        for (Contato agendaContato : agenda.getContatos()) {
            System.out.println(agendaContato);
        }

        System.out.println("XML");

        Contatos agenda2 = new Contatos(xml);
        agenda2.puxarContatos();
        for (Contato agenda2Contato : agenda2.getContatos()) {
            System.out.println(agenda2Contato);

        }

        System.out.println("CSV");

        Contatos agenda3 = new Contatos(csv);
        agenda3.puxarContatos();
        for (Contato agenda3Contato : agenda3.getContatos()) {
            System.out.println(agenda3Contato);
        }
    }
}
