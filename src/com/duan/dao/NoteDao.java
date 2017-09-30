package com.duan.dao;

import java.util.List;

import com.duan.entitly.Note;

public interface NoteDao extends Dao {

	public int addNote(Note note);

	public List<Note> queryAll();

}
