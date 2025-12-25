package com.blogApp.application.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogApp.application.payloads.FileResponce;
import com.blogApp.application.services.FileService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${project.image}")  
    private String path = "L:/Project_WorkSpace/blogapplication/src/main/resources/static/images/";

    @PostMapping("/upload")
    public ResponseEntity<FileResponce> uploadFile(
        @RequestParam("image") MultipartFile image
    ) {
        String fileName;
        try {
            fileName = this.fileService.uploadImage(path, image);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<FileResponce>(new FileResponce("", "File upload failed"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<FileResponce>(new FileResponce(fileName, "File uploaded successfully"), HttpStatus.OK);
    }

    @GetMapping(value = "/profiles/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadFile(
            @PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }

}
