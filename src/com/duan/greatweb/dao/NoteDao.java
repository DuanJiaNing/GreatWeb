package com.duan.greatweb.dao;

import java.util.List;

import com.duan.greatweb.entitly.Note;

// TODO 注释
public interface NoteDao extends Dao {

	int addNote(Note note);

	List<Note> queryAll();

	Note query(int noteId);

	int delete(int noteId);

	List<Note> queryWithState(int noteState);

}
