package br.com.fiap.infra.security.dto;

import br.com.fiap.Main;
import br.com.fiap.infra.security.entity.PessoaFisica;
import br.com.fiap.infra.security.entity.Sexo;
import br.com.fiap.infra.security.service.PessoaFisicaService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.Objects;

public record PessoaFisicaDTO(
        Long id,
        String cpf,
        String nome,
        @PastOrPresent LocalDate nascimento,
        @Email String email,
        Sexo sexo
) {

    private static PessoaFisicaService service = PessoaFisicaService.of(Main.PERSISTENCE_UNIT);
    public static PessoaFisicaDTO of(PessoaFisica p) {
        return new PessoaFisicaDTO( p.getId(), p.getCpf(), p.getNome(),  p.getNascimento(), p.getEmail(), p.getSexo() );
    }

    public static PessoaFisica of(PessoaFisicaDTO p) {

        PessoaFisica pessoa = null;

        if (Objects.isNull(p)) return null;

        if (Objects.nonNull(p.id)) {
            pessoa = service.findById(p.id());
            return pessoa;
        }

        pessoa = new PessoaFisica();
        pessoa.setCpf(p.cpf);
        pessoa.setSexo(p.sexo);
        pessoa.setNome(p.nome);
        pessoa.setNascimento(p.nascimento);
        pessoa.setEmail(p.email);

        return pessoa;
    }
}
