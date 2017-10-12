package com.duan.greatweb.controller.servlet;

import com.duan.greatweb.dao.NoteDao;
import com.duan.greatweb.dao.NoteDaoImpl;
import com.duan.greatweb.entitly.Note;
import com.duan.greatweb.util.Utils;
import net.sf.json.JSONObject;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by DuanJiaNing on 2017/10/11.
 */
public class NoteControlServlet extends DataManipulateAbstract {

    /**
     * 删除留言
     */
    private static final int CATEGORY_DELETE_NOTES = 1;

    /**
     * 添加留言
     */
    private static final int CATEGORY_ADD_NOTES = 2;

    /**
     * 将留言移动到回收站
     */
    private static final int CATEGORY_ADD_NOTES_TO_RECYCLE_BIN = 3;

    /**
     * 将留言从回收站中还原
     */
    private static final int CATEGORY_RESTORE_NOTES_FROM_RECYCLE_BIN = 4;

    /**
     * 留言状态：正常
     */
    private static final int NOTE_STATE_NORMAL = 0;

    /**
     * 留言状态：回收站
     */
    private static final int NOTE_STATE_RECYCLE_BIN = 1;

    @Override
    protected String handleManipulate() {
        final StringBuilder builder = new StringBuilder();
        final NoteDao noteDao = new NoteDaoImpl();

        int resultCode = 1;
        int category = getCode("category");

        if (category == -1) {
            resultCode = -1;
            builder.append("要执行的操作不存在");
        } else {

            switch (category) {
                case CATEGORY_DELETE_NOTES: {
                    int[] noteIds = parseNoteIds(request);
                    if (!Utils.isArrayEmpty(noteIds)) {
                        for (int noteId : noteIds) {

                            int res = noteDao.delete(noteId);
                            if (res != NoteDao.STATE_DELETE_SUCCESS) {
                                resultCode = -1;
                                builder.append(noteId).append(" ");
                            }
                        }
                    } else {
                        resultCode = -1;
                        builder.append("未正确传递要删除的留言id");
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
                        String title = URLDecoder.decode(jsonObject.getString("title"), "utf-8");
                        String content = URLDecoder.decode(jsonObject.getString("content"), "utf-8");
                        String date = jsonObject.getString("dateTime");
                        String uid = jsonObject.getString("userId");

                        int userId = Integer.valueOf(uid);
                        long dateTime = Long.valueOf(date);

                        Note note = new Note(title, content, dateTime, userId, 0);
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
                case CATEGORY_ADD_NOTES_TO_RECYCLE_BIN: {
                    String res = updateNoteState(noteDao, request, NOTE_STATE_RECYCLE_BIN);
                    if (res != null){
                        resultCode = -1;
                        builder.append(res);
                    }
                    break;
                }
                case CATEGORY_RESTORE_NOTES_FROM_RECYCLE_BIN:{
                    String res = updateNoteState(noteDao, request, NOTE_STATE_NORMAL);
                    if (res != null){
                        resultCode = -1;
                        builder.append(res);
                    }
                    break;
                }
                default: {
                    resultCode = -1;
                    builder.append("要执行的操作不存在");
                    break;
                }
            }
        }

        return "{\"code\": " + resultCode + ",\"result\": \"" + builder.toString() + "\"}";
    }

    private String updateNoteState(NoteDao noteDao, HttpServletRequest request, int state) {

        int[] noteIds = parseNoteIds(request);
        StringBuilder builder = null;

        if (!Utils.isArrayEmpty(noteIds)) {
            for (int noteId : noteIds) {

                int res = noteDao.updateNoteState(noteId,state);

                if (res != NoteDao.STATE_DELETE_SUCCESS) {
                    if (builder == null) {
                        builder = new StringBuilder();
                    }
                    builder.append(noteId).append(" ");
                }
            }
        } else {
            builder = new StringBuilder();
            builder.append("未正确传递要删除的留言id");
        }

        return builder == null ? null : builder.toString();

    }

    private int[] parseNoteIds(HttpServletRequest request) {

        String str = request.getParameter("noteIds");
        String[] ns = str.split("\\*");

        int[] ids = new int[ns.length];
        for (int i = 0; i < ns.length; i++) {
            try {
                ids[i] = Integer.valueOf(ns[i]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return null;
            }
        }

        return ids;
    }

}
