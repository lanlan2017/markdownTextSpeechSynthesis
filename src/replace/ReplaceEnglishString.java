package replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceEnglishString
{
	/**
	 * 拆分英文字符串.
	 * 
	 * @param text
	 *            一串文本.
	 * @return 拆分驼峰命名法之后的文本.
	 */
	public static String replaceEnglish(String text)
	{
		Pattern englishPattern = Pattern.compile("[a-zA-Z]+");
		Matcher englishMatcher = englishPattern.matcher(text);

		StringBuffer sb = new StringBuffer();
		String line = null;
		while (englishMatcher.find())
		{
			line = englishMatcher.group();
			// 拆分java关键字.
			line = ReplaceJavaKeyWord.replaceJavaKeyWord(line);
			englishMatcher.appendReplacement(sb, line);
		}
		// 把文本中没有匹配的剩下的文本也加入到StringBuffer中
		englishMatcher.appendTail(sb);
		return sb.toString();
	}
}
