package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Skill;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:56:50 PM
 * Responsibility:
 */
public interface SkillDao extends GenericDao<Skill, String> {
    Skill getSkillByCode(String code) throws PersistenceException;
    List<Skill> getSkills(String name, Integer index, Integer maxResults) throws PersistenceException;
}