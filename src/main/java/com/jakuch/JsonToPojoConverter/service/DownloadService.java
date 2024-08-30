package com.jakuch.JsonToPojoConverter.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class DownloadService {

    public void download() throws IOException {
        var fileOutputStream = new FileOutputStream("GeneratedClasses.zip");
        var zipOutputStream = new ZipOutputStream(fileOutputStream);
        //TODO fix this, it cant find generated folder
        var fileToZip = new File("generated");
        zipFile(fileToZip, fileToZip.getName(), zipOutputStream);

        zipOutputStream.close();
        fileOutputStream.close();
    }

    private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOutputStream) throws IOException {
        //TODO debug this and see if its working correctly
        var path = fileToZip.toPath();
        if (Files.isHidden(path)) {
            return;
        }
        if (Files.isDirectory(path)) {
            if (fileName.endsWith("/")) {
                zipOutputStream.putNextEntry(new ZipEntry(fileName));
                zipOutputStream.closeEntry();
            } else {
                zipOutputStream.putNextEntry(new ZipEntry(fileName + "/"));
                zipOutputStream.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            assert children != null;
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOutputStream);
            }
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOutputStream.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fileInputStream.read(bytes)) >= 0) {
            zipOutputStream.write(bytes, 0, length);
        }
        fileInputStream.close();
    }
}
