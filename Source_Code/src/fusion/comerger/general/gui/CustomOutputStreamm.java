package fusion.comerger.general.gui;
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
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

//output .. Redirect console to JtextArea
        public class CustomOutputStreamm extends OutputStream {
            private JTextArea textArea;
             
            public CustomOutputStreamm(JTextArea textArea) {
                this.textArea = textArea;
            }
             
            @Override
            public void write(int b) throws IOException {
                // redirects data to the text area
                textArea.append(String.valueOf((char)b));
                // scrolls the text area to the end of data
                textArea.setCaretPosition(textArea.getDocument().getLength());
                ((DefaultCaret) textArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            }
        }

 



