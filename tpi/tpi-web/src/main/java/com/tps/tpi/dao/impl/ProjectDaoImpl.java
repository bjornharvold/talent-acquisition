package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.ProjectDao;
import com.tps.tpi.model.objects.entity.Project;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:53:16 PM
 * Responsibility:
 */
@Repository("projectDao")
public class ProjectDaoImpl extends AbstractHibernateDao<Project, String> implements ProjectDao {
}
