package latex.reader;

public class Test
{
	public static void main(String[] args)
	{
//		String input=SysClipboardUtil.getSysClipboardText();
		String input="$D(z)=\\dfrac{U(z)}{E(z)}=\\dfrac{b_0+b_1z^{-1}+\\codts+b_mz^{-m}}{1+a_1z^{-1}+\\cdots+a_nz^{-n}}$";
		System.out.println(input);
		LatexReader.findDfracs(input);
	}
}
