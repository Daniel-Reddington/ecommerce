package com.backend.ecommerce.services.impl;

import com.backend.ecommerce.services.interfaces.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String saveFile(MultipartFile multipartFile, String directory) {
        if(multipartFile.isEmpty()) return null;

        String fileName = UUID.randomUUID() +"_"+multipartFile.getOriginalFilename();
        String filePath = directory+fileName;

        try {
            multipartFile.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return filePath;
    }
}
