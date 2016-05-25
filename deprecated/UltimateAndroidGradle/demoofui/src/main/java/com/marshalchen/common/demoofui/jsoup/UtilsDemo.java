

package com.marshalchen.common.demoofui.jsoup;

import com.marshalchen.common.commonUtils.logUtils.Logs;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by cym on 14-7-19.
 */
public class UtilsDemo {
    public static String TestJsoup() {
        String html1 = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p></body></html>";
        StringBuffer sb = new StringBuffer();
        Document doc = Jsoup.parse(html1);
        Logs.d("docs---" + doc.title() + "   " + doc.getAllElements().size());
        Logs.d("docs---" + doc.children().size() + "   " + doc.location());
        for (Element element : doc.getAllElements()) {
            sb.append(element.tagName() + "   " + element.nodeName() + "   " + element.children().size() + "   " + element.data() + "   " + element.text() + "\n");
            Logs.d(element.text() + "   ");
        }
        return sb.toString();

    }
}
