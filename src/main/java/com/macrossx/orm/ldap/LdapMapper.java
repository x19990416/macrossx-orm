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
package com.macrossx.orm.ldap;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class LdapMapper<E> implements IMapper<E> {
	@Inject
	@Named("provider.url")
	private String providerUrl;

	@Inject
	@Named("root")
	private String root;

	@Inject
	@Named("security.authentication")
	private String securityAuthentication;

	@Inject
	@Named("security.principal")
	private String securityPrincipal;

	@Inject
	@Named("security.credentials")
	private String securityCredentials;

	private Hashtable<String, String> env = new Hashtable<String, String>();

	public LdapMapper() {
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		System.out.println("xxx====>"+providerUrl);
		env.put(Context.PROVIDER_URL, providerUrl + root);
		env.put(Context.SECURITY_AUTHENTICATION, securityAuthentication);
		env.put(Context.SECURITY_PRINCIPAL, securityPrincipal);
		env.put(Context.SECURITY_CREDENTIALS, securityCredentials);

	}

	@Override
	public List<E> query(GeneratedCriteria criteria, Class<E> clazz) {
		String baseDN = criteria.getCriteria().get("baseDN");
		String condition = criteria.getCriteria().get("condition");
		DirContext ctx = null;
		try {
			ctx = new InitialDirContext(env);

			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration<SearchResult> en = ctx.search(baseDN, condition, constraints);
			List list = new ArrayList();
			while (en != null && en.hasMoreElements()) {
				Object obj = en.nextElement();
				if (obj instanceof SearchResult) {
					SearchResult si = (SearchResult) obj;
					Attributes attrs = si.getAttributes();
					list.add(new AttributesMapper() {
						public Object mapFromAttributes(Attributes arg0) throws NamingException {
							try {
								Object o = clazz.newInstance();
								converAttsToModel(o, arg0);
								return o;
							} catch (InstantiationException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
							return null;
						}
					}.mapFromAttributes(attrs));
				}
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ctx != null)
				try {
					ctx.close();
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return null;
	}

	private <T> void converAttsToModel(T reader, Attributes attrs) {
		Class readerClass = reader.getClass();
		Map methodSetMap = new HashMap();
		Class[] classes = null;
		if (readerClass.getSuperclass().getName().equals("java.lang.Object")) {
			classes = new Class[] { readerClass };
		} else {
			classes = new Class[] { readerClass, readerClass.getSuperclass() };
		}

		// 收集set方法
		for (Class clazz : classes) {
			for (Method method : clazz.getDeclaredMethods()) {
				if (method.getName().startsWith("set")) {
					String fieldName = method.getName().substring(3);
					fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1, fieldName.length());
					methodSetMap.put(fieldName, method);
				}
			}

		}

		// 遍历set方法
		for (Iterator iterator = methodSetMap.keySet().iterator(); iterator.hasNext();) {
			String fieldName = (String) iterator.next();
			Method method = (Method) methodSetMap.get(fieldName);
			if (method != null) {
				Class paramCalss = method.getParameterTypes()[0];

				Attribute o = attrs.get(fieldName);
				if (o == null)
					continue;

				try {
					if (paramCalss.getName().equals("java.lang.String")) {
						if (o.get() instanceof String) {
							method.invoke(reader, new Object[] { (String) o.get() });
						} else if (o.get() instanceof byte[]) {
							method.invoke(reader, new Object[] { new String((byte[]) o.get()) });
						} else {
							System.out.println("error");
						}
					} else {
						List<String> valuesList = new ArrayList<String>();
						for (int i = 0; i < o.size(); i++) {
							valuesList.add((String) o.get(i));
						}
						method.invoke(reader, new Object[] { valuesList });
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

	}

}
