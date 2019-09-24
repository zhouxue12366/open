package com.demo.booksBorrowInfo;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 图书借阅
 * @author suwn
 *
 */
public class BooksBorrowInfo extends Model<BooksBorrowInfo> {
	public static final BooksBorrowInfo me = new BooksBorrowInfo();
	
	public Page<Record> paginateBorrowInfo(int pageNumber, int pageSize,String employeeId){
		return Db.paginate(pageNumber, pageSize, "select a.id,b.booksSerialNo,b.booksNameCN,d.publishname,a.lendDate,a.agreeReturnDate,a.realReturnDate,a.deposit,a.status",
				"from booksBorrowInfo as a left join booksInfo as b on (a.booksInfo_id=b.id) left join booksEmployee as c on (a.employee_id=c.id) left join booksPublish as d on (b.booksPublish_id=d.id) where c.employeeId = "+employeeId+"  order by a.lendDate desc");
	}
	
	//查询逾期情况
	public Page<Record> paginateOverdue(int pageNumber, int pageSize){
		return Db.paginate(pageNumber, pageSize, "select a.id,b.booksSerialNo,b.booksNameCN,c.employeeId,a.lendDate,a.agreeReturnDate,datediff(now(),a.agreeReturnDate) as overdueDays,a.status",
							"from booksBorrowInfo as a left join booksInfo as b on (a.booksInfo_id=b.id) left join booksEmployee as c on (a.employee_id=c.id)"
							+ "where a.status='0' and a.agreeReturnDate < now() order by a.lendDate desc"); 

	
	}
	
}
