package replace;

import clipboard.util.SysClipboardUtil;

public class RMMD
{
	public static void main(String[] args)
	{
		//�Ӽ������л�ȡ����
		String input=SysClipboardUtil.getSysClipboardText();
		System.out.println("------------------------------------------------------- �������� -------------------------------------------------------");
		System.out.println(input);
		System.out.println("------------------------------------------------------- �������� -------------------------------------------------------");
		//�Ƴ�markdown�ı����ʽ
		input=RemoveMarkDownTags.removeMDTitle(input);
		//�Ƴ�markdown�ļӴ�,б���ʽ��
		input=RemoveMarkDownTags.removeMDStrongOrItalic(input);
		//�Ƴ�markdown�е�ͼƬ.
		input=RemoveMarkDownTags.removeMDIMG(input);
		System.out.println("------------------------------------------------------- ������ -------------------------------------------------------");
		SysClipboardUtil.setSysClipboardText(input);
		System.out.println(input);
		System.out.println("------------------------------------------------------- ������ -------------------------------------------------------");
		
	}
}
