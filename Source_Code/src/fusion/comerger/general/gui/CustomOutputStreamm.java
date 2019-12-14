package fusion.comerger.general.gui;

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

 



