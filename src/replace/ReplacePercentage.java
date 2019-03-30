package replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplacePercentage
{
	public static String replacePercentage(String line)
	{
		Pattern percentPattern=Pattern.compile("\\d+\\.\\d+%");
		Matcher percent=percentPattern.matcher(line);
		while(percent.find())
		{
			System.out.println(percent.group());
		}
		return null;
	}
	public static void main(String[] args)
	{
		String testStr="99.9999%��servlet����HttpServlet�����ࡣ\r\n" + 
				"\r\n" + 
				"��ʵ����,99.9%��servlet���Ḳ��doGet()��doPost()������\r\n" + 
				"\r\n" + 
				"ע�⣬servletû��main()������servlet���������ڷ�������doGet()�������������á�\r\n" + 
				"\r\n" + 
				"servlet��doGet()�����Ĳ����б����õ������������������Ӧ��������á�";
		replacePercentage(testStr);
	}
}
