package lucene.day01.fy.entity;
/**
 * 文章
 * @author leifeng
 * 2016年8月29日
 */
public class Article {
	private Integer id;
	private String title;
	private String content;
	
	public Article() {}
	
	public Article(Integer id, String title, String content) {
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "编号：" + id + "\n标题:" + title + "\n内容:" + content ;
	}
	

}
