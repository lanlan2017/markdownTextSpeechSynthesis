package replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceJavaKeyWord
{
	/**
	 * 拆分驼峰命名法,在大写前面加上空格.
	 * 
	 * @param englishWord
	 *            英文单词.
	 * @return javaKeyWord 大写字母前面加上空格的字符字符串.
	 */
	public static String replaceJavaKeyWord(String englishWord)
	{
		// 替换驼峰
		Pattern javaKeyWordPattern = Pattern.compile("([A-Z][a-z]+)");
		// java关键字匹配器
		Matcher javaKeyWordMatcher;
		javaKeyWordMatcher = javaKeyWordPattern.matcher(englishWord);
		String javaKeyWord = null;
		// 在所有的大写字符之前添加一个空格
		if (javaKeyWordMatcher.find())
		{
			javaKeyWord = javaKeyWordMatcher.replaceAll(" $1");
		}
		// 如果这个单词不包含大写字母
		if (javaKeyWord == null)
			javaKeyWord = " " + englishWord;
		// 如果处理后这个单词前面没有带空格,这说明这个单词是小写字母
		else if (!javaKeyWord.startsWith(" "))
			javaKeyWord = " " + javaKeyWord;
		return javaKeyWord;
	}
//	public static void main(String[] args)
//	{
//		String testStr = "ReplaceJavaKeyWord";
//		String testStr1 = "replaceJavaKeyWord";
//		String testStr2 = "replace";
//		String testStr4 = "Java";
//		String testStr5 = "doGet()";
//		String testStr6 = "String()";
//		System.out.println(replaceJavaKeyWord(testStr));
//		System.out.println(replaceJavaKeyWord(testStr1));
//		System.out.println(replaceJavaKeyWord(testStr2));
//		System.out.println(replaceJavaKeyWord(testStr4));
//		System.out.println(replaceJavaKeyWord(testStr5));
//		System.out.println(replaceJavaKeyWord(testStr6));
//	}
}
