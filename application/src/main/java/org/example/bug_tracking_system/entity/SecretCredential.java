package org.example.bug_tracking_system.entity;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class SecretCredential {

	private static final int SALT_BYTES = 32;
	private static final int ITERATIONS = 65536;
	private static final int KEY_LENGTH = 256;

	private static final SecureRandom sr = new SecureRandom();
	private static final SecretKeyFactory skf;

	static {
		try {
			skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Could not get factory instance of required algorithm", e);
		}
	}

	private final byte[] hash;
	private final byte[] salt;

	public byte[] getHash() {
		return hash;
	}

	public byte[] getSalt() {
		return salt;
	}

	private byte[] hashPassword(String password) {
		PBEKeySpec pks = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
		try {
			return skf.generateSecret(pks).getEncoded();
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException("Failed to hash password", e);
		} finally {
			pks.clearPassword();
		}
	}

	SecretCredential(String password) {
		salt = new byte[SALT_BYTES];
		sr.nextBytes(salt);
		hash = hashPassword(password);
	}

	SecretCredential(byte[] hash, byte[] salt) {
		this.hash = hash;
		this.salt = salt;
	}

	/**
	 * Verify if the provided password matches with the stored hash
	 * @param password The password to test
	 * @return True if password matches, else false
	 */
	public boolean verify(String password) {
		return Arrays.equals(hashPassword(password), hash);
	}

}
