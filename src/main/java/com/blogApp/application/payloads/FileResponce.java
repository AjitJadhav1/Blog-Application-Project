package com.blogApp.application.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileResponce {

    private String fileName;
    private String message;

    public FileResponce(String fileName, String message) {
        this.fileName = fileName;
        this.message = message;
    }


}
