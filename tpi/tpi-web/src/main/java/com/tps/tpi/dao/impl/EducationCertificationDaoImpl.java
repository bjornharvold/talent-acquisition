package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.EducationCertificationDao;
import com.tps.tpi.model.objects.entity.EducationCertification;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:54:17 PM
 * Responsibility:
 */
@Repository("educationCertificationDao")
public class EducationCertificationDaoImpl extends AbstractHibernateDao<EducationCertification, String> implements EducationCertificationDao {
}
