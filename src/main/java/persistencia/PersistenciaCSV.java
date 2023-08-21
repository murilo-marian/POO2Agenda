package persistencia;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import contatos.Contato;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaCSV implements IPersistencia{

    String filePath = "data.csv";


    @Override
    public void salvar(Contato contato) {

        List<String[]> csvData = createCsvDataSpecial(contato);

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeAll(csvData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String[]> createCsvDataSpecial(Contato contato) {

        List<String[]> list = new ArrayList<>();
        File arquivo = new File("data.csv");
        if (arquivo.length() < 40) {
            String[] header = {"Nome", "Nascimento", "Email", "Telefone"};
            list.add(header);
        } else {
            try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
                List<String[]> r = reader.readAll();
                r.forEach(x -> {
                    String[] oldContent = {x[0], x[1], x[2], x[3]};
                    list.add(oldContent);
                });
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (CsvException e) {
                throw new RuntimeException(e);
            }
        }

        String[] registro = {contato.getNome(), contato.getNascimento().toString(), contato.getEmail(), contato.getTelefone()};
        list.add(registro);

        return list;
    }

    @Override
    public List<Contato> resgatarTodos() {
        List<Contato> contatos = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> r = reader.readAll();
            r.forEach(x -> {
                IPersistencia persistencia = new PersistenciaCSV();
                if (!x[0].equals("Nome")) {
                    Contato contato = new Contato(x[0], LocalDate.parse(x[1]), x[2], x[3], persistencia);
                    contatos.add(contato);
                }
            });
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
        return contatos;
    }


}
