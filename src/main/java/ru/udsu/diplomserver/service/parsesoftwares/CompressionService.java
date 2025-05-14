package ru.udsu.diplomserver.service.parsesoftwares;

import java.io.IOException;

public interface CompressionService {
    String decompress(byte[] compressedData) throws IOException;
}
