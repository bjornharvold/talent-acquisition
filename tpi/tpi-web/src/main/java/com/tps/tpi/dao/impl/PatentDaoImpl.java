package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.PatentDao;
import com.tps.tpi.model.objects.entity.Patent;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:47:54 PM
 * Responsibility:
 */
@Repository
public class PatentDaoImpl extends AbstractHibernateDao<Patent, String> implements PatentDao {
}
