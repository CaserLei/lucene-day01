package lucene.day01.fy.entity;

import java.util.ArrayList;
import java.util.List;

/**'
 * 用于Article的分页类
 * @author leifeng
 * 2016年9月11日
 */
public class Page {
	private Integer currPageNO;//当前页号
	private Integer perPageSize=2;//每页显示记录数，默认2条
	private Integer allRecordNo;//总记录数
	private Integer allPageNO;//总页数
	private List<Article> articleList=new ArrayList<Article>();
	
	public Page(){}

	public Integer getCurrPageNO() {
		return currPageNO;
	}

	public void setCurrPageNO(Integer currPageNO) {
		this.currPageNO = currPageNO;
	}


	public Integer getPerPageSize() {
		return perPageSize;
	}

	public void setPerPageSize(Integer perPageSize) {
		this.perPageSize = perPageSize;
	}

	public Integer getAllRecordNo() {
		return allRecordNo;
	}

	public void setAllRecordNo(Integer allRecordNo) {
		this.allRecordNo = allRecordNo;
	}

	public Integer getAllPageNO() {
		return allPageNO;
	}

	public void setAllPageNO(Integer allPageNO) {
		this.allPageNO = allPageNO;
	}

	public List<Article> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<Article> articleList) {
		this.articleList = articleList;
	}
	

}
