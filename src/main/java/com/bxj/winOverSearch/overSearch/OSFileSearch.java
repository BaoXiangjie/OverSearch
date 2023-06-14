package com.bxj.winOverSearch.overSearch;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author BaoXiangjie
 * @date 2023/6/14 21:41
 */
public class OSFileSearch {

    OSFileUtils OSFileUtils = new OSFileUtils();

    public boolean search(Path file, String dirUrl) throws IOException, ParseException {

        // 配置并写入索引目录
        Path path = Paths.get(dirUrl);
        Directory directory = FSDirectory.open(path);
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(directory, config);

        // 读取 txt 文件内容并添加到索引目录
        File txtFile = new File(file.toAbsolutePath().toString());
        List<String> lines = OSFileUtils.readLines(txtFile);
        for (String line : lines) {
            Document doc = new Document();
            doc.add(new TextField("txt", line, Field.Store.YES));
            writer.addDocument(doc);
        }

        // 关闭索引写入器
        writer.close();

        // 创建索引
        IndexReader reader = DirectoryReader.open(directory);

        // 创建索引搜索器
        IndexSearcher searcher = new IndexSearcher(reader);

        // 创建查询解析器
        QueryParser parser = new QueryParser("txt", analyzer);

        // 构建查询对象
        Query query = parser.parse("private");

        // 搜索并返回结果
        TopDocs results = searcher.search(query, 10);
        ScoreDoc[] hits = results.scoreDocs;
        for (ScoreDoc doc : hits) {
            Document document = searcher.doc(doc.doc);
            System.out.println(document.toString());
        }

        try {
            return hits.length > 0;
        } catch (Exception ignored) {
        } finally {
            reader.close();
            directory.close();
            OSFileUtils.deleteDirectory(dirUrl);
        }
        return false;
    }
}
