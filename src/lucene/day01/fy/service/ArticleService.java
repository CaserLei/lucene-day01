package lucene.day01.fy.service;

import java.util.List;

import org.apache.commons.collections.set.SynchronizedSortedSet;

import lucene.day01.fy.dao.ArticleDao;
import lucene.day01.fy.entity.Article;
import lucene.day01.fy.entity.Page;


/**
 * 业务层
 * @author leifeng
 * 2016年9月11日
 */
public class ArticleService {
	//持久层
	private ArticleDao articleDao=new ArticleDao();
	
	/**
	 * 根据关键字和页号，查询内容
	 */
	public Page show(String keyWords,int currPageNo) throws Exception{
		Page page=new Page();
		//封装当前页号
		page.setCurrPageNO(currPageNo);
		//封装总记录数
		int allRecordNo=articleDao.getAllRecord(keyWords);
		page.setAllRecordNo(allRecordNo);
		
		//封装总页数
		int allPageNO=0;
		if(page.getAllRecordNo() % page.getPerPageSize()==0){
			allPageNO=page.getAllRecordNo()/page.getPerPageSize();
		}else{
			allPageNO=page.getAllRecordNo()/ page.getPerPageSize()+1;
		}
		page.setAllPageNO(allPageNO);
		
		//封装内容
		int size=page.getPerPageSize();
		int start=(page.getCurrPageNO()-1)* size;
		List<Article> articleList=articleDao.findAll(keyWords, start, size);
		page.setArticleList(articleList);
		
		return page;
	}
	
	
	public static void main(String[] args) throws Exception {
		ArticleService test=new ArticleService();
		Page page=test.show("培训", 4);
		
		System.out.println(page.getCurrPageNO());
		System.out.println(page.getPerPageSize());
		System.out.println(page.getAllRecordNo());
		System.out.println(page.getAllPageNO());
		for(Article a:page.getArticleList()){
			System.out.println(a);
		}

	}

}
