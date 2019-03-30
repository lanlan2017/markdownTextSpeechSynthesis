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
		// 2 将“XXXXXXXX”替换成您申请的APPID
		String fileName;
		SpeechUtility.createUtility(SpeechConstant.APPID + "="+PropertiesReader.getAPPID());
		// 3.创建SpeechSynthesizer对象
		SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
//		mTts.setParameter(SpeechConstant.VOICE_NAME, "vixf");//小峰
//		fileName="小峰vixf.pcm";
		// 设置语速，范围0~100
		mTts.setParameter(SpeechConstant.SPEED, "50");
		// 设置语调，范围0~100
		mTts.setParameter(SpeechConstant.PITCH, "50");
		// 设置音量，范围0~100
		mTts.setParameter(SpeechConstant.VOLUME, "50");
		// 4.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
		// 设置发音人
//		小燕 	青年女声 	中英文（普通话） 	xiaoyan 		默认
//		小研 	青年女声 	中英文（普通话） 	vixy 		
//		小琪 	青年女声 	中英文（普通话） 	vixq 	xiaoqi 	
//		小宇 	青年男声 	中英文（普通话） 	xiaoyu
//		小峰 	青年男声 	中英文（普通话） 	vixf 		
//		mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//小燕  
//		fileName="小燕xiaoyan.pcm";
		mTts.setParameter(SpeechConstant.VOICE_NAME, "vixy");//小研 
		fileName="小研vixy.pcm";
//		mTts.setParameter(SpeechConstant.VOICE_NAME, "vixq");//小琪  
//		fileName="小琪vixq.pcm";
//		mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyu");//小宇
//		fileName="小宇xiaoyu.pcm";
//		ArrayList<SpeechSynthesizer> mTtsList=new ArrayList<SpeechSynthesizer>();
//		String[] fileNameArr=new String[]{"小燕xiaoyan.pcm","小研vixy.pcm","小琪vixq.pcm","小宇xiaoyu.pcm","小峰vixf.pcm"};
//		{
//		}
		// 5设置要合成的文本
//		String text = "测试语音合成";
		System.out.println("######################################## 讯飞语音合成系统 ########################################");
		System.out.println("输入要合成的文字(以: \"#exit\"作为结束符):");
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
		System.out.println("---------------------------------- 特殊字符处理 ----------------------------------");
		System.out.println(Input);
		System.out.println("---------------------------------- 特殊字符处理 ----------------------------------");

		// 6.开始合成 //设置合成音频保存位置（可自定义保存位置），默认保存在“./tts_test.pcm”
		mTts.synthesizeToUri(Input, fileName, synthesizeToUriListener);
		System.out.println("程序终止了");
	}
	// 1 设置合成监听器
	static SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener()
	{
		// progress为合成进度0~100
		public void onBufferProgress(int progress)
		{
		}
		// 会话合成完成回调接口
		// uri为合成保存地址，error为错误信息，为null时表示合成会话成功
		public void onSynthesizeCompleted(String uri, SpeechError error)
		{
			System.out.println(error);
			//设置为true表明下载文阿城
//			isEnd=true;
			System.out.println("下载完毕");
		}
		@Override
		public void onEvent(int arg0, int arg1, int arg2, int arg3, Object arg4,
				Object arg5)
		{
			// TODO Auto-generated method stub

		}
	};
}
