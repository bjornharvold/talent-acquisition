package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.CoverageDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Coverage;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:51:12 PM
 * Responsibility:
 */
@Repository("coverageDao")
public class CoverageDaoImpl extends AbstractHibernateDao<Coverage, String> implements CoverageDao {
    @Override
    public List<Coverage> getCoverages(String historyId, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(Coverage.class);

        c.createAlias("history", "h").add(Restrictions.eq("h.id", historyId));

        if (index != null && maxResults != null) {
            c.setFirstResult(index * maxResults);
            c.setMaxResults(maxResults);
        }

        return c.list();
    }
}
