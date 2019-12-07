package es.tiwadmin.info;

import java.util.*;

public class InformationProperties {

	private static String strServer;
	
	private static String strPort;

	private static String strUser;
	
	private static String strPassword;
	
	private static String strDataSource;
	
	private static String strClassDriver;
	
	private static String strDatabaseName;
	
	private static String strQCF;
	
	private static String strQueue;
	
	private static String strQueueAsincrona;

	private static final String nameProperties = "AppInformation";



	public static String getStrServer() {
		
		if (strServer == null)
			loadProperties();
		return strServer;
	}

	public static String getStrPort() {
		if (strPort == null)
			loadProperties();
		return strPort;
	}

	public static String getStrDatabaseName() {
		if (strDatabaseName == null)
			loadProperties();
		return strDatabaseName;
	}
	
	public static String getStrUser() {
		if (strUser == null)
			loadProperties();
		return strUser;
	}

	public static String getStrPassword() {
		if (strPassword == null)
			loadProperties();
		return strPassword;
	}

	public static String getStrDataSource() {
		if (strDataSource == null)
			loadProperties();
		return strDataSource;
	}

	public static String getStrClassDriver() {
		if (strClassDriver == null)
			loadProperties();
		return strClassDriver;
	}

	public static String getQCF() {
		if (strQCF == null)
			loadProperties();
		return strQCF;
	}
	public static String getQueue() {
		if (strQueue == null)
			loadProperties();
		return strQueue;
	}

	public static String getQueueAsincrona() {
		if (strQueueAsincrona == null)
			loadProperties();
		return strQueueAsincrona;
	}
	
	// **************************************************
	private static void loadProperties() throws MissingResourceException {

		PropertyResourceBundle appProperties = null;

		try {

			appProperties = (PropertyResourceBundle) PropertyResourceBundle.getBundle(nameProperties);

			strServer = appProperties.getString("Info.strServer");
			strPort = appProperties.getString("Info.strPort");
			strUser = appProperties.getString("Info.strUser");
			strPassword = appProperties.getString("Info.strPassword");
			strDataSource = appProperties.getString("Info.strDataSource");
			strClassDriver = appProperties.getString("Info.strClassDriver");
			strDatabaseName = appProperties.getString("Info.strDatabaseName");
			strQCF = appProperties.getString("Info.strGCF");
			strQueue = appProperties.getString("Info.strQueue");
			//strQueueAsincrona = appProperties.getString("strQueueAsincrona");
					
		} catch (MissingResourceException e) {

			throw e;
		}

	}
}