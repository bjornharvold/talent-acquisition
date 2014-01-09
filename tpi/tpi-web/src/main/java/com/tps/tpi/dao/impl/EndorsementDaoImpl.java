package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.EndorsementDao;
import com.tps.tpi.model.objects.entity.Endorsement;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 21, 2009
 * Time: 12:02:11 PM
 * Responsibility:
 */
@Repository("endorsementDao")
public class EndorsementDaoImpl extends AbstractHibernateDao<Endorsement, String> implements EndorsementDao {
}
