package br.com.fiap.infra.configuration.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@JsonTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JsTokenFilterNeeded implements ContainerRequestFilter {

    private static final String AUTHORIZATION_SERVICE_PATH = "authorization";

    private static final String PRIVATE_KEY = "privateKey";

    private final JwTokenHelper jwTokenHelper = JwTokenHelper.getInstance();

    @Override
    public void filter(ContainerRequestContext requestContext) {

        String path = requestContext.getUriInfo().getPath();

        if (path.equals( AUTHORIZATION_SERVICE_PATH )) return;

        String privateKeyHeaderValue = requestContext.getHeaderString( PRIVATE_KEY );
        if (privateKeyHeaderValue == null || privateKeyHeaderValue.isEmpty()) {
            throw new WebApplicationException( "Missing " + PRIVATE_KEY + " inside the header", Response.Status.UNAUTHORIZED );
        }

        try {
            jwTokenHelper.claimKey( privateKeyHeaderValue );
        } catch (ExpiredJwtException e) {
            throw new WebApplicationException( PRIVATE_KEY + " is expired.\n " + e.getMessage(), Response.Status.UNAUTHORIZED );
        } catch (MalformedJwtException e) {
            throw new WebApplicationException( PRIVATE_KEY + " is not correct.\n" + e.getMessage(), Response.Status.UNAUTHORIZED );
        } catch (SignatureException e) {
            throw new WebApplicationException( PRIVATE_KEY + " is not correct.\n" + e.getMessage(), Response.Status.UNAUTHORIZED );
        }
    }
}