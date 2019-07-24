package tools.io.markdown.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.Set;
import clipboard.util.SysClipboardUtil;
import read.IOFlag;
import regex.RegexHTML;
import tools.io.properties.PropertiesInstance;

public class MyMarkdownReader
{
	public static void main(String[] args)
	{
		String path = SysClipboardUtil.getSysClipboardText();
		System.out.print(readerMyMarkdownFile(path));
	}

	/**
	 * @param path
	 */
	public static StringBuffer readerMyMarkdownFile(String path)
	{
		boolean fileStart = false;
		boolean read = false;
		HashMap<String, String> repalceMap = new HashMap<String, String>();
		StringBuffer sbBuffer = new StringBuffer();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(path)), "utf-8"));)
		{

			String line = null;
			String[] replaceEntry;
			String key;
			String value;
			while ((line = reader.readLine()) != null)
			{
				// ����ļ���ʼ��
				if (!fileStart && "<!--end-->".equals(line))
				{
					fileStart = true;
					continue;
				}
				if (IOFlag.SSTStart.equals(line))
				{
					read = true;
					continue;
				} else if (IOFlag.SSTStop.equals(line))
				{
					read = false;
					continue;
				}
				// ���ɨ�赽��ȷ�������
				else if (line.matches(RegexHTML.correctReading))
				{
					// ȡ�������ļ�
					line = line.replaceAll(RegexHTML.correctReading, "$1");
					replaceEntry = line.split("&");
					for (int i = 0; i < replaceEntry.length; i++)
					{
						key = replaceEntry[i].substring(0,
								replaceEntry[i].indexOf("="));
						value = replaceEntry[i]
								.substring(replaceEntry[i].indexOf("=") + 1);
						repalceMap.put(key, value);
					}
					continue;
				}
				if (fileStart && read)
				{
					// System.out.print(line + "\r\n");
					// ���浽�����ַ�����
					sbBuffer.append(line + "\r\n");
				}
			}
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		// System.out.println(
		// "------------------------------------------------------------");
		// ɾ�����һ��,���д������µĳ�����
		deleteLastLine(sbBuffer);
		if (repalceMap.size() > 0)
		{
			// �滻���ⵥ��,�Ա���ȷ�ʶ�.
			sbBuffer = replaceKeyToValue(sbBuffer.toString(), repalceMap);
		}

		return sbBuffer;
	}

	/**
	 * ʹ��Map�е�key,�滻value.
	 * 
	 * @param input
	 *            ��Ҫ�滻���ַ���
	 * @param repalceMap
	 *            ����key��value��map����
	 * @return �����滻����ַ�����StringBuffer.
	 */
	public static StringBuffer replaceKeyToValue(String input,
			HashMap<String, String> repalceMap)
	{
		//
		Properties properties = PropertiesInstance
				.getPropertiesInstanceUTF8("ContainSpecialWords.properties");
		Set<String> keySet = properties.stringPropertyNames();

		// 1 ��ȡMap.Entry�����Set����
		Set<Entry<String, String>> mapEntry = repalceMap.entrySet();
		// 2 Map.Entry�����Set���ϵ�����
		Iterator<Entry<String, String>> mapEntryIt = mapEntry.iterator();
		while (mapEntryIt.hasNext())
		{
			// 2 ��Set������ȡ��һ�� Map.Entryʵ��
			Entry<String, String> mapEntryElement = mapEntryIt.next();
			// 3 �ֱ�ȡ������ֵ
			String key = mapEntryElement.getKey();
			String value = mapEntryElement.getValue();
			System.err.println("key=" + key + ",value=" + value);
			// ʹ��key�滻value
			// input = input.replace(key, value);
			input = input.replaceAll("\\b" + key + "\\b", value);
			// ��������ļ���û�����key�Ļ�
			if (!keySet.contains(key))
			{
				// ���浽�����ļ�������
				properties.setProperty(key, value);
			}
		}
		try
		{
			// ���浽�����ϵ������ļ�
			properties.store(
					new FileOutputStream(
							new File("ContainSpecialWords.properties")),
					"utf-8");
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		StringBuffer sb = new StringBuffer(input);
		return sb;
	}

	/**
	 * ɾ�����һ���ı�.
	 * 
	 * @param sbBuffer
	 */
	public static void deleteLastLine(StringBuffer sbBuffer)
	{
		String lastLine = null;
		for (int length = sbBuffer.length() - 1, i = length; i > 0; i--)
		{
			char ch = sbBuffer.charAt(i);
			// �����ڶ������з�֮��ľ��ǻ��з�.
			if (i < length && ch == '\n')
			{
				lastLine = sbBuffer.substring(i + 1, length);
				// System.out.println(lastLine.startsWith(">ԭ������: "));
				// ������һ���ǰ�Ȩ��Ϣ,��ɾ������
				if (lastLine.startsWith(">ԭ������: "))
				{
					// ɾ����Ȩ����
					sbBuffer.delete(i + 1, length);
				}
				break;
			}
		}
	}
}
