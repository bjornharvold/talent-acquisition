package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.InterestDao;
import com.tps.tpi.model.objects.entity.Interest;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 16, 2009
 * Time: 3:59:41 PM
 * Responsibility:
 */
@Repository("interestDao")
public class InterestDaoImpl extends AbstractHibernateDao<Interest, String> implements InterestDao {
}
