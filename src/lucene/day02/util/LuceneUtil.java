package lucene.day02.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import lucene.day01.entity.Article;


/**
 * 工具类
 * @author leifeng
 * 2016年9月8日
 */
public class LuceneUtil {
	private static Directory directory;
	private static Version version;
	private static Analyzer analyzer;
	private static MaxFieldLength maxFiledLenth;
	
	static{
		try {
			directory=FSDirectory.open(new File("E:/IndexDBDBDB"));
			version=Version.LUCENE_30;
			analyzer=new StandardAnalyzer(version);
			maxFiledLenth=MaxFieldLength.LIMITED;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	//防止外界new 该帮助类
	private LuceneUtil(){}
	
	public static Directory getDirectory() {
		return directory;
	}
	public static Version getVersion() {
		return version;
	}
	public static Analyzer getAnalyzer() {
		return analyzer;
	}
	public static MaxFieldLength getMaxFiledLenth() {
		return maxFiledLenth;
	}
	//将JavaBean转换成Doucument对象
	public static Document javabean2document(Object obj) throws Exception{
		//创建Documet对象
		Document document=new Document();
		//获取字节码，获取obj引用对象字节码
		Class clazz=obj.getClass();
		//通过对象字节码获取稀有属性
		java.lang.reflect.Field[] reflectFields=clazz.getDeclaredFields();
		//迭代
		for (java.lang.reflect.Field reflectField : reflectFields) {
			//强力反射
			reflectField.setAccessible(true);
			//获取属性名
			String name=reflectField.getName();
			//手工拼接方法名
			String methodName="get"+name.substring(0, 1).toUpperCase()+name.substring(1);
			//获取方法
			Method method=clazz.getMethod(methodName, null);
			//执行方法
			String value=method.invoke(obj, null).toString();
			document.add(new Field(name,value,Store.YES,Index.ANALYZED));
		}
		
		
		return document;
	}
	
	//将Doucument对象转换成JavaBean对象
	public static Object document2javabean(Document document,Class clazz) throws Exception{
		Object obj=clazz.newInstance();
		java.lang.reflect.Field[] reflectFields=clazz.getDeclaredFields();
		//迭代
		for (java.lang.reflect.Field reflectField : reflectFields) {
			//强力反射
			reflectField.setAccessible(true);
			//获取属性名
			String name=reflectField.getName();
			String value=document.get(name);
			BeanUtils.setProperty(obj, name, value);
		}
		return obj;
	}
	
	
	//测试
	public static void main(String[] args) throws Exception {
		Article article=new Article(1,"培训","传智是一家培训机构");
		Document document=LuceneUtil.javabean2document(article);
		
		System.out.println(document.get("id"));
		System.out.println(document.get("title"));
		System.out.println(document.get("content"));
		
		System.out.println("===========================");
		
		Article article2=(Article)LuceneUtil.document2javabean(document,Article.class);
		
		System.out.println(article2);
		
	}

}
