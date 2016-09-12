package lucene.day01.firstApp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import lucene.day01.entity.Article;
/**
 * Lucene 第一个列子
 * @author leifeng
 * 2016年8月29日
 */
public class FirstApp {
	/**
	 * 创建索引库
	 * 将Article对象放入索引库中的原始记录表中，从而形成词汇表
	 */
	@Test
	public void createIndexDB() throws Exception{
		//创建Article对象
		Article article=new Article(1,"培训","创智是一家IT培训机构");
		//创建Document对象
		Document document=new Document();
		//将Article对象中的三个属性分别绑定到Document对象中
		/*
		 * 参数一：document对象中的属性名叫xid,article对象中属性名叫id,项目中提倡相同
		 * 参数二：document对象中的属性xid的值，与artticle对象中相同
		 * 参数三：是否将xid属性值存入由原始记录表中转存入词汇表中
		 *      Store.YES 表示该属性值会存入词汇表
		 *      Store.No表示该属性值不会存入词汇表中
		 *      项目中提倡非id值都存入词汇表中
		 * 参数四：是否将xid属性值进行词汇拆分
		 *      Index.ANALYZED表示该属性会进行词汇拆分
		 *      Index.NOT_ANALYZED表示该属性值不会进行词汇拆分
		 *      项目中提倡非id值都存入词汇表
		 */
		document.add(new Field("xid",article.getId().toString(),Store.YES,Index.ANALYZED));
		document.add(new Field("xtitle",article.getTitle(),Store.YES,Index.ANALYZED));
		document.add(new Field("xcontent",article.getContent(),Store.YES,Index.ANALYZED));
		
		//创建IndexWriter字符流
		/*
		 * 参数一:lucene索引库最终对于硬盘中的目录，列如:E:/IndexDBDBDB
		 * 参数二:采用什么策略将文本拆分，一个策略就是一个具体的实现类
		 * 参数三:最duo拆分出多少词汇，LIMIITED表示1W个，即只取前1W个词汇，如果不足1w个词汇，以实际为准
		 */
		Directory directory=FSDirectory.open(new File("E:/IndexDBDBDB"));
		Version version=Version.LUCENE_30;
		Analyzer analyzer=new StandardAnalyzer(version);
		MaxFieldLength maxFiledLenth=MaxFieldLength.LIMITED;
		IndexWriter indexWriter=new IndexWriter(directory, analyzer,maxFiledLenth);
		
		//将document对象写入lucene索引库
		indexWriter.addDocument(document);
		//关闭IndexWriter字符流对象
		indexWriter.close();
		
	}
	
	/**
	 * 根据关键字从索引库中搜索符合条件的内容
	 */
	@Test
	public void findIndexDB() throws Exception{
		String keywords="培";
		List<Article> articleList=new ArrayList<Article>();
		Directory directory=FSDirectory.open(new File("E:/IndexDBDBDB"));
		Version version=Version.LUCENE_30;
		Analyzer analyzer=new StandardAnalyzer(version);
		MaxFieldLength maxFieldLength=MaxFieldLength.LIMITED;
		
		
		//创建IndexSearcher字符流对象
		IndexSearcher indexSearcher=new IndexSearcher(directory);
		/*
		 * 参数一：使用分词器的版本，提倡使用该jar包中的最高版本
		 * 参数二：针对document对象中的那个属性进行搜索
		 */
		QueryParser querParser=new QueryParser(version,"xcontent",analyzer);
		//创建对象，封装查询关键字
		Query query=querParser.parse(keywords);
		//根据关键字，去索引库中的词汇表搜索
		/*
		 * 参数一：表示封装关键字查询对象，其它QueryParser表示查询解析器
		 * 参数二：MAX_RECORD表示如果根据关键字搜索出来的内容比较多，只取前MAX_RECORD个内容，不足Max_RECORD个数的话，以实际为准。
		 */
		int MAX_RECORD=100;
		TopDocs topDcos=indexSearcher.search(query, MAX_RECORD);
		//迭代词汇表中符合条件的编号
		for(int i=0;i<topDcos.scoreDocs.length;i++){
			//取出封装编号he分数的ScoreDoc对象
			ScoreDoc scoreDoc=topDcos.scoreDocs[i];
			//取出每一个编号，列如：0,1,2
			int no=scoreDoc.doc;
			//根据编号去索引库中的原始记录表中查询对应的document对象
			Document document=indexSearcher.doc(no);
			//获取document 对象中的三个属性值
			String xid=document.get("xid");
			String xtitle=document.get("xtitle");
			String xcontent=document.get("xcontent");
			
			//封装到article对象中
			Article article=new Article(Integer.parseInt(xid),xtitle,xcontent);
			articleList.add(article);
		}
		
		for (Article q : articleList) {
			System.out.println(q);
			
		}
		
	}

}
