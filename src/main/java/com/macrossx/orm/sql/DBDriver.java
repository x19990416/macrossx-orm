/**
 * Copyright (C) 2016 X-Forever.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.macrossx.orm.sql;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.google.common.collect.Lists;

public class DBDriver {
	private String dbUrl;
	private String dbDriverName;
	private Connection conActive;
	private Statement stmtActive;

	public DBDriver() {

	}

	public DBDriver(String dbDriverName, String dburl) {
		this.dbDriverName = dbDriverName;
		this.dbUrl = dburl;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbDriverName() {
		return dbDriverName;
	}

	public void setDbDriverName(String dbDriverName) {
		this.dbDriverName = dbDriverName;
	}

	public void init() {
		try {
			Class.forName(dbDriverName);
			conActive = DriverManager.getConnection(dbUrl);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public <T> List<T> executeQuery(String sSQL, Class<T> clazz) throws SQLException {
		this.openStatement();
		List<T> list = Lists.newArrayList();
		try {

			ResultSet rsQuery = stmtActive.executeQuery(sSQL);
			while (rsQuery.next()) {
				Object o = clazz.newInstance();
				clazz.getMethod("convert", java.sql.ResultSet.class).invoke(o, rsQuery);
				list.add((T) o);
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException | InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeStatment();
		}
		return list;
	}

	public int executeUpdate(String sSQL) throws SQLException {
		return stmtActive.executeUpdate(sSQL);
	}

	public void closeStatment() throws SQLException {
		stmtActive.close();
	}

	public void openStatement() throws SQLException {
		stmtActive = conActive.createStatement();

	}

	public void connect() {
		try {
			if (conActive.isClosed())
				conActive = DriverManager.getConnection(dbUrl);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void disconnect() {
		try {
			conActive.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
