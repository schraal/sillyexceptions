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
    
	$RCSfile: XMLTableRenderer.java,v $
	$Date: 2004/08/26 07:25:03 $
*/
package net.sf.statcvs.renderer.xml;

import java.util.Iterator;

import net.sf.statcvs.model.Author;
import net.sf.statcvs.model.CvsFile;
import net.sf.statcvs.model.Directory;
import net.sf.statcvs.renderer.TableCellRenderer;
import net.sf.statcvs.reportmodel.Column;
import net.sf.statcvs.reportmodel.Table;

import org.jdom.Element;

/**
 * Renders a {@link net.sf.statcvs.reportmodel.Table} to a JDOM XML element
 * 
 * @author Richard Cyganiak <rcyg@gmx.de>
 * @version $Id: XMLTableRenderer.java,v 1.1 2004/08/26 07:25:03 hippe Exp $
 */
public class XMLTableRenderer implements TableCellRenderer {
	private final String tableElementName;
	private final String rowElementName;
	private String cellRendererResult;

	/**
	 * Constructor
	 * @param tableElementName the XML element name for the table
	 * @param rowElementName the XML element name for each row of the table
	 */
	public XMLTableRenderer(String tableElementName, String rowElementName) {
		this.tableElementName = tableElementName;
		this.rowElementName = rowElementName;
	}
	
	/**
	 * Returns an XML element containing the table
	 * @param table the table
	 * @return a JDOM element
	 */
	public Element getRenderedTable(Table table) {
		Element result = new Element(tableElementName);
		result.setAttribute("summary", table.getSummary());
		for (int i = 0; i < table.getRowCount(); i++) {
			Element row = new Element(rowElementName);
			Iterator it = table.getColumnIterator();
			int colCount = 1;
			while (it.hasNext()) {
				Column col = (Column) it.next();
				col.renderHead(this);
				String attributeName = convertToXMLName(cellRendererResult);
				if (attributeName == null) {
					attributeName = "column" + colCount;
				}
				col.renderCell(i, this);
				row.setAttribute(attributeName, cellRendererResult);
				colCount++;
			}
			result.addContent(row);
		}
		return result;
	}

	/**
	 * Tries to convert a <tt>String</tt> into an XML name, for example
	 * &quot;<code>Hello world!!!</code>&quot; to
	 * &quot;<code>helloWorld</code>&quot;. If this doesn't succeed,
	 * <tt>null</tt> will be returned. For example,
	 * &quot;<code>123</code>&quot; can't be easily turned into an
	 * XML name because they must start with a letter.
	 * @param string any <tt>String</tt> 
	 * @return an XML name similar to the string, or <tt>null</tt>
	 */
	public static String convertToXMLName(String string) {
		if (string == null) {
			return null;
		}
		StringBuffer result = new StringBuffer();
		boolean previousWasDeleted = false;
		boolean previousWasLowerCase = false;
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if (result.length() == 0) {
				if (!Character.isLetter(c) && c != '_' && c != ':') {
					continue;
				}
				result.append(Character.toLowerCase(c));
				previousWasLowerCase = Character.isLowerCase(c);
				continue;
			}
			if (!Character.isLetter(c) && !Character.isDigit(c) && c != '_'
					&& c != ':' && c != '.' && c != '-') {
				previousWasDeleted = true;
				continue;
			}
			if (previousWasDeleted) {
				result.append(Character.toUpperCase(c));
			} else if (!previousWasLowerCase) {
				result.append(Character.toLowerCase(c));
			} else {
				result.append(c);
			}
			previousWasDeleted = false;
			previousWasLowerCase = Character.isLowerCase(c);
		}
		if (result.length() == 0) {
			return null;
		}
		return result.toString();
	}

	/**
	 * @see net.sf.statcvs.renderer.TableCellRenderer#renderCell(java.lang.String)
	 */
	public void renderCell(String content) {
		cellRendererResult = content;
	}

	/**
	 * @see net.sf.statcvs.renderer.TableCellRenderer#renderEmptyCell()
	 */
	public void renderEmptyCell() {
		cellRendererResult = null;
	}

	/**
	 * @see net.sf.statcvs.renderer.TableCellRenderer#renderIntegerCell(int)
	 */
	public void renderIntegerCell(int value) {
		cellRendererResult = Integer.toString(value);
	}

	/**
	 * @see net.sf.statcvs.renderer.TableCellRenderer#renderIntegerCell(int, int)
	 */
	public void renderIntegerCell(int value, int total) {
		cellRendererResult = Integer.toString(value);
	}

	/**
	 * @see net.sf.statcvs.renderer.TableCellRenderer#renderPercentageCell(double)
	 */
	public void renderPercentageCell(double ratio) {
		cellRendererResult = getPercentage(ratio);
	}

	/**
	 * @see net.sf.statcvs.renderer.TableCellRenderer#renderAuthorCell(net.sf.statcvs.model.Author)
	 */
	public void renderAuthorCell(Author author) {
		cellRendererResult = author.getName();
	}

	/**
	 * @see net.sf.statcvs.renderer.TableCellRenderer#renderDirectoryCell
	 */
	public void renderDirectoryCell(Directory directory) {
		cellRendererResult = directory.getPath();
	}

	/**
	 * @see net.sf.statcvs.renderer.TableCellRenderer#renderFileCell
	 */
	public void renderFileCell(CvsFile file, boolean withIcon) {
		cellRendererResult = file.getFilenameWithPath();
	}

	//TODO: duplicate of HTMLTableCellRenderer.getPercentage
	private String getPercentage(double ratio) {
		if (Double.isNaN(ratio)) {
			return "-";
		}
		int percentTimes10 = (int) Math.round(ratio * 1000);
		double percent = percentTimes10 / 10.0;
		return Double.toString(percent) + "%";
	}
}