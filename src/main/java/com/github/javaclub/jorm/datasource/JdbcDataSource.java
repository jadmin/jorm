/*
 * @(#)JdbcDataSource.java	2011-08-06
 *
 * Copyright (c) 2010 by gerald. All Rights Reserved.
 */

package com.github.javaclub.jorm.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDriverBasedDataSource;

import com.github.javaclub.jorm.Environment;
import com.github.javaclub.jorm.config.JdbcConfigXmlParser;

/**
 * A custom jdbc datasource that implements <code>javax.sql.DataSource</code>
 *
 * @author <a href="mailto:gerald.chen@qq.com">Gerald Chen</a>
 * @version $Id: JdbcDataSource.java 217 2011-08-06 14:06:47Z gerald.chen.hz@gmail.com $
 */
public class JdbcDataSource extends AbstractDriverBasedDataSource implements InitializingBean {
	
	public JdbcDataSource() {
	}
	
	public void afterPropertiesSet() throws Exception {
		String defaultConnectionName = JdbcConfigXmlParser.getDefaultConnectionName();
		Properties jdbcPropertity = JdbcConfigXmlParser.getJdbcPropertity(defaultConnectionName);
		jdbcPropertity.setProperty(Environment.PROVIDER, defaultConnectionName);
		setUrl(jdbcPropertity.getProperty(Environment.JDBC_URL));
		setUsername(jdbcPropertity.getProperty(Environment.USERNAME));
		setPassword(jdbcPropertity.getProperty(Environment.PASSWORD));
	}

	protected Connection getConnectionFromDriver(Properties props) throws SQLException {
		String url = getUrl();
		if (logger.isDebugEnabled()) {
			logger.debug("Creating new JDBC DriverManager Connection to [" + url + "]");
		}
		return getConnectionFromDriverManager(url, props);
	}

	/**
	 * Getting a Connection using the nasty static from DriverManager is extracted
	 * into a protected method to allow for easy unit testing.
	 * @see java.sql.DriverManager#getConnection(String, java.util.Properties)
	 */
	protected Connection getConnectionFromDriverManager(String url, Properties props) throws SQLException {
		return DriverManager.getConnection(url, props);
	}

}
