package lucene.day02.sort;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.junit.Test;

import lucene.day02.entity.Article;
import lucene.day02.util.LuceneUtil;
/**
 * 演示lucene根据单个或多个字段排序
 * @author leifeng
 * 2016年9月12日
 */
public class ArticleDao1 {
	/**
	 * 增加document对象索引库
	 * 问题引入，运行5次
	 */
	@Test
	public void add() throws Exception{
		//Article article=new Article(1,"培训","传智是一家it培训机构",10);
		//Article article=new Article(2,"培训","北大是一家it培训机构",20);
		//Article article=new Article(3,"培训","中大是一家it培训机构",20);
		Article article=new Article(4,"培训","小大是一家it培训机构",30);
		Document document=LuceneUtil.javabean2document(article);
		IndexWriter indexWriter=new IndexWriter(LuceneUtil.getDirectory(),LuceneUtil.getAnalyzer(),LuceneUtil.getMaxFiledLenth());
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
		
		
		//按得分度高低排序
		//TopDocs topDocs=indexSearcher.search(query, 100);
		
		//创建排序对象
		//参数一：id表示依据document对象中的那个字段排序，列如：id
		//参数二：SortField.INT表示document对象中该字段的类型，以常量方式书写
		//参数三：true表示降序，类似于order by id desc
		//      false表示升序，类似于order by id asc 
		//Sort sort=new Sort(new SortField("id", SortField.INT,true));
		
		//按count字段的降序排列，如果count字段相同的话，在按id升序排序
		Sort sort=new Sort(new SortField("count",SortField.INT,true),new SortField("id", SortField.INT,false));
		
		
		//sort表示排序的条件
		TopDocs topDocs=indexSearcher.search(query, null,100,sort);
		
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
	

}
