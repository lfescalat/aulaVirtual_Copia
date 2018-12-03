/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.aulavirtual.utilities;

import java.io.File;
import java.util.Locale;

/**
 *
 * @author sistem17user
 */
public class OsUtils {

	/**
	 * types of Operating Systems
	 */
	public enum OSType {
		Windows, MacOS, Linux, Other
	};

	// cached result of OS detection
	protected static String detectedOS;

	/**
	 * detect the operating system from the os.name System property and cache the
	 * result
	 *
	 * @return - the operating system detected
	 */
	public static String getOperatingSysstemType() {
		if (detectedOS == null) {
			String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
			if ((OS.contains("mac")) || (OS.contains("darwin"))) {
				detectedOS = "MacOS";
			} else if (OS.contains("win")) {
				detectedOS = "Windows";
			} else if (OS.contains("nux")) {
				detectedOS = "Linux";
			} else {
				detectedOS = "Other";
			}
		}
		return detectedOS;
	}

	public static String getDotEnvPath() {
		String path = "";
		String detectedOs = OsUtils.getOperatingSysstemType();
		switch (detectedOs) {
			case "MacOS":
				path = "";
				break;
			case "Windows":
				char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
				for (char letter : alphabet) {
					path = letter + ":/dotenv/aulavirtual";
					File directory = new File(path);
					if (directory.exists()) {
						break;
					}
				}
				break;
			case "Linux":
				path = "/opt/dotenv/democrud";
				break;
		}
		return path;
	}

}
