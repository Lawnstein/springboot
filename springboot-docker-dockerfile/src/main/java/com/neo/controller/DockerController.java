package com.neo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

/**
 * @author xiaogege
 */
@RestController
public class DockerController {

    @RequestMapping("/")
    public String index() throws IOException {
        int count = 1;
        String fileName = "/tmp/data/";
        File dir = new File(fileName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(fileName + "data.txt");
        if (file.exists()) {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String s = br.readLine();
            System.out.println(" 读取到数据 ：" + s);
            count = Integer.parseInt(s);
            count += 1;

            br.close();
            fr.close();
        }
        FileWriter fw = new FileWriter(file);
        fw.write(count + "");
        fw.flush();
        fw.close();

        return "网站访问次数：" + count;
    }


}