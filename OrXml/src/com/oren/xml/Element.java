/**
 * 
 */
package com.oren.xml;

import java.util.Vector;

/**
 * @author giil
 *
 */
public class Element {
	Vector attrs = null;
	Namespace ns = null;
	String elemName = null;
	String value = null;
	Vector eles = new Vector();
	Element parentElement = null;
	
	public Element(String name) {
		elemName = name;
		attrs = new Vector();
	}
	public Element(String name, Namespace ns) {
		elemName = name;
		this.ns = ns;
		attrs = new Vector();
		if(this.ns.gNS().equals("")) 
			sAttr("xmlns", ns.gNSUri());
		else 
			sAttr(ns.gNS(), ns.gNSUri());
	}
	public Namespace gNS(){
		return ns;
	}
	public void sNS(Namespace ns) {
		this.ns = ns;
		if(ns.gNS().equals("")) 
			sAttr("xmlns", ns.gNSUri());
		else 
			sAttr(ns.gNS(), ns.gNSUri());
	}
	public void aNSDeclaration(Namespace ns) {
		sAttr(ns.gNS(), ns.gNSUri());
	}
	public void sParentElem(Element el) {
		parentElement = el;
	}
	public Element gParentElem() {
		return parentElement;
	}
	public void rChild(int idx) {
		eles.removeElementAt(idx);
	}
	public void rChild(Element el) {
		eles.removeElement(el);
	}
	public void rChild(String name) {
		int cnt = eles.size();
		for(int i = 0; i<cnt;i++) {
			Element ne = (Element) eles.elementAt(i);
			if(ne.gName().equals(name)) {
				eles.removeElement(ne);
				return;
			}
		}
	}
	public void rAll() {
		eles.removeAllElements();
	}
	public void rChild(String name, Namespace ns) {
		int cnt = eles.size();
		if(ns != null && !ns.gNS().equals("")) name = ns.gNS()+":"+ name;
		for(int i = 0; i<cnt;i++) {
			Element ne = (Element) eles.elementAt(i);
			if(ne.gName().equals(name)) {
				eles.removeElement(ne);
				return;
			}
		}
	}
	public void rChildren(String name) {
		int cnt = eles.size();
		for(int i = cnt-1;i >= 0;i--) {
			Element ne = (Element) eles.elementAt(i);
			if(ne.gName().equals(name)) {
				eles.removeElement(ne);
			}
		}
	}
	public void rChildren(String name, Namespace ns) {
		int cnt = eles.size();
		if(ns != null && !ns.gNS().equals("")) name = ns.gNS()+":"+ name;
		for(int i = cnt-1;i >= 0;i--) {
			Element ne = (Element) eles.elementAt(i);
			if(ne.gName().equals(name)) {
				eles.removeElement(ne);
			}
		}
	}
	public String gName() {
		return elemName;
	}
	public String gText() {
		return value;
	}
	public Document gOwnerDoc() {
		Element newele = null;
		while(true) {
			Element ele = parentElement;
			if(ele == null) break;
			newele = ele;
			ele = ele.gParentElem();
		}
		if(newele != null)
			return new Document().build(newele);
		return null;
	}
	public Document gDoc() {
		return new Document().build(this);
	}
	public ElemList gChildren() {
		return new ElemList(eles);
	}
	public ElemList gChildren(String name) {
		Vector newEles = new Vector();
		int cnt = eles.size();
		for(int i = 0;i<cnt;i++) {
			Element newele = (Element)eles.elementAt(i);
			if(newele.gName().equals(name)
					|| newele.gName().lastIndexOf(":"+name) > -1) {
				newEles.addElement(newele);
			}
		}
		return new ElemList(newEles);
	}
	public ElemList gChildren(String name, Namespace ns) {
		if(ns != null && !ns.gNS().equals("")) name = ns.gNS()+":"+ name;
		Vector newEles = new Vector();
		int cnt = eles.size();
		for(int i = 0;i<cnt;i++) {
			Element newele = (Element)eles.elementAt(i);
			if(newele.gName().equals(name))
				newEles.addElement(newele);
		}
		return new ElemList(newEles);
	}
	public Element gChild(String name) {
		int cnt = eles.size();
		for(int i = 0;i<cnt;i++) {
			Element newele = (Element)eles.elementAt(i);
			if(newele.gName().equals(name)
					|| newele.gName().lastIndexOf(":"+name) > -1)
				return newele;
		}
		return null;
	}
	public Element gChild(String name, Namespace ns) {
		if(ns != null && !ns.gNS().equals("")) name = ns.gNS()+":"+ name;
		int cnt = eles.size();
		for(int i = 0;i<cnt;i++) {
			Element newele = (Element)eles.elementAt(i);
			if(newele.gName().equals(name))
				return newele;
		}
		return null;
	}
	public String gChildText(String name) {
		return gChild(name)==null?null:gChild(name).gText();
	}
	public String gChildText(String name, Namespace ns) {
		return gChild(name, ns)==null?null:gChild(name, ns).gText();
	}
	public Element aContent(Element el) {
		el.sParentElem(this);
		eles.addElement(el);
		return this; //new Element((org.w3c.dom.Element)eltmp);
	}
	public Element aChild(String name) {
		Element newel = new Element(name);
		eles.addElement(newel);
		return newel;
	}
	public Element aChild(String name, Namespace ns) {
		if(ns != null && !ns.gNS().equals("")) name = ns.gNS()+":"+ name;
		Element newel = new Element(name);
		eles.addElement(newel);
		return newel;
	}

	public Element sText(String txt) {
		value = txt;
		return this;
	}

	public Element sAttr(String name, String txt) {
		int cnt = attrs.size();
		for(int i = 0;i<cnt;i++) {
			Attribute attr = (Attribute)attrs.elementAt(i);
			if(attr.gName().equals(name)) {
				attr.sValue(txt);
				return this;
			}
		}
		attrs.addElement(new Attribute(name).sValue(txt));
		return this;
	}
	public Element sAttr(String name, String txt, Namespace ns) {
		if(ns != null && !ns.gNS().equals("")) name = ns.gNS()+":"+ name;
		int cnt = attrs.size();
		for(int i = 0;i<cnt;i++) {
			Attribute attr = (Attribute)attrs.elementAt(i);
			if(attr.gName().equals(name)) {
				attr.sValue(txt);
				return this;
			}
		}
		attrs.addElement(new Attribute(name).sValue(txt));
		return this;
	}

	public AttrList gAttrs() {
		return new AttrList(attrs);
	}
	public Attribute gAttr(String name) {
		int cnt = attrs.size();
		for(int i = 0;i<cnt;i++) {
			Attribute attr = (Attribute)attrs.elementAt(i);
			if(attr.gName().equals(name)) {
				return attr;
			}
		}
		return null;
	}
	public String gAttrValue(String name) {
		int cnt = attrs.size();
		for(int i = 0;i<cnt;i++) {
			Attribute attr = (Attribute)attrs.elementAt(i);
			if(attr.gName().equals(name)) {
				return attr.gValue();
			}
		}
		return null;
	}
	public String gAttrValue(String name, Namespace ns) {
		if(ns != null && !ns.gNS().equals("")) name = ns.gNS()+":"+ name;
		int cnt = attrs.size();
		for(int i = 0;i<cnt;i++) {
			Attribute attr = (Attribute)attrs.elementAt(i);
			if(attr.gName().equals(name)) {
				return attr.gValue();
			}
		}
		return null;
	}
	public void rAttr(String name) {
		int cnt = attrs.size();
		for(int i = 0;i<cnt;i++) {
			Attribute attr = (Attribute)attrs.elementAt(i);
			if(attr.gName().equals(name)) {
				attrs.removeElement(attr);
				return;
			}
		}
	}
}
