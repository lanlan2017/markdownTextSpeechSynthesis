package replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import clipboard.util.SysClipboardUtil;
import static regex.RegexHTML.htmlRegex;;

public class RemoveHtmlTags
{
	/**
	 * �Ƴ�Ƕ�׵�HTML��ǩ,����<code><center><strong>��19.3input��ǩ������</strong></center></code>�����ı�ǩ.
	 * 
	 * @param input
	 * @return
	 */
	public static String removeHtmlDoubleTags(String input)
	{
		// Pattern htmlTagsPattern=Pattern.compile("<(.+?)>(.+)</\\1>");
		Pattern htmlTagsPattern = Pattern.compile(htmlRegex);

		Matcher htmlTagsMatcher = htmlTagsPattern.matcher(input);
		while (htmlTagsMatcher.find())
		{
			input = htmlTagsMatcher.replaceAll("$2");
			// �ٴ�ƥ��ɾ��һ����ǩ��֮������
			htmlTagsMatcher = htmlTagsPattern.matcher(input);
		}
		return input;
	}

	public static void main(String[] args)
	{
		String input = SysClipboardUtil.getSysClipboardText();
		System.out.println(removeHtmlDoubleTags(input));
	}
}
