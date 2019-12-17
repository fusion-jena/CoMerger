package fusion.comerger.general.gui;
/* 
* Please refer to https://github.com/fusion-jena/OAPT
* Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "SeeCOnt: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
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

 



