package test;

import java.util.Scanner;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizeToUriListener;
import clipboard.util.SysClipboardUtil;
import replace.RemoveMarkDownTags;
import replace.Replace;
import replace.ReplaceSpaceInChineses;
import tools.io.reader.PropertiesReader;
import tools.io.writer.MyStringWriter;
/**
 * �����ı��������,������Main���һ������. �������ڲ����ַ��滻Ч��,û�������һ����������ϳ�API.
 */
public class Test
{
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args)
	{
		// 2 ����XXXXXXXX���滻���������APPID
		String fileName;
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
		fileName = "С��vixy.pcm";
		// 5����Ҫ�ϳɵ��ı�
		// String text = "���������ϳ�";
		System.out.println(
				"######################################## Ѷ�������ϳ�ϵͳ ########################################");
		// System.out.println("����Ҫ�ϳɵ�����(��: \"#\"��Ϊ������):");
		// //�ӿ���̨��ȡ����.
		// String input = getInput();
		// �Ӽ������л�ȡ����
		String input = SysClipboardUtil.getSysClipboardText();
		// �Ƴ�����֮���һ�������ո�
		System.out.println(
				"---------------------------------- �Ƴ������еĿո� ----------------------------------");
		input = ReplaceSpaceInChineses.replaceSpaceInChineses(input);
		System.out.println(input);
		// ��û�����Ŀո���ַ�д�ؼ�����.
		SysClipboardUtil.setSysClipboardText(input);
		System.out.println(
				"---------------------------------- �Ƴ������еĿո� ----------------------------------");
		System.out.println(
				"---------------------------------- �����ַ����� ----------------------------------");
		// ȥ�����ĵ��
		input = Replace.replaceTitle(input);
		// �Ƴ�markdown���
		input = RemoveMarkDownTags.replaceMD(input);
		// �Ƴ�xml���
		// input = ReplaceHtmlXmlTags.replaceHtmlXmlTags(input);
		// ����շ�������,
		input = Replace.replaceEnglish(input);
		// �滻б��/,��б��\,�������&�����������ַ�.
		// input = ReplaceSpecialCharacter.replaceSpecialCharacter(input);
		System.out.println(input);
		System.out.println(
				"---------------------------------- �����ַ����� ----------------------------------");

		// 6.��ʼ�ϳ� //���úϳ���Ƶ����λ�ã����Զ��屣��λ�ã���Ĭ�ϱ����ڡ�./tts_test.pcm��
		// mTts.synthesizeToUri(input, fileName, synthesizeToUriListener);
		//д�봦����,���ں�������
		MyStringWriter.writerString(input);
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
			// TODO Auto-generated method stub

		}
	};
}
