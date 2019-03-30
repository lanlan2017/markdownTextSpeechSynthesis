package replace.special.character;

public class ReplaceSpecialCharacter
{
	public static String replaceSlant(String input)
	{
		return input.replaceAll("/", "б��");
	}
	public static String replaceBackSlant(String input)
	{
		return input.replaceAll("\\\\", "��б��");
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
		String testStr = "C:test\\test\\����Դ��֮�������һ��������ѯ������·�� ��������ѯ�����һ��Key/Value�飬�����ѯ���� �á�&�����ŷָ���·�����������ڲ�ѯ��䣬��ֻ�� value���֣����value�����á�/�����ŷָ���";
//		String testStr = "C:test\\test\\";
//		String testStr = "Key/Value";
//		String testStr = "�����ѯ���� �á�&�����ŷָ�";
		System.out.println(testStr);
		System.out.println("------------------------------------------------");
//		System.out.println(replaceBackSlant(testStr));
//		System.out.println(replaceSlant(testStr));
//		System.out.println(replaceAndSymbol(testStr));
		System.out.println(replaceSpecialCharacter(testStr));
	}
}
