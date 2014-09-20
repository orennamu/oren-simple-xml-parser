/**
 * 
 */
package com.oren.xml;

import java.util.Vector;

/**
 * @author orennamu
 *
 */ 

public class Attribute {
	Namespace ns = null;
	String attrName = null;
	String value = null;
	public Attribute(String name) {
		attrName = name;
	}
	public Attribute(String name, Namespace ns) {
		attrName = name;
		this.ns = ns;
	}
	public String gName() {
		return attrName;
	}
	public Namespace gNS() {
		return this.ns;
	}
	public String gValue() {
		return value;
	}
	public Attribute sValue(String txt) {
		String[] strs = split(txt, "&");
		int cnt = strs.length;
		value = strs[0];//.trim();
		for(int i =1;i<cnt;i++) {
			value += "&amp;" + strs[i];//.trim();
		}
		return this;
	}
	public String[] split(String original, String separator) {
		Vector nodes = new Vector();
		// Parse nodes into vector
		int index = original.indexOf(separator);
		while(index>=0) {
			nodes.addElement( original.substring(0, index) );
			original = original.substring(index+separator.length());
			index = original.indexOf(separator);
		}
		// Get the last node
		nodes.addElement( original );

		// Create splitted string array
		String[] result = new String[ nodes.size() ];
		if( nodes.size()>0 ) {
			for(int loop=0; loop<nodes.size(); loop++)
			{
				result[loop] = (String)nodes.elementAt(loop);
			}
		}
		return result;
	}
}
