/*
 * @(#)JormTest.java	2011-7-20
 *
 * Copyright (c) 2011. All Rights Reserved.
 *
 */

package com.github.javaclub.jorm.mysql.one2many;

import java.util.Collection;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.javaclub.jorm.Jorm;
import com.github.javaclub.jorm.Page;
import com.github.javaclub.jorm.Session;
import com.github.javaclub.jorm.common.DateTime;
import com.github.javaclub.jorm.common.Numbers;
import com.github.javaclub.jorm.common.Strings;
import com.github.javaclub.jorm.jdbc.collection.PersistentCollection;

/**
 * JormTest
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: JormTest.java 2011-7-20 15:12:56 Exp $
 */
public class ManyToOneTest {

	static Session session;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		session = Jorm.getSession();
	}
	
	@Test
	public void testGetOne() {
		session.clean(Category.class);
		Category category = null;
		for(int i = 0; i < 28; i++) {
			category = new Category(Strings.fixed(8));
			category.setOrder(i);
			category.setCreateTime(DateTime.randomDate("1999-01-01", "2011-01-01"));
			session.save(category);
		}
		
		session.clean(Book.class);
		Book book = null;
		for(int i = 0; i < 1000; i++) {
			book = new Book(Strings.fixed(10));
			book.setCategoryId(Numbers.random(28));
			book.setAuthor(Strings.fixed(3));
			book.setIsbn(Strings.fixed(3) + "-" + Strings.fixed(2) + "-" + Strings.fixed(6));
			book.setPubTime(DateTime.randomDate("1999-01-01", "2008-01-01"));
			book.setCreateTime(DateTime.randomDate("1999-01-01", "2008-01-01"));
			session.save(book);
			System.out.println(book.getCategory());
		}
	}
	
	@Test
	public void testGetBook() throws Exception {
//		Book book = Book.class.newInstance();
		Book book = session.read(Book.class, "1e31f573d1aa48109a9b53c22e3acd1e");
		System.out.println(book);
	}
	
	@Test
	public void testGetMany0() {
		Category category = null;
		category = session.read(Category.class, 1);;
		List<Book> list = category.getBooks();
		System.out.println("size=" + list.size());
		PersistentCollection pc = (PersistentCollection) list;
		System.out.println("totalBooks=" + pc.count());
		while(pc.hasNext()) {
			Book bk = (Book) pc.next();
			System.out.println(bk);
			System.out.println("category=" + bk.getCategory());
		}
	}
	
	@Test
	public void testGetMany1() {
		Category category = null;
		category = session.read(Category.class, 1);;
		List<Book> list = category.getBooks();
		PersistentCollection pc = (PersistentCollection) list;
		long count = pc.count();
		int pages = (int) (count % 10 > 0 ? (count/10 + 1) : count/10);
		for (int i = 1; i <= pages; i++) {
			Collection page = pc.presentLimit(10*(i - 1), 10);
			System.out.println("page-" + i + " => " + page.size());
			System.out.println("-----------------------");
		}
	}
	
	@Test
	public void testGetMany() {
		/*Category category = null;
		for(int i = 0; i < 28; i++) {
			category = session.read(Category.class, i + 1);;
			List<Book> list = category.getBooks(1, 20);
			for(int j = 0; j < list.size(); j++) {
				System.out.println((j + 1) + " -> " + (Book) list.get(j));
			}
		}*/
	}
}
