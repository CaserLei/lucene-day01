package lucene.day02.sort;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.junit.Test;

import lucene.day02.entity.Article;
import lucene.day02.util.LuceneUtil;
/**
 * 在默认情况下，lucene是按照相关度得分排序的
 * @author leifeng
 * 2016年9月12日
 */
public class ArticleDao2 {
	/**
	 * 增加document对象索引库
	 * 问题引入，运行5次
	 */
	@Test
	public void add() throws Exception{
		//Article article=new Article(1,"培训","传智是一家it培训机构",10);
		//Article article=new Article(2,"培训","北大是一家it培训机构",20);
		Article article=new Article(3,"培训","中大是一家华南地区的it培训机构",30);
		//Article article=new Article(4,"培训","哈哈培训机构是好的培训",9);
		//Article article=new Article(5,"培训","培训培训培训培训培训培训培训培训培训培训培训培训培",15);
		//Article article=new Article(7,"培训","培训培训培训培训培训培训培训培训培训培训培训培培训内容培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训内容培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训字数培训内容培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训内容培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训字数培训内容培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训字数培训内容培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训字数培训内容培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训字数培训内容培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训字数培训内容培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训字数培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训字数培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训字数训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训培训",35);
		Document document=LuceneUtil.javabean2document(article);
		IndexWriter indexWriter=new IndexWriter(LuceneUtil.getDirectory(),LuceneUtil.getAnalyzer(),LuceneUtil.getMaxFiledLenth());
		//人工设置该documnet的得分
		document.setBoost(100F);
		
		indexWriter.addDocument(document);
		indexWriter.close();
	}
	
	
	
	@Test
	public void findAll() throws Exception{
		String keywords="培训";
		List<Article> articleList=new ArrayList<Article>();
		QueryParser queryParser=new QueryParser(LuceneUtil.getVersion(),"content",LuceneUtil.getAnalyzer());
		Query query=queryParser.parse(keywords);
		IndexSearcher indexSearcher=new IndexSearcher(LuceneUtil.getDirectory());
		TopDocs topDocs=indexSearcher.search(query, 100);
		
		for(int i=0;i<topDocs.scoreDocs.length;i++){
			ScoreDoc scoreDoc=topDocs.scoreDocs[i];
			int no=scoreDoc.doc;
			
			//获取document对象的评分
			float score=scoreDoc.score;
			System.out.println("score="+score);
			
			Document document=indexSearcher.doc(no);
			
			Article article=(Article)LuceneUtil.document2javabean(document, Article.class);
			articleList.add(article);
		}
		for (Article a : articleList) {
			System.out.println(a);
		}
	}
	

}
