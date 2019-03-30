package replace;

public class ReplaceHtmlXmlTags
{
	public static String replaceHtmlXmlTags(String input)
	{
		
		return input.replaceAll("<(.+?)>", "$1ÔªËØ");
	}
}
