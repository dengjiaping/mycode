package com.wufangnao.DBManager;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

public interface ModuleBase {
	public String getTableName();
	public String[] getRowArray();
	public List<Object> rebulid(Cursor cursor);
	public ContentValues getContentValues();
	public String getWhereClause();
	public String[] getWhereArgs();
	public String getOrderBy();
}
