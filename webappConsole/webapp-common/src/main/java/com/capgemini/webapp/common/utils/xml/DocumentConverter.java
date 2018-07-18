package com.capgemini.webapp.common.utils.xml;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class DocumentConverter {

  public static String convertDocumentToString(Document doc) {
    DOMImplementation impl = doc.getImplementation();
    if (impl instanceof DOMImplementationLS) {
      return ((DOMImplementationLS) impl).createLSSerializer().writeToString(doc);
    } else {
      throw new IllegalArgumentException(
          "Unable to serialize document.  Implementation not supported: " + (impl == null ?
              "null" :
              impl.getClass().getSimpleName()));
    }
  }


  public static Document convertStringToDocument(String xmlStr) {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder;
    try {
      builder = factory.newDocumentBuilder();
      Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
      return doc;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
