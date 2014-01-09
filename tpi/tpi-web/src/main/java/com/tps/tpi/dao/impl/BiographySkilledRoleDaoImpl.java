package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.BiographySkilledRoleDao;
import com.tps.tpi.model.objects.entity.BiographySkilledRole;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 18, 2009
 * Time: 1:33:54 PM
 * Responsibility:
 */
@Repository("biographySkilledRoleDao")
public class BiographySkilledRoleDaoImpl extends AbstractHibernateDao<BiographySkilledRole, String> implements BiographySkilledRoleDao {
}
