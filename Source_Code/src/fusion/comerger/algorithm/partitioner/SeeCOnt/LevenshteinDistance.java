package fusion.comerger.algorithm.partitioner.SeeCOnt;

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

public class LevenshteinDistance {

 public static double similarity(String s1, String s2) {
     if (s1.length() < s2.length()) { // s1 should always be bigger
         String swap = s1; s1 = s2; s2 = swap;
     }
     int bigLen = s1.length();
     if (bigLen == 0) { return 1.0; /* both strings are zero length */ }
     return (bigLen - computeEditDistance(s1, s2)) / (double) bigLen;
 }

 public static int computeEditDistance(String s1, String s2) {
     s1 = s1.toLowerCase();
     s2 = s2.toLowerCase();

     int[] costs = new int[s2.length() + 1];
     for (int i = 0; i <= s1.length(); i++) {
         int lastValue = i;
         for (int j = 0; j <= s2.length(); j++) {
             if (i == 0)
                 costs[j] = j;
             else {
                 if (j > 0) {
                     int newValue = costs[j - 1];
                     if (s1.charAt(i - 1) != s2.charAt(j - 1))
                         newValue = Math.min(Math.min(newValue, lastValue),
                                 costs[j]) + 1;
                     costs[j - 1] = lastValue;
                     lastValue = newValue;
                 }
             }
         }
         if (i > 0)
             costs[s2.length()] = lastValue;
     }
     return costs[s2.length()];
 }

 public static double getDistance(String s1, String s2) {
     double CSimilarity = similarity(s1, s2);
 return CSimilarity;
 }

}