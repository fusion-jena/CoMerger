
package fusion.comerger.general.cc;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import fusion.comerger.general.cc.Parameters;
public class Configuration
{
    public Properties getProperties()
    {
        try {
//        	URL url = getClass().getResource(".");
//        	System.out.println(url.getPath());
//            File file = new File("./OPT.properties");
            File file = new File("C://Doc//OPT.properties");
            FileInputStream fis = new FileInputStream(file);
            Properties p = new Properties();
            p.load(fis);
            fis.close();
            return p;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void init()
    {
        Properties properties = getProperties();
        setFilepaths(properties);
        setEvaluation(properties);
        setControlCenter(properties);
    }

    private void setFilepaths(Properties properties)
    {
        String s1 = properties.getProperty("onto1").trim();
        if (s1 != null && s1.length() != 0) {
            Parameters.onto1 = s1;
        }
        String s2 = properties.getProperty("onto2").trim();
        if (s2 != null && s2.length() != 0) {
            Parameters.onto2 = s2;
        }
        String s3 = properties.getProperty("output").trim();
        if (s3 != null && s3.length() != 0) {
            Parameters.output = s3;
        }
        String s4 = properties.getProperty("reference").trim();
        if (s4 != null && s4.length() != 0) {
            Parameters.reference = s4;
        }
    }

    public void setEvaluation(Properties properties)
    {
        String s = properties.getProperty("alpha");
        if (s != null && s.length() != 0) {
        	fusion.comerger.general.output.Parameters.alpha = Double.parseDouble(s);
        }
    }

    private void setControlCenter(Properties properties)
    {
        String s1 = properties.getProperty("lowComp").trim();
        if (s1 != null && s1.length() != 0) {
            Parameters.lowComp = Integer.parseInt(s1);
        }
        String s2 = properties.getProperty("mediumComp").trim();
        if (s2 != null && s2.length() != 0) {
            Parameters.mediumComp = Integer.parseInt(s2);
        }
        String s3 = properties.getProperty("highComp").trim();
        if (s3 != null && s3.length() != 0) {
            Parameters.highComp = Integer.parseInt(s3);
        }
        String s4 = properties.getProperty("lingHighSim").trim();
        if (s4 != null && s4.length() != 0) {
            Parameters.lingHighSim = Double.parseDouble(s4);
        }
        String s5 = properties.getProperty("lingLowSim").trim();
        if (s5 != null && s5.length() != 0) {
            Parameters.lingLowSim = Double.parseDouble(s5);
        }
        String s6 = properties.getProperty("lingHighValue").trim();
        if (s6 != null && s6.length() != 0) {
            Parameters.lingHighValue = Double.parseDouble(s6);
        }
        String s7 = properties.getProperty("lingLowValue").trim();
        if (s7 != null && s7.length() != 0) {
            Parameters.lingLowValue = Double.parseDouble(s7);
        }
        String s8 = properties.getProperty("structPercent").trim();
        if (s8 != null && s8.length() != 0) {
            Parameters.structPercent = Double.parseDouble(s8);
        }
        String s9 = properties.getProperty("structHighValue").trim();
        if (s9 != null && s9.length() != 0) {
            Parameters.structHighValue = Double.parseDouble(s9);
        }
        String s10 = properties.getProperty("structLowValue").trim();
        if (s10 != null && s10.length() != 0) {
            Parameters.structLowValue = Double.parseDouble(s10);
        }
        String s11 = properties.getProperty("structHighRate").trim();
        if (s11 != null && s11.length() != 0) {
            Parameters.structHighRate = Double.parseDouble(s11);
        }
        String s12 = properties.getProperty("structLowRate").trim();
        if (s12 != null && s12.length() != 0) {
            Parameters.structLowRate = Double.parseDouble(s12);
        }
        String s13 = properties.getProperty("combWeight").trim();
        if (s13 != null && s13.length() != 0) {
            Parameters.combWeight = Double.parseDouble(s13);
        }
        String s14 = properties.getProperty("inclInstMatch").trim();
        if (s14 != null && s14.length() != 0) {
            Parameters.inclInstMatch = Boolean.parseBoolean(s14);
        }
        String s15 = properties.getProperty("largeOnto").trim();
        if (s15 != null && s15.length() != 0) {
            Parameters.largeOnto = Integer.parseInt(s15);
        }
    }


    }
