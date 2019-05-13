package replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ɾ������֮��Ŀո�.
 */
public class ReplaceSpaceInChineses
{
	public static String replaceSpaceInChineses(String line)
	{
		Pattern spaceInChPT = Pattern
				.compile("([\\u4e00-\\u9fa5])[ ]+([\\u4e00-\\u9fa5])");
		Matcher sepaceInChMT = spaceInChPT.matcher(line);
		return sepaceInChMT.replaceAll("$1$2");
	}
	public static void main(String[] args)
	{
		String testStr = "Spring MVC��Spring���������WebӦ�ÿ��ٿ� ����һ��ģ�飬�ǵ��������е�Web�������֮һ�� ������Servlet��JSP��Spring MVC��ѧϰָ�ϡ�ȫ�����ݷ�Ϊ�������֣���һ������Ҫ����Servlet��JSP����֪ʶ�ͼ�����������1������15�£���2������Ҫ����Spring MVC��������16������24�¡���󣬸�¼�� �ָ�����Tomcat��װ������ָ������������Servlet and JSPע���Լ�SSL֤�顣 �������ݳ�ʵ�������������ǳ��ʺ�Web�������� ���ǻ���Java��WebӦ�ÿ������Ķ���";
		System.out.println(testStr);
		System.out.println(
				"-----------------------------------------------------------------------------");
		System.out.println(replaceSpaceInChineses(testStr));
	}
}
