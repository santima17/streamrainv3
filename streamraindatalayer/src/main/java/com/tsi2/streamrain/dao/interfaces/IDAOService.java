/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsi2.streamrain.dao.interfaces;
import java.util.List;


/**
 *
 * @author santiago
 */
public interface IDAOService {
    
    public <T> Integer save(final T o, final String tenantID);
    public void delete(final Object object, final String tenantID);
    public <T> T get(final Class<T> type, final Integer id, final String tenantID);
    public <T> T merge(final T o, final String tenantID);
    public <T> boolean saveOrUpdate(final T o, final String tenantID);
    public <T> List<T> getAll(final Class<T> type, final String tenantID);
    public <T> List<T> getAllByExample(final Class<T> type, final T objectQuery, final String tenantID);
}
