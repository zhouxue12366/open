package com.demo.booksclass;

import java.util.List;

import com.demo.blog.Blog;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 图书分类
 * @author suwn
 *
 *+------------+--------------+------+-----+---------+----------------+
| Field      | Type         | Null | Key | Default | Extra          |
+------------+--------------+------+-----+---------+----------------+
| id         | int(11)      | NO   | PRI | NULL    | auto_increment |
| className  | varchar(100) | YES  |     | NULL    |                |
| classNum   | varchar(100) | YES  |     | NULL    |                |
| parent_id  | int(11)      | YES  |     | NULL    |                |
| borrowDays | varchar(20)  | YES  |     | NULL    |                |
| booksNum   | varchar(5)   | YES  |     | NULL    |                |
| subClassNum| int(5)       | YES  |     | 0       |                |
+------------+--------------+------+-----+---------+----------------+·
 */

public class BooksClass extends Model<BooksClass>{
	public static final BooksClass me = new BooksClass();
	
	public List<BooksClass> findParent(){
		return me.find("select * from booksClass where parent_id is Null  order by classNum ASC");
	}
	//查询二级类目
	public List<BooksClass> findChildren(int id){
		return me.find("select * from booksClass where parent_id = ? order by classNum ASC",id);
	}
	//更新子类目数
	public void addSubClassNum(int parent_id){
		Db.update("update booksClass set subClassNum = subClassNum +1 where id=?",parent_id);
	}
	public void subSubClassNum(int parent_id){
		Db.update("update booksClass set subClassNum = subClassNum -1 where id=?",parent_id);
	}
	//更新图书本数
	public void subbooksNum(int id){
		Db.update("update booksClass set booksNum = booksNum -1 where id=?",id);
	}
	public void addbooksNum(int id){
		Db.update("update booksClass set booksNum = booksNum +1 where id=?",id);
	}
}
