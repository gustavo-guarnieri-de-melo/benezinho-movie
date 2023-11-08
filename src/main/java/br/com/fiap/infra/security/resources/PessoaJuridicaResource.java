package br.com.fiap.infra.security.resources;

import br.com.fiap.Main;
import br.com.fiap.infra.security.dto.NewPessoaJuridicaDTO;
import br.com.fiap.infra.security.entity.PessoaJuridica;
import br.com.fiap.infra.security.service.PessoaJuridicaService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

@Path("/pj")
//@JsonTokenNeeded //Necessita de um "privateKey" em Usuario type APIKey
public class PessoaJuridicaResource implements Resource<NewPessoaJuridicaDTO, Long> {
    @Context
    UriInfo uriInfo;

    private final PessoaJuridicaService service = PessoaJuridicaService.of( Main.PERSISTENCE_UNIT );

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response findAll() {

        List<PessoaJuridica> all = service.findAll();

        return Response
                .status( Response.Status.OK )
                .entity( all.stream().map( NewPessoaJuridicaDTO::of ).toList() )
                .build();
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response findById(@PathParam("id") Long id) {
        PessoaJuridica  pessoa = service.findById( id );

        return Response
                .status( Response.Status.OK )
                .entity( NewPessoaJuridicaDTO.of( pessoa ) )
                .build();
    }


    @POST
    @Override
    public Response persist(NewPessoaJuridicaDTO p) {

        var pessoa = NewPessoaJuridicaDTO.of( p );

        PessoaJuridica persist = service.persist( pessoa );

        //https://docs.oracle.com/middleware/1213/wls/RESTF/develop-restful-service.htm#RESTF238
        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI uri = ub.path( String.valueOf( persist.getId() ) ).build();

        return Response
                .created( uri )
                .entity( NewPessoaJuridicaDTO.of( persist ) )
                .build();
    }
}
