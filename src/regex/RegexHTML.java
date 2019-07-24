package regex;

public class RegexHTML
{
	/**
	 * 匹配Html代码的正则表达式.
	 */
	public final static String htmlRegex = "<(.+?)>(.+)</\\1>";
	/**
	 * 讯飞语音朗读正确标记
	 */
	public final static String correctReading = "<!\\-\\-replace:(.+)\\-\\->";

}
