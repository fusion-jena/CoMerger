
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
public class WordFrequency
{
    private String word = "";
    private double frequency = 0;

    public WordFrequency(String w, double f)
    {
        if (w != null) {
            word = w;
        }
        if (f > 0) {
            frequency = f;
        }
    }

    public void setWord(String w)
    {
        if (w != null) {
            word = w;
        }
    }

    public void setFrequency(double f)
    {
        if (f > 0) {
            frequency = f;
        }
    }

    public String getWord()
    {
        return word;
    }

    public double getFrequency()
    {
        return frequency;
    }
}
