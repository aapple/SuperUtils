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
import java.util.Set;
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

            List<Object[]> dataList = getObjects(list);
            ExportExcelUtils exporter = new ExportExcelUtils("shortUrl",
                    new String[]{"短链", "offerUrl", "shortUrl"}, dataList);

            File file = new File("f:/result.xls");
            OutputStream out = new FileOutputStream(file);
            exporter.export(out);

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static List<Object[]> getObjects(List<Map<String, Object>> list) {
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objects0 = new Object[list.get(0).size()+1];
        dataList.add(objects0);
        Map<String, Object> map2 = list.get(0);
        Set<String> keySet = map2.keySet();
        int m = 1;
        for (String key : keySet) {
            dataList.get(0)[m++] = key;
        }
        for (int i = 0; i < list.size(); i++) {
            Object[] objects = new Object[list.get(i).size()+1];
            dataList.add(objects);
            int j = 1;
            Map<String, Object> map3 = list.get(i);
            Set<String> keySet1 = map3.keySet();
            for (String key : keySet1) {
                dataList.get(i+1)[j++] = map3.get(key);
            }
        }
        return dataList;
    }
}
