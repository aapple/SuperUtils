package com.young.image;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaobin on 2017/10/16.
 */
public class LongImageMaker {

    /**
     * 将宽度相同的图片，竖向追加在一起 ##注意：宽度必须相同
     *
     * @param piclist
     *            文件路径列表
     * @param outPath
     *            输出路径
     */
    public static void yPic(List<String> piclist, String outPath) {// 纵向处理图片
        if (piclist == null || piclist.size() <= 0) {
            System.out.println("图片数组为空!");
            return;
        }
        try {
            int height = 0, // 总高度
                    width = 0, // 总宽度
                    _height = 0, // 临时的高度 , 或保存偏移高度
                    __height = 0, // 临时的高度，主要保存每个高度
                    picNum = piclist.size();// 图片的数量
            File fileImg = null; // 保存读取出的图片
            int[] heightArray = new int[picNum]; // 保存每个文件的高度
            BufferedImage buffer = null; // 保存图片流
            List<int[]> imgRGB = new ArrayList<int[]>(); // 保存所有的图片的RGB
            int[] _imgRGB; // 保存一张图片中的RGB数据
            for (int i = 0; i < picNum; i++) {
                fileImg = new File(piclist.get(i));
                System.out.println("处理图片：" + piclist.get(i));
                buffer = ImageIO.read(fileImg);
                heightArray[i] = _height = buffer.getHeight();// 图片高度
                if (i == 0) {
                    width = buffer.getWidth();// 图片宽度
                }
                height += _height; // 获取总高度
                _imgRGB = new int[width * _height];// 从图片中读取RGB
                _imgRGB = buffer.getRGB(0, 0, width, _height, _imgRGB, 0, width);
                imgRGB.add(_imgRGB);
            }
            _height = 0; // 设置偏移高度为0
            // 生成新图片
            BufferedImage imageResult = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < picNum; i++) {
                __height = heightArray[i];
                if (i != 0) _height += __height; // 计算偏移高度
                imageResult.setRGB(0, _height, width, __height, imgRGB.get(i), 0, width); // 写入流中
            }
            File outFile = new File(outPath);
            ImageIO.write(imageResult, "jpg", outFile);// 写图片
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addBorderToImage(String filePath) {
        try {
            File _file = new File(filePath); // 读入文件
            Image src = javax.imageio.ImageIO.read(_file); // 构造Image对象
            int width = src.getWidth(null); // 得到源图宽
            int height = src.getHeight(null); // 得到源图长

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = image.getGraphics();
            graphics.drawImage(src, 0, 0, width, height, null); // 绘制图

            // 画边框
            graphics.setColor(Color.white);
            graphics.drawRect(0, 0, width - 1, height - 1);
            graphics.drawRect(1, 1, width - 1, height - 1);
            graphics.drawRect(0, 0, width-2, height- 2);

            FileOutputStream out = new FileOutputStream(filePath); // 输出到文件流
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();

        File directory = new File("");//设定为当前文件夹
//        String currentDir = System.getProperty("user.dir");
        String currentDir = directory.getAbsolutePath();
//        String currentDir = "f:/bak";
        File file = new File(currentDir);
        File[] array = file.listFiles();

        for(int i=0;i<array.length;i++){
            if(array[i].isFile() && array[i].getName().contains("jpg")){
                list.add(array[i].getAbsolutePath());
                System.out.println("待处理图片：" + array[i].getAbsolutePath());
            }
        }

        System.out.println("当前目录是：" + currentDir);
        for(String path : list){
            addBorderToImage(path);
        }
        yPic(list, currentDir + "/output.jpg");
    }
}
