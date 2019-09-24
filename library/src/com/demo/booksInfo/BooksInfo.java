package com.demo.booksInfo;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 图书信息
 * @author suwn
 *
 */
public class BooksInfo extends Model<BooksInfo>{
	public static final BooksInfo me = new BooksInfo();
	
	public Page<Record> paginate(int pageNumber, int pageSize){
		
		return Db.paginate(pageNumber, pageSize,"select  booksInfo.id,booksInfo.booksSerialNo,booksInfo.volumeNumber,booksClass.className as booksClass_name,booksInfo.booksNameCN,booksPublish.publishname as booksPublish_name,booksInfo.publishDate,booksInfo.recordDate,booksInfo.borrowTimes,booksInfo.status "
				,"from booksInfo ,booksClass ,booksPublish where booksInfo.booksClass_id = booksClass.id and booksInfo.booksPublish_id = booksPublish.id");
					
	}
	
	public List<Record> findOne(int id){
		
		return Db.find("select booksInfo.id,booksInfo.booksSerialNo,booksInfo.volumeNumber,booksClass.id as booksClass_id,booksClass.className as booksClass_name,booksInfo.booksNameCN,booksInfo.booksNameReal,booksPublish.id as booksPublish_id,booksPublish.publishname as booksPublish_name,booksInfo.author,booksInfo.translator,booksInfo.stock,booksInfo.publishDate,booksInfo.recordDate,booksInfo.lastDate,booksInfo.price,booksInfo.pageNum,booksInfo.borrowTimes,booksInfo.borrowDays,booksInfo.userid,booksInfo.booksdesc,booksInfo.catalog "
				+ "from booksInfo ,booksClass ,booksPublish "
				+ "where booksInfo.booksClass_id = booksClass.id and booksInfo.booksPublish_id = booksPublish.id and  booksInfo.id=?",id);
	
	}
	//按图书编号查找
	public Page<Record> paginateAll(int pageNumber, int pageSize){
		
		return Db.paginate(pageNumber, pageSize,"select  booksInfo.id,booksInfo.booksSerialNo,booksClass.className as booksClass_name,booksInfo.booksNameCN,booksPublish.publishname as booksPublish_name,booksInfo.author,booksInfo.status "
				,"from booksInfo ,booksClass ,booksPublish where booksInfo.booksClass_id = booksClass.id and booksInfo.booksPublish_id = booksPublish.id ");
					
	}
	//按图书编号查找
	public Page<Record> paginateBooksSerialNo(int pageNumber, int pageSize,String booksSerialNo){
		
		return Db.paginate(pageNumber, pageSize,"select  booksInfo.id,booksInfo.booksSerialNo,booksClass.className as booksClass_name,booksInfo.booksNameCN,booksPublish.publishname as booksPublish_name,booksInfo.author,booksInfo.status "
				,"from booksInfo ,booksClass ,booksPublish where booksInfo.booksClass_id = booksClass.id and booksInfo.booksPublish_id = booksPublish.id and booksInfo.booksSerialNo like '%"+booksSerialNo+"%'");
					
	}
	//按中文名称查找
	public Page<Record> paginateBooksNameCN(int pageNumber, int pageSize,String booksNameCN){
			
		return Db.paginate(pageNumber, pageSize,"select  booksInfo.id,booksInfo.booksSerialNo,booksClass.className as booksClass_name,booksInfo.booksNameCN,booksPublish.publishname as booksPublish_name,booksInfo.author,booksInfo.status "
					,"from booksInfo ,booksClass ,booksPublish where booksInfo.booksClass_id = booksClass.id and booksInfo.booksPublish_id = booksPublish.id and booksInfo.booksNameCN like '%"+booksNameCN+"%'");
						
	}
	//按类别查找
	public Page<Record> paginateBooksClassName(int pageNumber, int pageSize,String booksClassName){
			
		return Db.paginate(pageNumber, pageSize,"select  booksInfo.id,booksInfo.booksSerialNo,booksClass.className as booksClass_name,booksInfo.booksNameCN,booksPublish.publishname as booksPublish_name,booksInfo.author,booksInfo.status "
					,"from booksInfo ,booksClass ,booksPublish where booksInfo.booksClass_id = booksClass.id and booksInfo.booksPublish_id = booksPublish.id and booksClass.className like '%"+booksClassName+"%'");
						
	}
	//按出版社查找
	public Page<Record> paginateBooksPublishname(int pageNumber, int pageSize,String booksPublishname){
		
		return Db.paginate(pageNumber, pageSize,"select  booksInfo.id,booksInfo.booksSerialNo,booksClass.className as booksClass_name,booksInfo.booksNameCN,booksPublish.publishname as booksPublish_name,booksInfo.author,booksInfo.status "
					,"from booksInfo ,booksClass ,booksPublish where booksInfo.booksClass_id = booksClass.id and booksInfo.booksPublish_id = booksPublish.id and booksPublish.publishname like '%"+booksPublishname+"%'");
						
	}
	//更新图书借出时的信息
	public void updateBorrow(String lastDate,int id){
		Db.update("update booksInfo set lastDate= ?,borrowTimes=borrowTimes+1,status=1 where id=?",lastDate,id);
	}
}
