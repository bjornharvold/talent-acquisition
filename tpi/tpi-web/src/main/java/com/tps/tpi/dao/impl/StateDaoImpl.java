package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.StateDao;
import com.tps.tpi.model.objects.entity.State;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:46:45 PM
 * Responsibility:
 */
@Repository
public class StateDaoImpl extends AbstractHibernateDao<State, String> implements StateDao {
}