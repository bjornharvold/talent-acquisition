package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.PersonAddressDao;
import com.tps.tpi.model.objects.entity.PersonAddress;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 16, 2009
 * Time: 3:16:00 PM
 * Responsibility:
 */
@Repository("personAddressDao")
public class PersonAddressDaoImpl extends AbstractHibernateDao<PersonAddress, String> implements PersonAddressDao {
}
