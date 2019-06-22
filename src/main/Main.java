package main;

import java.io.File;
import java.util.Scanner;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizeToUriListener;
import clipboard.util.SysClipboardUtil;
import replace.RemoveHtmlTags;
import replace.RemoveMarkDownTags;
import replace.ReplaceEnglishString;
import replace.ReplaceSpaceInChineses;
import system.call.cmd.Command;
import tools.io.markdown.reader.MyMarkdownReader;
import tools.io.properties.PerorationProperties;
import tools.io.properties.SpeechSynthesisProperties;
import tools.io.reader.PropertiesReader;
import tools.io.writer.MyStringWriter;
public class Main
{

	static Scanner scanner = new Scanner(System.in);
	static String fileName = null;
	public static void main(String[] args)
	{
		switch (args.length)
		{
			case 0 :
				noArgs();
				break;
			case 1 :
				oneArgs(args);
			default :
				break;
		}

	}
	/**
	 * @param args
	 * 
	 */
	private static void oneArgs(String[] args)
	{
		switch (args[0])
		{
			case "setting" :
				// System.out.println(System.getProperty("user.dir"));
				Command.exeCmd(
						"explorer.exe " + System.getProperty("user.dir"));
				break;
			default :
				break;
		}
	}
	/**   
	 *   
	 */
	private static void noArgs()
	{
		// �Ӽ������ȡ�ļ�·��
		String path = SysClipboardUtil.getSysClipboardText();
		// ������ļ��ĵ�ַ�Ļ�
		if (new File(path).isFile() && path.contains("source\\_posts"))
		{
			// ����markdown�ļ���·��������Ƶ�ļ���·��
			fileName = filePath(path);
			// ���ļ��ж�ȡmarkdown�ı�
			String input = MyMarkdownReader.readerMyMarkdownFile(path)
					.toString();
			// Ѷ�ɻ���������
			SpeechSynthesizer mTts = xunfeiSettings();
			// ����������ı�
			input = inputProcessing(input);
			input = input + PerorationProperties.getPeroration();
			System.out.println("--------------------------------------------");
			System.out.println(input);
//			// 6.��ʼ�ϳ� //���úϳ���Ƶ����λ�ã����Զ��屣��λ�ã���Ĭ�ϱ����ڡ�./tts_test.pcm��
//			mTts.synthesizeToUri(input, fileName, synthesizeToUriListener);
			int maxSize = 4000;
			XunFei.xunfeiAll(input, maxSize, fileName);
			// 7.������ʾ
			MyStringWriter.writerString(input);
//			System.out.println(fileName);
		} else
		{
			// Ϊ���Ķ�����
			String input = path;
			input = inputProcessing(input);
			System.out.println("---------------- ֱ�Ӻϳ� --------------------");
			System.out.println(input);
			System.out.println("---------------- ֱ�Ӻϳ� --------------------");
			fileName = System.getProperty("user.dir") + File.separator
					+ "ֱ�Ӻϳ�.pcm";
			// Ѷ�ɻ���������
//			SpeechSynthesizer mTts = xunfeiSettings();
//			// 6.��ʼ�ϳ� //���úϳ���Ƶ����λ�ã����Զ��屣��λ�ã���Ĭ�ϱ����ڡ�./tts_test.pcm��
//			mTts.synthesizeToUri(input, fileName, synthesizeToUriListener);
			int maxSize = 4000;
			XunFei.xunfeiAll(input, maxSize, fileName);
		}
	}

	/**   
	 *   
	 */
	public static void openFileUseAudition(String filePath)
	{
		String audition = SpeechSynthesisProperties.getAuditionPath();
		// String filePath = "G:\\Desktop\\�����ϳ�\\���Java���������\\��13��\\13.2.4 DDL���
		// 2.�޸ı�ṹ���﷨.pcm";
		audition += "\"" + filePath + "\"";
		Command.exeCmd("cmd /c start " + audition);
	}

	/**
	 * ������Ƶ�ļ��ľ���·��.
	 * 
	 * @param path
	 *            hexo������markdown�ļ���·��.
	 * @return ���ɵ���Ƶ�ļ���·��.
	 */
	public static String filePath(String path)
	{
		// ��������ļ��ĸ�Ŀ¼
		// String fileName = "G:\\Desktop\\�����ϳ�";
		String fileName = SpeechSynthesisProperties.getRootDir();
		// markdown�ļ�����ڲ�������Ŀ¼�����·��
		String relativePath = path.substring(
				path.indexOf("source\\_posts") + "source\\_posts".length());
		// �����ļ��ĸ�·��ƴ�������·���õ������ļ��ľ���·��
		fileName = fileName + relativePath.replace(".md", ".pcm");
		return fileName;
	}

	/**
	 * �����û����˵�Markdown�ı�.
	 * 
	 * @return
	 */
	public static String inputProcessing(String input)
	{
		System.out.println(
				"######################################## Ѷ�������ϳ�ϵͳ ########################################");

		// �Ƴ�����֮���һ�������ո�
		System.out.println("------------------- ɾ�������ж���ո� ��ʼ --------");
		input = ReplaceSpaceInChineses.replaceSpaceInChineses(input);
		// System.out.println(input);
		System.out.println("------------------- ɾ�������ж���ո� ���� --------");
		System.out.println("------------------- ����Markown�ı� ��ʼ ----------");
		// �Ƴ�markdown���
		input = RemoveMarkDownTags.replaceMD(input);
		// System.out.println(input);
		System.out.println("------------------- ����Markown�ı� ���� ----------");
		System.out.println("------------------- ����HTML�ı� ��ʼ -------------");
		// ע��Ҫ����RemoveMarkDownTags.replaceMD(input);֮��,�����Ƴ���������е�����
		// �Ƴ�����`<center><strong>��19.3input��ǩ������</strong></center>`�����ı�ǩ
		input = RemoveHtmlTags.removeHtmlDoubleTags(input);
		// System.out.println(input);
		System.out.println("------------------- ����HTML�ı� ���� -------------");
		System.out.println("------------------- ���Java�շ������� ��ʼ  -------");
		// ����շ�������
		input = ReplaceEnglishString.replaceEnglish(input);
		System.out.println("------------------- ���Java�շ������� ��ʼ  --------");
		System.out.println("------------------- ������: ---------------------");
		input=input.replaceAll("(?m)[ ]+$","");
		System.out.println(input);

		return input;
	}

	/**
	 * @return
	 */
	public static SpeechSynthesizer xunfeiSettings()
	{
		// �������ļ��ж�ȡAPPID
		SpeechUtility.createUtility(
				SpeechConstant.APPID + "=" + PropertiesReader.getAPPID());
		// 3.����SpeechSynthesizer����
		SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
		// �������٣���Χ0~100
		mTts.setParameter(SpeechConstant.SPEED, "50");
		// �����������Χ0~100
		mTts.setParameter(SpeechConstant.PITCH, "50");
		// ������������Χ0~100
		mTts.setParameter(SpeechConstant.VOLUME, "50");
		// 4.�ϳɲ������ã������MSC Reference Manual��SpeechSynthesizer ��
		mTts.setParameter(SpeechConstant.VOICE_NAME, "vixy");// С��
		return mTts;
	}

	/**
	 * @return
	 */
	public static String getInput()
	{
		StringBuffer sbBuffer = new StringBuffer(10240);
		String line = null;
		while (scanner.hasNext())
		{
			line = scanner.nextLine();
			if ("#".equals(line))
			{
				break;
			}
			sbBuffer.append(line.concat("\r\n"));
		}
		// ��ȡ���������.
		String input = sbBuffer.toString();
		return input;
	}

	// 1 ���úϳɼ�����
	static SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener()
	{
		// progressΪ�ϳɽ���0~100
		public void onBufferProgress(int progress)
		{
		}
		// �Ự�ϳ���ɻص��ӿ�
		// uriΪ�ϳɱ����ַ��errorΪ������Ϣ��Ϊnullʱ��ʾ�ϳɻỰ�ɹ�
		public void onSynthesizeCompleted(String uri, SpeechError error)
		{
			if (error == null)
			{
				System.out.println("    �ϳɳɹ�");
				// 8.ʹ��auditon���ļ�.
				openFileUseAudition(fileName);
			} else
				System.out.println("    �ϳ�ʧ��");

		}
		@Override
		public void onEvent(int arg0, int arg1, int arg2, int arg3, Object arg4,
				Object arg5)
		{

		}
	};
}
