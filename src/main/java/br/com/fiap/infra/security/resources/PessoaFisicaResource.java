package br.com.fiap.infra.security.resources;

import br.com.fiap.Main;
import br.com.fiap.infra.security.dto.NewPessoaFisicaDTO;
import br.com.fiap.infra.security.dto.PessoaFisicaDTO;
import br.com.fiap.infra.security.entity.PessoaFisica;
import br.com.fiap.infra.security.service.PessoaFisicaService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

@Path("/pf")
//@JsonTokenNeeded //Necessita de um "privateKey" em Usuario type APIKey
public class PessoaFisicaResource implements Resource<NewPessoaFisicaDTO, Long> {

    @Context
    UriInfo uriInfo;

    private final PessoaFisicaService service = PessoaFisicaService.of( Main.PERSISTENCE_UNIT );

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response findAll() {
        List<PessoaFisica> all = service.findAll();

        return Response
                .status( Response.Status.OK )
                .entity( all.stream().map( PessoaFisicaDTO::of ).toList() )
                .build();
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response findById(@PathParam("id") Long id) {
        PessoaFisica pessoa = service.findById( id );

        return Response
                .status( Response.Status.OK )
                .entity( PessoaFisicaDTO.of( pessoa ) )
                .build();
    }


    @POST
    @Override
    public Response persist(@Valid NewPessoaFisicaDTO pessoa) {

        var p = NewPessoaFisicaDTO.of( pessoa );

        PessoaFisica persist = service.persist( p);

        //https://docs.oracle.com/middleware/1213/wls/RESTF/develop-restful-service.htm#RESTF238
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI uri = ub.path( String.valueOf( persist.getId() ) ).build();

        return Response
                .created( uri )
                .entity( PessoaFisicaDTO.of( persist ) )
                .build();
    }
}
