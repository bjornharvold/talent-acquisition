package com.tps.tpi.dao.impl;

import com.tps.tpi.dao.LanguageDao;
import com.tps.tpi.model.objects.entity.Language;
import org.springframework.stereotype.Repository;

/**
 * User: Bjorn Harvold
 * Date: Oct 6, 2009
 * Time: 3:56:08 PM
 * Responsibility:
 */
@Repository("languageDao")
public class LanguageDaoImpl extends AbstractHibernateDao<Language, String> implements LanguageDao {
}
