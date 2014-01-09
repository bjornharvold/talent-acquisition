package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.BiographyCityDao;
import com.tps.tpi.model.objects.entity.BiographyCity;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 18, 2009
 * Time: 12:55:09 PM
 * Responsibility:
 */
@Repository("biographyCityDao")
public class BiographyCityDaoImpl extends AbstractHibernateDao<BiographyCity, String> implements BiographyCityDao {
}
