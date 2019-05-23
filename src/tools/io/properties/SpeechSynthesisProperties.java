package tools.io.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class SpeechSynthesisProperties
{
	static Properties pr = null;
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
	 * 促使化配置文件.
	 */
	public static void initProperties()
	{
		if (pr == null)
		{
			pr = new Properties();
			try
			{
				pr.load(new InputStreamReader(
						new FileInputStream(
								new File("SpeechSynthesis.properties")),
						"utf-8"));
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
