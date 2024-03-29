/**
 * 
 */
package com.oren.xml;

/**
 * @author orennamu
 *
 */

public class Namespace {
	String NamespaceURI = "";
	String names = ""; 
	public Namespace(String names) {
		this.names = names;
	}
	public Namespace(String names, String namesUri) {
		NamespaceURI = namesUri;
		this.names = names;
	}
	public static Namespace gNS(String names, String namesUri) {
		return new Namespace(names, namesUri);
	}
	public static Namespace gNS(String namesUri) {
		return new Namespace(namesUri);
	}
	public String gNSUri() {
		return NamespaceURI;
	}
	public String gNS() {
		return names;
	}
}
