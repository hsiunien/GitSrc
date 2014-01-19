package com.softa.fscl.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private int mVersion=1;
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		
		super(context, name, factory, version); 
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
 
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
