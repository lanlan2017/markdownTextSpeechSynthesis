package replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceJavaKeyWord
{
	/**
	 * ����շ�������,�ڴ�дǰ����Ͽո�.
	 * @param englishWord Ӣ�ĵ���.
	 * @return javaKeyWord ��д��ĸǰ����Ͽո���ַ��ַ���.
	 */
	public static String replaceJavaKeyWord(String englishWord)
	{
		//java�ؼ���ģ��
		Pattern javaKeyWordPattern=Pattern.compile("(?=[A-Z])([A-Z][a-z]+)");
		//java�ؼ���ƥ����
		Matcher javaKeyWordMatcher;
		javaKeyWordMatcher=javaKeyWordPattern.matcher(englishWord);
		String javaKeyWord = null;
		while (javaKeyWordMatcher.find())
		{
			javaKeyWord=javaKeyWordMatcher.replaceAll(" $1");
		}
		//��������ƥ������:����java,static���ֵ������ʲ�������ĸû�д�д�ĵ���.
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
