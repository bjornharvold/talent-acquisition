package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.DegreeDao;
import com.tps.tpi.model.objects.entity.Degree;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:55:17 PM
 * Responsibility:
 */
@Repository("degreeDao")
public class DegreeDaoImpl extends AbstractHibernateDao<Degree, String> implements DegreeDao {
}
