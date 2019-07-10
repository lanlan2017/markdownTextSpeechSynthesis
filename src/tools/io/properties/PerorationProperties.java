package tools.io.properties;

import java.util.Properties;

/**
 * Ω· ¯”Ô
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
	 * ∑µªÿΩ· ¯”Ô.
	 * 
	 * @return
	 */
	public static String getPeroration()
	{
		return getInstance().getProperty("peroration");
	}
}
