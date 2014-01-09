package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Education;

/**
 * User: Bjorn Harvold
 * Date: Oct 5, 2009
 * Time: 10:48:30 AM
 * Responsibility:
 */
public interface EducationDao extends GenericDao<Education, String> {
    Education getEducation(String personId) throws PersistenceException;
}
