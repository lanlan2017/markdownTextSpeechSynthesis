package replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Replace
{
	public static String replaceTitle(String text)
	{
		Pattern titlePattern = Pattern.compile("(?:\\w+)?(?:(\\.)\\w+)+");
		Matcher titleMatcher = titlePattern.matcher(text);
		StringBuffer sb = new StringBuffer();
		String line = null;
		boolean titleFind = false;
		int end=0;
		// find()方法向前查找匹配的子串
		while ((titleFind = titleMatcher.find()))
		{
			if (titleFind)
			{
//				start=titleMatcher.start();
				end=titleMatcher.end();
//				System.out.println(text.charAt(end));
				//99.99999%这种情况不要替换点号
				if('%'==text.charAt(end))
				{
					continue;
				}
//				System.out.println(text.charAt(end)+1);
				
				// 获取位置在匹配子串之前的文本strbefore，获取匹配的子串strmacther，然后把匹配的子串strmacther替换为replacement，
				// 然后(strbefore+replacement)追加到StringBuffer中
				
				line = titleMatcher.group();
				line = line.replaceAll("\\.", "点");
				titleMatcher.appendReplacement(sb, line);
			}
		}
		// 把文本中没有匹配的剩下的文本也加入到StringBuffer中
		titleMatcher.appendTail(sb);
		return sb.toString();
	}
	public static String replaceEnglish(String text)
	{
		Pattern englishPattern = Pattern.compile("[a-zA-Z]+");
		Matcher englishMatcher = englishPattern.matcher(text);

		StringBuffer sb = new StringBuffer();
		String line = null;
		// find()方法向前查找匹配的子串
		boolean englishFind = false;
		while ((englishFind = englishMatcher.find()))
		{
			if (englishFind)
			{
				// 获取位置在匹配子串之前的文本strbefore，获取匹配的子串strmacther，然后把匹配的子串strmacther替换为replacement，
				// 然后(strbefore+replacement)追加到StringBuffer中
//				line = "`".concat(englishMatcher.group().concat("`"));
//				line = "`".concat(englishMatcher.group());//这个效果还行
//				line =" ".concat(englishMatcher.group());//这个效果也还行
				
				line =englishMatcher.group();//这个效果也还行
//				拆分java关键字.
				line=ReplaceJavaKeyWord.replaceJavaKeyWord(line);
				
				englishMatcher.appendReplacement(sb, line);
			}

		}
		// 把文本中没有匹配的剩下的文本也加入到StringBuffer中
		englishMatcher.appendTail(sb);
		return sb.toString();
	}
	public static void main(String[] args)
	{
		 String text = "99.9999%的servlet都是HttpServlet的子类。\r\n" + 
		 		"\r\n" + 
		 		"在实际中,99.9%的servlet都会覆盖doGet()或doPost()方法。\r\n" + 
		 		"\r\n" + 
		 		"注意，servlet没有main()方法。servlet的生命周期方法，如doGet()方法由容器调用。\r\n" + 
		 		"\r\n" + 
		 		"servlet从doGet()方法的参数列表中拿到容器创建的请求和响应对象的引用。";
		// StringBuffer sb=new StringBuffer(text);
		// replaceTitle(text);
		 System.out.println(replaceTitle(text));

//		String text = "java.lang.Object";
//		System.out.println(replaceTitle(text));

	}
}
