package replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MDCodeReplace
{
	/**
	 * 处理包含这些单词的情况.
	 * 
	 * @param matcherStr
	 * @return
	 */
	public static String replaceContainSpecialWords(String matcherStr)
	{
		matcherStr = matcherStr.replace("javax", "java x");
		matcherStr = matcherStr.replace("https", "http s");
		matcherStr = matcherStr.replace("JavaEE", "java E E");
		matcherStr = matcherStr.replace("radiobutton", "radio button");
		matcherStr = matcherStr.replace("radio buttons", "radio buttons");
		return matcherStr;
	}
	/**
	 * 替换特殊字符.
	 * 
	 * @param matcherStr
	 * @return
	 */
	public static String replaceSpecialChars(String matcherStr)
	{
		matcherStr = matcherStr.replace("=", "等于");
		matcherStr = matcherStr.replace("-", "杠");
		matcherStr = matcherStr.replace("&", "单与符号");
		matcherStr = matcherStr.replace("?", "问号");
		matcherStr = matcherStr.replace("*", "星号");
		matcherStr = matcherStr.replace("/", "斜杠");
		matcherStr = matcherStr.replace("\\", "反斜杠");
		matcherStr = matcherStr.replace(":", "冒号");
		return matcherStr;
	}
	/**
	 * 处理匹配特定正则表达式的情况.
	 * 
	 * @param matcherStr
	 * @return
	 */
	public static String replaceMatcher(String matcherStr)
	{
		// HTML代码
		Pattern htmlTagPattern = Pattern.compile("<(.+?)/?>");
		Matcher htmlTagMatcher = htmlTagPattern.matcher(matcherStr);
		// 如果是负数的情况
		if (matcherStr.matches("-\\d+"))
		{
			// System.out.println("匹配到负数: " + matcherStr);
			matcherStr = matcherStr.replace("-", "负");
		}
		// 如果是html标签
		if (htmlTagMatcher.matches())
		{
			matcherStr = htmlTagMatcher.replaceAll("$1");
		}
		return matcherStr;
	}
	/**
	 * 替换容易读错的单词.
	 * 
	 * @param matcherStr
	 * @return
	 */
	public static String replaceSpecialWords(String matcherStr)
	{
		switch (matcherStr)
		{
			case "id" :
				matcherStr = "ID";
				break;
			case "MIME" :
				matcherStr = "M I M E";
				break;
			case "url" :
				matcherStr = "URL";
				break;
			case "GET" :
				matcherStr = "get";
				break;
			case "CRUD" :
				matcherStr = "C R U D";
				break;
			case "JavaEE" :
				matcherStr = "Java E E";
				break;
			default :
				break;
		}

		return matcherStr;
	}
}
