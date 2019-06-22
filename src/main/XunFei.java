package main;

import java.io.File;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizeToUriListener;
import system.call.cmd.Command;

public class XunFei
{
	String input;
	String fileName;
	static boolean wait = true;
	// 1 ���úϳɼ�����
	private SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener()
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
				System.out.println("�ϳ�:" + fileName + "-->�ɹ�");
				// 8.ʹ��auditon���ļ�.
				openFileUseAudition(fileName);
			} else
				System.out.println("�ϳ�:" + fileName + "-->ʧ��");
			wait = false;
		}
		@Override
		public void onEvent(int arg0, int arg1, int arg2, int arg3, Object arg4,
				Object arg5)
		{

		}
	};
	/**    
	 *  
	 */
	public XunFei(String input, String fileName)
	{
		this.input = input;
		this.fileName = fileName;
	}
	/**
	 * @return
	 */
	public SpeechSynthesizer xunfeiSettings()
	{
		// �������ļ��ж�ȡAPPID
		SpeechUtility.createUtility(SpeechConstant.APPID + "=" + "5c80ae6b");
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
	 *   
	 */
	public void openFileUseAudition(String filePath)
	{
		String audition = "F:\\���\\��װ��_��Ƶ����\\Adobe\" \"Audition\" \"CS6\\Adobe\" \"Audition\" \"CS6.exe ";
		// String filePath = "G:\\Desktop\\�����ϳ�\\���Java���������\\��13��\\13.2.4 DDL���
		// 2.�޸ı�ṹ���﷨.pcm";
		audition += "\"" + filePath + "\"";
		Command.exeCmd("cmd /c start " + audition);
	}
	public void xunfei()
	{
		// String input = "Ѷ�������ϳɲ���.";
		// String fileName = System.getProperty("sser.dir") + File.separator
		// + "����.pcm";
		// XunFei xunFei = new XunFei();
		// Ѷ�ɻ���������
		SpeechSynthesizer mTts = xunfeiSettings();
		mTts.synthesizeToUri(input, fileName, synthesizeToUriListener);
	}
	// public static void main(String[] args)
	// {
	// String input = "Ѷ�������ϳɲ���.\r\n" + "��ð�С��,�����������ò�����.\r\n";
	// System.out.println(input.length());
	//
	// String fileName = System.getProperty("user.dir") + File.separator
	// + "����1.pcm";
	//
	// System.out.println(fileName);
	// // �ϳ�һ��
	// xunfeiOnes(input, fileName);
	//
	// input = "������ְְ�.";
	// fileName = System.getProperty("user.dir") + File.separator + "����2.pcm";
	// System.out.println(fileName);
	// // �ϳ�һ��
	// xunfeiOnes(input, fileName);
	// }
	/**
	 * @param input
	 * @param fileName
	 */
	public static void xunfeiOnes(String input, String fileName)
	{
		new XunFei(input, fileName).xunfei();
		wait = true;
		// �ȴ��ϴ�ִ�����
		waitFor();
	}
	/**
	 * ����Ѷ�������ϳ����е������ı�.
	 * 
	 * @param input
	 *            Ҫ�ϳɵ��ı�.
	 * @param maxSize
	 *            Ѷ��SDKÿ�������յ���������,�Ҳ�����,�����Ϊ4000û������.
	 * @param fileName
	 *            Ҫ�ϳ����ɵ��ļ�.
	 */
	public static void xunfeiAll(String input, int maxSize, String fileName)
	{
		System.out.println("�ַ����ܳ���:" + input.length());
		// �����ַ����ĳ���
		int inputLength = input.length();
		// ��ǰ�ʶ����ı�.
		String before;
		// ������
		int count = 0;
		// ��ֵ��ļ���
		String fileNamePart;
		while (inputLength > maxSize)
		{
			// ����2
			int splitIndex = 0;
			char end;
			for (int i = maxSize; i > 0; i--)
			{
				end = input.charAt(i);
				if ('\n' == end || '��' == end)
				{
					// System.out.print("%%%%%%%%%%%%%%%%%%%%%���񵽷ָ���:");
					// if ('\n' == end)
					// {
					// System.out.println("\\n");
					// } else
					// {
					// System.out.println(end);
					//
					// }
					// System.out.println("%%%%%%%%%%%%%%%%%%%%%�ָ����±�:" + i);
					splitIndex = i;
					break;
				}
			}
			before = input.substring(0, splitIndex + 1);
			System.out.println(
					"------------------------------------------------");
			System.out.println(before);
			fileNamePart = fileNamePart(fileName, count++);
			XunFei.xunfeiOnes(before, fileNamePart);
			// ����ʣ�µ��ı�
			input = input.substring(splitIndex + 1);
			// ���³���
			inputLength = input.length();
		}
		// ���ʣ�µ��ı�
		System.out.println("------------------------------------------------");
		System.out.println(input);
		fileNamePart = fileNamePart(fileName, count);
		XunFei.xunfeiOnes(input, fileNamePart);
		// XunFei.xunfeiOnes(input, System.getProperty("user.dir") +
		// File.separator
		// + "����" + count + ".pcm");
	}
	/**
	 * ���ɲ���������·��.
	 * 
	 * @param fileName
	 * @param count
	 * @return
	 */
	public static String fileNamePart(String fileName, int count)
	{
		String fileNamePart;
		fileNamePart = fileName.substring(0, fileName.lastIndexOf(".pcm"))
				+ (count) + fileName.substring(fileName.lastIndexOf(".pcm"));
		return fileNamePart;
	}
	/**   
	 *   
	 */
	public static void waitFor()
	{
		while (wait)
		{
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
