package fr.univtln.gageyreboulmusial.services;

import fr.univtln.gageyreboulmusial.entities.Entity;
import fr.univtln.gageyreboulmusial.exceptions.DataAccessException;

import java.util.List;
import java.util.Optional;

public interface WS<E extends Entity> {
    public List<E> getAll() throws DataAccessException;

    public E get(int id) throws DataAccessException;

    //public E post() throws DataAccessException;

    //public void put() throws DataAccessException;

    public void delete(int id) throws DataAccessException;
}
