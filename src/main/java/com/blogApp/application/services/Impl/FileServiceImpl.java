package com.blogApp.application.services.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogApp.application.services.FileService;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        
        //file name
        String name = file.getOriginalFilename();

        //random name generate file
        String randomName = java.util.UUID.randomUUID().toString();
        String newName = randomName + name.substring(name.lastIndexOf("."));

        //full path
        String filePath = path + File.separator + newName;

        //Create folder if not created
        File f = new File(path);
        if(!f.exists()) {
            f.mkdir();
        }

        //file copy
        Files.copy(file.getInputStream(), new File(filePath).toPath());  

        return newName;

    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {

        String filePath = path + File.separator + fileName;
        InputStream is = new FileInputStream(filePath);

        //db logic to return input stream
        return is;
    }

}
