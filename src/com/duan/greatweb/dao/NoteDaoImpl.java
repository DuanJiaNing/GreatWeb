package com.duan.greatweb.dao;

import java.util.Arrays;
import java.util.List;

import com.duan.greatweb.dao.db.DBHelper;
import com.duan.greatweb.dao.db.DataBase;
import com.duan.greatweb.entitly.Note;

public class NoteDaoImpl implements NoteDao {

    private final DataBase dataBase;
    private static final Class<Note> sCLASS = Note.class;

    public NoteDaoImpl() {
        dataBase = DBHelper.getDataBase();
    }

    @Override
    public int addNote(Note note) {
        return dataBase.insert(note);
    }

    @Override
    public List<Note> queryAll() {
        Note[] notes = dataBase.queryAll(sCLASS);
        return notes == null || notes.length == 0 ? null : Arrays.asList(notes);
    }

    @Override
    public Note query(int noteId) {
        Note[] notes = dataBase.query(sCLASS, new String[]{"id"}, new String[]{noteId + ""});
        return notes != null && notes.length > 0 ? notes[0] : null;
    }

    @Override
    public int delete(int noteId) {
        return dataBase.delete(sCLASS, noteId);
    }

    @Override
    public List<Note> queryWithState(int noteState) {
        Note[] notes = dataBase.query(sCLASS, new String[]{"state"}, new String[]{noteState + ""});
        return notes == null || notes.length == 0 ? null : Arrays.asList(notes);
    }

    @Override
    public int updateNoteState(int noteId, int state) {
        Note note = new Note();
        note.setId(noteId);
        note.setState(state);

        return dataBase.update(note);
    }

    @Override
    public int updateNote(int noteId, Note note) {
        if (note == null) {
            return NoteDao.STATE_UPDATE_FAIL;
        }

        note.setId(noteId);
        return dataBase.update(note);
    }

}
