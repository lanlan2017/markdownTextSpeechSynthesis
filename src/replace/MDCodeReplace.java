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

		// �ʶ�Java����
		matcherStr = replaceJavaMethod(matcherStr);
		// �ʶ�ȫ��д�ַ���
		matcherStr = replaceStringAllUpperCase(matcherStr);

		// �����html��ǩ
		if (htmlTagMatcher.matches())
		{
			// �Ƴ�html��ǩ��`<`��`/?>`
			matcherStr = htmlTagMatcher.replaceAll("$1");
		}
		return matcherStr;
	}
	/**
	 * java�������ʶ�����.
	 * 
	 * @param matcherStr
	 *            java���������ַ���.
	 * @return �ʶ����java����.
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
			// ��ȡƥ�䵽��һ������
			group1 = matcher.group(1);
			group2 = matcher.group(2);
			group3 = matcher.group(3);
			System.out.println("group1-->" + group1);
			System.out.println("group2-->" + group2);
			System.out.println("group3-->" + group3);

			matcherStr = group2 + "����,�÷������� " + group1;
			if (!"".equals(group3))
			{
				matcherStr = matcherStr + " �÷��������б� " + group3;
			} else
			{
				matcherStr = matcherStr + " �÷������޲η��� ";
			}
			matcherStr = matcherStr + " �÷������� ";
			System.out.println("�滻���:" + matcherStr);
		}
		return matcherStr;
	}
	/**
	 * �ʶ�ȫ��д���ַ���.
	 * 
	 * @param matcherStr
	 *            ȫ����д���ַ���.
	 * @return ������д�ֿ����ַ���.
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
