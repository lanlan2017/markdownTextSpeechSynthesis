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
		String testStr="99.9999%的servlet都是HttpServlet的子类。\r\n" + 
				"\r\n" + 
				"在实际中,99.9%的servlet都会覆盖doGet()或doPost()方法。\r\n" + 
				"\r\n" + 
				"注意，servlet没有main()方法。servlet的生命周期方法，如doGet()方法由容器调用。\r\n" + 
				"\r\n" + 
				"servlet从doGet()方法的参数列表中拿到容器创建的请求和响应对象的引用。";
		replacePercentage(testStr);
	}
}
