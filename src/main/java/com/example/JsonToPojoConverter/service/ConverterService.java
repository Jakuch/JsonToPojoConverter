package com.example.JsonToPojoConverter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.codemodel.JCodeModel;
import lombok.AllArgsConstructor;
import org.jsonschema2pojo.SchemaMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@AllArgsConstructor
public class ConverterService {

    private ObjectMapper objectMapper;

    private SchemaMapper schemaMapper;

    public String convertFileToJson(MultipartFile file) throws IOException {
        return objectMapper.readTree(file.getInputStream()).toString();
    }

    public void convertJsonToJavaClass(MultipartFile file, String pojoName) throws IOException {
        File outputJavaClassDir = new File("src/main/resources/");
        pojoName = pojoName.isBlank() ? "MyGeneratedClass" : pojoName;
        var json = convertFileToJson(file);

        var jCodeModel = new JCodeModel();
        schemaMapper.generate(jCodeModel, pojoName, "generated", json);

        jCodeModel.build(outputJavaClassDir);
    }
}
