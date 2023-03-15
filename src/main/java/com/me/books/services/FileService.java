package com.me.books.services;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileService {
    private final static String STORAGE = "storage";
    public final static String BOOK_STORAGE = "storage/books";
    public final static String PIC_STORAGE = "storage/pics";
    public FileService(){
        File directory = new File(STORAGE);
        if (!directory.exists()) {
            directory.mkdir();
        }
        directory = new File(PIC_STORAGE);
        if(!directory.exists()){
            directory.mkdir();
        }
        directory = new File(BOOK_STORAGE);
        if(!directory.exists()){
            directory.mkdir();
        }
    }
    public String uploadBook(long userID, String bookName, int chapterInd,  byte[] filesBytes) throws IOException {
        String dir = String.format("%s/%s/%s/%s",BOOK_STORAGE, userID, bookName, chapterInd);
        Files.createDirectories(Path.of(dir).getParent());
        Files.write(Path.of(dir), filesBytes);
        return dir;
    }
}
