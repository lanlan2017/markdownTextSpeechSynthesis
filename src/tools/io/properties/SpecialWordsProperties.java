package tools.io.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 匹配特定的单词.
 */
public class SpecialWordsProperties
{
	// 1.实例化配置文件对象
	private static Properties properties = null;
	/**
	 * 查找配置文件之中有没有相关的key.
	 * 
	 * @param keyToFind
	 *            想要查找的key.
	 * @return 如果找到返回true,如果没有找到返回false.
	 */
	public static String replace(String keyToFind)
	{
		if (properties == null)
		{
			try
			{
				// 1.创建配置文件列表
				properties = new Properties();
				// 2.加载配置文件
				properties
						.load(new InputStreamReader(
								new FileInputStream(
										new File("SpecialWords.properties")),
								"utf-8"));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return properties.getProperty(keyToFind, keyToFind);
	}
}
