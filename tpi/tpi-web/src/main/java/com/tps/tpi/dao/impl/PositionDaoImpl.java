package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.PositionDao;
import com.tps.tpi.model.objects.entity.Position;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:52:15 PM
 * Responsibility:
 */
@Repository("positionDao")
public class PositionDaoImpl extends AbstractHibernateDao<Position, String> implements PositionDao {
}
