package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.HistoryDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.History;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 5, 2009
 * Time: 10:27:02 AM
 * Responsibility:
 */
@Repository("historyDao")
public class HistoryDaoImpl extends AbstractHibernateDao<History, String> implements HistoryDao {
    @Override
    public History getHistory(String personId) throws PersistenceException {
        Criteria c = getSession().createCriteria(History.class);
        c.createAlias("person" , "p").add(Restrictions.eq("p.id", personId));

        return (History) c.uniqueResult();
    }
}
