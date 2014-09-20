/**
 * 
 */
package com.oren.xml;

import java.util.Vector;

/**
 * @author orennamu
 *
 */

public class ElemList {
	Vector nl = null;
	int[] obj = null;
	public ElemList(Vector n) {
		nl = n;
	}
	public int size() {
		return nl.size();
	}
	public Element get(int idx) {
		return (Element)nl.elementAt(idx);
	}
	public void rm(int idx) {
		nl.removeElementAt(idx);
	}
	public void clear() {
		nl.removeAllElements();
	}
	public void rAll() {
		nl.removeAllElements();
	}
}
