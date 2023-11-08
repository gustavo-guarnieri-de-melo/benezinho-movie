package br.com.fiap.infra.security.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface Resource<T, U> {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response findAll();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response findById(U id);

    @POST
    Response persist(T t);
}
