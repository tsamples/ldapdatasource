package com.tobysamples.ldap.datasource;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import com.ibm.xsp.model.AbstractDataContainer;

public class LdapDataContainer extends AbstractDataContainer {

	private static Logger logger = Logger.getLogger(LdapDataContainer.class.getName());
	private List<LdapRowData> data;
	private String  query;
	private String  domain;
	private String username;
	private String pass;
	private String searchRoot;
	private List<String> fields;
	private String ldapUrl;
	
	public String getLdapUrl() {
		return ldapUrl;
	}

	public void setLdapUrl(String ldapUrl) {
		this.ldapUrl = ldapUrl;
	}

	public String getSearchRoot() {
		return searchRoot;
	}

	public void setSearchRoot(String searchRoot) {
		this.searchRoot = searchRoot;
	}

	public LdapDataContainer(){
	}
	
	public LdapDataContainer(String beanId, String uniqueId, String query,
							String domain, String username, 
							String pass, String searchRoot, List<String> fields, String ldapUrl) {
		super(beanId, uniqueId);
		this.query = query;
		this.domain = domain;
		this.username = username;
		this.pass = pass;
		this.searchRoot = searchRoot;
		this.fields = fields;
		this.ldapUrl = ldapUrl;
	}

	public void deserialize(ObjectInput in) throws IOException {
		this.setDomain(readUTF(in));
		this.setQuery(readUTF(in));
		this.setUsername(readUTF(in));
		this.setPass(readUTF(in));
		this.setLdapUrl(readUTF(in));
		try {
			fields = (List<String>)in.readObject();
			data = (List<LdapRowData>)in.readObject();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void serialize(ObjectOutput out) throws IOException {
		writeUTF(out, getQuery());
		writeUTF(out, getDomain());
		writeUTF(out, getUsername());
		writeUTF(out, getPass());
		writeUTF(out, getSearchRoot());
		out.writeObject(getFields());
		out.writeObject(data);
	}

	public List<?> getLdapDataAsList(){
		if (data == null){
			data = getData();
		}
		return data;
	}
	
	protected List<LdapRowData> getData() {
		List entries = new ArrayList();
		try {
			entries = authenticate(this.getUsername(), this.getPass(), this.getDomain(),  this.getSearchRoot(), this.getQuery(),this.getFields().toArray(new String[this.getFields().size()]), this.getLdapUrl());
		} catch(Exception e) {
			logger.log(Level.SEVERE, "Error getting List from LDAP", e );
		}

		return entries;
		
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public List<String> getFields() {
		return fields;
	}

	private static List<Map<String, Object>> authenticate(String user, String pass, String domain, String searchRoot, String searchFilter, String[] attributes, String ldapUrl)
	  {
		
	    String returnedAtts[] = attributes;
	    //Create the search controls
	    SearchControls searchCtls = new SearchControls();
	    searchCtls.setReturningAttributes(returnedAtts);

	    //Specify the search scope
	    searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

	    Hashtable env = new Hashtable();
	    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	    env.put(Context.PROVIDER_URL, "ldap://" + ldapUrl);
	    env.put(Context.SECURITY_AUTHENTICATION, "simple");
	    env.put(Context.SECURITY_PRINCIPAL, user + "@" + domain);
	    env.put(Context.SECURITY_CREDENTIALS, pass);

	    LdapContext ctxGC = null;
	    List entries = new ArrayList();
	    try
	    {
	      ctxGC = new InitialLdapContext(env, null);
	      //Search objects in GC using filters
	      NamingEnumeration answer = ctxGC.search(searchRoot, searchFilter, searchCtls);
	      while (answer.hasMoreElements())
	      {
	        SearchResult sr = (SearchResult) answer.next();
	        Attributes attrs = sr.getAttributes();
	        //Map amap = null;
	        LdapRowData lrd = null;
	        if (attrs != null)
	        {
	          lrd = new LdapRowData();
	          NamingEnumeration ne = attrs.getAll();
	          while (ne.hasMore())
	          {
	            Attribute attr = (Attribute) ne.next();
	            lrd.setValue(attr.getID(), attr.get());
	          }
	          ne.close();
	        }
	          entries.add(lrd);
	      }
	      return entries;
	    }
	    catch (NamingException ex)
	    {
	      ex.printStackTrace();
	    }

	    return null;
	  }
	
	
}
