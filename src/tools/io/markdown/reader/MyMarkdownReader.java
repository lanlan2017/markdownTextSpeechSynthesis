package tools.io.markdown.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import clipboard.util.SysClipboardUtil;

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
		StringBuffer sbBuffer = new StringBuffer();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(path)), "utf-8"));)
		{

			String line = null;
			while ((line = reader.readLine()) != null)
			{
				if (!fileStart && "<!--end-->".equals(line))
				{
					fileStart = true;
					continue;
				}
				if (fileStart)
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
		System.out.println(
				"------------------------------------------------------------");
		deleteLastLine(sbBuffer);
		return sbBuffer;
	}

	/**
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
