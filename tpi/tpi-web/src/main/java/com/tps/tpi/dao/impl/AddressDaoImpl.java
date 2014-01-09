package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.AddressDao;
import com.tps.tpi.model.objects.entity.Address;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 12:24:27 PM
 * Responsibility:
 */
@Repository("addressDao")
public class AddressDaoImpl extends AbstractHibernateDao<Address, String> implements AddressDao {
}
