package replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceEnglishString
{
	/**
	 * ���Ӣ���ַ���.
	 * 
	 * @param text
	 *            һ���ı�.
	 * @return ����շ�������֮����ı�.
	 */
	public static String replaceEnglish(String text)
	{
		Pattern englishPattern = Pattern.compile("[a-zA-Z]+");
		Matcher englishMatcher = englishPattern.matcher(text);

		StringBuffer sb = new StringBuffer();
		String line = null;
		// find()������ǰ����ƥ����Ӵ�
		boolean englishFind = false;
		while ((englishFind = englishMatcher.find()))
		{
			if (englishFind)
			{
				line = englishMatcher.group();// ���Ч��Ҳ����
				// ���java�ؼ���.
				line = ReplaceJavaKeyWord.replaceJavaKeyWord(line);
				englishMatcher.appendReplacement(sb, line);
			}

		}
		// ���ı���û��ƥ���ʣ�µ��ı�Ҳ���뵽StringBuffer��
		englishMatcher.appendTail(sb);
		return sb.toString();
	}
}
