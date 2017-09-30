package com.duan.other;

import com.duan.entitly.Note;

public class NoteTable extends DatabaseTable<Note> {

	@Override
	protected void setTable(Table table) {
		table.setName("note");
	}

	@Override
	public Note[] query(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(Note... t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Note... t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int... ids) {
		// TODO Auto-generated method stub
		return 0;
	}

}
