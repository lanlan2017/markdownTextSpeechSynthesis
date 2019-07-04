package replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tools.io.properties.ContainSpecialWordsProperties;
import tools.io.properties.SpecialWordsProperties;

public class MDCodeReplace
{
	/**
	 * ���������Щ���ʵ����.
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
	 * �滻�����ַ�.
	 * 
	 * @param matcherStr.
	 * @return
	 */
	public static String replaceSpecialChars(String matcherStr)
	{
		matcherStr = matcherStr.replace("=", "����");
		matcherStr = matcherStr.replace("-", "��");
		matcherStr = matcherStr.replace("&", "�������");
		matcherStr = matcherStr.replace("?", "�ʺ�");
		matcherStr = matcherStr.replace("*", "�Ǻ�");
		matcherStr = matcherStr.replace("/", "б��");
		matcherStr = matcherStr.replace("\\", "��б��");
		matcherStr = matcherStr.replace(":", "ð��");
		matcherStr = matcherStr.replace("$", "��Ԫ");
		matcherStr = matcherStr.replace("#", "����");
		matcherStr = matcherStr.replace(".", "��");
		return matcherStr;
	}
	/**
	 * ����ƥ���ض�������ʽ�����.
	 * 
	 * @param matcherStr
	 * @return
	 */
	public static String replaceMatcher(String matcherStr)
	{
		// HTML����
		Pattern htmlTagPattern = Pattern.compile("<(.+?)/?>");
		Matcher htmlTagMatcher = htmlTagPattern.matcher(matcherStr);
		// ����Ǹ��������
		if (matcherStr.matches("-\\d+"))
		{
			// System.out.println("ƥ�䵽����: " + matcherStr);
			matcherStr = matcherStr.replace("-", "��");
		}
		// �����html��ǩ
		if (htmlTagMatcher.matches())
		{
			matcherStr = htmlTagMatcher.replaceAll("$1");
		}

		return matcherStr;
	}
	/**
	 * �滻���׶���ĵ���.
	 * 
	 * @param matcherStr
	 *            ���׶���ĵ���.
	 * @return
	 */
	public static String replaceSpecialWords(String matcherStr)
	{
		matcherStr = SpecialWordsProperties.replaceByVlaue(matcherStr);
		return matcherStr;
	}
}
