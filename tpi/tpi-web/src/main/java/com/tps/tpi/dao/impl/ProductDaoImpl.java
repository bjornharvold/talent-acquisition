package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.ProductDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Product;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 17, 2009
 * Time: 2:41:52 PM
 * Responsibility:
 */
@Repository("productDao")
public class ProductDaoImpl extends AbstractHibernateDao<Product, String> implements ProductDao {
    @Override
    public List<Product> getProducts(String companyId, String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(Product.class);

        c.createAlias("company", "c").add(Restrictions.eq("c.id", companyId));

        if (StringUtils.isNotBlank(name)) {
            c.add(Restrictions.ilike("shortName", name + "%"));
        }

        if (index != null && maxResults != null) {
            c.setFirstResult(index * maxResults);
            c.setMaxResults(maxResults);
        }

        c.addOrder(Order.asc("shortName"));

        return c.list();
    }

    @Override
    public Product getProductByName(String companyId, String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(Product.class);
        c.add(Restrictions.eq("shortName", name));
        c.createAlias("company", "c").add(Restrictions.eq("c.id", companyId));

        return (Product) c.uniqueResult();
    }
}
