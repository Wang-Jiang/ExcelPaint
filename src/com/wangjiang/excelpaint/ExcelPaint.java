package com.wangjiang.excelpaint;

import org.apache.poi.hssf.usermodel.*;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WangJiang on 2016/11/26.
 */
public class ExcelPaint {

    /**
     * 实际上POI是支持自定义颜色的，但是颜色索引有限
     * 在这里就简单处理，直接查找相似颜色
     */
    public void paint(String picturePath, String filePath) {
        Map<String, HSSFCellStyle> styleMap = new HashMap<>();
        HSSFWorkbook excel = new HSSFWorkbook();    //创建excel
        HSSFSheet sheet = excel.createSheet("picture"); //创建一个表格
        //设置宽度高度，让它看起来是正方形
        sheet.setDefaultColumnWidth(2);
        sheet.setDefaultRowHeight((short) 1);

        PictureUtil.Pixel[][] picture = PictureUtil.getPicturePixel(picturePath);
        HSSFPalette palette = excel.getCustomPalette();
        HSSFCellStyle style = excel.createCellStyle();

        for (int i = 0; i < picture.length; i++) {
            HSSFRow row = sheet.createRow(i); //创建行
            for (int j = 0; j < picture[i].length; j++) {
                HSSFCell cell = row.createCell(j);
                //System.out.println("index="+palette.findSimilarColor(picture[i][j].getRed(),picture[i][j].getGreen(), picture[i][j].getBlue()).getIndex());
                style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                style.setFillForegroundColor(palette.findSimilarColor(picture[i][j].getRed(), picture[i][j].getGreen(), picture[i][j].getBlue()).getIndex());   //找到相似颜色
                if (styleMap.get(style.toString()) == null) {
                    //该颜色还没有存储到map里面
                    //因为POI最多只支持生成4000个style对象，所以放到MAP里面，避免产生太多style
                    if (styleMap.size() >= 4000) {
                        System.out.println("样式数量超出限制");
                        break;
                    }
                    HSSFCellStyle newStyle = excel.createCellStyle();
                    newStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                    newStyle.setFillForegroundColor(palette.findSimilarColor(picture[i][j].getRed(), picture[i][j].getGreen(), picture[i][j].getBlue()).getIndex());
                    styleMap.put(style.toString(), newStyle);
                    System.out.println("新加入一个新样式");
                }
                cell.setCellStyle(styleMap.get(style.toString()));
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            excel.write(fos);
            fos.close();
            System.out.println("文件保存成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
