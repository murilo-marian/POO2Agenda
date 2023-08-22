package persistencia;

import contatos.Contato;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaMySQL implements IPersistencia{
    final static String NOME_DA_TABELA = "contatos";
    @Override
    public void salvar(Contato contato) {
        try {
            Connection conn = Conexao.conectar();
            String sql = "INSERT INTO " + NOME_DA_TABELA + " (nome, nascimento, email, telefone) VALUES (?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, contato.getNome());
            ps.setDate(2, Date.valueOf(contato.getNascimento()));
            ps.setString(3, contato.getEmail());
            ps.setString(4, contato.getTelefone());
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Contato> resgatarTodos() {
        try {
            Connection conn = Conexao.conectar();
            String sql = "SELECT * FROM " + NOME_DA_TABELA + ";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                List<Contato> resultadoPesquisa = montarLista(rs);
                ps.close();
                rs.close();
                conn.close();
                return resultadoPesquisa;
            } else {
                ps.close();
                rs.close();
                conn.close();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Contato> montarLista(ResultSet rs) {
        List<Contato> listObj = new ArrayList<Contato>();
        IPersistencia persistencia = new PersistenciaMySQL();
        try {
            do {
                String nome = rs.getString(2);
                LocalDate nascimento = rs.getDate(3).toLocalDate();
                String email = rs.getString(4);
                String telefone = rs.getString(5);
                Contato obj = new Contato(nome, nascimento, email, telefone, persistencia);
                listObj.add(obj);
            } while (rs.next());
            return listObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
