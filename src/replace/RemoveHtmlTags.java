package replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import clipboard.util.SysClipboardUtil;
import static regex.RegexHTML.htmlRegex;;

public class RemoveHtmlTags
{
	/**
	 * 移除嵌套的HTML标签,类似<code><center><strong>表19.3input标签的属性</strong></center></code>这样的标签.
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
			// 再次匹配删除一个标签对之后的情况
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
