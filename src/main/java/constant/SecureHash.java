package constant;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SecureHash {

	public static String getSHA256Password(String password2Hash, String saltString) {

		String password = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(saltString.getBytes());
			byte[] bytes = md.digest(password2Hash.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			password = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return password;
	}

	public static boolean validateSHA265Password(String password, String storedPassword, String saltString) {

		String hashPassword = getSHA256Password(password, saltString);
		if (hashPassword.equals(storedPassword)) {
			return true;
		}

		return false;
	}

	public static String getPBKDF2Password(String password2Hash, String saltString)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		int iterations = 1000;
		char[] chars = password2Hash.toCharArray();
		byte[] salt = saltString.getBytes();

		PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

		byte[] hash = skf.generateSecret(spec).getEncoded();
		return iterations + ":" + toHex(salt) + ":" + toHex(hash);
	}

	public boolean validatePBKDF2Password(String password, String storedPassword, String saltString)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		String hashPassword = getPBKDF2Password(password, saltString);
		if (hashPassword.equals(storedPassword)) {
			return true;
		}

		return false;
	}

	public static String getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt.toString();
	}

	private static String toHex(byte[] array) throws NoSuchAlgorithmException {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);

		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}

}
