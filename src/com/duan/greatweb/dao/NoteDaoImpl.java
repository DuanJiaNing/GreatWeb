package com.duan.greatweb.dao;

import java.util.Arrays;
import java.util.List;

import com.duan.greatweb.dao.db.DBHelper;
import com.duan.greatweb.dao.db.DataBase;
import com.duan.greatweb.entitly.Note;

public class NoteDaoImpl implements NoteDao {

	private final DataBase dataBase;

	public NoteDaoImpl() {
		dataBase = DBHelper.getDataBase();
	}

	@Override
	public int addNote(Note note) {
		return dataBase.insert(note);
	}

	@Override
	public List<Note> queryAll() {
		return Arrays.asList(dataBase.queryAll(Note.class));
	}

}
