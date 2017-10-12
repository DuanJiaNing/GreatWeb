package com.duan.greatweb.controller.servlet;

import com.duan.greatweb.dao.NoteDao;
import com.duan.greatweb.dao.NoteDaoImpl;
import com.duan.greatweb.dao.UserDao;
import com.duan.greatweb.dao.UserDaoImpl;
import com.duan.greatweb.entitly.Note;
import com.duan.greatweb.entitly.User;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DataObtainServlet extends DataManipulateAbstract {

    private static final int CATEGORY_ALL_NOTES = 1;
    private static final int CATEGORY_ALL_USERS = 2;

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
                default:
                    break;
            }
        }
        return data;
    }

    private String queryAllNotes() {
        NoteDao nd = new NoteDaoImpl();
        List<Note> notes = nd.queryAll();
        Gson gson = new Gson();
        return gson.toJson(notes);
    }

    private String queryAllUsers() {
        UserDao ud = new UserDaoImpl();
        List<User> users = ud.queryAll(0);
        return new Gson().toJson(users);
    }

    private String queryAllNotes(int noteState) {
        NoteDao nd = new NoteDaoImpl();
        List<Note> notes = nd.queryWithState(noteState);
        Gson gson = new Gson();
        return gson.toJson(notes);
    }

}
