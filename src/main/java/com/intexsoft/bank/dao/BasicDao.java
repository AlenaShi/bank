package com.intexsoft.bank.dao;

import com.intexsoft.bank.exception.DaoException;

import java.sql.SQLException;

/**
 * Basic dao interface for work with crud operations
 *
 * @param <T>
 */
public interface BasicDao<T> {
    /**
     * For creating object
     *
     * @param object to create
     * @throws DaoException if exception during saving
     */
    void create(T object) throws DaoException;

    /**
     * For deleting object
     *
     * @param id object's identifier
     * @throws DaoException if something go wrong during operation
     */
    void delete(Long id) throws DaoException;

    /**
     * For updating object
     *
     * @param object to update
     * @throws DaoException if something go wrong during operation
     */
    void update(T object) throws DaoException;

    /**
     * To get object
     *
     * @param id object's identifier
     * @return object
     * @throws DaoException if something go wrong during operation
     */
    T get(Long id) throws DaoException;
}
