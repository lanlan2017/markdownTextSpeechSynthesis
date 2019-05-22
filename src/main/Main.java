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
import tools.io.reader.PropertiesReader;
import tools.io.writer.MyStringWriter;
public class Main
{
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args)
	{
		String fileName;
		// Ѷ�ɻ���������
		SpeechSynthesizer mTts = xunfeiSettings();
		fileName = "С��vixy.pcm";
		// ����������ı�
		String input = inputProcessing();
		// 6.��ʼ�ϳ� //���úϳ���Ƶ����λ�ã����Զ��屣��λ�ã���Ĭ�ϱ����ڡ�./tts_test.pcm��
		 mTts.synthesizeToUri(input, fileName, synthesizeToUriListener);
		// д�봦����
		MyStringWriter.writerString(input);
	}

	/**
	 * �����û����˵�Markdown�ı�.
	 * 
	 * @return
	 */
	public static String inputProcessing()
	{
		System.out.println(
				"######################################## Ѷ�������ϳ�ϵͳ ########################################");
		// �Ӽ������л�ȡ����
		String input = SysClipboardUtil.getSysClipboardText();
		// �Ƴ�����֮���һ�������ո�
		System.out.println("------------------- ɾ�������ж���ո� ��ʼ --------");
		input = ReplaceSpaceInChineses.replaceSpaceInChineses(input);
//		System.out.println(input);
		System.out.println("------------------- ɾ�������ж���ո� ���� --------");
		System.out.println("------------------- ����Markown�ı� ��ʼ ----------");
		// �Ƴ�markdown���
		input = RemoveMarkDownTags.replaceMD(input);
//		System.out.println(input);
		System.out.println("------------------- ����Markown�ı� ���� ----------");
		System.out.println("------------------- ����HTML�ı� ��ʼ -------------");
		// ע��Ҫ����RemoveMarkDownTags.replaceMD(input);֮��,�����Ƴ���������е�����
		// �Ƴ�����`<center><strong>��19.3input��ǩ������</strong></center>`�����ı�ǩ
		input = RemoveHtmlTags.removeHtmlDoubleTags(input);
//		System.out.println(input);
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
