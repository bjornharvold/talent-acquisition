package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.SkillDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Skill;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:46:45 PM
 * Responsibility:
 */
@Repository("skillDao")
public class SkillDaoImpl extends AbstractHibernateDao<Skill, String> implements SkillDao {
    @Override
    public Skill getSkillByCode(String code) throws PersistenceException {
        Criteria c = getSession().createCriteria(Skill.class);
        c.add(Restrictions.eq("code", code));

        return (Skill) c.uniqueResult();
    }

    @Override
    public List<Skill> getSkills(String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(Skill.class);

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