package com.bxj.winOverSearch.overSearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

/**
 * @author BaoXiangjie
 * @date 2023/6/14 21:40
 */
public class OSFileUtils {

    //需自行进行配置，待优化
    String[] types = {".txt", ".csv", ".log", ".java", ".html", ".xml", ".yaml", ".md", ".markdown", ".json", ".sql", ".trf", ".docx", ".xlsx", ".ppt", ".pdf"};

    public OSFileUtils() {
    }

    /**
     * 检查文件类型
     */
    public boolean checkFileType(Path file) {
        String fileType = file.getFileName().toString();
        for (String type : types) {
            if (fileType.endsWith(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 统计文件类型数量
     */
    public void countFile(Path file, AtomicLong totalFiles, ConcurrentHashMap<String, Long> typeQuantity, Vector<String> urlList) {
        totalFiles.incrementAndGet();
        urlList.add(file.toAbsolutePath().toString());
        String[] split = file.getFileName().toString().split("\\.");
        String fileType = "." + split[split.length - 1];
        if (typeQuantity.containsKey(fileType)) {
            typeQuantity.put(fileType, typeQuantity.get(fileType) + 1);
        } else {
            typeQuantity.put(fileType, 1L);
        }
    }

    /**
     * 输出文件统计
     */
    public void printSearchStatistics(AtomicLong totalFiles, ConcurrentHashMap<String, Long> typeQuantity) {

        //设置表头
        String header = Stream.of("FileType", "Amount")
                .map(s -> String.format("%-10s", s))
                .collect(Collectors.joining(""));

        //设置内容
        String values = typeQuantity.entrySet().stream()
                .map(e -> String.format("%-10s", e.getKey()) + String.format("%-10s", e.getValue()))
                .collect(Collectors.joining("\n"));

        //打印检索统计
        System.out.printf("文件总数：%d个\n", totalFiles.get());
        System.out.println(header + "\n" + values);
    }

    /**
     * 读取文件内容
     */
    public List<String> readLines(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    /**
     * 删除目录
     */
    public void deleteDirectory(String dirUrl) throws IOException {
        FileUtils.deleteDirectory(new File(dirUrl));
    }
}
