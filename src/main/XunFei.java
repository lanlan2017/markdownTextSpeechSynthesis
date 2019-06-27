package main;

import java.util.ArrayList;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizeToUriListener;

public class XunFei
{
	// 要处理的字符串.
	String input;
	// 生成的文件.
	String fileNamePart;
	// static boolean wait = true;
	// static ArrayList<String> fileNameList = null;
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
				System.out.println("合成:" + fileNamePart + "-->成功");
				// 8.使用auditon打开文件.
				// openFileUseAudition(fileName);
				// 第一次合成成功时,创建集合保存合成成功的文件名
				if (XunFeiTools.getFileNameList() == null)
				{
					XunFeiTools.setFileNameList(new ArrayList<String>());
				}
				XunFeiTools.getFileNameList().add(fileNamePart);
			} else
				System.out.println("合成:" + fileNamePart + "-->失败");
			// 不再等待,继续合成下一个分组.
			XunFeiTools.wait = false;
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
	public XunFei(String input, String fileNamePart)
	{
		this.input = input;
		this.fileNamePart = fileNamePart;
	}

	// /**
	// * 获取保存的所有文件名列表.
	// *
	// * @return
	// */
	// public static ArrayList<String> getFileNameList()
	// {
	// return fileNameList;
	// }

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
	// /**
	// * 使用Auditon打开音频.
	// *
	// * @param filePath
	// */
	// public static void openFileUseAudition(String filePath)
	// {
	// String audition = "F:\\软件\\安装包_音频处理\\Adobe\" \"Audition\" \"CS6\\Adobe\"
	// \"Audition\" \"CS6.exe ";
	// // String filePath = "G:\\Desktop\\语音合成\\疯狂Java讲义第三版\\第13章\\13.2.4 DDL语句
	// // 2.修改表结构的语法.pcm";
	// audition += "\"" + filePath + "\"";
	// Command.exeCmd("cmd /c start " + audition);
	// }
	public void xunfei()
	{
		// 讯飞机器人设置
		SpeechSynthesizer mTts = xunfeiSettings();
		// 合成音频
		mTts.synthesizeToUri(input, fileNamePart, synthesizeToUriListener);
	}
	// /**
	// * @param input
	// * @param fileName
	// */
	// public static void xunfeiOnes(String input, String fileNamePart)
	// {
	// new XunFei(input, fileNamePart).xunfei();
	// wait = true;
	// // 等待上次执行完毕
	// waitFor();
	// }
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
	// public static void xunfeiAll(String input, int maxSize, String fileName)
	// {
	// System.out.println("字符串总长度:" + input.length());
	// // 整个字符串的长度
	// int inputLength = input.length();
	// // 当前朗读的文本.
	// String now;
	// // 计数器
	// int count = 0;
	// // 拆分的文件名
	// String fileNamePart;
	// while (inputLength > maxSize)
	// {
	// // 除以2
	// int splitIndex = 0;
	// char end;
	// for (int i = maxSize; i > 0; i--)
	// {
	// end = input.charAt(i);
	// if ('\n' == end || '。' == end)
	// {
	// splitIndex = i;
	// break;
	// }
	// }
	// // 获取当前的文本.
	// now = input.substring(0, splitIndex + 1);
	// System.out.println(
	// "------------------------------------------------");
	// System.out.println(now);
	// // 动态生成文件名
	// fileNamePart = fileNamePart(fileName, count++);
	// // 把当前的合成当前的文本now,保存到fileNamePart
	// XunFei.xunfeiOnes(now, fileNamePart);
	//
	// // 更新剩下的文本
	// input = input.substring(splitIndex + 1);
	// // 更新剩下文本的长度
	// inputLength = input.length();
	// }
	// // 输出剩下的文本
	// System.out.println("------------------------------------------------");
	// System.out.println(input);
	// fileNamePart = fileNamePart(fileName, count);
	// XunFei.xunfeiOnes(input, fileNamePart);
	// }
	/**
	 * 生成部分语音的路径.
	 * 
	 * @param fileName
	 * @param count
	 * @return
	 */
	// public static String fileNamePart(String fileName, int count)
	// {
	// String fileNamePart;
	// // 第一个部分使用原来的文件名
	// fileNamePart = fileName.substring(0, fileName.lastIndexOf(".pcm"))
	// + (count) + fileName.substring(fileName.lastIndexOf(".pcm"));
	// return fileNamePart;
	// }
	// /**
	// * 阻塞线程.
	// */
	// public static void waitFor()
	// {
	// while (wait)
	// {
	// try
	// {
	// Thread.sleep(1000);
	// } catch (InterruptedException e)
	// {
	// e.printStackTrace();
	// }
	// }
	// }

}
