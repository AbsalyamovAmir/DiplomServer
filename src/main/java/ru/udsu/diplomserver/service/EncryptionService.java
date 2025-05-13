package ru.udsu.diplomserver.service;

public interface EncryptionService {

    byte[] decrypt(byte[] encryptedData) throws Exception;
}
