/**
 * 
 */
package com.oren.xml;

import java.util.Vector;

/**
 * @author giil
 *
 */

public class AttrList {
	Vector nnm = null;
	public AttrList(Vector nn) {
		nnm = nn;
	}
	public Attribute get(int idx) {
		return (Attribute)nnm.elementAt(idx);
	}
	public int size() {
		return nnm.size();
	}
	public void rm(int idx) {
		nnm.removeElementAt(idx);
	}
	public void clear() {
		nnm.removeAllElements();
	}
	public void rmAll() {
		nnm.removeAllElements();
	}
}
