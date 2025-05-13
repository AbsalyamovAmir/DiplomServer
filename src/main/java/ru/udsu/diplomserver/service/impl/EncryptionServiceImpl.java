package ru.udsu.diplomserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.udsu.diplomserver.service.EncryptionService;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Service
@Slf4j
public class EncryptionServiceImpl implements EncryptionService {
    private static final String ENCRYPTION_KEY = "32-char-encryption-key-here";
    private static final int GCM_TAG_LENGTH = 128;
    private static final int NONCE_LENGTH = 12;
    private static final int TAG_LENGTH = 16;

    public byte[] decrypt(byte[] encryptedData) throws Exception {
        log.info("Начало дешифровки данных размером {} байт", encryptedData.length);

        try {
            byte[] nonce = Arrays.copyOfRange(encryptedData, 0, NONCE_LENGTH);
            byte[] tag = Arrays.copyOfRange(encryptedData, NONCE_LENGTH, NONCE_LENGTH + TAG_LENGTH);
            byte[] cipherText = Arrays.copyOfRange(encryptedData, NONCE_LENGTH + TAG_LENGTH, encryptedData.length);

            SecretKeySpec keySpec = generateKeySpec();
            Cipher cipher = configureCipher(keySpec, nonce);

            byte[] result = cipher.doFinal(combineCipherTextAndTag(cipherText, tag));
            log.info("Данные успешно дешифрованы. Размер: {} байт", result.length);

            return result;
        } catch (Exception e) {
            log.error("Ошибка дешифровки: {}", e.getMessage(), e);
            throw e;
        }
    }

    private SecretKeySpec generateKeySpec() throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = digest.digest(ENCRYPTION_KEY.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(keyBytes, "AES");
    }

    private Cipher configureCipher(SecretKeySpec keySpec, byte[] nonce) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "BC");
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, nonce);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);
        return cipher;
    }

    private byte[] combineCipherTextAndTag(byte[] cipherText, byte[] tag) {
        return ByteBuffer.allocate(cipherText.length + tag.length)
                .put(cipherText)
                .put(tag)
                .array();
    }
}
