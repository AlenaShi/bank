package com.intexsoft.bank.service;

import com.intexsoft.bank.dao.BankDao;
import com.intexsoft.bank.dao.BankDaoImpl;
import com.intexsoft.bank.exception.DaoException;
import com.intexsoft.bank.model.Bank;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Used for working with bank
 */
public class BankService {

    private BankService() {
    }

    /**
     * Used for creating bank
     *
     * @param name                 bank name
     * @param commissionIndividual individual commission
     * @param commissionLegal      legal commission
     * @throws DaoException throws from dao layer
     */
    public static void createBank(String name, Integer commissionIndividual, Integer commissionLegal) throws DaoException {
        BankDao bankDao = BankDaoImpl.getInstance();
        Bank bank = new Bank();
        bank.setName(name);
        bank.setCommissionIndividual(commissionIndividual);
        bank.setCommissionLegal(commissionLegal);
        bank.setCreationDate(new Timestamp(new java.util.Date().getTime()));
        bank.setModificationDate(new Timestamp(new java.util.Date().getTime()));
        bankDao.create(bank);
    }

    /**
     * Used for deleting bank
     *
     * @param id bank's id
     * @throws DaoException throws from dao layer
     */
    public static void deleteBank(Long id) throws DaoException {
        BankDao bankDao = BankDaoImpl.getInstance();
        bankDao.delete(id);
    }

    /**
     * Used for updating bank
     *
     * @param name                 bank's name
     * @param commissionIndividual individual commission
     * @param commissionLegal      legal commission
     * @throws DaoException throws from dao layer
     */
    public static void updateBank(String name, Integer commissionIndividual, Integer commissionLegal) throws DaoException {
        BankDao bankDao = BankDaoImpl.getInstance();
        Bank bank = new Bank();
        bank.setName(name);
        bank.setCommissionIndividual(commissionIndividual);
        bank.setCommissionLegal(commissionLegal);
        bank.setModificationDate(new Timestamp(new java.util.Date().getTime()));
        bankDao.update(bank);
    }

    /**
     * For getting bank object
     *
     * @param id bank's id
     * @return bank object
     * @throws DaoException throws from dao layer
     */
    public static Bank getBank(Long id) throws DaoException {
        BankDao bankDao = BankDaoImpl.getInstance();
        return bankDao.get(id);
    }

    /**
     * Used for getting bank by bank's name
     *
     * @param name bank's name
     * @return bank object
     * @throws DaoException throws from dao layer
     */
    public static Bank getBank(String name) throws DaoException {
        BankDao bankDao = BankDaoImpl.getInstance();
        return bankDao.get(name);
    }
}
