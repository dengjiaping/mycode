package com.wufangnao.DBManager;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper{
    private static final String databaseName = "wufangnao.db";
    private static final int version = 1;
    
	public DataBaseHelper(Context context) {
		super(context, databaseName, null, version);
	}

	protected void finalize()
	{
		try {
			this.close();
			super.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public void onCreate(SQLiteDatabase db) {
		
    }  

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
	}
	
	public long insert(ModuleBase moduleBase)
	{
		SQLiteDatabase db= getWritableDatabase();
		long insertReturn = db.insert(moduleBase.getTableName(), null, moduleBase.getContentValues());
		return insertReturn;
	}
	public void delete(ModuleBase moduleBase){
        SQLiteDatabase db= getWritableDatabase();
        Log.d("lrk","delete = "+moduleBase.getContentValues().toString());
        db.delete(moduleBase.getTableName(), moduleBase.getWhereClause(), moduleBase.getWhereArgs());
      
	}
	public void deleteAll(ModuleBase moduleBase){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(moduleBase.getTableName(), "1=1", null);
	}
	@SuppressWarnings("rawtypes")
	public List select(ModuleBase moduleBase){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(moduleBase.getTableName(), moduleBase.getRowArray(), moduleBase.getWhereClause(), moduleBase.getWhereArgs(), null, null, moduleBase.getOrderBy());
        List select = moduleBase.rebulid(cursor);
        return select;
	}
	@SuppressWarnings("rawtypes")
	public List selectAll(ModuleBase moduleBase){
        SQLiteDatabase db = getReadableDatabase();
        return moduleBase.rebulid(db.query(moduleBase.getTableName(), moduleBase.getRowArray(), null, null, null, null, moduleBase.getOrderBy()));
	}
	public void update(ModuleBase moduleBase){
        SQLiteDatabase db= getWritableDatabase();
        db.update(moduleBase.getTableName(), moduleBase.getContentValues(), moduleBase.getWhereClause(), moduleBase.getWhereArgs());
	}
}
