### Read Xml ###
```
// Url Read
URL url;
url = new URL("http://url/test.xml");
URLConnection uc = url.openConnection();
Document doc = new XmlParser().parse(uc.getInputStream());
Element ele = doc.gRootElem();

// File Read
Document doc = new XmlParser().parse("C:/test.xml");
Element ele = doc.gRootElem();

// String Read
String xml = "<root><sub1><sub2><sub3>test</sub3></sub2><sub1></root>";
Document doc = new XmlParser().parseString(xml);
Element ele = doc.gRootElem();

// new Xml
Document doc = new Document();
doc.sRootElem(new Element("root"));
```

### Document output ###
```
String xml = "<root><sub1><sub2><sub3>test</sub3></sub2><sub1></root>";
Document doc = new XmlParser().parseString(xml);

// InputStream
InputStram in = doc.xmlToInputStream();

// OutputStream
OutputStream out = new Out.....;
doc.xmlToOutputStream(out);

// String
doc.xmlToString();

// bytes
doc.xmlToBytes();

```