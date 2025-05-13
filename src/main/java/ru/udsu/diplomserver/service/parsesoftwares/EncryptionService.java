package ru.udsu.diplomserver.service.parsesoftwares;

public interface EncryptionService {

    byte[] decrypt(byte[] encryptedData) throws Exception;
}
