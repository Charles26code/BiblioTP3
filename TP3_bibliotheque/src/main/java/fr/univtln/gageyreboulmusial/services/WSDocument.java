package fr.univtln.gageyreboulmusial.services;

import fr.univtln.gageyreboulmusial.daos.DocumentDAO;
import fr.univtln.gageyreboulmusial.entities.Document;
import fr.univtln.gageyreboulmusial.exceptions.DataAccessException;
import lombok.extern.java.Log;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("document/")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Log
public class WSDocument implements WS<Document> {

    @Override
    @GET
    @Path("all")
    public List<Document> getAll() throws DataAccessException {
        try(DocumentDAO documentDAO = new DocumentDAO()) {
            return documentDAO.findAll();
        }
    }

    @Override
    @GET
    @Path("{id}")
    public Document get(@PathParam("id") int id) throws DataAccessException {
        try(DocumentDAO documentDAO = new DocumentDAO()) {
            Optional<Document> document = documentDAO.find(id);
            if(document.isPresent()) {
                return document.get();
            }
        }
        return null;
    }

    @POST
    public Document post(
            @QueryParam("title") String title,
            @QueryParam("idlibrary") int idlibrary
    ) throws DataAccessException {
        try(DocumentDAO documentDAO = new DocumentDAO()) {
            return documentDAO.persist(title, idlibrary);
        }
    }

    @PUT
    public void put(
            @QueryParam("id") int id,
            @QueryParam("title") String title,
            @QueryParam("idlibrary") int idlibrary
    ) throws DataAccessException {
        try(DocumentDAO documentDAO = new DocumentDAO()) {
            documentDAO.update(title, idlibrary, id);
        }
    }

    @DELETE
    public void delete(
            @QueryParam("id") int id
    ) throws DataAccessException {
        try(DocumentDAO documentDAO = new DocumentDAO()) {
            documentDAO.remove(id);
        }
    }
}
