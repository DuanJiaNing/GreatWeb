package com.duan.greatweb.controller.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.duan.greatweb.dao.NoteDao;
import com.duan.greatweb.dao.NoteDaoImpl;
import com.duan.greatweb.dao.UserDao;
import com.duan.greatweb.dao.UserDaoImpl;
import com.duan.greatweb.entitly.Note;
import com.duan.greatweb.entitly.User;
import com.duan.greatweb.util.Utils;
import com.google.gson.Gson;

public class NotesObtainServlet extends HttpServlet {

    private static final int CATEGORY_ALL_NOTES = 1;
    private static final int CATEGORY_ALL_USERS = 2;

    private OutputStream outStream;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        String str = request.getParameter("category");
        outStream = response.getOutputStream();

        if (!Utils.isReal(str)) {
            LogErrorRequest("需要在请求中指明请求笔记类别,category=?");
            return;
        }

        String errorInfo = "没有获取到数据";
        boolean error = false;
        String jsonString;

        int cate = Integer.valueOf(str);
        switch (cate) {
            case CATEGORY_ALL_NOTES:
                jsonString = queryAllNotes();
                break;
            case CATEGORY_ALL_USERS:
                jsonString = queryAllUsers();
                break;
            default:
                error = true;
                jsonString = "请求的数据类型不存在,category=?";
                break;
        }

//        Utils.log(jsonString);
        if (error) {
            LogErrorRequest(errorInfo);
        } else {
            out(jsonString);
        }

    }

    private String queryAllUsers() {
        UserDao ud = new UserDaoImpl();
        List<User> users = ud.queryAll(0);
        return new Gson().toJson(users);
    }

    private String queryAllNotes() {
        NoteDao nd = new NoteDaoImpl();
        List<Note> notes = nd.queryAll();
        Gson gson = new Gson();
        return gson.toJson(notes);
    }

    private void LogErrorRequest(String msg) {
        Utils.log(msg);
        close();
    }

    private void close() {
        if (outStream != null) {
            try {
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void out(String str) {
        if (outStream == null || !Utils.isReal(str)) {
            return;
        }

        try {
            // 中文乱码 ***** spend 2 hour
            outStream.write(str.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
