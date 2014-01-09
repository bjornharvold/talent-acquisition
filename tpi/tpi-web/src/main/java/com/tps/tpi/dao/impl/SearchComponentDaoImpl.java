package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.SearchComponentDao;
import com.tps.tpi.model.objects.entity.SearchComponent;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Dec 31, 2009
 * Time: 8:18:26 PM
 * Responsibility:
 */
@Repository("searchComponentDao")
public class SearchComponentDaoImpl extends AbstractHibernateDao<SearchComponent, String> implements SearchComponentDao{
}
