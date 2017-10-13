package com.duan.greatweb.controller.servlet;

import com.duan.greatweb.dao.NoteDao;
import com.duan.greatweb.dao.NoteDaoImpl;
import com.duan.greatweb.dao.UserDao;
import com.duan.greatweb.dao.UserDaoImpl;
import com.duan.greatweb.entitly.Note;
import com.duan.greatweb.entitly.User;
import com.google.gson.Gson;

import java.util.List;

/**
 * 数据获取
 */
public class DataObtainServlet extends DataManipulateAbstract {

    /**
     * 获取所有的笔记
     */
    private static final int CATEGORY_ALL_NOTES = 1;

    /**
     * 获取所有的用户
     */
    private static final int CATEGORY_ALL_USERS = 2;

    /**
     * 获取指定笔记
     */
    private static final int CATEGORY_PARTICULAR_NOTE = 3;

    /**
     * 获取指定用户
     */
    private static final int CATEGORY_PARTICULAR_USER = 4;

    private final NoteDao noteDao = new NoteDaoImpl();
    private final UserDao userDao = new UserDaoImpl();
    private final Gson gson = new Gson();

    @Override
    protected String handleManipulate() {
        String data = null;

        int category = getCode("category");

        if (category != -1) {
            switch (category) {
                case CATEGORY_ALL_NOTES: {
                    int noteState = getCode("noteState");

                    if (noteState == -1) { // 所有留言
                        data = queryAllNotes();
                    } else { // 指定状态的留言
                        data = queryAllNotes(noteState);
                    }
                    break;
                }
                case CATEGORY_ALL_USERS: {
                    data = queryAllUsers();
                    break;
                }
                case CATEGORY_PARTICULAR_NOTE: {
                    int noteId = getCode("noteId");
                    if (noteId != -1) {
                        data = queryNote(noteId);
                    }
                    break;
                }
                case CATEGORY_PARTICULAR_USER: {
                    int userId = getCode("userId");
                    if (userId != -1) {
                        data = queryUser(userId);
                    }
                    break;
                }
                default:
                    break;
            }
        }
        return data;
    }

    private String queryUser(int userId) {
        User user = userDao.query(userId);
        return gson.toJson(user);
    }

    private String queryNote(int noteId) {
        Note note = noteDao.query(noteId);
        return gson.toJson(note);
    }

    private String queryAllNotes() {
        List<Note> notes = noteDao.queryAll();
        return gson.toJson(notes);
    }

    private String queryAllUsers() {
        List<User> users = userDao.queryAll(0);
        return gson.toJson(users);
    }

    private String queryAllNotes(int noteState) {
        List<Note> notes = noteDao.queryWithState(noteState);
        return gson.toJson(notes);
    }

}
