package lucene.day02.bean2json;

/**
 * 用户
 * @author leifeng
 * 2016年9月12日
 */
public class User {
	private Integer id;//编号
	private String name;//姓名
	private Integer sal;//薪水
	
	public User(){}

	public User(Integer id, String name, Integer sal) {
		this.id = id;
		this.name = name;
		this.sal = sal;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSal() {
		return sal;
	}

	public void setSal(Integer sal) {
		this.sal = sal;
	}
	

}
