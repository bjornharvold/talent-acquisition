package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.BiographySkillDao;
import com.tps.tpi.model.objects.entity.BiographySkill;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:50:18 PM
 * Responsibility:
 */
@Repository("biographySkillDao")
public class BiographySkillDaoImpl extends AbstractHibernateDao<BiographySkill, String> implements BiographySkillDao {
}
