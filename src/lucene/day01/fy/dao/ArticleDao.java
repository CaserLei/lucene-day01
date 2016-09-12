package lucene.day01.fy.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.junit.Test;

import lucene.day01.fy.entity.Article;
import lucene.day01.util.LuceneUtil;


/**
 * 持久层
 * @author leifeng
 * 2016年9月11日
 */
public class ArticleDao {
	/**
	 * 根据关键字，获取总记录
	 */
	public int getAllRecord(String keyWords) throws Exception{
		//List<Article> articleList=new ArrayList<Article>();
		QueryParser queryParser=new QueryParser(LuceneUtil.getVersion(), "content", LuceneUtil.getAnalyzer());
		Query query=queryParser.parse(keyWords);
		IndexSearcher indexSearcher=new IndexSearcher(LuceneUtil.getDirectory());
		TopDocs topDocs=indexSearcher.search(query, 2);
		//返回符合条件的真实总记录数，不受2的影响
	    return topDocs.totalHits;
		//返回符合条件的真实总记录数，受2的影响
		//return topDocs.scoreDocs.length;
	}
	
	/**
	 * 根据关键字，批量查询记录
	 * @param start 从第几条记录的索引号开始查询，索引号从0开始
	 * @param size 最多查询几条记录
	 * @return 集合
	 */
	public List<Article> findAll(String keyWords,int start,int size) throws Exception{
		List<Article> articleList=new ArrayList<Article>();
		QueryParser queryPaser=new QueryParser(LuceneUtil.getVersion(),"content",LuceneUtil.getAnalyzer());
		Query query=queryPaser.parse(keyWords);
		IndexSearcher indexSearcher=new IndexSearcher(LuceneUtil.getDirectory());
		TopDocs topDocs=indexSearcher.search(query, 100);
		int middle=Math.min(start+size, topDocs.totalHits);
		for (int i = start; i < middle; i++) {
			ScoreDoc scoreDoc=topDocs.scoreDocs[i];
			int no=scoreDoc.doc;
			Document document=indexSearcher.doc(no);
			Article article=(Article)LuceneUtil.document2javabean(document, Article.class);
			articleList.add(article);
		}
		return articleList;
	}
	
	public static void main(String[] args) throws Exception {
		ArticleDao dao=new ArticleDao();
		System.out.println(dao.getAllRecord("培训"));
		
		System.out.println("=====================================");
		System.out.println("第一页");
		List<Article> list=dao.findAll("培训", 0, 2);
		for (Article article : list) {
			System.out.println(article);
		}
		System.out.println("=====================================");
		System.out.println("第二页");
		List<Article> list2=dao.findAll("培训", 2, 2);
		for (Article article : list2) {
			System.out.println(article);
		}
		System.out.println("=====================================");
		System.out.println("第三页");
		List<Article> list3=dao.findAll("培训", 4, 2);
		for (Article article : list3) {
			System.out.println(article);
		}
		System.out.println("=====================================");
		System.out.println("第四页");
		List<Article> list4=dao.findAll("培训", 6, 2);
		for (Article article : list4) {
			System.out.println(article);
		}
		
	}

}
