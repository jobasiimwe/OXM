/**
 * 
 */
package org.agric.oxm.server.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Job
 * 
 *         An Example Class to use for the submission using HTTP API You can
 *         perform your own validations into this Class For username,
 *         password,destination, source, dlr, type, message, server and port
 **/
public class Sender {

	/**
	 * Destinations to which message is to be sent For submitting more than one
	 * * destination at once destinations should be comma separated Like
	 * 91999000123,91999000124
	 */
	private String username = "ugd-rweb", password = "jar123", message,
			destination, source;
	// private String server;
	/**
	 * What type of the message that is to be sent
	 * <ul>
	 * <li>0:means plain text</li>
	 * <li>1:means flash</li>
	 * <li>2:means Unicode (Message content should be in Hex)</li>
	 * <li>6:means Unicode Flash (Message content should be in Hex)</li>
	 * </ul>
	 */
	// private String type;
	/**
	 * Require DLR or not
	 * <ul>
	 * <li>0:means DLR is not Required</li>
	 * <li>1:means DLR is Required</li>
	 * </ul>
	 */
	// private String dlr;

	// private int port;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public Sender(String server, int port, String username, String password,
			String message, String dlr, String type, String destination,
			String source) {
		this.username = username;
		this.password = password;
		this.message = message;
		// this.dlr = dlr;
		// this.type = type;
		this.destination = destination;
		// this.source = source;
		// this.server = server;
		// this.port = port;
	}

	public Sender(String message, String destination, String source) {
		this.message = message;
		this.destination = destination;
		this.source = source;
	}

	public void sendMessage() {

		try {
			String routeUrl = "http://121.241.242.114:80/bulksms/bulksms?";

			String q = "username=" + URLEncoder.encode(username, "UTF-8");
			q += "&" + "password=" + URLEncoder.encode(password, "UTF-8");
			q += "&type=0&dlr=0";
			q += "&" + "destination=" + destination;
			q += "&source=Acholi-FP";
			q += "&message=" + message;

			URL url = new URL(routeUrl);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(q);
			wr.flush();

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;
			System.out.println("RouteSMS API Response :");
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}

		} catch (Exception e) {
			log.error("Error", e);
		}
	}

	/**
	 * Below method converts the unicode to hex value
	 * 
	 * @param regText
	 * @return
	 */
	public static StringBuffer convertToUnicode(String regText) {
		char[] chars = regText.toCharArray();
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			String iniHexString = Integer.toHexString((int) chars[i]);
			if (iniHexString.length() == 1)
				iniHexString = "000" + iniHexString;
			else if (iniHexString.length() == 2)
				iniHexString = "00" + iniHexString;
			else if (iniHexString.length() == 3)
				iniHexString = "0" + iniHexString;
			hexString.append(iniHexString);
		}
		System.out.println(hexString);
		return hexString;
	}

}
