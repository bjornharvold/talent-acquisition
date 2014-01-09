package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.AwardDao;
import com.tps.tpi.model.objects.entity.Award;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:46:45 PM
 * Responsibility:
 */
@Repository("awardDao")
public class AwardDaoImpl extends AbstractHibernateDao<Award, String> implements AwardDao {
}
