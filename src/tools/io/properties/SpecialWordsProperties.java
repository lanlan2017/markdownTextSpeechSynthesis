package tools.io.properties;

import java.util.Properties;

/**
 * ƥ���ض��ĵ���.
 */
public class SpecialWordsProperties
{
	// 1.ʵ���������ļ�����
	private static Properties properties = null;
	/**
	 * ʹ�������ļ��е�value�滻key
	 * 
	 * @param keyToFind
	 *            ��Ҫ���ҵ�key.
	 * @return ����ҵ�����key��Ӧ��value��ʾ�滻��value,���û���ҵ�����keyToFind��ʾ���滻.
	 */
	//SpecialWordsProperties.replaceByVlaue
	public static String replaceByVlaue(String keyToFind)
	{
		// ����ģʽ��ȡ.
		if (properties == null)
		{
			// ���ɶ��󲢼�����������
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
