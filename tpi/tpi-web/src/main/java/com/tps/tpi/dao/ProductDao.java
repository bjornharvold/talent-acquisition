package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Product;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 17, 2009
 * Time: 2:41:29 PM
 * Responsibility:
 */
public interface ProductDao extends GenericDao<Product, String> {
    List<Product> getProducts(String companyId, String name, Integer index, Integer maxResults) throws PersistenceException;
    Product getProductByName(String companyId, String name) throws PersistenceException;
}
