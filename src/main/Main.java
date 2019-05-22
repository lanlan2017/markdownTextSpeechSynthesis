package main;

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
import tools.io.markdown.reader.MyMarkdownReader;
import tools.io.properties.SpeechSynthesisProperties;
import tools.io.reader.PropertiesReader;
import tools.io.writer.MyStringWriter;
public class Main
{
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args)
	{
		// �Ӽ������ȡ�ļ�·��
		String path = SysClipboardUtil.getSysClipboardText();
		if (path.contains("source\\_posts"))
		{
			// ����markdown�ļ���·��������Ƶ�ļ���·��
			String fileName = filePath(path);
			// ���ļ��ж�ȡmarkdown�ı�
			String input = MyMarkdownReader.readerMyMarkdownFile(path)
					.toString();
			// Ѷ�ɻ���������
			SpeechSynthesizer mTts = xunfeiSettings();
			// ����������ı�
			input = inputProcessing(input);
			// 6.��ʼ�ϳ� //���úϳ���Ƶ����λ�ã����Զ��屣��λ�ã���Ĭ�ϱ����ڡ�./tts_test.pcm��
			mTts.synthesizeToUri(input, fileName, synthesizeToUriListener);
			// 7.������ʾ
			MyStringWriter.writerString(input);
			System.out.println(fileName);
		} else
		{
			System.err.println("��ַ����:" + path);
		}
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
				System.out.println("    �ϳɳɹ�");
			else
				System.out.println("    �ϳ�ʧ��");

		}
		@Override
		public void onEvent(int arg0, int arg1, int arg2, int arg3, Object arg4,
				Object arg5)
		{

		}
	};
}
