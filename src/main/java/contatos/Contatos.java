package contatos;

import persistencia.IPersistencia;

import java.util.List;

public class Contatos {
    private List<Contato> contatos;
    protected IPersistencia persistencia;

    public Contatos(List<Contato> contatos, IPersistencia persistencia) {
        this.contatos = contatos;
        this.persistencia = persistencia;
    }

    public Contatos(IPersistencia persistencia) {
        this.persistencia = persistencia;
    }

    public void puxarContatos() {
        setContatos(this.persistencia.resgatarTodos());
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public void setContatos(List<Contato> contatos) {
        this.contatos = contatos;
    }

    public IPersistencia getPersistencia() {
        return persistencia;
    }

    public void setPersistencia(IPersistencia persistencia) {
        this.persistencia = persistencia;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Contatos{");
        sb.append("contatos=").append(contatos);
        sb.append(", persistencia=").append(persistencia);
        sb.append('}');
        return sb.toString();
    }
}
