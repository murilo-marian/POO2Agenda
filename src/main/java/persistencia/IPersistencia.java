package persistencia;

import contatos.Contato;

import java.util.List;

public interface IPersistencia {
    void salvar(Contato contato);

    List<Contato> resgatarTodos();
}
