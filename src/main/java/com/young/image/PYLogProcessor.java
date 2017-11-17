package com.young.image;

import com.young.image.utils.ExportExcelUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yaobin on 2017/10/18.
 */
public class PYLogProcessor {

    public static void main(String[] args) {

        try {

            File directory = new File("");//设定为当前文件夹
            String currentDir = directory.getAbsolutePath();
            File file = new File(currentDir);
            File[] array = file.listFiles();

            List<Map<String, Object>> list = new ArrayList<>();

            for(int i=0;i<array.length;i++){

                if(array[i].isDirectory() || !array[i].getName().contains("txt")){
                    continue;
                }

                int index = 0;
                int name = 0;
                LineIterator it = FileUtils.lineIterator(array[i], "UTF-8");;
                while (it.hasNext()) {

                    index++;
                    if(index == 50000){
                        List<Object[]> dataList = ExportExcelUtils.getObjects(list);
                        ExportExcelUtils exporter = new ExportExcelUtils("品友数据",
                                new String[]{"品友数据", "1", "2", "3", "4", "5"}, dataList);

                        File re = new File(currentDir + "/result"+name+".xls");
                        OutputStream out = new FileOutputStream(re);
                        exporter.export(out);
                        name++;

                        list = new ArrayList<>();
                        index = 0;
                        System.out.println("数据："+ name);
                    }

                    String line = it.next();

                    String[] lineArr = line.split("\t");
                    Map<String, Object> map = new HashedMap();
                    map.put("1", lineArr[0]);
                    map.put("2", lineArr[1]);
                    map.put("3", lineArr[2]);
                    map.put("4", lineArr[3]);
                    map.put("5", lineArr[4]);
                    list.add(map);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
