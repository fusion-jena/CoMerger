
package fusion.comerger.algorithm.matcher.vdoc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Stopword
{
    private ArrayList<String> stopwordlist = new ArrayList<String>();

    public Stopword()
    {
        try {
//            FileReader fr = new FileReader(path + "stopwords.txt");
        	//To DO: local address should be correct
        	FileReader fr = new FileReader("C://Doc//stopwords.txt");
//            FileReader fr = new FileReader("./stopwords.txt");
            BufferedReader br = new BufferedReader(fr);
            String s = br.readLine();
            while (s != null) {
                stopwordlist.add(s.trim());
                s = br.readLine();
            }
            fr.close();
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] removeStopword(String src[])
    {
        int length = src.length;
        if (length == 1) {
            return src;
        } else {
            ArrayList<String> temp = new ArrayList<String>();
            for (int i = 0; i < src.length; i++) {
                if (!stopwordlist.contains(src[i])) {
                    temp.add(src[i]);
                }
            }
            String des[] = new String[temp.size()];
            for (int i = 0; i < temp.size(); i++) {
                des[i] = temp.get(i);
            }
            return des;
        }
    }

    public ArrayList<String> getStopwordList()
    {
        Stopword sw = new Stopword();
        return sw.stopwordlist;
    }
}
