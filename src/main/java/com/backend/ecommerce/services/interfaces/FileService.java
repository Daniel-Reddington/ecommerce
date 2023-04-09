package com.backend.ecommerce.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    String saveFile(MultipartFile multipartFile, String directory);

}
