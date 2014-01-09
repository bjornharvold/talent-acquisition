package com.tps.tpi.editors;

import com.tps.tpi.dao.RegionDao;
import com.tps.tpi.exception.DomainException;
import com.tps.tpi.exception.PersistenceException;
import com.tps.tpi.model.objects.entity.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

/**
 * User: Bjorn Harvold
 * Date: Dec 23, 2009
 * Time: 3:41:15 AM
 * Responsibility:
 */

@Component
public class RegionEditor extends PropertyEditorSupport {
    private static final Logger log = LoggerFactory.getLogger(RegionEditor.class);
    private SimpleTypeConverter typeConverter = new org.springframework.beans.SimpleTypeConverter();
    private final RegionDao dao;

    @Autowired
    public RegionEditor(RegionDao dao) {
        this.dao = dao;
    }

    public String getAsText() {
        Object obj = getValue();
        if (obj == null) {
            return null;
        }
        return typeConverter.convertIfNecessary(((Region) obj).getId(), String.class);
    }

    public void setAsText(java.lang.String text) {
        if (text == null || 0 == text.length()) {
            setValue(null);
            return;
        }

        String identifier = typeConverter.convertIfNecessary(text, String.class);
        if (identifier == null) {
            setValue(null);
            return;
        }

        try {
            setValue(dao.get(Region.class, identifier));
        } catch (PersistenceException e) {
            log.error(e.getMessage());
        }
    }

}
