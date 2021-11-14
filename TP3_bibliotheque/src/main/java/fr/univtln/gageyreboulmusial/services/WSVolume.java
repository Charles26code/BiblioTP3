package fr.univtln.gageyreboulmusial.services;

import fr.univtln.gageyreboulmusial.daos.VolumeDAO;
import fr.univtln.gageyreboulmusial.entities.Volume;
import fr.univtln.gageyreboulmusial.exceptions.DataAccessException;
import lombok.extern.java.Log;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.List;
import java.util.Optional;

@Path("volume/")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Log
public class WSVolume implements WS<Volume>{

    @Override
    @GET
    @Path("all")
    public List<Volume> getAll() throws DataAccessException {
        try(VolumeDAO volumeDAO = new VolumeDAO()) {
            return volumeDAO.findAll();
        }
    }

    @Override
    @GET
    @Path("{id}")
    public Volume get(@PathParam("id") int id) throws DataAccessException {
        try(VolumeDAO volumeDAO = new VolumeDAO()) {
            Optional<Volume> volume = volumeDAO.find(id);
            if(volume.isPresent()) {
                return volume.get();
            }
        }
        return null;
    }

    @POST
    public Volume post(
            @QueryParam("title") String title,
            @QueryParam("idlibrary") int idlibrary,
            @QueryParam("author") String author
    ) throws DataAccessException {
        try(VolumeDAO volumeDAO = new VolumeDAO()) {
            return volumeDAO.persist(author, title, idlibrary);
        }
    }

    @PUT
    public void put(
            @QueryParam("id") int id,
            @QueryParam("title") String title,
            @QueryParam("idlibrary") int idlibrary,
            @QueryParam("iddocument") int iddocument,
            @QueryParam("author") String author
    ) throws DataAccessException {
        try(VolumeDAO volumeDAO = new VolumeDAO()) {
            Volume v1 = Volume.volumeBuilder()
                    .id(id)
                    .idlibrary(idlibrary)
                    .title(title)
                    .author(author)
                    .iddocument(iddocument)
                    .build();
            volumeDAO.update(v1);
        }
    }

    @DELETE
    public void delete(
            @QueryParam("id") int id
    ) throws DataAccessException {
        try(VolumeDAO volumeDAO = new VolumeDAO()) {
            volumeDAO.remove(id);
        }
    }
}
