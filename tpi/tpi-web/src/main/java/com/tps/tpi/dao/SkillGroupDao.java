package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.SkillGroup;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 9, 2009
 * Time: 3:44:20 PM
 * Responsibility:
 */
public interface SkillGroupDao extends GenericDao<SkillGroup, String> {
    SkillGroup getSkillGroupByCode(String code) throws PersistenceException;
    List<SkillGroup> getSkillGroups(String name, Integer index, Integer maxResults) throws PersistenceException;
}
