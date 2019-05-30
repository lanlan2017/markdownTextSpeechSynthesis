package tools.io.properties;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class ContainSpecialWordsProperties
{
	// 1.创建配置文件对象
	private static Properties pr = null;
	private static Set<String> keySet;
	// 3.使用当前配置文件中的文本替换匹配的文本
	public static String repalceByProperties(String input)
	{
		if (pr == null)
		{
			// try
			// {
			// pr = new Properties();
			// // 2.加载配置文件
			// pr.load(new InputStreamReader(
			// new FileInputStream(
			// new File("ContainSpecialWords.properties")),
			// "utf-8"));
			// // 获取所有的key
			// keySet = pr.stringPropertyNames();
			// } catch (IOException e)
			// {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			pr = PropertiesInstance
					.getPropertiesInstanceUTF8("ContainSpecialWords.properties");
			 keySet = pr.stringPropertyNames();
		}
		for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();)
		{
			String key = (String) iterator.next();
			if (input.contains(key))
			{
				input = input.replace(key, pr.getProperty(key));
			}
		}
		return input;
	}
//	public static void main(String[] args)
//	{
//		System.out.println(repalceByProperties("javax.https."));
//	}
}
