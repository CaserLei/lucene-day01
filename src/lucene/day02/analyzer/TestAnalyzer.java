package lucene.day02.analyzer;

import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.fr.FrenchAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import lucene.day02.util.LuceneUtil;

/**
 * 测试Lucene内置和第三方分词器的分词效果
 * @author leifeng
 * 2016年9月11日
 */
public class TestAnalyzer {
	private static void testAnalyzer(Analyzer analyzer,String text) throws Exception{
		System.out.println("当前使用的分词器："+analyzer.getClass());
		TokenStream tokenStream=analyzer.tokenStream("content", new StringReader(text));
		tokenStream.addAttribute(TermAttribute.class);
		while(tokenStream.incrementToken()){
			TermAttribute termAttribute=tokenStream.getAttribute(TermAttribute.class);
			System.out.println(termAttribute.term());
		}
	}
	
	public static void main(String[] args) throws Exception {
		//Lucene内置分词器
		//testAnalyzer(new StandardAnalyzer(LuceneUtil.getVersion()), "传智播客说我们的首都是北京呀it");
		//testAnalyzer(new FrenchAnalyzer(LuceneUtil.getVersion()), "传智播客说我们的首都是北京呀it");
		//testAnalyzer(new CJKAnalyzer(LuceneUtil.getVersion()), "传智播客说我们的首都是北京呀it");
		testAnalyzer(new IKAnalyzer(), "传智播客说我们的首都是北京呀it");
	}

}
