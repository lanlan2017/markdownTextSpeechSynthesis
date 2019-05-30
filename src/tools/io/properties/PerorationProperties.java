package tools.io.properties;

import java.util.Properties;

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
	public static String getPeroration()
	{
		return getInstance().getProperty("peroration");
	}
}
