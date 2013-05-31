package org.agric.oxm.server.security.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.agric.oxm.model.User;
import org.agric.oxm.model.exception.SessionExpiredException;
import org.agric.oxm.model.exception.UnexpectedException;
import org.agric.oxm.server.security.OXMUserDetails;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

public final class OXMSecurityUtil {

	private static Logger log = LoggerFactory.getLogger(OXMSecurityUtil.class);

	private OXMSecurityUtil() {

	}

	public static String encodeString(String strToEncode) {
		try {
			String algorithm = "SHA1";
			MessageDigest md = MessageDigest.getInstance(algorithm);
			byte[] input = strToEncode.getBytes();
			return hexString(md.digest(input));
		} catch (NoSuchAlgorithmException ex) {
			throw new UnexpectedException(ex);
		}
	}

	public static String encodeString2(String strToEncode) {
		try {
			String algorithm = "SHA1";
			MessageDigest md = MessageDigest.getInstance(algorithm);

			/*
			 * we use platform default character encoding
			 */
			byte[] input = strToEncode.getBytes();
			return hexString2(md.digest(input));
		} catch (NoSuchAlgorithmException ex) {
			throw new UnexpectedException(ex);
		}
	}

	private static String hexString2(byte[] b) {
		if (b == null || b.length < 1) {
			return "";
		}
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			s.append(Integer.toHexString(b[i] & 0xFF));
		}
		return new String(s);
	}

	private static String hexString(byte[] b) {
		StringBuffer buf = new StringBuffer();
		char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		int len = b.length;
		int high = 0;
		int low = 0;
		for (int i = 0; i < len; i++) {
			high = ((b[i] & 0xf0) >> 4);
			low = (b[i] & 0x0f);
			buf.append(hexChars[high]);
			buf.append(hexChars[low]);
		}

		return buf.toString();
	}

	public static void setSecurityContext(OXMUserDetails userDetails) {
		User user = userDetails.getSystemUser();

		SecurityContext sc = new SecurityContextImpl();
		Authentication auth = new UsernamePasswordAuthenticationToken(user,
				user.getPassword(), userDetails.getAuthorities());
		sc.setAuthentication(auth);
		SecurityContextHolder.setContext(sc);

		log.info("Successfully logged in User: << " + user.getUsername()
				+ " >> ");
		log.info("<< " + "Setting User:" + user.getUsername() + " in Context"
				+ ">> ");
	}

	public static User getLoggedInUser() throws SessionExpiredException {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			Authentication auth = context.getAuthentication();
			if (auth != null) {
				User user = null;
				if (auth.getPrincipal() instanceof OXMUserDetails) {
					OXMUserDetails userDetails = (OXMUserDetails) auth
							.getPrincipal();
					user = userDetails.getSystemUser();
				} else if (auth.getPrincipal() instanceof User) {
					user = (User) auth.getPrincipal();
				} else {
					log.debug("Auth not an instance of OXMUserDetails - i.e. no logged in user. Auth="
							+ auth);
					throw new SessionExpiredException(
							"Could not find logged in user");
				}
				return user;
			}
		}

		log.debug("No Spring SecurityContext or Authentication - i.e. no logged in user");
		throw new SessionExpiredException("Could not find logged in user");
	}

	public static String getRandomToken() {
		Random rng = new Random();
		return encodeString(Long.toString(System.currentTimeMillis())
				+ Long.toString(rng.nextLong()));
	}

	public static void prepUserCredentials(User user) {
		if (user != null) {
			if (StringUtils.isNotBlank(user.getClearTextPassword())
					&& StringUtils.isNotEmpty(user.getClearTextPassword())) {

				if (StringUtils.isBlank(user.getSalt())
						&& StringUtils.isEmpty(user.getSalt())) {
					user.setSalt(OXMSecurityUtil.getRandomToken());
				}

				String hashedPassword = OXMSecurityUtil.encodeString(user
						.getClearTextPassword() + user.getSalt());
				user.setPassword(hashedPassword);
			}
		}
	}

	public static String generateUserName(User user) {
		String username = "";

		if (StringUtils.isEmpty(user.getUsername()))
			username = user.getName().toLowerCase().trim().replace(" ", ".");
		else
			username = user.getUsername().toLowerCase().trim()
					.replace(" ", ".").concat("1");

		return username;
	}
}
