package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.BiographyDepartmentDao;
import com.tps.tpi.model.objects.entity.BiographyDepartment;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 18, 2009
 * Time: 1:07:09 PM
 * Responsibility:
 */
@Repository("biographyDepartmentDao")
public class BiographyDepartmentDaoImpl extends AbstractHibernateDao<BiographyDepartment, String> implements BiographyDepartmentDao {
}
