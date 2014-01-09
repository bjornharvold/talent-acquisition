/*
 * Copyright (c) 2008, Health XCEL Inc.. All Rights Reserved.
 */

package com.tps.tpi.datacreator;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: bjorn
 * Date: Nov 4, 2007
 * Time: 11:17:25 AM
 */
public interface DataCreator {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void create() throws DataCreatorException;
 
}
