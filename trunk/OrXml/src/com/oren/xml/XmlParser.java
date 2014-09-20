package com.oren.xml;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.ParserAdapter;

public class XmlParser extends DefaultHandler {
	  // Private members needed to parse the XML document
	  private boolean parsingInProgress; // keep track of parsing
	  private Stack elementStack = new Stack();
	  private boolean bFirstElement = true;
	  private Document doc = null; //new Document().newDocument(); // keep track of QName

	  public Document parse(String filePath) {
		  return parse(filePath, true, null);
	  }
	  public Document parse(String filePath, String url) {
		  return parse(filePath, true, url);
	  }
	  public Document parse(String filePath,boolean bNs) {
		  return parse(filePath, bNs, null);
	  }
	  public Document parse(String filePath,boolean bNs, String url) {
		  InputStream is;
		  try {
			  is = new FileInputStream(filePath);
			  return parse(is, bNs, null);
		  } catch (FileNotFoundException e) {
			  System.out.println("gXml : XmlParser : Error -> " + e.getMessage());
			  return null;
		  }
	  }
	  public Document parseString(String str) {
		  return parseString(str, true, null);
	  }
	  public Document parseString(String str, String url) {
		  return parseString(str, true, url);
	  }
	  public Document parseString(String str,boolean bNs) {
		  return parseString(str, bNs, null);
	  }
	  public Document parseString(String str, boolean bNs, String url) {
		  try {
			  SAXParserFactory factory = SAXParserFactory.newInstance();
			  if(bNs) {
				  factory.setNamespaceAware(bNs);
				  factory.setValidating(bNs);
				  if(url != null)
					  factory.setFeature(url, true);
			  }
			  SAXParser sp = factory.newSAXParser();
			  ParserAdapter pa = new ParserAdapter(sp.getParser());
			  pa.setContentHandler(this);  
			  pa.parse(new InputSource(new ByteArrayInputStream(str.getBytes())));
		  } catch (ParserConfigurationException e) {
				System.out.println("gXml : XmlParser : Error -> " + e.getMessage());
		  } catch (SAXException e) {
				System.out.println("gXml : XmlParser : Error -> " + e.getMessage());
		  } catch (FactoryConfigurationError e) {
				System.out.println("gXml : XmlParser : Error -> " + e.getMessage());
		  } catch (IOException e) {
				System.out.println("gXml : XmlParser : Error -> " + e.getMessage());
		  }
		  return doc;
	  }
	  public Document parse(InputStream in) {
		  return parse(in, true, null);
	  }
	  public Document parse(InputStream in, String url) {
			return parse(in, true, url);
		}
	  public Document parse(InputStream in, boolean bNs, String url) {
		  try {
			  SAXParserFactory factory = SAXParserFactory.newInstance();
			  if(bNs) {
				  factory.setNamespaceAware(bNs);
				  factory.setValidating(bNs);
				  if(url != null)
					  factory.setFeature(url, true);
			  }
			  SAXParser sp = factory.newSAXParser();
			  ParserAdapter pa = new ParserAdapter(sp.getParser());  
			  pa.setContentHandler(this);  
			  pa.parse(new InputSource(in)); 
		  } catch (ParserConfigurationException e) {
				System.out.println("gXml : XmlParser : Error -> " + e.getMessage());
		  } catch (SAXException e) {
				System.out.println("gXml : XmlParser : Error -> " + e.getMessage());
		  } catch (FactoryConfigurationError e) {
				System.out.println("gXml : XmlParser : Error -> " + e.getMessage());
		  } catch (IOException e) {
				System.out.println("gXml : XmlParser : Error -> " + e.getMessage());
		  }
		  return doc;
	  }
//	  public Document parse(InputStream in, Document doc) {
//		  this.doc = doc;
//		  try {
//			  SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
//			  parser.parse(in, this);
////			  while(parsingInProgress){}
//		  } catch (ParserConfigurationException e) {
//				System.out.println("gXml : XmlParser : Error -> " + e.getMessage());
//		  } catch (SAXException e) {
//				System.out.println("gXml : XmlParser : Error -> " + e.getMessage());
//		  } catch (FactoryConfigurationError e) {
//				System.out.println("gXml : XmlParser : Error -> " + e.getMessage());
//		  } catch (IOException e) {
//				System.out.println("gXml : XmlParser : Error -> " + e.getMessage());
//		  }
//		  return doc;
//	  }
	  /**
	   * Start of document processing.
	   * @throws org.xml.sax.SAXException is any SAX exception, 
	   * possibly wrapping another exception.
	  */
	  public void startDocument() throws SAXException {
	      parsingInProgress = true;
	      doc = new Document();//.newDocument();
	  }

	  /**
	   * End of document processing.
	   * @throws org.xml.sax.SAXException is any SAX exception, 
	   * possibly wrapping another exception.
	   */
	  public void endDocument() throws SAXException {
	      parsingInProgress = false;
	  }

	  /**
	   * Process the new element.
	   * @param uri is the Namespace URI, or the empty string if the element 
	   * has no Namespace URI or if Namespace processing is not being performed.
	   * @param localName is the The local name (without prefix), or the empty 
	   * string if Namespace processing is not being performed.
	   * @param qName is the qualified name (with prefix), or the empty string 
	   * if qualified names are not available.
	   * @param attributes is the attributes attached to the element. If there 
	   * are no attributes, it shall be an empty Attributes object.
	   * @throws org.xml.sax.SAXException is any SAX exception, 
	   * possibly wrapping another exception.
	   */
	  public Element root  = null;
	  public String oldNs= null;
	  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//		  System.out.println("xml_element --> " + uri);
		  String elemName = qName;
		  String nsName = "";
		  if(qName == null || qName.equals("")) {
			  elemName = localName;
		  } else {
			  if(qName.indexOf(":") > -1) {
				  nsName = "xmlns:"+qName.substring(0, qName.indexOf(":"));
			  } else {
				  nsName = "xmlns";
			  }
		  }
		  
		  Element ele = new Element(elemName);
		  if(root == null) 
			  root = ele;
		  if((oldNs == null || !oldNs.equals(nsName)) && uri != null && !uri.equals("")) {
//			  if(nsName.equals("")) nsName = "xmlns";
			  root.sNS(new Namespace(nsName, uri));
			  oldNs = nsName;
//			  System.out.println("xml_start --> " + uri+"=="+nsName+"=="+qName);
		  }
		  
	      for (int index = 0, count = attributes.getLength(); index < count; index++) {
	    	  String suri = attributes.getURI(index);
	    	  
	    	  String qsName = attributes.getQName(index);
	    	  String aName = attributes.getLocalName(index);
	    	  if(qsName == null || qsName.equals("")) {
	    		  qsName = aName;
	    	  }
	    	  if(suri != null && !suri.equals("")) {
	    		  if(qsName.indexOf(":") > -1) {
	    			  String ans = qsName.substring(0,qsName.indexOf(":"));
	    			  ele.sNS(new Namespace("xmlns:"+ans, suri));
				  } else
					  root.sNS(new Namespace("xmlns:"+qsName, suri));
	    	  }
//	    	  System.out.println("xml_attribute --> " + suri + ":" + qsName);
	    	  ele.sAttr(qsName, attributes.getValue(index));
//	    	  System.out.println("xml2 --> "  + qsName + "="+attributes.getValue(index)+"="+ attributes.getURI(index));
	      }		  
		  if(bFirstElement) {
			  doc.build(ele);
			  bFirstElement = false;
		  } else {
			  Element parentEle = (Element) elementStack.peek();
			  parentEle.aContent(ele);
		  }
		  elementStack.push(ele);
	  }
	  
	  /**
	   * Process the character data for current tag.
	   * @param ch are the element's characters.
	   * @param start is the start position in the character array.
	   * @param length is the number of characters to use from the 
	   * character array.
	   * @throws org.xml.sax.SAXException is any SAX exception, 
	   * possibly wrapping another exception.
	   */
	  public void characters(char[] ch, int start, int length) throws SAXException {
		  Element ele = (Element) elementStack.peek();
	      String chars = new String(ch, start, length);
	      StringBuffer strbuf = new StringBuffer();
	      if(ele.gText() != null) strbuf.append(ele.gText());
	      strbuf.append(chars);
//	      System.out.println("xml_char --> " + ele.getText()+ "="+chars);
	      ele.sText(strbuf.toString());
	  }

	  /**
	   * Process the end element tag.
	   * @param uri is the Namespace URI, or the empty string if the element 
	   * has no Namespace URI or if Namespace processing is not being performed.
	   * @param localName is the The local name (without prefix), or the empty 
	   * string if Namespace processing is not being performed.
	   * @param qName is the qualified name (with prefix), or the empty 
	   * string if qualified names are not available.
	   * @throws org.xml.sax.SAXException is any SAX exception, 
	   * possibly wrapping another exception.
	   */
	  public void endElement(String uri, String localName, String qName) throws SAXException {
//		  System.out.println("xml_end --> " + uri+"=="+localName+"=="+qName);
		  elementStack.pop();
	  }
}
