package mainPackage;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {

	public static byte[] encrypt(char[] plainText, char[] key) {
		try {
			byte[] clean = toBytes(plainText);

			int ivSize = 16;
			byte[] iv = new byte[ivSize];
			SecureRandom random = new SecureRandom();
			random.nextBytes(iv);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(toBytes(key));
			byte[] keyBytes = new byte[16];
			System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
			SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
			byte[] encrypted = cipher.doFinal(clean);

			byte[] encryptedIVAndText = new byte[ivSize + encrypted.length];
			System.arraycopy(iv, 0, encryptedIVAndText, 0, ivSize);
			System.arraycopy(encrypted, 0, encryptedIVAndText, ivSize, encrypted.length);

			return encryptedIVAndText;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static char[] decrypt(byte[] encryptedIvTextBytes, char[] key) throws Exception {
		try {
			int ivSize = 16;
			int keySize = 16;

			byte[] iv = new byte[ivSize];
			System.arraycopy(encryptedIvTextBytes, 0, iv, 0, iv.length);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

			int encryptedSize = encryptedIvTextBytes.length - ivSize;
			byte[] encryptedBytes = new byte[encryptedSize];
			System.arraycopy(encryptedIvTextBytes, ivSize, encryptedBytes, 0, encryptedSize);

			byte[] keyBytes = new byte[keySize];
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(toBytes(key));
			System.arraycopy(md.digest(), 0, keyBytes, 0, keyBytes.length);
			SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

			Cipher cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
			byte[] decrypted = cipherDecrypt.doFinal(encryptedBytes);

			char[] toReturned = toChars(decrypted);

			return toReturned;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static char[] toChars(byte[] bytes) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
		CharBuffer charBuffer = Charset.forName("UTF-8").decode(byteBuffer);
		char[] chars = Arrays.copyOfRange(charBuffer.array(), charBuffer.position(), charBuffer.limit());
		return chars;
	}

	private static byte[] toBytes(char[] chars) {
		CharBuffer charBuffer = CharBuffer.wrap(chars);
		ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
		byte[] bytes = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());
		return bytes;
	}
}
