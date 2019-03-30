package replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceJavaKeyWord
{
	/**
	 * 拆分驼峰命名法,在大写前面加上空格.
	 * @param englishWord 英文单词.
	 * @return javaKeyWord 大写字母前面加上空格的字符字符串.
	 */
	public static String replaceJavaKeyWord(String englishWord)
	{
		//java关键字模板
		Pattern javaKeyWordPattern=Pattern.compile("(?=[A-Z])([A-Z][a-z]+)");
		//java关键字匹配器
		Matcher javaKeyWordMatcher;
		javaKeyWordMatcher=javaKeyWordPattern.matcher(englishWord);
		String javaKeyWord = null;
		while (javaKeyWordMatcher.find())
		{
			javaKeyWord=javaKeyWordMatcher.replaceAll(" $1");
		}
		//上面正则不匹配的情况:例如java,static这种单个单词并且首字母没有大写的单词.
		if(javaKeyWord==null)
		{
			javaKeyWord=englishWord;
		}
		if(!javaKeyWord.startsWith(" "))
		{
			javaKeyWord=" "+javaKeyWord;
		}
		return javaKeyWord;
	}
	public static String replaceSuffixName(String input)
	{
		
		return null;
	}
	public static void main(String[] args)
	{
		String testStr="ReplaceJavaKeyWord";
		String testStr1="replaceJavaKeyWord";
		String testStr2="replace";
		String testStr4="Java";
		String testStr5="doGet()";
		String testStr6="String()";
		System.out.println(replaceJavaKeyWord(testStr));
		System.out.println(replaceJavaKeyWord(testStr1));
		System.out.println(replaceJavaKeyWord(testStr2));
		System.out.println(replaceJavaKeyWord(testStr4));
		System.out.println(replaceJavaKeyWord(testStr5));
		System.out.println(replaceJavaKeyWord(testStr6));
	}
}
