package com.jakuch.JsonToPojoConverter.controller;


import com.jakuch.JsonToPojoConverter.service.ConverterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class ConverterController {


    private ConverterService converterService;

    @RequestMapping(value = {"/", "/home"})
    public String get() {
        return "home";
    }

    @RequestMapping(value = "/home", params = {"convert"})
    public String convert(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "name") String pojoName) throws IOException {
        converterService.convertJsonToJavaClass(file, pojoName);

        return "home";
    }

}
