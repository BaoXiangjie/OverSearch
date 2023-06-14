package com.bxj.winOverSearch.overSearch;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author BaoXiangjie
 * @date 2023/6/14 21:39
 */
public class OSDirectoryScan {

    OSFileUtils osFileUtils = new OSFileUtils();
    OSFileSearch osFileSearch = new OSFileSearch();

    public OSDirectoryScan() {
    }

    /**
     * 扫描目录
     */
    public Vector<String> scanDir(String rootUrl, String dirUrl) throws IOException {
        Path root = Paths.get(rootUrl);
        AtomicLong totalFiles = new AtomicLong(0);
        ConcurrentHashMap<String, Long> typeQuantity = new ConcurrentHashMap<>();
        Vector<String> urlList = new Vector<>();
        Files.walkFileTree(root, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                try {
                    if (osFileUtils.checkFileType(file) && osFileSearch.search(file, dirUrl)) {
                        osFileUtils.countFile(file, totalFiles, typeQuantity, urlList);
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                if (exc instanceof AccessDeniedException) {
                    System.err.println(file.toAbsolutePath() + " 访问失败：" + exc.getMessage());
                    return FileVisitResult.SKIP_SUBTREE;
                } else {
                    System.err.println("其他异常：" + exc.getMessage());
                    return FileVisitResult.CONTINUE;
                }
            }
        });
        osFileUtils.printSearchStatistics(totalFiles, typeQuantity);
        return urlList;
    }
}
