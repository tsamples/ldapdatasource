package com.tobysamples.ldap.datasource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import com.ibm.xsp.FacesExceptionEx;
import com.ibm.xsp.model.AbstractDataSource;
import com.ibm.xsp.model.DataContainer;
import com.ibm.xsp.model.DataSource;

public class LdapDataSource extends AbstractDataSource implements DataSource {

	private String  query;
	private String  domain;
	private String username;
	private String pass;
	private String searchRoot;
	private List<String> fields;
	private String ldapUrl;
	
	public String getLdapUrl() {
		if (ldapUrl != null) {
			return ldapUrl;
		}

		ValueBinding valueBinding = getValueBinding("ldapUrl");
		if (valueBinding != null) {
			return (String) valueBinding.getValue(FacesContext.getCurrentInstance());
		}
		return null;
	}

	public void setLdapUrl(String ldapUrl) {
		this.ldapUrl = ldapUrl;
	}

	public String getSearchRoot() {
		if (searchRoot != null) {
			return searchRoot;
		}

		ValueBinding valueBinding = getValueBinding("searchRoot");
		if (valueBinding != null) {
			return (String) valueBinding.getValue(FacesContext.getCurrentInstance());
		}
		return null;
	}

	public void setSearchRoot(String searchRoot) {
		this.searchRoot = searchRoot;
	}

	public List<String> getFields() {
		if (fields != null) {
			return fields;
		}

		ValueBinding valueBinding = getValueBinding("fields");
		if (valueBinding != null) {
			return (List<String>) valueBinding.getValue(FacesContext.getCurrentInstance());
		}
		fields = new ArrayList<String>();
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	@Override
	protected String composeUniqueId() {
		return getClass().getName();
	}

	@Override
	public Object getDataObject() {
		return ((LdapDataContainer)getDataContainer()).getLdapDataAsList();
	}

	@Override
	public boolean isReadonly() {
		return true;
	}

	@Override
	public DataContainer load(FacesContext context) throws IOException {
		return new LdapDataContainer(getBeanId(), getUniqueId(), this.getQuery(), 
				                    this.getDomain(), this.getUsername(), 
				                    this.getPass(), this.getSearchRoot(), this.getFields(), this.getLdapUrl() );
	}


	@Override
	public boolean save(FacesContext arg0, DataContainer arg1)
			throws FacesExceptionEx {
		return false;
	}

        
    public Object saveState(FacesContext context) {
        if (isTransient()) {
            return null;
        }
        Object[] state = new Object[8];
        state[0] = super.saveState(context);
        state[1] = query;
        state[2] = domain;
        state[3] = username;
        state[4] = pass;
        state[5] = searchRoot;
        state[6] = fields;
        state[7] = ldapUrl;
        return state;
    }


    public void restoreState(FacesContext context, Object state) {
        Object[] values = (Object[])state;
        super.restoreState(context, values[0]);
        query = (String)values[1];
        domain  = (String)values[2];
        username     = (String)values[3];
        pass = (String)values[4];
        searchRoot = (String)values[5];
        fields = (List<String>)values[6];
        ldapUrl = (String)values[7];
    }

	public String getQuery() {
		if (query != null) {
			return query;
		}

		ValueBinding valueBinding = getValueBinding("query");
		if (valueBinding != null) {
			return (String) valueBinding.getValue(FacesContext.getCurrentInstance());
		}
		return null;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getDomain() {
		if (domain != null) {
			return domain;
		}

		ValueBinding valueBinding = getValueBinding("domain");
		if (valueBinding != null) {
			return (String) valueBinding.getValue(FacesContext.getCurrentInstance());
		}
		return null;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUsername() {
		if (username != null) {
			return username;
		}

		ValueBinding valueBinding = getValueBinding("username");
		if (valueBinding != null) {
			return (String) valueBinding.getValue(FacesContext.getCurrentInstance());
		}
		return null;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPass() {
		if (pass != null) {
			return pass;
		}

		ValueBinding valueBinding = getValueBinding("pass");
		if (valueBinding != null) {
			return (String) valueBinding.getValue(FacesContext.getCurrentInstance());
		}
		return null;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public void readRequestParams(FacesContext arg0, Map<String, Object> arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
