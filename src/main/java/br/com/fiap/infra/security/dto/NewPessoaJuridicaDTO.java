package br.com.fiap.infra.security.dto;

import br.com.fiap.Main;
import br.com.fiap.infra.configuration.criptografia.Password;
import br.com.fiap.infra.security.entity.PessoaJuridica;
import br.com.fiap.infra.security.entity.Usuario;
import br.com.fiap.infra.security.service.PessoaJuridicaService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.Objects;

public record NewPessoaJuridicaDTO(
        Long id,
        @NotNull String nome,
        @PastOrPresent LocalDate nascimento,
        @NotNull @Email String email,
        @NotNull String cnpj,
        CredenciaisDTO credenciais
) {

    private static PessoaJuridicaService service = PessoaJuridicaService.of( Main.PERSISTENCE_UNIT );

    public static NewPessoaJuridicaDTO of(PessoaJuridica p) {
        if (Objects.isNull( p ) ) return null;
        return new NewPessoaJuridicaDTO( p.getId(), p.getNome(), p.getNascimento(), p.getEmail(),   p.getCnpj(), null );
    }

    public static PessoaJuridica of(NewPessoaJuridicaDTO p) {
        PessoaJuridica pessoa = null;

        if (Objects.isNull( p )) return null;

        if (Objects.nonNull( p.id )) {
            pessoa = service.findById( p.id() );
            return pessoa;
        }
        pessoa = new PessoaJuridica();
        pessoa.setCnpj( p.cnpj );
        pessoa.setNome( p.nome );
        pessoa.setNascimento( p.nascimento );
        pessoa.setEmail( p.email );

        if(Objects.nonNull( p.credenciais ) ){
            Usuario usuario = new Usuario();
            usuario.setUsername( p.credenciais.username() ).setPassword( Password.encoder(  p.credenciais.password()) );
            pessoa.setUsuario( usuario );
        }

        return pessoa;
    }
}
