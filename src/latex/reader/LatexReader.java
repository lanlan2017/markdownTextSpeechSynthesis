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
	static String exponentialFunctionReplace = "$1的$2次方 ";
	static String substitution = "\\|(\\w+)=(.+)";
	static String substitutionReplace = "当$1等于$2时 ";
	// static String fush = "^-(.+)";
	// static String fushReplace = "负$1";

	public static void main(String[] args)
	{
		// String latexCode = "$T\\approx(0.15 \\sim
		// 0.5)\\dfrac{1}{\\omega_c}$";
		// String latexCode = "$T\\approx(0.15 \\sim
		// 0.5)\\dfrac{1}{\\alpha_c}$";
		String latexCode = SysClipboardUtil.getSysClipboardText();

		latexCode = readeLaTexCode(latexCode);
		System.out.println("----------- 朗读公式 开始 ---------------");
		System.out.println(latexCode);
		System.out.println("----------- 朗读公式 结束 ---------------");
	}

	/**
	 * @param latexCode
	 * @return
	 */
	public static String readeLaTexCode(String latexCode)
	{
		latexCode = latexCode.replace("$", "");
		latexCode = latexCode.replaceAll("\\\\approx", "约等于 ");
		latexCode = latexCode.replaceAll(substitution, substitutionReplace);
		latexCode = latexCode.replaceAll("\\\\cdots", "省略号 ");
		latexCode = latexCode.replaceAll("\\\\sim", "到");
		// 朗读分式
		latexCode = replaceDfrac(latexCode);
		// latexCode = latexCode.replaceAll(fraction, "$2分之$1 ");

		// latexCode = latexCode.replaceAll(
		// "(" + ".+?分之.+?" + ") (" + ".+?分之.+?" + ")", "$1乘以$2");
		latexCode = latexCode.replaceAll(greekAlphabet, "$1");
		// latexCode = latexCode.replaceAll(exponentialFunction,
		// exponentialFunctionReplace);
		// 朗读指数函数
		latexCode = readExponentialFun(latexCode);

		latexCode = latexCode.replace("=", "等于 ");
		latexCode = latexCode.replace("-", "减去");
		return latexCode;
	}

	/**
	 * @param latexCode
	 * @return
	 */
	private static String replaceDfrac(String latexCode)
	{
		HashMap<String, String> hashMap = findDfracs(latexCode);
		// 1 获取Map.Entry对象的Set集合
		Set<Entry<String, String>> mapEntry = hashMap.entrySet();
		// 2 Map.Entry对象的Set集合迭代器
		Iterator<Entry<String, String>> mapEntryIt = mapEntry.iterator();
		while (mapEntryIt.hasNext())
		{
			// 2 从Set集合中取出一个 Map.Entry实例
			Entry<String, String> mapEntryElement = mapEntryIt.next();
			// 3 分别取出键和值
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
			// 获取匹配到的一个分组
			diShu = matcher.group(1);
			zhiShu = matcher.group(2);
			diShu = fuShu(diShu);
			zhiShu = fuShu(zhiShu);
			System.out.println("diShu:" + diShu);
			System.out.println("zhiShu:" + zhiShu);
			// 在这里写上处理方法....
			// 替换原来匹配的文本
			matcher.appendReplacement(sb, diShu + "的" + zhiShu + "次方");
		}
		// 添加后面没有匹配的文本
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
				// 添加括号里面的东西
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
							"\\\\dfrac\\{(.+)\\}\\{(.+)\\}", "$2分之$1");
					System.out.println(key);
					System.out.println(value);
					// 保存
					hashMap.put(key, value);
					sb.delete(0, sb.length());
				}
				// 上次的括号数量
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
		String fushReplace = "负$1";
		fushCode = fushCode.replaceAll(fush, fushReplace);
		return fushCode;
	}
}
