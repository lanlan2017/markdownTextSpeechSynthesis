package replace.special.character;

public class ReplaceSpecialCharacter
{
	public static String replaceSlant(String input)
	{
		return input.replaceAll("/", "斜杠");
	}
	public static String replaceBackSlant(String input)
	{
		return input.replaceAll("\\\\", "反斜杠");
	}
	public static String replaceAndSymbol(String input)
	{
		return input.replaceAll("&", "and");
	}
	public static String replaceSpecialCharacter(String input)
	{
		String result;
		result = replaceSlant(input);
		result = replaceBackSlant(result);
		result = replaceAndSymbol(result);
		return result;
	}
	public static void main(String[] args)
	{
		String testStr = "C:test\\test\\在资源名之后可以有一个或多个查询语句或者路径 参数。查询语句是一个Key/Value组，多个查询语句间 用“&”符号分隔。路径参数类似于查询语句，但只有 value部分，多个value部分用“/”符号分隔。";
//		String testStr = "C:test\\test\\";
//		String testStr = "Key/Value";
//		String testStr = "多个查询语句间 用“&”符号分隔";
		System.out.println(testStr);
		System.out.println("------------------------------------------------");
//		System.out.println(replaceBackSlant(testStr));
//		System.out.println(replaceSlant(testStr));
//		System.out.println(replaceAndSymbol(testStr));
		System.out.println(replaceSpecialCharacter(testStr));
	}
}
