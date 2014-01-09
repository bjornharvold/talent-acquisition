package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.PublicationDao;
import com.tps.tpi.model.objects.entity.Publication;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:49:09 PM
 * Responsibility:
 */
@Repository("publicationDao")
public class PublicationDaoImpl extends AbstractHibernateDao<Publication, String> implements PublicationDao {
}
