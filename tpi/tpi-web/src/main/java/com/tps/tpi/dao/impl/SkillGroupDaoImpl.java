package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.SkillGroupDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.SkillGroup;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 9, 2009
 * Time: 3:44:51 PM
 * Responsibility:
 */
@Repository
public class SkillGroupDaoImpl extends AbstractHibernateDao<SkillGroup, String> implements SkillGroupDao {
    @Override
    public SkillGroup getSkillGroupByCode(String code) throws PersistenceException {
        Criteria c = getSession().createCriteria(SkillGroup.class);
        c.add(Restrictions.eq("code", code));

        return (SkillGroup) c.uniqueResult();
    }

    @Override
    public List<SkillGroup> getSkillGroups(String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(SkillGroup.class);

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
}
