package persistencia;

import contatos.Contato;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaJSON implements IPersistencia {
    @Override
    public void salvar(Contato contato) {
        JSONObject obj = new JSONObject();
        JSONParser parser = new JSONParser();

        obj.put("nome", contato.getNome());
        obj.put("nascimento", contato.getNascimento().toString());
        obj.put("telefone", contato.getTelefone());
        obj.put("email", contato.getEmail());


        FileWriter file = null;
        try {
            FileReader fileReader = new FileReader("data.json");
            File arquivo = new File("data.json");

            if (arquivo.length() == 0) {
                file = new FileWriter("data.json");
                System.out.println("Arquivo vazio");
                JSONArray salva = new JSONArray();
                salva.add(obj);
                file.write(salva.toJSONString());
            } else {
                JSONArray puxado = (JSONArray) parser.parse(fileReader);
                puxado.add(obj);
                file = new FileWriter("data.json");
                file.write(puxado.toJSONString());
            }
            file.flush();
            file.close();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Contato> resgatarTodos() {
        List<Contato> contatos = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try {
            FileReader fileReader = new FileReader("data.json");

            JSONArray obj = (JSONArray) parser.parse(fileReader);

            obj.forEach(item -> {
                JSONObject convertido = (JSONObject) item;
                IPersistencia persistencia = new PersistenciaJSON();
                String nome = convertido.get("nome").toString();
                LocalDate data = LocalDate.parse(convertido.get("nascimento").toString());
                String telefone = convertido.get("telefone").toString();
                String email = convertido.get("email").toString();

                Contato contato = new Contato(nome, data, telefone, email, persistencia);
                contatos.add(contato);
            });
            return contatos;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
