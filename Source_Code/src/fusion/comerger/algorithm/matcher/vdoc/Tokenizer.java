
package fusion.comerger.algorithm.matcher.vdoc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import org.tartarus.snowball.SnowballProgram;

public class Tokenizer
{
    private final String delimiters = " \t\n\r\f~!@#$%^&*()_+|`-=\\{}[]:\";'<>?,./'1234567890";

    public String[] split(String source)
    {
        ArrayList<String> listForNumber = new ArrayList<String>();
        flag3:
        for (int i = 0; i < source.length(); i++) {
            char thisChar = source.charAt(i);
            StringBuffer thisNumber = new StringBuffer();
            boolean hasDigit = false;
            if (Character.isDigit(thisChar)) {
                thisNumber.append(thisChar);
                for (++i; i < source.length(); i++) {
                    thisChar = source.charAt(i);
                    if ((thisChar == '.') && !hasDigit) {
                        thisNumber.append(thisChar);
                        hasDigit = true;
                    } else if (Character.isDigit(thisChar)) {
                        thisNumber.append(thisChar);
                    } else {
                        if (thisNumber.length() != 0) {
                            listForNumber.add(thisNumber.toString());
                            continue flag3;
                        }
                    }
                }
                if (thisNumber.length() != 0) {
                    listForNumber.add(thisNumber.toString());
                }
            }
        }

        int positionOfDot;
        StringBuffer tempSource = new StringBuffer(source);
        while ((positionOfDot = tempSource.indexOf(".")) != -1) {
            tempSource.deleteCharAt(positionOfDot);
        }
        source = tempSource.toString();
        StringTokenizer stringTokenizer = new StringTokenizer(source, delimiters);

        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> listForAllUpperCase = new ArrayList<String>();

        flag0:
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            boolean allUpperCase = true;
            for (int i = 0; i < token.length(); i++) {
                if (!Character.isUpperCase(token.charAt(i))) {
                    allUpperCase = false;
                }
            }
            if (allUpperCase) {
                listForAllUpperCase.add(token);
                continue flag0;
            }
            int index = 0;
            flag1:
            while (index < token.length()) {
                flag2:
                while (true) {
                    index++;
                    if ((index == token.length()) || 
                    		!Character.isLowerCase(token.charAt(index))) {
                        break flag2;
                    }
                }
                list.add(token.substring(0, index).toLowerCase());
                token = token.substring(index);
                index = 0;
                continue flag1;
            }
        }

        try {
            String language = "english";
            if (fusion.comerger.general.cc.Parameters.lang.equals("nl")) {
                language = "dutch";
            }
            Class<?> stemClass = 
            	Class.forName("org.tartarus.snowball.ext." + language + "Stemmer");
            SnowballProgram stemmer = (SnowballProgram) stemClass.newInstance();
            Method stemMethod = stemClass.getMethod("stem", new Class[0]);
            Object[] emptyArgs = new Object[0];
            for (int i = 0; i < list.size(); i++) {
                stemmer.setCurrent((String) list.get(i));
                stemMethod.invoke(stemmer, emptyArgs);
                list.set(i, stemmer.getCurrent());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < listForAllUpperCase.size(); i++) {
            list.add(listForAllUpperCase.get(i));
        }
        for (int i = 0; i < listForNumber.size(); i++) {
            list.add(listForNumber.get(i));
        }

        String[] array = new String[list.size()];
        Iterator<String> iter = list.iterator();
        int index = 0;
        while (iter.hasNext()) {
            array[index] = iter.next().toLowerCase();
            index++;
        }
        return array;
    }
}
