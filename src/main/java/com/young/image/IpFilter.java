package com.young.image;

import com.young.image.utils.ExportExcelUtils;
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
 * Created by yaobin on 2017/10/20.
 */
public class IpFilter {

    public static void main(String[] args){
        try {

            List<List<String>> excels = ReadExcelUtil.readXlsx("f:/ip_list.xlsx");
            Pattern pattern = Pattern
                    .compile("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");

            List<Map<String, Object>> list = new ArrayList<>();

            int index = 0;
            for(List<String> sheet : excels){

                for(String cell : sheet){
                    if(pattern.matcher(cell).matches()){
                        Map<String, Object> map = new HashedMap();
                        String[] cells = cell.split("\\.");
                        map.put("ip", cells[0] + "." + cells[1]);
                        list.add(map);
                        System.out.println("ip生成进度："+ ++index);
                    }
                }
            }

            List<Object[]> dataList = ExportExcelUtils.getObjects(list);
            ExportExcelUtils exporter = new ExportExcelUtils("ip",
                    new String[]{"ip", "ip", "shortUrl"}, dataList);

            File file = new File("f:/ip2.xls");
            OutputStream out = new FileOutputStream(file);
            exporter.export(out);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
