package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.AffiliationDao;
import com.tps.tpi.model.objects.entity.Affiliation;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:46:45 PM
 * Responsibility:
 */
@Repository("affiliationDao")
public class AffiliationDaoImpl extends AbstractHibernateDao<Affiliation, String> implements AffiliationDao {
}