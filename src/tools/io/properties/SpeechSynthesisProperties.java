package tools.io.properties;

import java.util.Properties;

public class SpeechSynthesisProperties
{
	private static Properties pr = null;
	private static String rootDir = null;
	private static String auditionPath = null;
	public static String getRootDir()
	{
		initProperties();
		if (rootDir == null)
		{
			rootDir = (String) pr.get("audioFileRootDir");
		}
		return rootDir;
	}
	public static String getAuditionPath()
	{
		initProperties();
		if (auditionPath == null)
		{
			auditionPath = (String) pr.get("auditionPath");
		}
		return auditionPath;
	}
	/**
	 * 实例化并加载配置文件.
	 */
	public static void initProperties()
	{
		if (pr == null)
		{
			pr = PropertiesInstance
					.getPropertiesInstanceUTF8("SST.properties");
		}
	}
}
