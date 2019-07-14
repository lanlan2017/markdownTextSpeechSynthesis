package latex.reader;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import clipboard.util.SysClipboardUtil;

public class LatexReader
{
	static String greekAlphabet = "\\\\(alpha|beta|gamma|delta|epsilon|zeta|eta|theta|iota|kappa|lambda|mu|nu|xi|omicron|pi|rho|sigma|tau|upsilon|phi|chi|psi|omega)";
	// static String fraction = "\\\\dfrac\\{(.+?)\\}\\{(.+?)\\}";
	static String exponentialFunction = "(\\w+)\\^\\{(.+?)\\}";
	static String exponentialFunctionReplace = "$1��$2�η� ";
	static String substitution = "\\|(\\w+)=(.+)";
	static String substitutionReplace = "��$1����$2ʱ ";
	// static String fush = "^-(.+)";
	// static String fushReplace = "��$1";

	public static void main(String[] args)
	{
		// String latexCode = "$T\\approx(0.15 \\sim
		// 0.5)\\dfrac{1}{\\omega_c}$";
		// String latexCode = "$T\\approx(0.15 \\sim
		// 0.5)\\dfrac{1}{\\alpha_c}$";
		String latexCode = SysClipboardUtil.getSysClipboardText();

		latexCode = readeLaTexCode(latexCode);
		System.out.println("----------- �ʶ���ʽ ��ʼ ---------------");
		System.out.println(latexCode);
		System.out.println("----------- �ʶ���ʽ ���� ---------------");
	}

	/**
	 * @param latexCode
	 * @return
	 */
	public static String readeLaTexCode(String latexCode)
	{
		latexCode = latexCode.replace("$", "");
		latexCode = latexCode.replaceAll("\\\\approx", "Լ���� ");
		latexCode = latexCode.replaceAll(substitution, substitutionReplace);
		latexCode = latexCode.replaceAll("\\\\cdots", "ʡ�Ժ� ");
		latexCode = latexCode.replaceAll("\\\\sim", "��");
		// �ʶ���ʽ
		latexCode = replaceDfrac(latexCode);
		// latexCode = latexCode.replaceAll(fraction, "$2��֮$1 ");

		// latexCode = latexCode.replaceAll(
		// "(" + ".+?��֮.+?" + ") (" + ".+?��֮.+?" + ")", "$1����$2");
		latexCode = latexCode.replaceAll(greekAlphabet, "$1");
		// latexCode = latexCode.replaceAll(exponentialFunction,
		// exponentialFunctionReplace);
		// �ʶ�ָ������
		latexCode = readExponentialFun(latexCode);

		latexCode = latexCode.replace("=", "���� ");
		latexCode = latexCode.replace("-", "��ȥ");
		return latexCode;
	}

	/**
	 * @param latexCode
	 * @return
	 */
	private static String replaceDfrac(String latexCode)
	{
		HashMap<String, String> hashMap = findDfracs(latexCode);
		// 1 ��ȡMap.Entry�����Set����
		Set<Entry<String, String>> mapEntry = hashMap.entrySet();
		// 2 Map.Entry�����Set���ϵ�����
		Iterator<Entry<String, String>> mapEntryIt = mapEntry.iterator();
		while (mapEntryIt.hasNext())
		{
			// 2 ��Set������ȡ��һ�� Map.Entryʵ��
			Entry<String, String> mapEntryElement = mapEntryIt.next();
			// 3 �ֱ�ȡ������ֵ
			String key = mapEntryElement.getKey();
			String value = mapEntryElement.getValue();
			latexCode=latexCode.replace(key, value);
		}

		return latexCode;
	}

	/**
	 * @param latexCode
	 * @return
	 */
	public static String readExponentialFun(String latexCode)
	{
		Pattern pattern = Pattern.compile(exponentialFunction);
		Matcher matcher = pattern.matcher(latexCode);
		StringBuffer sb = new StringBuffer();
		String diShu;
		String zhiShu;
		while (matcher.find())
		{
			// ��ȡƥ�䵽��һ������
			diShu = matcher.group(1);
			zhiShu = matcher.group(2);
			diShu = fuShu(diShu);
			zhiShu = fuShu(zhiShu);
			System.out.println("diShu:" + diShu);
			System.out.println("zhiShu:" + zhiShu);
			// ������д�ϴ�����....
			// �滻ԭ��ƥ����ı�
			matcher.appendReplacement(sb, diShu + "��" + zhiShu + "�η�");
		}
		// ��Ӻ���û��ƥ����ı�
		matcher.appendTail(sb);

		latexCode = sb.toString();
		return latexCode;
	}

	/**
	 * @param latexCode
	 * @return
	 */
	public static HashMap<String, String> findDfracs(String latexCode)
	{
		String startFlag = "\\dfrac{";
		int dfracStartIndex = latexCode.indexOf(startFlag);

		HashMap<String, String> hashMap = null;
		if (dfracStartIndex > 0)
		{
			int daKuoHao = 0;
			int before = 0;
			int count = 0;
			char ch;
			StringBuffer sb = new StringBuffer();
			hashMap = new HashMap<String, String>();
			daKuoHao = 0;
			for (int i = dfracStartIndex; i < latexCode.length(); i++)
			{
				ch = latexCode.charAt(i);
				if ('{' == ch)
				{
					daKuoHao++;
				}
				// �����������Ķ���
				if (daKuoHao >= 1)
				{
					sb.append(ch);
				}
				if ('}' == ch)
				{
					daKuoHao--;
				}
				//
				if (before == 1 && daKuoHao == 0 && (++count) % 2 == 0)
				{
					String key = "\\dfrac" + sb.toString();
					String value = key.replaceAll(
							"\\\\dfrac\\{(.+)\\}\\{(.+)\\}", "$2��֮$1");
					System.out.println(key);
					System.out.println(value);
					// ����
					hashMap.put(key, value);
					sb.delete(0, sb.length());
				}
				// �ϴε���������
				before = daKuoHao;
			}
		}
		return hashMap;
	}

	/**
	 * @param fushCode
	 * @return
	 */
	public static String fuShu(String fushCode)
	{
		String fush = "^-(.+)";
		String fushReplace = "��$1";
		fushCode = fushCode.replaceAll(fush, fushReplace);
		return fushCode;
	}
}
