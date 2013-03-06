package com.tobysamples.ldap.datasource.library;


import com.ibm.xsp.library.AbstractXspLibrary;
import com.tobysamples.ldap.datasource.Activator;

public class LdapLibrary extends AbstractXspLibrary {


	public LdapLibrary() {

	}

	public String getLibraryId() {
		return LdapLibrary.class.getName();
	}

	public String getPluginId() {
		return Activator.PLUGIN_ID;
	}
	@Override
	public String[] getXspConfigFiles() {
		String[] files = new String[] { "META-INF/ldapDataSource.xsp-config"};

		return files;
	}

}
