package tools.io.properties;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class ContainSpecialWordsProperties {
    // 1.���������ļ�����
    private static Properties pr = null;
    private static Set<String> keySet;
    // 3.ʹ�õ�ǰ�����ļ��е��ı��滻ƥ����ı�
    public static String repalceByProperties(String input)
    {
        if (pr == null)
        {
            pr = PropertiesInstance.getPropertiesInstanceUTF8("ContainSpecialWords.properties");
            keySet = pr.stringPropertyNames();
        }
        for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();)
        {
            String key = (String) iterator.next();
            if (input.contains(key))
            {
                input = input.replace(key, pr.getProperty(key));
            }
        }
        return input;
    }
    // public static void main(String[] args)
    // {
    // System.out.println(repalceByProperties("javax.https."));
    // }
}
