
package fusion.comerger.general.gernalAnalysis.similarBlock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class BlockMappingWriter2
{
    private ArrayList<BlockMapping> blockMappings = null;
    private RandomAccessFile raf = null;
    private String filepath = null;
    private ArrayList<String> writeList = null;

    public BlockMappingWriter2(ArrayList<BlockMapping> bm, String fp)
    {
        blockMappings = bm;
        filepath = fp;
        try {
            File file = new File(fp);
            if (file.exists()) {
                file.delete();
            }
            raf = new RandomAccessFile(filepath, "rw");
            writeList = new ArrayList<String>();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void write(String block1, String block2, String uri1, String uri2)
    {
        writeNS();
        writeStart("yes", "0", "11", block1, block2, uri1, uri2);
        for (int i = 0, n = blockMappings.size(); i < n; i++) {
            BlockMapping bm = blockMappings.get(i);
            String e1 = bm.getClusterName1();
            String e2 = bm.getClusterName2();
            String measure = Double.toString(bm.getSimilarity());
            writeElement(e1, e2, measure);

        }
        writeEnd();
        writeToFile();
        try {
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeNS()
    {
        String temp = "<?xml version='1.0' encoding='utf-8'?>\n" 
                + "<rdf:RDF xmlns='http://iws.seu.edu.cn/falcon' \n" 
                + "xmlns:rdf='http://www.w3.org/1999/02/22-rdf-syntax-ns#' \n" 
                + "xmlns:xsd='http://www.w3.org/2001/XMLSchema#'>\n";
        writeList.add(temp);
    }

    public void writeStart(String xml, String level, String type, 
    		String block1, String block2, String uri1, String uri2)
    {
        String temp = "<BlockMapping>\n" 
                + "  <xml>" + xml + "</xml>\n" 
                + "  <level>" + level + "</level>\n" 
                + "  <type>" + type + "</type>\n" 
                + "  <onto1>" + block1 + "</onto1>\n" 
                + "  <onto2>" + block2 + "</onto2>\n" 
                + "  <uri1>" + uri1 + "</uri1>\n" 
                + "  <uri2>" + uri2 + "</uri2>";
        writeList.add(temp);
    }

    public void writeEnd()
    {
        String temp = "  </BlockMapping>\n</rdf:RDF>";
        writeList.add(temp);
    }

    public void writeElement(String res1, String res2, String measure)
    {
        String temp = "    <map>\n" 
                + "      <Cell>\n" 
                + "        <block1 rdf:resource=\"" + res1 + "\"/>\n" 
                + "        <block2 rdf:resource=\"" + res2 + "\"/>\n" 
                + "        <measure rdf:datatype=\"http://www.w3.org/2001/XMLSchema#float\">" 
                + measure + "</measure>\n" 
                + "      </Cell>\n"
                + "    </map>";
        writeList.add(temp);
    }

    public void writeToFile()
    {
        try {
            for (int i = 0, n = writeList.size(); i < n; i++) {
                raf.seek(raf.length());
                raf.writeBytes((String) writeList.get(i) + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
