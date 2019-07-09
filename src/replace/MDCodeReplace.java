package replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tools.io.properties.ContainSpecialWordsProperties;
import tools.io.properties.SpecialWordsProperties;

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
		matcherStr = ContainSpecialWordsProperties
				.repalceByProperties(matcherStr);
		return matcherStr;
	}
	/**
	 * 替换特殊字符.
	 * 
	 * @param matcherStr.
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
		matcherStr = matcherStr.replace("$", "美元");
		matcherStr = matcherStr.replace("#", "井号");
		matcherStr = matcherStr.replace(".", "点");
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

		// 朗读Java方法
		matcherStr = replaceJavaMethod(matcherStr);
		// 朗读全大写字符串
		matcherStr = replaceStringAllUpperCase(matcherStr);

		// 如果是html标签
		if (htmlTagMatcher.matches())
		{
			// 移除html标签的`<`和`/?>`
			matcherStr = htmlTagMatcher.replaceAll("$1");
		}
		return matcherStr;
	}
	/**
	 * java方法的朗读规则.
	 * 
	 * @param matcherStr
	 *            java方法定义字符串.
	 * @return 朗读后的java方法.
	 */
	private static String replaceJavaMethod(String matcherStr)
	{
		Pattern pattern = Pattern
				.compile("((?:[a-zA-Z]+ )+([a-zA-Z]+))\\((.*)\\)");
		Matcher matcher = pattern.matcher(matcherStr);
		String group1;
		String group2;
		String group3;
		if (matcher.matches())
		{
			// 获取匹配到的一个分组
			group1 = matcher.group(1);
			group2 = matcher.group(2);
			group3 = matcher.group(3);
			System.out.println("group1-->" + group1);
			System.out.println("group2-->" + group2);
			System.out.println("group3-->" + group3);

			matcherStr = group2 + "方法,该方法定义 " + group1;
			if (!"".equals(group3))
			{
				matcherStr = matcherStr + " 该方法参数列表 " + group3;
			} else
			{
				matcherStr = matcherStr + " 该方法是无参方法 ";
			}
			matcherStr = matcherStr + " 该方法功能 ";
			System.out.println("替换结果:" + matcherStr);
		}
		return matcherStr;
	}
	/**
	 * 朗读全大写的字符串.
	 * 
	 * @param matcherStr
	 *            全部大写的字符串.
	 * @return 各个大写分开的字符串.
	 */
	private static String replaceStringAllUpperCase(String matcherStr)
	{

		Pattern pattern = Pattern.compile("[A-Z]+");
		Matcher matcher = pattern.matcher(matcherStr);
		StringBuffer sbBuffer = new StringBuffer(matcherStr.length() * 2);
		if (matcher.matches())
		{
			for (int i = 0, length = matcherStr.length(); i < length; i++)
			{
				sbBuffer.append(matcherStr.charAt(i) + " ");
			}

		}
		return sbBuffer.toString();
	}
	/**
	 * 替换容易读错的单词.
	 * 
	 * @param matcherStr
	 *            容易读错的单词.
	 * @return
	 */
	public static String replaceSpecialWords(String matcherStr)
	{
		matcherStr = SpecialWordsProperties.replaceByVlaue(matcherStr);
		return matcherStr;
	}
}
