/**
 * 
 */
package com.oren.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author giil
 *
 */
public class Document {
	String header = null;
	Element root = null;
	public Document() {
		header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	}
	public Document(String header) {
		this.header = header;
	}
	public Document nDoc() {
		root = null;
		return this;
	}
	public void sHeader(String header) {
		this.header = header;
	}
	public String gHeader() {
		return this.header;
	}

	public Document build(Element ele) {
		nDoc();
		root = ele;
		return this;
	}
	public Document sRootElem(Element ele) {
		nDoc();
		root = ele;
		return this;
	}
	public Element gRootElem() {
		return root;
	}
	public String xmlToString() {
		StringBuffer xmlStr = new StringBuffer(512);
		xmlStr.append(this.header);
		xmlStr.append(convertXmlString(root, 0));
		return xmlStr.toString();
	}
	protected StringBuffer convertXmlString(Element ele, int level) {
		StringBuffer chum = new StringBuffer();
		chum.append("\n");
		for(int i = 0; i <level;i++) chum.append("\t");
		StringBuffer xmlStr = new StringBuffer(64);
		xmlStr.append(chum.toString()+"<" + ele.gName());
		StringBuffer strAttr = new StringBuffer();
		for(int i = 0; i<ele.gAttrs().size();i++) {
			Attribute attr = ele.gAttrs().get(i);
			if(attr.gNS() != null) {
				strAttr.append(" " + attr.gNS().gNS()+":"+attr.gName()+"=\""+attr.gValue()+"\"");
			} else 
				strAttr.append(" " + attr.gName()+"=\""+attr.gValue()+"\"");
		}
		strAttr.append(">");
		xmlStr.append(strAttr);
		if(ele.gText() != null && !ele.gText().trim().equals("")) {
			xmlStr.append(ele.gText());
			xmlStr.append("</" + ele.gName()+">");
		} else {
			int cnt = ele.gChildren().size();
			for(int i = 0; i < cnt; i++) {
				xmlStr.append(this.convertXmlString(ele.gChildren().get(i), level+1));
			}
			if(cnt == 0) xmlStr.append("</" + ele.gName()+">");
			else xmlStr.append(chum.toString() + "</" + ele.gName()+">");
		}
		return xmlStr;
	}
	public byte[] xmlToBytes() {
		return xmlToString().getBytes();
	}
	public byte[] xmlToBytes(String encodding) {
		try {
			return xmlToString().getBytes(encodding);
		} catch (UnsupportedEncodingException e) {
			System.out.println("gXml : Document : Error -> " + e.getMessage());
			return null;
		}
	}
	public InputStream xmlToInputStream() {
		ByteArrayInputStream stream = new ByteArrayInputStream(xmlToBytes());
		return stream;
	}
	protected void XmlToOutputStream(Element ele, int level, OutputStream out) throws IOException {
		StringBuffer chum = new StringBuffer();
		chum.append("\n");
		for(int i = 0; i <level;i++) chum.append("\t");
		out.write((chum.toString()+"<" + ele.gName()).getBytes());
		StringBuffer strAttr = new StringBuffer(32);
		for(int i = 0; i<ele.gAttrs().size();i++) {
			Attribute attr = ele.gAttrs().get(i);
			strAttr.append(" " + attr.gName()+"=\""+attr.gValue()+"\"");
		}
		out.write((strAttr.toString() + ">").getBytes());
		
		if(ele.gText() != null && !ele.gText().trim().equals("")) {
			String xmlStr = ele.gText()+"</" + ele.gName()+">";
			out.write(xmlStr.getBytes());
		} else {
			int cnt = ele.gChildren().size();
			for(int i = 0; i < cnt; i++) {
				this.XmlToOutputStream(ele.gChildren().get(i), level+1, out);
//				out.write(xmlStr.getBytes());
			}
			StringBuffer xmlStr = new StringBuffer();
			if(cnt == 0) xmlStr.append("</" + ele.gName()+">");
			else xmlStr.append(chum.toString() + "</" + ele.gName()+">");
			out.write(xmlStr.toString().getBytes());
		}
		out.flush();
	}
	public void xmlToOutputStream(OutputStream out) {
		try {
			out.write(this.header.getBytes());
			XmlToOutputStream(root, 0, out);
		} catch (IOException e) {
			System.out.println("gXml : Document : Error -> " + e.getMessage());
		}
	}
}
