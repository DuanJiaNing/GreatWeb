package com.duan.dao;

import java.util.Arrays;
import java.util.List;

import com.duan.dao.db.DBHelper;
import com.duan.dao.db.DataBase;
import com.duan.entitly.Note;

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
