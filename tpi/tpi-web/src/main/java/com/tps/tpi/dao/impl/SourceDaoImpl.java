package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.SourceDao;
import com.tps.tpi.model.objects.entity.Source;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * User: Bjorn Harvold
 * Date: Dec 12, 2009
 * Time: 12:43:01 AM
 * Responsibility:
 */
@Repository("sourceDao")
public class SourceDaoImpl extends AbstractHibernateDao<Source, String> implements SourceDao {
}
