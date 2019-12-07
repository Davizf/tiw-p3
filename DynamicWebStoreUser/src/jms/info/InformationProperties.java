package jms.info;

import java.util.*;

public class InformationProperties {

	private static String strQCF;

	private static String strQueue;

	private static String strQueueAsincrona;

	private static final String nombreProperties = "AppInformation";

	// **************************************************
	public InformationProperties() {
		super();
	}

	// **************************************************
	public static String getQCF() {

		if (strQCF == null)
			cagarProperties();

		return strQCF;
	}

	// **************************************************
	public static String getQueue() {

		if (strQueue == null)
			cagarProperties();

		return strQueue;
	}

	// **************************************************
		public static String getQueueAsincrona() {

			if (strQueueAsincrona == null)
				cagarProperties();

			return strQueueAsincrona;
		}

	// **************************************************
	private static void cagarProperties() throws MissingResourceException {

		PropertyResourceBundle appProperties = null;

		try {

			appProperties = (PropertyResourceBundle) PropertyResourceBundle
					.getBundle(nombreProperties);

			strQCF = appProperties.getString("Info.strQCF");
			strQueue = appProperties.getString("Info.strQueue");
			strQueueAsincrona = appProperties.getString("Info.strQueueAsincrona");

		} catch (MissingResourceException e) {

			throw e;
		}

	}
}