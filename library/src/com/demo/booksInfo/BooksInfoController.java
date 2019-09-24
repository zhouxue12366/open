package com.demo.booksInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.demo.booksclass.BooksClass;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

public class BooksInfoController extends Controller {

	public void index(){		
		setAttr("booksInfoPage",BooksInfo.me.paginate(getParaToInt(0, 1), 20));
		render("booksInfo.jsp");
	}
	
	public void add(){
		
	}
	
	public void save(){
		Date date=new Date();
		SimpleDateFormat  format=new SimpleDateFormat("yyyy-MM-dd");
		String time=format.format(date);
		
		BooksInfo booksInfo=getModel(BooksInfo.class);
		booksInfo.set("recordDate", time);
		booksInfo.save();
		
		//更新图书本数
		BooksClass.me.addbooksNum(booksInfo.getInt("booksClass_id"));
		redirect("/booksInfo");
	}
	
	public void edit(){
		List<Record> list = BooksInfo.me.findOne(getParaToInt());
		setAttr("booksInfo",list.get(0));
	}
	
	public void update(){
		BooksInfo booksInfo = getModel(BooksInfo.class);
		BooksInfo oldBooksInfo = BooksInfo.me.findById(booksInfo.getInt("id"));
		int newBooksClass_id =booksInfo.getInt("booksClass_id");
		int oldBookClass_id =oldBooksInfo.getInt("booksClass_id");
	
		//如果所属分类改变了，则要更新图书本数
		if(newBooksClass_id!=oldBookClass_id){
			BooksClass.me.addbooksNum(newBooksClass_id); //新的分类图书本数+1
			BooksClass.me.subbooksNum(oldBookClass_id);//旧的分类图书本数-1
		}
			
		getModel(BooksInfo.class).update();
		
		redirect("/booksInfo");
	}
	
	public void delete(){
		BooksInfo booksInfo = BooksInfo.me.findById(getParaToInt());
		BooksClass.me.subbooksNum(booksInfo.getInt("booksClass_id"));//分类图书本数-1
		BooksInfo.me.deleteById(getParaToInt());
		redirect("/booksInfo");
	}
	public void open(){
		setAttr("booksInfoPage",BooksInfo.me.paginateAll(getParaToInt(0, 1), 20));
		render("list.jsp");
	}
	public void list(){
		
		String queryCondition = getPara("queryCondition");
		String conditionValue = getPara("conditionValue");
		if("1".equals(queryCondition)){  //按图书编号查找
			setAttr("booksInfoPage",BooksInfo.me.paginateBooksSerialNo(getParaToInt(0, 1), 20, conditionValue));
		}
		else if("2".equals(queryCondition)) { //按中文名称查找
			setAttr("booksInfoPage",BooksInfo.me.paginateBooksNameCN(getParaToInt(0, 1), 20, conditionValue));
		}
		else if("3".equals(queryCondition)){ //按类别查找
			setAttr("booksInfoPage",BooksInfo.me.paginateBooksClassName(getParaToInt(0, 1), 20, conditionValue));
		}
		else if("4".equals(queryCondition)){ //按出版社查找
			setAttr("booksInfoPage",BooksInfo.me.paginateBooksPublishname(getParaToInt(0, 1), 20, conditionValue));
		}
		else{}
	}
}
