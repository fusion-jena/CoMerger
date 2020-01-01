package fusion.comerger.algorithm.matcher.string;
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

	import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;
	 
	public class WordNetMatcher {
	 
		public WordNetMatcher()
		{
			
		}
		private static ILexicalDatabase db = new NictWordNet();
		/*
		//available options of metrics
		private static RelatednessCalculator[] rcs = { new HirstStOnge(db),
				new LeacockChodorow(db), new Lesk(db), new WuPalmer(db),
				new Resnik(db), new JiangConrath(db), new Lin(db), new Path(db) };
		*/
		public static double compute(String word1, String word2) {
			WS4JConfiguration.getInstance().setMFS(true);
			double s = new WuPalmer(db).calcRelatednessOfWords(word1, word2);
			//double s1 = new Lin(db).calcRelatednessOfWords(word1, word2);
			//double s2 = new Path(db).calcRelatednessOfWords(word1, word2);
			//double s3 = new HirstStOnge(db).calcRelatednessOfWords(word1, word2);
			return s;
		}
	 
		public static void main(String[] args) {
			String[] words = {"add", "get", "filter", "remove", "check", "find", "collect", "create"};
	 
			for(int i=0; i<words.length-1; i++){
				for(int j=i+1; j<words.length; j++){
					double distance = compute(words[i], words[j]);
					System.out.println(words[i] +" -  " +  words[j] + " = " + distance);
				}
			}
		}
	}


