package replace;

public class MDCodeReplace
{
	/**
	 * @param matcherStr
	 * @return
	 */
	public static String replaceContainSpecialWords(String matcherStr)
	{
		matcherStr = matcherStr.replace("javax", "java x");
		matcherStr = matcherStr.replace("https", "http s");
		matcherStr = matcherStr.replace("JavaEE", "java E E");
		return matcherStr;
	}
	/**
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
	 * @param matcherStr
	 * @return
	 */
	public static String replaceMatcher(String matcherStr)
	{
		if (matcherStr.matches("-\\d+"))
		{
//			System.out.println("匹配到负数: " + matcherStr);
			matcherStr = matcherStr.replace("-", "负");
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
