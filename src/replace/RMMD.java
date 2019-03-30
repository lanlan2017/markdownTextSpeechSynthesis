package replace;

import clipboard.util.SysClipboardUtil;

public class RMMD
{
	public static void main(String[] args)
	{
		//从剪贴板中获取数据
		String input=SysClipboardUtil.getSysClipboardText();
		System.out.println("------------------------------------------------------- 输入数据 -------------------------------------------------------");
		System.out.println(input);
		System.out.println("------------------------------------------------------- 输入数据 -------------------------------------------------------");
		//移除markdown的标题格式
		input=RemoveMarkDownTags.removeMDTitle(input);
		//移除markdown的加粗,斜体格式。
		input=RemoveMarkDownTags.removeMDStrongOrItalic(input);
		//移除markdown中的图片.
		input=RemoveMarkDownTags.removeMDIMG(input);
		System.out.println("------------------------------------------------------- 处理结果 -------------------------------------------------------");
		SysClipboardUtil.setSysClipboardText(input);
		System.out.println(input);
		System.out.println("------------------------------------------------------- 处理结果 -------------------------------------------------------");
		
	}
}
