package br.com.fiap.infra.security.entity;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "TB_PF", uniqueConstraints = {
        @UniqueConstraint(name = "UK_PF_CPF", columnNames = "NR_CPF")
})
@DiscriminatorValue("PF")
public class PessoaFisica extends Pessoa { //

    //@CPF
    @Column(name = "NR_CPF", nullable = false)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    public PessoaFisica() {
    }

    public PessoaFisica(Long id, String nome, LocalDate nascimento, String email, String password,  String cpf, Sexo sexo) {
        super(id, nome, nascimento, email);
        this.cpf = cpf;
        this.sexo = sexo;
    }

    public PessoaFisica(Long id, String nome, LocalDate nascimento, String email, String password, Sexo sexo, String cpf) {
        this.cpf = cpf;
        this.sexo = sexo;
    }

    public String getCpf() {
        return cpf;
    }

    public PessoaFisica setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public PessoaFisica setSexo(Sexo sexo) {
        this.sexo = sexo;
        return this;
    }

    @Override
    public String toString() {
        return "PessoaFisica{" +
                "cpf='" + cpf + '\'' +
                ", sexo=" + sexo +
                "} " + super.toString();
    }
}
