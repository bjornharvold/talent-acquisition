package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.BiographyCompanyTitleDao;
import com.tps.tpi.model.objects.entity.BiographyCompanyTitle;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Dec 18, 2009
 * Time: 2:07:36 AM
 * Responsibility:
 */
@Repository("biographyCompanyTitleDao")
public class BiographyCompanyTitleDaoImpl extends AbstractHibernateDao<BiographyCompanyTitle, String> implements BiographyCompanyTitleDao{
}
