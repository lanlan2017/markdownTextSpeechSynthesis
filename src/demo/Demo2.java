package demo;
import java.util.Scanner;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizeToUriListener;
import replace.Replace;
import tools.io.reader.PropertiesReader;
public class Demo2
{
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args)
	{
		// 2 ����XXXXXXXX���滻���������APPID
		String fileName;
		SpeechUtility.createUtility(SpeechConstant.APPID + "="+PropertiesReader.getAPPID());
		// 3.����SpeechSynthesizer����
		SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
//		mTts.setParameter(SpeechConstant.VOICE_NAME, "vixf");//С��
//		fileName="С��vixf.pcm";
		// �������٣���Χ0~100
		mTts.setParameter(SpeechConstant.SPEED, "50");
		// �����������Χ0~100
		mTts.setParameter(SpeechConstant.PITCH, "50");
		// ������������Χ0~100
		mTts.setParameter(SpeechConstant.VOLUME, "50");
		// 4.�ϳɲ������ã������MSC Reference Manual��SpeechSynthesizer ��
		// ���÷�����
//		С�� 	����Ů�� 	��Ӣ�ģ���ͨ���� 	xiaoyan 		Ĭ��
//		С�� 	����Ů�� 	��Ӣ�ģ���ͨ���� 	vixy 		
//		С�� 	����Ů�� 	��Ӣ�ģ���ͨ���� 	vixq 	xiaoqi 	
//		С�� 	�������� 	��Ӣ�ģ���ͨ���� 	xiaoyu
//		С�� 	�������� 	��Ӣ�ģ���ͨ���� 	vixf 		
//		mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//С��  
//		fileName="С��xiaoyan.pcm";
		mTts.setParameter(SpeechConstant.VOICE_NAME, "vixy");//С�� 
		fileName="С��vixy.pcm";
//		mTts.setParameter(SpeechConstant.VOICE_NAME, "vixq");//С��  
//		fileName="С��vixq.pcm";
//		mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyu");//С��
//		fileName="С��xiaoyu.pcm";
//		ArrayList<SpeechSynthesizer> mTtsList=new ArrayList<SpeechSynthesizer>();
//		String[] fileNameArr=new String[]{"С��xiaoyan.pcm","С��vixy.pcm","С��vixq.pcm","С��xiaoyu.pcm","С��vixf.pcm"};
//		{
//		}
		// 5����Ҫ�ϳɵ��ı�
//		String text = "���������ϳ�";
		System.out.println("######################################## Ѷ�������ϳ�ϵͳ ########################################");
		System.out.println("����Ҫ�ϳɵ�����(��: \"#exit\"��Ϊ������):");
		StringBuffer sbBuffer=new StringBuffer(10240);
		String line=null;
		while(scanner.hasNext())
		{
			line=scanner.nextLine();
			if("#exit".equals(line))
			{
				break;
			}
			sbBuffer.append(line.concat("\r\n"));
		}
//		System.out.println(sbBuffer.toString());
		String Input=Replace.replaceTitle(sbBuffer.toString());
		Input=Replace.replaceEnglish(Input);
		System.out.println("---------------------------------- �����ַ����� ----------------------------------");
		System.out.println(Input);
		System.out.println("---------------------------------- �����ַ����� ----------------------------------");

		// 6.��ʼ�ϳ� //���úϳ���Ƶ����λ�ã����Զ��屣��λ�ã���Ĭ�ϱ����ڡ�./tts_test.pcm��
		mTts.synthesizeToUri(Input, fileName, synthesizeToUriListener);
		System.out.println("������ֹ��");
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
			System.out.println(error);
			//����Ϊtrue���������İ���
//			isEnd=true;
			System.out.println("�������");
		}
		@Override
		public void onEvent(int arg0, int arg1, int arg2, int arg3, Object arg4,
				Object arg5)
		{
			// TODO Auto-generated method stub

		}
	};
}
