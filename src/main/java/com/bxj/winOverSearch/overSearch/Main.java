package com.bxj.winOverSearch.overSearch;

import java.io.IOException;
import java.util.Vector;

/**
 * @author BaoXiangjie
 * @date 2023/6/13 23:35
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String rootUrl = "E:\\project\\全盘检索\\WinOverSearch";
        String dirUrl = "src/main/java/com/bxj/index";
        OSDirectoryScan osDirectoryScan = new OSDirectoryScan();
        long startTime = System.currentTimeMillis();
        Vector<String> urlList = osDirectoryScan.scanDir(rootUrl, dirUrl);
        long endTime = System.currentTimeMillis();
        System.out.println("\n检索用时：" + (endTime - startTime) + "ms");
        System.out.println("结果输出：");
        for (String s : urlList) {
            System.out.println(s);
        }
    }
}
