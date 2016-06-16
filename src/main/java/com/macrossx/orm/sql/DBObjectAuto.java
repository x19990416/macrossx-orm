package com.macrossx.orm.sql;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 基于属性与表列名的ormapping
 * 属性必须小写且与列名相同
 * 
 * @author lmguo
 *
 */
public abstract class DBObjectAuto implements DBConvertor {

	@Override
	public void convert(ResultSet result) {
		try {
			int count = result.getMetaData().getColumnCount();
			String[] name = new String[count];
			for (int i = 0; i < count; i++) {
				name[i] = result.getMetaData().getColumnLabel(i + 1);
				StringBuilder sb = new StringBuilder(result.getMetaData()
						.getColumnLabel(i + 1).toLowerCase());
				sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
				String prop = sb.toString();
				Field[] fields = this.getClass().getDeclaredFields();
				boolean flag = false;
				for (Field f : fields) {
					if (f.getName().equals(prop.toLowerCase())) {
						flag = true;
						break;
					}
				}
				if (!flag)
					continue;
				this.getClass().getMethod("set"+prop, String.class).invoke(this, result.getString(i+1));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
