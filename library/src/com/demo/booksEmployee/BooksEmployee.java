package com.demo.booksEmployee;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

/**
 * 员工表
 * @author suwn
 *
 */
public class BooksEmployee extends Model<BooksEmployee>{
	public static final BooksEmployee me = new BooksEmployee();
	
	public BooksEmployee findOne(String employeeId){
		return me.findFirst("select * from booksEmployee where employeeId = "+employeeId);
	}
	
}
