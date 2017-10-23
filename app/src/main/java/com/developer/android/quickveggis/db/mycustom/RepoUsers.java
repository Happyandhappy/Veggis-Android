package com.developer.android.quickveggis.db.mycustom;

import java.sql.SQLException;
import java.util.List;

import com.developer.android.quickveggis.model.NotificationModel;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class RepoUsers {
	
	Dao<NotificationModel, String> userDao;
	
	public RepoUsers(DatabaseHelper db)
	{
		try {
			userDao = db.getUserDao();
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
	}
	
	public int create(NotificationModel user)
	{
		try {
			return userDao.create(user);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}
	public int update(NotificationModel user)
	{
		try {
			return userDao.update(user);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}
	public int delete(NotificationModel user)
	{
		try {
			return userDao.delete(user);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}
	public NotificationModel getByUsername(String username)
	{		
		try {
			QueryBuilder<NotificationModel, String> qb = userDao.queryBuilder();
			
			qb.where().eq("username", username);
			
			PreparedQuery<NotificationModel> pq = qb.prepare();
			return userDao.queryForFirst(pq);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return null;
	}
	public List<NotificationModel> getAll()
	{		
		try {
			return userDao.queryForAll();
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return null;
	}

}
