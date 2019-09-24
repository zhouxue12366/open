package com.demo.booksclass;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

public class BooksClassController extends Controller {
	public void index() {
		Map<BooksClass,List<BooksClass>> map = new LinkedHashMap<BooksClass,List<BooksClass>>();
		List<BooksClass> list = BooksClass.me.findParent();
		for(int i=0;i<list.size();i++){
			BooksClass booksClass = list.get(i);
			List<BooksClass> listChildren = BooksClass.me.findChildren(booksClass.getInt("id"));
			
			map.put(booksClass, listChildren);
		}
		setAttr("booksClassMap",map);
		
		render("booksClass.jsp");
	}

	public void add(){
		Map<BooksClass,List<BooksClass>> map = new LinkedHashMap<BooksClass,List<BooksClass>>();
		List<BooksClass> list = BooksClass.me.findParent();
		for(int i=0;i<list.size();i++){
			BooksClass booksClass = list.get(i);
			List<BooksClass> listChildren = BooksClass.me.findChildren(booksClass.getInt("id"));
			
			map.put(booksClass, listChildren);
		}
		
		setAttr("booksClassMap",map);
	}
	
	@Before(BooksClassValidator.class)
	public void save()  throws Exception{
		Integer parent_id = getParaToInt("booksClass.parent_id");
		//加上类目数
		if( parent_id != null )
			BooksClass.me.addSubClassNum(parent_id);
		
		getModel(BooksClass.class).save();
		redirect("/booksClass");
	}
	
	public void edit() {
		setAttr("booksClass", BooksClass.me.findById(getParaToInt()));
		Map<BooksClass,List<BooksClass>> map = new LinkedHashMap<BooksClass,List<BooksClass>>();
		List<BooksClass> list = BooksClass.me.findParent();
		for(int i=0;i<list.size();i++){
			BooksClass booksClass = list.get(i);
			List<BooksClass> listChildren = BooksClass.me.findChildren(booksClass.getInt("id"));
			
			map.put(booksClass, listChildren);
		}
		
		setAttr("booksClassMap",map);
		System.out.println(BooksClass.me.findById(getParaToInt()));
	
	}
	@Before(BooksClassValidator.class)
	public void update() {
		getModel(BooksClass.class).update();
		redirect("/booksClass");
	}

	public void delete() {
		BooksClass booksClass = BooksClass.me.findById(getParaToInt());
		Integer parent_id = booksClass.get("parent_id");
		//减去类目数
		if(parent_id != null)
			BooksClass.me.subSubClassNum(parent_id);
		BooksClass.me.deleteById(getParaToInt());
		redirect("/booksClass");
	}
	
	public void list(){
		Map<BooksClass,List<BooksClass>> map = new LinkedHashMap<BooksClass,List<BooksClass>>();
		List<BooksClass> list = BooksClass.me.findParent();
		for(int i=0;i<list.size();i++){
			BooksClass booksClass = list.get(i);
			List<BooksClass> listChildren = BooksClass.me.findChildren(booksClass.getInt("id"));
			
			map.put(booksClass, listChildren);
		}
		
		setAttr("booksClassMap",map);
		render("list.jsp");
	}
}
