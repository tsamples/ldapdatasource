package com.tobysamples.ldap.datasource;

import java.io.Serializable;
import java.util.HashMap;

import com.ibm.xsp.model.DataObject;

public class LdapRowData implements DataObject, Serializable {

	private static final long serialVersionUID = 1L;

	private HashMap<String,Object> properties;
	
	public LdapRowData() {
	}

	public Class<?> getType(Object key) {
		if(properties!=null) {
			Object o = properties.get(key);
			if(o!=null) {
				return o.getClass();
			}
		}
		return null;
	}

	public Object getValue(Object key) {
		if(properties!=null) {
			if (key instanceof Integer) { key = key.toString(); }
			if (key instanceof Long) { key = key.toString(); }
			return properties.get((String)key);
		}
		return null;
	}

	public boolean isReadOnly(Object key) {
		return true;
	}

	public void setValue(Object key, Object value) {
		if(properties==null) {
			properties = new HashMap<String,Object>();
		}
		properties.put((String)key,value);
	}

}
