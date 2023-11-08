package br.com.fiap.infra.security.dto;

import br.com.fiap.Main;
import br.com.fiap.infra.security.entity.Pessoa;
import br.com.fiap.infra.security.entity.PessoaFisica;
import br.com.fiap.infra.security.entity.PessoaJuridica;
import br.com.fiap.infra.security.service.PessoaFisicaService;
import br.com.fiap.infra.security.service.PessoaJuridicaService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.Objects;

public record PessoaDTO(
        Long id,
        String nome,
        @Email String email,
        @PastOrPresent LocalDate nascimento,
        String tipo) {

    private static PessoaFisicaService pfService = PessoaFisicaService.of(Main.PERSISTENCE_UNIT);

    private static PessoaJuridicaService pjService = PessoaJuridicaService.of(Main.PERSISTENCE_UNIT);

    public static PessoaDTO of(Pessoa p) {
        return new PessoaDTO(p.getId(), p.getNome(), p.getEmail(), p.getNascimento(), p instanceof PessoaFisica ? "PF" : "PJ");
    }

    public static Pessoa of(PessoaDTO p) {

        if (Objects.isNull(p)) return null;

        Pessoa pessoa = null;

        if (Objects.nonNull(p.id)) {
            pessoa = pfService.findById(p.id);
            if (Objects.isNull(pessoa)) {
                pessoa = pjService.findById(p.id);
            }
            if (Objects.isNull(pessoa)) return null;
            return pessoa;
        }

        if (!p.tipo.equals(null)) {
            if (p.tipo.equalsIgnoreCase("PF")) {
                PessoaFisica pf = new PessoaFisica();
                pf.setNome(p.nome);
                pf.setNascimento(p.nascimento);
                pf.setEmail(p.email);
                return pf;
            } else if (p.tipo.equalsIgnoreCase("PJ")) {
                PessoaJuridica pj = new PessoaJuridica();
                pj.setNome(p.nome);
                pj.setNascimento(p.nascimento);
                pj.setEmail(p.email);
                return pj;
            }
        }
        return pessoa;
    }

}


