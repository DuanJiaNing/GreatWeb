package com.duan.greatweb.dao;

import java.util.List;

import com.duan.greatweb.entitly.Note;

public interface NoteDao extends Dao {

	public int addNote(Note note);

	public List<Note> queryAll();

}
