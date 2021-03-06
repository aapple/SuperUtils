package com.young.image;

import com.young.image.utils.ExportExcelUtils;
import com.young.image.utils.GoogleShortUtils;
import com.young.image.utils.ReadExcelUtil;
import org.apache.commons.collections.map.HashedMap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by yaobin on 2017/10/18.
 */
public class ShortUrlMaker {

    public static void main(String[] args) {

        try {

            List<List<String>> excels = ReadExcelUtil.readXlsx("f:/url.xlsx");
            Pattern pattern = Pattern
                    .compile("^([hH][tT]{2}[pP]://)(.)+$");

            List<Map<String, Object>> list = new ArrayList<>();

            int index = 0;
            for(List<String> sheet : excels){

                for(String cell : sheet){
                    if(pattern.matcher(cell).matches()){
                        String shortUrl = GoogleShortUtils.convert(cell);
                        Map<String, Object> map = new HashedMap();
                        map.put("offerUrl", cell);
                        map.put("shortUrl", shortUrl);
                        list.add(map);
                        System.out.println("短链生成进度："+ ++index);
                    }
                }
            }

            List<Object[]> dataList = ExportExcelUtils.getObjects(list);
            ExportExcelUtils exporter = new ExportExcelUtils("shortUrl",
                    new String[]{"短链", "offerUrl", "shortUrl"}, dataList);

            File file = new File("f:/result.xls");
            OutputStream out = new FileOutputStream(file);
            exporter.export(out);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
