package com.duan.greatweb.controller.servlet;

import com.duan.greatweb.dao.NoteDao;
import com.duan.greatweb.dao.NoteDaoImpl;
import com.duan.greatweb.entitly.Note;
import com.duan.greatweb.util.Utils;
import net.sf.json.JSONObject;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

/**
 * Created by DuanJiaNing on 2017/10/11.
 */
public class NoteControlServlet extends DataManipulateAbstract {

    private static final int CATEGORY_DELETE_NOTES = 1;
    private static final int CATEGORY_ADD_NOTES = 2;

    @Override
    protected String handleManipulate(int category, HttpServletRequest request, HttpServletResponse response) {
        final StringBuilder builder = new StringBuilder();
        final NoteDao noteDao = new NoteDaoImpl();

        int resultCode = 1;
        switch (category) {
            case CATEGORY_DELETE_NOTES: {
                String str = request.getParameter("noteIds");
                String[] ns = str.split("\\*");

                Object[] objs = Stream.of(ns).map(Utils::parseStringToInt).toArray();

                for (Object d : objs) {
                    int noteId = (int) d;
                    int res = noteDao.delete(noteId);

                    if (res != NoteDao.STATE_DELETE_SUCCESS) {
                        resultCode = -1;
                        builder.append(noteId).append(" ");
                    }
                }
                break;
            }
            case CATEGORY_ADD_NOTES: {
                try {

                    ServletInputStream stream = request.getInputStream();
                    String str = Utils.readStringFromInputStream(stream);
                    if (!Utils.isReal(str)) {
                        resultCode = -1;
                        builder.append("json 参数错误");
                        break;
                    }

                    JSONObject jsonObject = JSONObject.fromObject(str);
                    String title = URLDecoder.decode(jsonObject.getString("title"),"utf-8");
                    String content = URLDecoder.decode(jsonObject.getString("content"),"utf-8");
                    String date = jsonObject.getString("dateTime");
                    String uid = jsonObject.getString("userId");

                    int userId = Integer.valueOf(uid);
                    long dateTime = Long.valueOf(date);

                    Note note = new Note(title, content, dateTime, userId);
                    int res = noteDao.addNote(note);
                    if (res != NoteDao.STATE_DELETE_SUCCESS) {
                        resultCode = -1;
                        builder.append(title);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    resultCode = -1;
                    builder.append(e.getMessage());
                }

                break;
            }
            default:
                break;
        }

        return "{\"code\": " + resultCode + ",\"result\": \"" + builder.toString() + "\"}";
    }

}
