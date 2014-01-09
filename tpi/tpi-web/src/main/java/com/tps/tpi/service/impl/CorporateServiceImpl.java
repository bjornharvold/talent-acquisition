package com.tps.tpi.service.impl;

import com.tps.tpi.dao.*;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.*;
import com.tps.tpi.service.CorporateService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 13, 2009
 * Time: 12:02:02 PM
 * Responsibility:
 */
@Service
public class CorporateServiceImpl implements CorporateService {
    private static final Logger log = LoggerFactory.getLogger(CorporateServiceImpl.class);
    private final CompanyDao companyDao;
    private final CompanyIndustryDao companyIndustryDao;
    private final CompanyTitleDao companyTitleDao;
    private final DivisionDao divisionDao;
    private final DepartmentDao departmentDao;
    private final ProductDao productDao;
    private final CoverageDao coverageDao;

    @Autowired
    public CorporateServiceImpl(CompanyDao companyDao, CompanyTitleDao companyTitleDao,
                                DivisionDao divisionDao, DepartmentDao departmentDao,
                                ProductDao productDao, CoverageDao coverageDao, 
                                CompanyIndustryDao companyIndustryDao) {
        this.companyDao = companyDao;
        this.companyTitleDao = companyTitleDao;
        this.divisionDao = divisionDao;
        this.departmentDao = departmentDao;
        this.productDao = productDao;
        this.coverageDao = coverageDao;
        this.companyIndustryDao = companyIndustryDao;
    }

    @Override
    public Company getCompanyByShortName(String shortName) throws DomainException {
        Company result = null;

        if (StringUtils.isBlank(shortName)) {
            throw new DomainException("error.missing.data", "shortName");
        }

        try {
            result = companyDao.getCompanyByShortName(shortName);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Company getCompanyByCode(String code) throws DomainException {
        Company result = null;

        if (StringUtils.isBlank(code)) {
            throw new DomainException("error.missing.data", "code");
        }

        try {
            result = companyDao.getCompanyByCode(code);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Company saveCompany(Company company) throws DomainException {
        if (company == null) {
            throw new DomainException("error.missing.data", "company");
        }

        try {
            company = companyDao.save(company);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return company;
    }

    @Override
    public Company updateCompany(Company company) throws DomainException {
        if (company == null) {
            throw new DomainException("error.missing.data", "company");
        }

        try {
            company = companyDao.update(company);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return company;
    }

    @Override
    public CompanyTitle saveCompanyTitle(CompanyTitle title) throws DomainException {
        if (title == null) {
            throw new DomainException("error.missing.data", "title");
        }

        try {
            title = companyTitleDao.save(title);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return title;
    }

    @Override
    public CompanyTitle updateCompanyTitle(CompanyTitle title) throws DomainException {
        if (title == null) {
            throw new DomainException("error.missing.data", "title");
        }

        try {
            title = companyTitleDao.update(title);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return title;
    }

    @Override
    public void deleteCompanyTitle(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            companyTitleDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public CompanyTitle getCompanyTitleByCode(String companyId, String code) throws DomainException {
        CompanyTitle result = null;

        if (StringUtils.isBlank(companyId)) {
            throw new DomainException("error.missing.data", "companyId");
        }

        if (StringUtils.isBlank(code)) {
            throw new DomainException("error.missing.data", "code");
        }

        try {
            result = companyTitleDao.getCompanyTitleByCode(companyId, code);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Division saveDivision(Division division) throws DomainException {
        if (division == null) {
            throw new DomainException("error.missing.data", "division");
        }

        try {
            division = divisionDao.save(division);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return division;
    }

    @Override
    public Division updateDivision(Division division) throws DomainException {
        if (division == null) {
            throw new DomainException("error.missing.data", "division");
        }

        try {
            division = divisionDao.update(division);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return division;
    }

    @Override
    public void deleteDivision(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            divisionDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<Division> getDivisions(String companyId, String name, Integer index, Integer maxResults) throws DomainException {
        List<Division> result = null;
        
        if (StringUtils.isBlank(companyId)) {
            throw new DomainException("error.missing.data", "companyId");
        }

        try {
            result = divisionDao.getDivisions(companyId, name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
        
        return result;
    }

    @Override
    public Division getDivisionByName(String companyId, String name) throws DomainException {
        Division result = null;
        
        if (StringUtils.isBlank(companyId)) {
            throw new DomainException("error.missing.data", "companyId");
        }
        
        if (StringUtils.isBlank(name)) {
            throw new DomainException("error.missing.data", "name");
        }

        try {
            result = divisionDao.getDivisionByName(companyId, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
        
        return result;
    }

    @Override
    public Department saveDepartment(Department department) throws DomainException {
        if (department == null) {
            throw new DomainException("error.missing.data", "department");
        }

        try {
            department = departmentDao.save(department);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return department;
    }

    @Override
    public Department updateDepartment(Department department) throws DomainException {
        if (department == null) {
            throw new DomainException("error.missing.data", "department");
        }

        try {
            department = departmentDao.update(department);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return department;
    }

    @Override
    public void deleteDepartment(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            departmentDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<Department> getDepartments(String companyId, String name, Integer index, Integer maxResults) throws DomainException {
        List<Department> result = null;

        if (StringUtils.isBlank(companyId)) {
            throw new DomainException("error.missing.data", "companyId");
        }

        try {
            result = departmentDao.getDepartments(companyId, name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Department getDepartmentByName(String divisionId, String name) throws DomainException {
        Department result = null;

        if (StringUtils.isBlank(divisionId)) {
            throw new DomainException("error.missing.data", "companyId");
        }

        if (StringUtils.isBlank(name)) {
            throw new DomainException("error.missing.data", "name");
        }

        try {
            result = departmentDao.getDepartmentByName(divisionId, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Department getDepartmentByNameAndCompanyId(String dept, String companyId) throws DomainException {
        Department result = null;

        if (StringUtils.isBlank(dept)) {
            throw new DomainException("error.missing.data", "dept");
        }

        if (StringUtils.isBlank(companyId)) {
            throw new DomainException("error.missing.data", "companyId");
        }

        try {
            result = departmentDao.getDepartmentByNameandCompanyId(companyId, dept);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Department getDepartment(String departmentName, String companyId, String divisionName) throws DomainException {
        Department result = null;

        if (StringUtils.isBlank(departmentName)) {
            throw new DomainException("error.missing.data", "departmentName");
        }

        if (StringUtils.isBlank(companyId)) {
            throw new DomainException("error.missing.data", "companyId");
        }

        if (StringUtils.isBlank(divisionName)) {
            throw new DomainException("error.missing.data", "divisionName");
        }

        try {
            result = departmentDao.getDepartment(departmentName, companyId, divisionName);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Product saveProduct(Product product) throws DomainException {
        if (product == null) {
            throw new DomainException("error.missing.data", "product");
        }

        try {
            product = productDao.save(product);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return product;
    }

    @Override
    public Product updateProduct(Product product) throws DomainException {
        if (product == null) {
            throw new DomainException("error.missing.data", "product");
        }

        try {
            product = productDao.update(product);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return product;
    }

    @Override
    public void deleteProduct(String id) throws DomainException {
        if (StringUtils.isBlank(id)) {
            throw new DomainException("error.missing.data", "id");
        }

        try {
            productDao.delete(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }
    }

    @Override
    public List<Product> getProducts(String companyId, String name, Integer index, Integer maxResults) throws DomainException {
        List<Product> result = null;

        if (StringUtils.isBlank(companyId)) {
            throw new DomainException("error.missing.data", "companyId");
        }

        try {
            result = productDao.getProducts(companyId, name, index, maxResults);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Product getProductByName(String companyId, String name) throws DomainException {
        Product result = null;

        if (StringUtils.isBlank(companyId)) {
            throw new DomainException("error.missing.data", "companyId");
        }

        if (StringUtils.isBlank(name)) {
            throw new DomainException("error.missing.data", "name");
        }

        try {
            result = productDao.getProductByName(companyId, name);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }

    @Override
    public CompanyIndustry getCompanyIndustryByCode(String companyId, String code) throws DomainException {
        CompanyIndustry result = null;

        if (StringUtils.isBlank(companyId)) {
            throw new DomainException("error.missing.data", "companyId");
        }

        if (StringUtils.isBlank(code)) {
            throw new DomainException("error.missing.data", "code");
        }

        try {
            result = companyIndustryDao.getCompanyIndustryByCode(companyId, code);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return result;
    }
    
    @Override
    public CompanyIndustry saveCompanyIndustry(CompanyIndustry ci) throws DomainException {
        if (ci == null) {
            throw new DomainException("error.missing.data", "ci");
        }

        try {
            ci = companyIndustryDao.save(ci);
        } catch (PersistenceException e) {
            log.error(e.getMessage(), e);
            throw new DomainException(e.getMessage(), e);
        }

        return ci;
    }
}
