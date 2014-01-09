/*
 * Copyright (c) 2009. All rights reserved. Bjorn Harvold.
 */

package com.tps.tpi.hibernate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.cfg.EJB3NamingStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Bjorn Harvold
 * Date: Apr 20, 2007
 * Time: 10:13:34 AM
 */
public class NamingStrategy extends EJB3NamingStrategy {
    private final static Logger log = LoggerFactory.getLogger(NamingStrategy.class);
    private Map<String, String> standardProperties;

    public static void main(String[] args) {
        NamingStrategy ns = new NamingStrategy();
        System.out.println(ns.classToTableName("UserCategory"));
        System.out.println(ns.columnName("user"));
        System.out.println(ns.propertyToColumnName("UserCategory"));
    }

    public NamingStrategy() {
        super();
        initializeStandardProperties();
    }

    private void initializeStandardProperties() {
		standardProperties = new HashMap<String, String>();
		standardProperties.put("user", "zuser");
		standardProperties.put("group", "zgroup");
		standardProperties.put("role", "zrole");
		standardProperties.put("status", "zstatus");
		standardProperties.put("version", "zversion");
		standardProperties.put("name", "zname");
		standardProperties.put("column", "ccolumn");
		standardProperties.put("key", "kkey");
		standardProperties.put("primary", "pprimary");
		standardProperties.put("table", "ttable");
		standardProperties.put("limit", "llimit");
		standardProperties.put("default", "ddefault");
		standardProperties.put("from", "ffrom");
		standardProperties.put("to", "tto");
	}

    public String classToTableName(String className) {
        StringBuilder sb = new StringBuilder(tablePrefix);
        sb.append("_");
        sb.append(super.classToTableName(className));

        return sb.toString();
    }

    public String columnName(String columnName) {
        return standardProperties.containsKey(columnName) ?
                standardProperties.get(columnName) :
                super.columnName(columnName);
    }

    /*public String foreignKeyColumnName(String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName) {
        String result =  super.foreignKeyColumnName(propertyName, propertyEntityName, propertyTableName, referencedColumnName);
        return columnName(result);
    }*/

    public String propertyToColumnName(String propertyName) {
        return columnName(propertyName);
    }

    public String tableName(String tableName) {
        StringBuilder sb = new StringBuilder(tablePrefix);
        sb.append("_");
        sb.append(super.tableName(tableName));

        return sb.toString();
    }

    /**
     * Not currently used. It would take a name like relationshipRequest
     * and turn it into relationship_request
     * @param name
     * @return
     */
    private String corporatify(String name) {
		StringBuilder buf = new StringBuilder( name.replace('.', '_') );
		for (int i=1; i<buf.length()-1; i++) {
			if (
				'_'!=buf.charAt(i-1) &&
				Character.isUpperCase( buf.charAt(i) ) &&
				!Character.isUpperCase( buf.charAt(i+1) )
			) {
				buf.insert(i++, '_');
			}
		}
		return buf.toString().toLowerCase();
	}

    // Spring IoC
    private String tablePrefix = "c";

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

}

