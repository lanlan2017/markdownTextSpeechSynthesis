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
		return matcherStr;
	}
	/**
	 * @param matcherStr
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
//			System.out.println("ƥ�䵽����: " + matcherStr);
			matcherStr = matcherStr.replace("-", "��");
		}
		return matcherStr;
	}
	/**
	 * �滻���׶���ĵ���.
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
			case "GET" :
				matcherStr = "get";
			default :
				break;
		}

		return matcherStr;
	}
}
