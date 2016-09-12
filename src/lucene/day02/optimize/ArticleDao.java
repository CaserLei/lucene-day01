package lucene.day02.optimize;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

import lucene.day02.util.LuceneUtil;
import lucene.day02.entity.Article;

/**
 * 索引库进行优化
 * @author leifeng
 * 2016年9月11日
 */
public class ArticleDao {
	/**
	 * 增加document对象索引库
	 * 问题引入，运行5次
	 */
	
	public void add() throws Exception{
		Article article=new Article(1,"培训","传智是一家it培训机构",10);
		Document document=LuceneUtil.javabean2document(article);
		IndexWriter indexWriter=new IndexWriter(LuceneUtil.getDirectory(),LuceneUtil.getAnalyzer(),LuceneUtil.getMaxFiledLenth());
		indexWriter.addDocument(document);
		indexWriter.close();
	}
	
	/**
	 * 合并cfs文件，合并后的cfs文件是二进制压缩字符，能解决的是文件大小和数量的问题
	 */
	@Test
	public void type1() throws Exception{
		Article article=new Article(1,"培训","传智是一家it培训机构",10);
		Document document=LuceneUtil.javabean2document(article);
		IndexWriter indexWriter=new IndexWriter(LuceneUtil.getDirectory(),LuceneUtil.getAnalyzer(),LuceneUtil.getMaxFiledLenth());
		indexWriter.addDocument(document);
		//合并cfs文本
		indexWriter.optimize();
		indexWriter.close();
	}
	/**
	 * 设定合并因子，自动合并sfs文件
	 */
	@Test
	public void type2() throws Exception{
		Article article=new Article(1,"培训","传智是一家it培训机构",10);
		Document document=LuceneUtil.javabean2document(article);
		IndexWriter indexWriter=new IndexWriter(LuceneUtil.getDirectory(),LuceneUtil.getAnalyzer(),LuceneUtil.getMaxFiledLenth());
		indexWriter.addDocument(document);
		//合并cfs文本
		indexWriter.setMergeFactor(3);
		indexWriter.close();
	}
	
	/**
	 * 设定合并因子，自动合并sfs文件
	 */
	@Test
	public void type3() throws Exception{
		Article article=new Article(1,"培训","传智是一家it培训机构",10);
		Document document=LuceneUtil.javabean2document(article);
		IndexWriter indexWriter=new IndexWriter(LuceneUtil.getDirectory(),LuceneUtil.getAnalyzer(),LuceneUtil.getMaxFiledLenth());
		indexWriter.addDocument(document);
		//设置合并因子，既满足10个sfs文本一合并
		//indexWriter.setMergeFactor(10);
		indexWriter.close();
	}
	
	
	@Test
	public void findAll() throws Exception{
		String keywords="家";
		List<Article> articleList=new ArrayList<Article>();
		
		QueryParser queryParser=new QueryParser(LuceneUtil.getVersion(),"content",LuceneUtil.getAnalyzer());
		Query query=queryParser.parse(keywords);
		IndexSearcher indexSearcher=new IndexSearcher(LuceneUtil.getDirectory());
		TopDocs topDocs=indexSearcher.search(query, 100);
		for(int i=0;i<topDocs.scoreDocs.length;i++){
			ScoreDoc scoreDoc=topDocs.scoreDocs[i];
			int no=scoreDoc.doc;
			Document document=indexSearcher.doc(no);
			Article article=(Article)LuceneUtil.document2javabean(document, Article.class);
			articleList.add(article);
		}
		for (Article a : articleList) {
			System.out.println(a);
		}
	}
	
	//======================解决速度问题
	/**
	 * 使用RAMDirectory,类似与内存索引库，能解决时的读取索引库文件的速度问题
	 */
	@Test
	public void type4() throws Exception{
		Article article=new Article(1,"培训","传智是一家it培训机构",10);
		
		Document document=LuceneUtil.javabean2document(article);
		
		//硬盘索引库
		Directory fsDirectory=FSDirectory.open(new File("E:/IndexDBDBDB"));
		
		//内存索引库,因为硬盘索引库中的内容要同步到内粗索引库中
		Directory ramDirectory=new RAMDirectory(fsDirectory);
		
		//指向硬盘索引库的字符流 true,表示如果内存索引库中和硬盘索引库中有相同的document对象时，先删除硬盘中document删除
		//再将内存索引库的document对象写入硬盘索引库中
		//反之是false,默认为false,这个boolean值写在硬盘字符流的构造器中
		IndexWriter fsIndexWriter=new IndexWriter(fsDirectory,LuceneUtil.getAnalyzer(),true,LuceneUtil.getMaxFiledLenth());
		
		//指向内存索引库的字符流
		IndexWriter ramIndexWriter=new IndexWriter(ramDirectory,LuceneUtil.getAnalyzer(),LuceneUtil.getMaxFiledLenth());
		
		//将document对象写入内存索引库
		ramIndexWriter.addDocument(document);
		ramIndexWriter.close();
		
		//将内存索引库的所有document对象同步到硬盘索引库中
		fsIndexWriter.addIndexesNoOptimize(ramDirectory);
		fsIndexWriter.close();
	}

}
