package edu.yale.sass.pr.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5CheckSum {

	private MessageDigest algorithm;

	private static MD5CheckSum md5;

	private MD5CheckSum() throws NoSuchAlgorithmException {
		algorithm = MessageDigest.getInstance("MD5");
	}

	public static MD5CheckSum getInstance() throws NoSuchAlgorithmException {
		if (md5 == null)
			md5 = new MD5CheckSum();
		return md5;
	}

	public String create(String value) {
		byte[] defaultBytes = value.getBytes();
		algorithm.reset();
		algorithm.update(defaultBytes);

		byte messageDigest[] = algorithm.digest();

		StringBuffer hexSb = new StringBuffer();
		for (byte md : messageDigest) {
			hexSb.append(Integer.toHexString(0xFF & md));
		}
		return hexSb.toString();
	}

}
