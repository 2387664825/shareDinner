package com.hfy.dinner.controller;

import com.hfy.dinner.consts.Const;
import com.hfy.dinner.repository.pojo.ResponseDo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 图片上传处理
 *
 * @author hfy
 */
@RestController
@RequestMapping(Const.API_V1 + "/image")
public class UploaderController {

    //path 为图片在服务器的绝对路径
    @RequestMapping("/get")
    public static void getPhoto(HttpServletResponse response, String path) throws Exception {
        File file = new File("E:\\image\\dinner\\" + path);
        FileInputStream fis;
        fis = new FileInputStream(file);

        long size = file.length();
        byte[] temp = new byte[(int) size];
        fis.read(temp, 0, (int) size);
        fis.close();
        byte[] data = temp;
        response.setContentType("image/png");
        OutputStream out = response.getOutputStream();
        out.write(data);
        out.flush();
        out.close();

    }

    @RequestMapping("/upload")
    public ResponseDo uploadPicture(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        ResponseDo result = null;
        Map<String, Object> map = new HashMap<String, Object>();
        File targetFile = null;
        String url = "";//返回存储路径
        int code = 1;
        System.out.println(file);
        String fileName = file.getOriginalFilename();//获取文件名加后缀
        if (fileName != null && fileName != "") {
            String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/image/get?path=";//存储路径
            String path = "E:\\image\\dinner"; //文件存储位置
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
            fileName = new Date().getTime() + "_" + new Random().nextInt(1000) + fileF;//新的文件名

            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            //获取文件夹路径
            File file1 = new File(path);
            //如果文件夹不存在则创建
            if (!file1.exists() && !file1.isDirectory()) {
                file1.mkdir();
            }
            //将图片存入文件夹
            targetFile = new File(file1, fileName);
            try {
                //将上传的文件写到服务器上指定的文件。
                file.transferTo(targetFile);
                url = returnUrl + fileName;
                code = 0;
                result = new ResponseDo(200, url);
            } catch (Exception e) {
                result = new ResponseDo(202, "系统异常，图片上传失败");
            }
        }
        return result;
    }
}
