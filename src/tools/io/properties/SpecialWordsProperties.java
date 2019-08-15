package tools.io.properties;

import java.util.Properties;

/**
 * 匹配特定的单词.
 */
public class SpecialWordsProperties
{
	// 1.实例化配置文件对象
	private static Properties properties = null;
	/**
	 * 使用配置文件中的value替换key
	 * 
	 * @param keyToFind
	 *            想要查找的key.
	 * @return 如果找到返回key对应的value表示替换成value,如果没有找到返回keyToFind表示不替换.
	 */
	//SpecialWordsProperties.replaceByVlaue
	public static String replaceByVlaue(String keyToFind)
	{
		// 单例模式获取.
		if (properties == null)
		{
			// 生成对象并加载所有配置
			properties = PropertiesInstance
					.getPropertiesInstanceUTF8("SpecialWords.properties");
		}
		return properties.getProperty(keyToFind, keyToFind);
	}
	public static void main(String[] args)
	{
		System.out.println(replaceByVlaue("GET"));
	}
}
