package com.developer.android.quickveggis.db.mycustom;
import android.content.Context;

public class Repo {
	
	DatabaseHelper db;
	
	public RepoUsers Users;
	
	public Repo(Context context)
	{
		DatabaseManager<DatabaseHelper> manager = new DatabaseManager<DatabaseHelper>();
		db = manager.getHelper(context);

//		db = new DatabaseHelper(context);
		
		Users = new RepoUsers(db);
	
	}	
}
