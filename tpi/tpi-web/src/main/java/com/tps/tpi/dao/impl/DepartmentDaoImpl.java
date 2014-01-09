package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.DepartmentDao;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Department;
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
@Repository("departmentDao")
public class DepartmentDaoImpl extends AbstractHibernateDao<Department, String> implements DepartmentDao {
    @Override
    public List<Department> getDepartments(String companyId, String name, Integer index, Integer maxResults) throws PersistenceException {
        Criteria c = getSession().createCriteria(Department.class);

        c.createAlias("division", "d").createAlias("d.company", "c").add(Restrictions.eq("c.id", companyId));

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
    public Department getDepartmentByName(String divisionId, String name) throws PersistenceException {
        Criteria c = getSession().createCriteria(Department.class);
        c.add(Restrictions.eq("shortName", name));
        c.createAlias("division", "d").add(Restrictions.eq("d.id", divisionId));

        return (Department) c.uniqueResult();
    }

    @Override
    public Department getDepartmentByNameandCompanyId(String companyId, String dept) throws PersistenceException {
        Criteria c = getSession().createCriteria(Department.class);
        c.add(Restrictions.eq("shortName", dept));
        c.createAlias("division", "d").createAlias("d.company", "c").add(Restrictions.eq("c.id", companyId));

        return (Department) c.uniqueResult();
    }

    @Override
    public Department getDepartment(String departmentName, String companyId, String divisionName) throws PersistenceException {
        Criteria c = getSession().createCriteria(Department.class);
        c.add(Restrictions.eq("shortName", departmentName));
        c.createAlias("division", "d").add(Restrictions.eq("d.shortName", divisionName));
        c.createAlias("d.company", "c").add(Restrictions.eq("c.id", companyId));

        return (Department) c.uniqueResult();
    }
}