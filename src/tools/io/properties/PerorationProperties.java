package tools.io.properties;

import java.util.Properties;

/**
 * 结束语
 */
public class PerorationProperties
{
	private static Properties instance = null;

	public static synchronized Properties getInstance()
	{
		if (instance == null)
		{
			instance = PropertiesInstance
					.getPropertiesInstanceUTF8("peroration.properties");
		}
		return instance;
	}
	/**
	 * 返回结束语.
	 * 
	 * @return
	 */
	public static String getPeroration()
	{
		return getInstance().getProperty("peroration");
	}
}
