package com.demo.booksUser;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

/**
 * User
 */
@SuppressWarnings("serial")
public class BooksUser extends Model<BooksUser> {
	public static final BooksUser me = new BooksUser();
	
	public List<BooksUser> findByUsername(String username){
		return me.find("select * from booksUser where username = ?",username);
	}
	public List<BooksUser> findByUsernameAndPassword(String username,String password){
		return me.find("select * from booksUser where username = ? and password =?",username,password);
	}
}
