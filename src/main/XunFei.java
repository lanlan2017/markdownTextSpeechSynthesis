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
	// 1 设置合成监听器
	private SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener()
	{
		// progress为合成进度0~100
		public void onBufferProgress(int progress)
		{
		}
		// 会话合成完成回调接口
		// uri为合成保存地址，error为错误信息，为null时表示合成会话成功
		public void onSynthesizeCompleted(String uri, SpeechError error)
		{
			if (error == null)
			{
				System.out.println("合成:" + fileName + "-->成功");
				// 8.使用auditon打开文件.
				openFileUseAudition(fileName);
			} else
				System.out.println("合成:" + fileName + "-->失败");
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
		// 从配置文件中读取APPID
		SpeechUtility.createUtility(SpeechConstant.APPID + "=" + "5c80ae6b");
		// 3.创建SpeechSynthesizer对象
		SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
		// 设置语速，范围0~100
		mTts.setParameter(SpeechConstant.SPEED, "50");
		// 设置语调，范围0~100
		mTts.setParameter(SpeechConstant.PITCH, "50");
		// 设置音量，范围0~100
		mTts.setParameter(SpeechConstant.VOLUME, "50");
		// 4.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
		mTts.setParameter(SpeechConstant.VOICE_NAME, "vixy");// 小研
		return mTts;
	}
	/**   
	 *   
	 */
	public void openFileUseAudition(String filePath)
	{
		String audition = "F:\\软件\\安装包_音频处理\\Adobe\" \"Audition\" \"CS6\\Adobe\" \"Audition\" \"CS6.exe ";
		// String filePath = "G:\\Desktop\\语音合成\\疯狂Java讲义第三版\\第13章\\13.2.4 DDL语句
		// 2.修改表结构的语法.pcm";
		audition += "\"" + filePath + "\"";
		Command.exeCmd("cmd /c start " + audition);
	}
	public void xunfei()
	{
		// String input = "讯飞语音合成测试.";
		// String fileName = System.getProperty("sser.dir") + File.separator
		// + "测试.pcm";
		// XunFei xunFei = new XunFei();
		// 讯飞机器人设置
		SpeechSynthesizer mTts = xunfeiSettings();
		mTts.synthesizeToUri(input, fileName, synthesizeToUriListener);
	}
	// public static void main(String[] args)
	// {
	// String input = "讯飞语音合成测试.\r\n" + "你好啊小明,这个世界很美好不是吗.\r\n";
	// System.out.println(input.length());
	//
	// String fileName = System.getProperty("user.dir") + File.separator
	// + "测试1.pcm";
	//
	// System.out.println(fileName);
	// // 合成一次
	// xunfeiOnes(input, fileName);
	//
	// input = "我是你爸爸啊.";
	// fileName = System.getProperty("user.dir") + File.separator + "测试2.pcm";
	// System.out.println(fileName);
	// // 合成一次
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
		// 等待上次执行完毕
		waitFor();
	}
	/**
	 * 调用讯飞语音合成所有的输入文本.
	 * 
	 * @param input
	 *            要合成的文本.
	 * @param maxSize
	 *            讯飞SDK每次最多接收的字数上限,我测试了,这个设为4000没有问题.
	 * @param fileName
	 *            要合成生成的文件.
	 */
	public static void xunfeiAll(String input, int maxSize, String fileName)
	{
		System.out.println("字符串总长度:" + input.length());
		// 整个字符串的长度
		int inputLength = input.length();
		// 当前朗读的文本.
		String before;
		// 计数器
		int count = 0;
		// 拆分的文件名
		String fileNamePart;
		while (inputLength > maxSize)
		{
			// 除以2
			int splitIndex = 0;
			char end;
			for (int i = maxSize; i > 0; i--)
			{
				end = input.charAt(i);
				if ('\n' == end || '。' == end)
				{
					// System.out.print("%%%%%%%%%%%%%%%%%%%%%捕获到分隔符:");
					// if ('\n' == end)
					// {
					// System.out.println("\\n");
					// } else
					// {
					// System.out.println(end);
					//
					// }
					// System.out.println("%%%%%%%%%%%%%%%%%%%%%分隔符下标:" + i);
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
			// 更新剩下的文本
			input = input.substring(splitIndex + 1);
			// 更新长度
			inputLength = input.length();
		}
		// 输出剩下的文本
		System.out.println("------------------------------------------------");
		System.out.println(input);
		fileNamePart = fileNamePart(fileName, count);
		XunFei.xunfeiOnes(input, fileNamePart);
		// XunFei.xunfeiOnes(input, System.getProperty("user.dir") +
		// File.separator
		// + "测试" + count + ".pcm");
	}
	/**
	 * 生成部分语音的路径.
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
