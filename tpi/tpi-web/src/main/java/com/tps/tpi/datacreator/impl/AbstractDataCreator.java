package com.tps.tpi.datacreator.impl;

import com.tps.tpi.datacreator.DataCreator;
import com.tps.tpi.datacreator.DataCreatorException;

/**
 * User: bjorn
 * Date: Aug 21, 2008
 * Time: 3:05:40 PM
 */
public abstract class AbstractDataCreator implements DataCreator {

    public abstract void create() throws DataCreatorException;

}
