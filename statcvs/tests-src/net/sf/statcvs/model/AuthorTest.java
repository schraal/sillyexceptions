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
package net.sf.statcvs.model;

import java.util.Date;
import java.util.Iterator;

import junit.framework.TestCase;

/**
 * Tests for {@link Author} 
 * @author Richard Cyganiak
 * @version $Id: AuthorTest.java,v 1.1 2004/08/26 07:25:04 hippe Exp $
 */
public class AuthorTest extends TestCase {

	/**
	 * Constructor
	 * @param arg0 input
	 */
	public AuthorTest(String arg0) {
		super(arg0);
	}

	public void testCreation() {
		Author author = new Author("author1");
		assertEquals("author1", author.getName());
		assertTrue(author.getDirectories().isEmpty());
		assertTrue(author.getRevisions().isEmpty());
	}
	
	public void testCompare() {
		Author author1 = new Author("author1");
		Author author2 = new Author("author2");
		assertEquals(-1, author1.compareTo(author2));
		assertEquals(1, author2.compareTo(author1));
		assertEquals(0, author2.compareTo(author2));
	}
	
	public void testDirectories() {
		Author author1 = new Author("author1");
		Author author2 = new Author("author2");
		Date date = new Date(100000000);
		Directory root = Directory.createRoot();
		Directory dir1 = root.createSubdirectory("dir1");
		Directory dir2 = root.createSubdirectory("dir2");
		Directory dir3 = root.createSubdirectory("dir3");
		Directory dir1subdir = dir1.createSubdirectory("subdir");
		CvsFile file1 = new CvsFile("dir1/file", dir1);
		CvsFile file2 = new CvsFile("dir2/file", dir2);
		CvsFile file3 = new CvsFile("dir3/file", dir3);
		CvsFile file4 = new CvsFile("dir1/subdir/file", dir1subdir);
		new CvsRevision(file1, "1.1", CvsRevision.TYPE_CREATION, author2, date, null, 0, 0, 0);
		new CvsRevision(file2, "1.2", CvsRevision.TYPE_CHANGE, author1, date, null, 0, 0, 0);
		new CvsRevision(file2, "1.1", CvsRevision.TYPE_CREATION, author1, date, null, 0, 0, 0);
		new CvsRevision(file3, "1.1", CvsRevision.TYPE_CREATION, author2, date, null, 0, 0, 0);
		new CvsRevision(file4, "1.1", CvsRevision.TYPE_CREATION, author1, date, null, 0, 0, 0);
		assertTrue(author1.getDirectories().contains(dir2));
		assertTrue(author1.getDirectories().contains(dir1subdir));
		assertEquals(2, author1.getDirectories().size());
		assertTrue(author2.getDirectories().contains(dir1));
		assertTrue(author2.getDirectories().contains(dir3));
		assertEquals(2, author2.getDirectories().size());
	}
	
	public void testRevisions() {
		Author author = new Author("author1");
		Directory root = Directory.createRoot();
		Date date1 = new Date(100000000);
		Date date2 = new Date(200000000);
		Date date3 = new Date(300000000);
		CvsFile file1 = new CvsFile("file1", root);
		CvsFile file2 = new CvsFile("file2", root);
		CvsRevision rev13 = new CvsRevision(file1, "1.3", CvsRevision.TYPE_CHANGE, author, date3, null, 0, 0, 0);
		CvsRevision rev12 = new CvsRevision(file1, "1.2", CvsRevision.TYPE_CHANGE, author, date2, null, 0, 0, 0);
		CvsRevision rev11 = new CvsRevision(file1, "1.1", CvsRevision.TYPE_CREATION, author, date1, null, 0, 0, 0);
		CvsRevision rev21 = new CvsRevision(file2, "1.1", CvsRevision.TYPE_CREATION, author, date2, null, 0, 0, 0);
		Iterator it = author.getRevisions().iterator();
		assertTrue(it.hasNext());
		assertSame(rev11, it.next());
		assertTrue(it.hasNext());
		CvsRevision r1 = (CvsRevision) it.next();
		assertTrue(r1 == rev12 || r1 == rev21);
		assertTrue(it.hasNext());
		CvsRevision r2 = (CvsRevision) it.next();
		assertTrue(r2 == rev12 || r2 == rev21);
		assertTrue(r1 != r2);
		assertTrue(it.hasNext());
		assertSame(rev13, it.next());
		assertTrue(!it.hasNext());		
	}
}
