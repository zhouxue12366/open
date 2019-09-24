package com.demo.bookspublish;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

/**
 * 出版社
 * @author suwn
 *
 */
public class BooksPublish  extends Model<BooksPublish>{
	public static final BooksPublish me = new BooksPublish();
	
	public List<BooksPublish> findAll(){
		return me.find("select * from booksPublish");
	}
}
