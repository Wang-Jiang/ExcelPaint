package com.wangjiang.excelpaint;

public class Main {

    public static void main(String[] args) {
        ExcelPaint excelPaint = new ExcelPaint();
        String name = "D:/1.jpg";
        excelPaint.paint( name, name + ".xls");
    }
}
