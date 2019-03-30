package demo;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SynthesizeToUriListener;

public class DemoListRunable implements Runnable
{
	public static boolean isEnd=false;
	String fileName;
	String Input;
	String voiceName;
	public DemoListRunable(String voiceName, String fileName, String Input)
	{
		this.voiceName = voiceName;
		this.fileName = fileName;
		this.Input = Input;
	}
	public void run()
	{
		// 3.创建一个SpeechSynthesizer对象
		SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
		// 4.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
		// 设置发音人
		// 小燕 青年女声 中英文（普通话） xiaoyan 默认
		// 小研 青年女声 中英文（普通话） vixy
		// 小琪 青年女声 中英文（普通话） vixq xiaoqi
		// 小宇 青年男声 中英文（普通话） xiaoyu
		// 小峰 青年男声 中英文（普通话） vixf
		// 4-1 设置发音人
		mTts.setParameter(SpeechConstant.VOICE_NAME, voiceName);//
		// 设置要下载的文件名
		mTts.setParameter(SpeechConstant.SPEED, "50");
		// 设置语调，范围0~100
		mTts.setParameter(SpeechConstant.PITCH, "50");
		// 设置音量，范围0~100
		mTts.setParameter(SpeechConstant.VOLUME, "50");
		// TODO Auto-generated method stub
		System.out.println(
				"    正在合成 " + fileName.substring(0, fileName.lastIndexOf("."))
						+ " 朗读样例" + " 下载位置:" + fileName);
		mTts.synthesizeToUri(Input, fileName, synthesizeToUriListener);
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
			//表明下载完成
			isEnd=true;
			if(error==null)
				System.out.println("        合成成功");
			else 
				System.out.println("        合成失败");
		}
		@Override
		public void onEvent(int arg0, int arg1, int arg2, int arg3, Object arg4,
				Object arg5)
		{
			// TODO Auto-generated method stub

		}
	};
}
