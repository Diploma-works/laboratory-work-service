package com.vko.labworkproducer.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileConverter {

    public static byte[] multipartFileToByteArray(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
