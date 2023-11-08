package br.com.fiap.infra.security.resources;


import br.com.fiap.Main;
import br.com.fiap.infra.configuration.jwt.JwTokenHelper;
import br.com.fiap.infra.security.dto.CredenciaisDTO;
import br.com.fiap.infra.security.dto.UsuarioDTO;
import br.com.fiap.infra.security.entity.Usuario;
import br.com.fiap.infra.security.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Objects;

@Path("/authorization")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorizationResource {

    UsuarioService service = UsuarioService.of(Main.PERSISTENCE_UNIT);

    @POST
    public Response authorizationService(@Valid CredenciaisDTO dto) {

        if (Objects.isNull(dto))
            return Response.status(401).entity("User is required").build();
        if (dto.username().isEmpty())
            return Response.status(401).entity("username field cannot be empty!").build();
        if (dto.password().isEmpty())
            return Response.status(401).entity("password field cannot be empty!").build();

        Usuario usuarioAutenticado = service.autenticar(dto);
        if (Objects.isNull(usuarioAutenticado))
            return Response.status(401).entity("User or password invalid!").build();

        //Gerando a chave JWT
        String privateKey = JwTokenHelper.getInstance().generatePrivateKey(usuarioAutenticado);

        //Adiciono a chave JWT e Responde a Requisição
        System.out.println("You're authenticated successfully.\nPrivate key will be valid for 30 mins: \n" + privateKey);

        return Response.ok(UsuarioDTO.of(usuarioAutenticado, privateKey)).build();
    }
}