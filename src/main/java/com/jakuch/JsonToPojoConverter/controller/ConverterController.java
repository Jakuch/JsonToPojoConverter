package com.jakuch.JsonToPojoConverter.controller;


import com.jakuch.JsonToPojoConverter.service.ConverterService;
import com.jakuch.JsonToPojoConverter.service.DownloadService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class ConverterController {


    private ConverterService converterService;
    private DownloadService downloadService;

    @RequestMapping(value = {"/", "/home"})
    public String get() {
        return "home";
    }

    @RequestMapping(value = "/home", params = {"convert"})
    public String convert(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "name") String pojoName) throws IOException {
        converterService.convertJsonToJavaClass(file, pojoName);
        return "redirect:/download";
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public String download() throws IOException {
        downloadService.download();
        //TODO download and push it to frontend (download pop-up or something?)
        return "home";
    }

}
