
package fusion.comerger.algorithm.matcher.vdoc;
/* 
 * This package is downloaded from the FALCON-AO tool, 
 * available in http://ws.nju.edu.cn/falcon-ao/
 * For more information of this method, please refer to
 * Hu, W. and Qu, Y., 2008. Falcon-AO: A practical ontology matching system. Journal of web semantics, 6(3), pp.237-239.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
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
