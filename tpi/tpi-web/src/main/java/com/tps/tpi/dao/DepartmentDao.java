package com.tps.tpi.dao;

import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Department;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:56:50 PM
 * Responsibility:
 */
public interface DepartmentDao extends GenericDao<Department, String> {
    List<Department> getDepartments(String companyId, String name, Integer index, Integer maxResults) throws PersistenceException;
    Department getDepartmentByName(String divisionId, String name) throws PersistenceException;
    Department getDepartmentByNameandCompanyId(String companyId, String dept) throws PersistenceException;
    Department getDepartment(String departmentName, String companyId, String divisionName) throws PersistenceException;
}