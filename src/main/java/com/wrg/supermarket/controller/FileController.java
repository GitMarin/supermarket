package com.wrg.supermarket.controller;

import com.wrg.supermarket.component.MkplatWebModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件上传
 */
@Controller
public class FileController {

    @ResponseBody
    @PostMapping("/import")
    public MkplatWebModel importData(MultipartFile file, HttpServletRequest req) throws IOException {
        String realPath = "C:/inetpub/wwwroot/image";
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        file.transferTo(new File(folder,newName));
        String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/image/" + newName;
        return MkplatWebModel.convertMetroPayWebModel(url);
    }
}