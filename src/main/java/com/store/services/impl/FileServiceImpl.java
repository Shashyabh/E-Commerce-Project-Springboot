package com.store.services.impl;

import com.store.exceptions.BadApiRequest;
import com.store.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private Logger logger= LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();//abc.png
        logger.info("Filename : {}",originalFilename);

        String filename= UUID.randomUUID().toString();//sjndkkemdllrmld
        String extension=originalFilename.substring(originalFilename.lastIndexOf("."));//.png

        String filenameWithExtension=filename+extension;//sjndkkemdllrmld.png

        String fullPathWithFileName = path+ filenameWithExtension;

        if(extension.equalsIgnoreCase(".png")||extension.equalsIgnoreCase(".jpg")||extension.equalsIgnoreCase("jpeg")){
            File folder=new File(path);
            if (!folder.exists()){
                //Create a folder
                folder.mkdirs();
            }
            //upload the file
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return filenameWithExtension;
        }
        else {
            throw new BadApiRequest("File with "+extension+" is not allowed");
        }
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath=path+File.separator+name;
        InputStream inputStream=new FileInputStream(fullPath);
        return inputStream;
    }
}
