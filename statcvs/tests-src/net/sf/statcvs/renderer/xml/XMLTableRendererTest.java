/*
    StatCvs - CVS statistics generation 
    Copyright (C) 2002  Lukasz Pekacki <lukasz@pekacki.de>
    http://statcvs.sf.net/
    
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
    
	$Name:  $ 
	Created on $Date: 2004/08/26 07:25:04 $ 
*/
package net.sf.statcvs.renderer.xml;

import java.util.Date;

import junit.framework.TestCase;
import net.sf.statcvs.model.Author;
import net.sf.statcvs.model.CvsFile;
import net.sf.statcvs.model.Directory;
import net.sf.statcvs.reportmodel.AuthorColumn;
import net.sf.statcvs.reportmodel.DirectoryColumn;
import net.sf.statcvs.reportmodel.FileColumn;
import net.sf.statcvs.reportmodel.IntegerColumn;
import net.sf.statcvs.reportmodel.RatioColumn;
import net.sf.statcvs.reportmodel.SimpleTextColumn;
import net.sf.statcvs.reportmodel.Table;

import org.jdom.Element;

/**
 * Test cases for {@link XMLTableRenderer}
 *
 * @author Richard Cyganiak
 * @version $Id: XMLTableRendererTest.java,v 1.1 2004/08/26 07:25:04 hippe Exp $
 */
public class XMLTableRendererTest extends TestCase {

	/**
	 * Constructor
	 * @param arg0 input 
	 */
	public XMLTableRendererTest(String arg0) {
		super(arg0);
	}
	
	/**
	 * test constructor
	 */
	public void testCreation() {
		new XMLTableRenderer("test", "row");
	}
	
	/**
	 * test a table with no rows or columns
	 */
	public void testEmptyTable() {
		Table dummyTable = new Table("summary");
		XMLTableRenderer xtr = new XMLTableRenderer("table", "row");
		Element e = xtr.getRenderedTable(dummyTable);
		assertEquals("table", e.getName());
		assertEquals("number of attributes", 1, e.getAttributes().size());
		assertEquals("summary", e.getAttributeValue("summary"));
		assertEquals(0, e.getChildren().size());
	}

	/**
	 * tests the output for a simple table
	 */
	public void testRenderDummyTable() {
		Table dummyTable = getDummyTable();
		XMLTableRenderer xtr = new XMLTableRenderer("dummy", "row");
		Element xml = xtr.getRenderedTable(dummyTable);
		assertEquals("dummy", xml.getName());
		assertEquals(1, xml.getAttributes().size());
		assertEquals("a test table", xml.getAttributeValue("summary"));
	}
	
	/**
	 * tests row values for a simple table
	 */
	public void testDummyTableRows() {
		Table dummyTable = getDummyTable();
		XMLTableRenderer xtr = new XMLTableRenderer("dummy", "row");
		Element xml = xtr.getRenderedTable(dummyTable);
		assertEquals("number of children", 2, xml.getChildren().size());
		Element row1 = (Element) xml.getChildren().get(0);
		assertEquals("row", row1.getName());
		assertEquals("number of attributes", 2, row1.getAttributes().size());
		assertEquals("A1", row1.getAttributeValue("column1"));
		assertEquals("B1", row1.getAttributeValue("column2"));
		Element row2 = (Element) xml.getChildren().get(1);
		assertEquals("row", row2.getName());
		assertEquals("number of attributes", 2, row2.getAttributes().size());
		assertEquals("A2", row2.getAttributeValue("column1"));
		assertEquals("B2", row2.getAttributeValue("column2"));
	}

	/**
	 * test various column types
	 */
	public void testColumnTypes() {
		Directory root = Directory.createRoot();
		CvsFile file = new CvsFile("file", root);
		file.addInitialRevision("1.1", null, new Date(10000000), null, 0);
		SimpleTextColumn textCol = new SimpleTextColumn("Text");
		AuthorColumn authorCol = new AuthorColumn();
		DirectoryColumn dirCol = new DirectoryColumn();
		FileColumn fileCol = new FileColumn();
		IntegerColumn intCol1 = new IntegerColumn("Integer");
		IntegerColumn intCol2 = new IntegerColumn("Integer");
		RatioColumn ratioCol = new RatioColumn("Ratio", intCol1, intCol2);
		textCol.addValue("Hello World!!!");
		authorCol.addValue(new Author("Richard"));
		dirCol.addValue(root.createSubdirectory("directory"));
		fileCol.addValue(file);
		intCol1.addValue(2);
		intCol2.addValue(3);
		Table t = new Table("test table");
		t.addColumn(textCol);
		t.addColumn(authorCol);
		t.addColumn(dirCol);
		t.addColumn(fileCol);
		t.addColumn(intCol1);
		t.addColumn(ratioCol);
		XMLTableRenderer xtr = new XMLTableRenderer("coltypetest", "set");
		Element e = (Element) xtr.getRenderedTable(t).getChildren().get(0);
		assertEquals("set", e.getName());
		assertEquals(6, e.getAttributes().size());
		assertEquals("Hello World!!!", e.getAttributeValue("text"));
		assertEquals("Richard", e.getAttributeValue("author"));
		assertEquals("directory/", e.getAttributeValue("directory"));
		assertEquals("file", e.getAttributeValue("file"));
		assertEquals("2", e.getAttributeValue("integer"));
		assertEquals("0.6", e.getAttributeValue("ratio"));
	}

	/**
	 * test {@link XMLTableRenderer#convertToXMLName(String)}
	 */
	public void testConvertToXMLName() {
		assertNull(XMLTableRenderer.convertToXMLName(null));
		assertNull(XMLTableRenderer.convertToXMLName(""));
		assertNull(XMLTableRenderer.convertToXMLName("123"));
		assertNull(XMLTableRenderer.convertToXMLName("!$%&/()=?"));
		assertEquals("a456", XMLTableRenderer.convertToXMLName("123a456"));
		assertEquals("aBC", XMLTableRenderer.convertToXMLName("a b c"));
		assertEquals("url", XMLTableRenderer.convertToXMLName("URL"));
		assertEquals("helloWorld", XMLTableRenderer.convertToXMLName("Hello World!!!"));
		assertEquals("aTest", XMLTableRenderer.convertToXMLName("aTest"));
		assertNull(XMLTableRenderer.convertToXMLName(null));
	}

	private Table getDummyTable() {
		Table result = new Table("a test table");
		SimpleTextColumn col1 = new SimpleTextColumn("Column 1");
		SimpleTextColumn col2 = new SimpleTextColumn("Column 2"); 
		result.addColumn(col1);
		result.addColumn(col2);
		col1.addValue("A1");
		col1.addValue("A2");
		col2.addValue("B1");
		col2.addValue("B2");
		return result;
	}
}