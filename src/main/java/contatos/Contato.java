package contatos;

import persistencia.IPersistencia;

import java.time.LocalDate;

public class Contato {
    private String nome;
    private LocalDate nascimento;
    private String telefone;
    private String email;
    protected IPersistencia persistencia;

    public Contato(String nome, LocalDate nascimento, String telefone, String email, IPersistencia persistencia) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.telefone = telefone;
        this.email = email;
        this.persistencia = persistencia;
    }

    public void salvar(){
        this.persistencia.salvar(this);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public IPersistencia getPersistencia() {
        return persistencia;
    }

    public void setPersistencia(IPersistencia persistencia) {
        this.persistencia = persistencia;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Contato{");
        sb.append("nome='").append(nome).append('\'');
        sb.append(", nascimento=").append(nascimento);
        sb.append(", telefone='").append(telefone).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", persistencia=").append(persistencia);
        sb.append('}');
        return sb.toString();
    }
}
