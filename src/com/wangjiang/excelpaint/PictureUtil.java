package com.wangjiang.excelpaint;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by WangJiang on 2016/11/26.
 */
public class PictureUtil {

    /**
     * 读取一张图片的RGB值
     *
     * @throws Exception
     */
    public static Pixel[][] getPicturePixel(String picture) {
        int[] rgb = new int[3];
        File file = new File(picture);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int width = bi.getWidth();
        int height = bi.getHeight();
        int minx = bi.getMinX();
        int miny = bi.getMinY();

        Pixel[][] pixels = new Pixel[height][width];  //初始化大小

        System.out.println("width=" + width + ",height=" + height + ".");
        System.out.println("minx=" + minx + ",miniy=" + miny + ".");
        for (int y = miny; y < height; y++) {
            for (int x = minx; x < width; x++) {
                int pixel = bi.getRGB(x, y);
                rgb[0] = (pixel & 0xff0000) >> 16; //一个数字转换为RGB数字
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                System.out.printf("y=%d,x=%d:(%d,%d,%d)\n", y, x, rgb[0], rgb[1], rgb[2]);
                pixels[y][x] = new Pixel(rgb[0], rgb[1], rgb[2]);
            }
        }
        return pixels;
    }

    public static class Pixel {
        private int red;
        private int green;
        private int blue;

        public Pixel(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        public int getRed() {
            return red;
        }

        public void setRed(int red) {
            this.red = red;
        }

        public int getGreen() {
            return green;
        }

        public void setGreen(int green) {
            this.green = green;
        }

        public int getBlue() {
            return blue;
        }

        public void setBlue(int blue) {
            this.blue = blue;
        }
    }

}
