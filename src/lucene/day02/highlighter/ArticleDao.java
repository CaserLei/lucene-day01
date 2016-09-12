package lucene.day02.highlighter;

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
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

import lucene.day02.util.LuceneUtil;
import lucene.day02.entity.Article;

/**
 * 搜索结果中关键字高亮
 * @author leifeng
 * 2016年9月11日
 */
public class ArticleDao {
	/**
	 * 增加document对象索引库
	 * 问题引入，运行5次
	 */
	@Test
	public void add() throws Exception{
		Article article=new Article(1,"培训","传智是一家it培训机构",10);
		Document document=LuceneUtil.javabean2document(article);
		IndexWriter indexWriter=new IndexWriter(LuceneUtil.getDirectory(),LuceneUtil.getAnalyzer(),LuceneUtil.getMaxFiledLenth());
		indexWriter.addDocument(document);
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
		
		//以下代码对内容中含有关键字的字符串高亮显示
		//格式高亮对象
		Formatter formatter=new SimpleHTMLFormatter("<font color='red'>","</font>");
		//关键字
		Scorer scorer=new QueryScorer(query);
		//高亮对象
		Highlighter highlighter=new Highlighter(formatter, scorer);
		
		for(int i=0;i<topDocs.scoreDocs.length;i++){
			ScoreDoc scoreDoc=topDocs.scoreDocs[i];
			int no=scoreDoc.doc;
			
			//关键字没有高亮
			Document document=indexSearcher.doc(no);
			
			//关键字高亮
			String contentHighlighter=highlighter.getBestFragment(LuceneUtil.getAnalyzer(), "content", document.get("content"));
			
			document.getField("content").setValue(contentHighlighter);
			
			
			Article article=(Article)LuceneUtil.document2javabean(document, Article.class);
			articleList.add(article);
		}
		for (Article a : articleList) {
			System.out.println(a);
		}
	}
	
	

}
