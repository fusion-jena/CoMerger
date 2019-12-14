
package fusion.comerger.general.gernalAnalysis.similarBlock;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BlockMappingReader2
{
    private String alignpath = null;

    public BlockMappingReader2(String ap)
    {
        alignpath = ap;
    }

    public ArrayList<BlockMapping> read()
    {
        try {
            File file = new File(alignpath);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(file);
            Element root = doc.getRootElement();
            Element align = root.element("BlockMapping");
            Iterator<?> map = align.elementIterator("map");
            if (!map.hasNext()) {
                return null;
            }
            ArrayList<BlockMapping> list = new ArrayList<BlockMapping>();
            while (map.hasNext()) {
                Element cell = ((Element) map.next()).element("Cell");
                String s1 = cell.element("block1").attributeValue("resource");
                String s2 = cell.element("block2").attributeValue("resource");
                double sim = Double.parseDouble(cell.elementText("measure"));
                BlockMapping bm = new BlockMapping(s1, s2, sim);
                list.add(bm);
            }
            return list;
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }
}
