package replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 删除中文之间的空格.
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
		String testStr = "Spring MVC是Spring框架中用于Web应用快速开 发的一个模块，是当今最流行的Web开发框架之一。 本书是Servlet、JSP和Spring MVC的学习指南。全书内容分为两个部分，第一部分主要介绍Servlet和JSP基础知识和技术，包括第1章至第15章；第2部分主要介绍Spring MVC，包括第16章至第24章。最后，附录部 分给出了Tomcat安装和配置指导，还介绍了Servlet and JSP注解以及SSL证书。 本书内容充实、讲解清晰，非常适合Web开发者尤 其是基于Java的Web应用开发者阅读。";
		System.out.println(testStr);
		System.out.println(
				"-----------------------------------------------------------------------------");
		System.out.println(replaceSpaceInChineses(testStr));
	}
}
