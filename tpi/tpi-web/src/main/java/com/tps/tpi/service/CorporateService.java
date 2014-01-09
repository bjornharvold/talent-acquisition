package com.tps.tpi.service;

import com.tps.tpi.model.objects.entity.*;
import com.tps.tpi.exception.DomainException;
import org.springframework.security.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 13, 2009
 * Time: 11:59:52 AM
 * Responsibility:
 */
@Secured("ROLE_USER")
public interface CorporateService {
    Company getCompanyByShortName(String shortName) throws DomainException;

    Company getCompanyByCode(String code) throws DomainException;

    @Transactional
    Company saveCompany(Company company) throws DomainException;

    @Transactional
    Company updateCompany(Company company) throws DomainException;

    @Transactional
    CompanyTitle saveCompanyTitle(CompanyTitle title) throws DomainException;

    @Transactional
    CompanyTitle updateCompanyTitle(CompanyTitle title) throws DomainException;

    @Transactional
    void deleteCompanyTitle(String id) throws DomainException;

    CompanyTitle getCompanyTitleByCode(String companyId, String code) throws DomainException;
    
    @Transactional
    Division saveDivision(Division division) throws DomainException;
    
    @Transactional
    Division updateDivision(Division division) throws DomainException;
    
    @Transactional
    void deleteDivision(String id) throws DomainException;

    List<Division> getDivisions(String companyId, String name, Integer index, Integer maxResults) throws DomainException;

    Division getDivisionByName(String companyId, String code) throws DomainException;
    
    @Transactional
    Department saveDepartment(Department dept) throws DomainException;
    
    @Transactional
    Department updateDepartment(Department dept) throws DomainException;
    
    @Transactional
    void deleteDepartment(String id) throws DomainException;

    List<Department> getDepartments(String companyId, String name, Integer index, Integer maxResults) throws DomainException;

    Department getDepartmentByName(String divisionId, String code) throws DomainException;

    Department getDepartmentByNameAndCompanyId(String deptS, String companyId) throws DomainException;

    Department getDepartment(String departmentName, String companyId, String divisionName) throws DomainException;
    
    @Transactional
    Product saveProduct(Product prod) throws DomainException;
    
    @Transactional
    Product updateProduct(Product prod) throws DomainException;
    
    @Transactional
    void deleteProduct(String id) throws DomainException;

    List<Product> getProducts(String companyId, String name, Integer index, Integer maxResults) throws DomainException;

    Product getProductByName(String companyId, String name) throws DomainException;

    CompanyIndustry getCompanyIndustryByCode(String companyId, String code) throws DomainException;

    CompanyIndustry saveCompanyIndustry(CompanyIndustry ci) throws DomainException;
}
