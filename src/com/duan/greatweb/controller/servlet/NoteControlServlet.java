package com.duan.greatweb.controller.servlet;

import com.duan.greatweb.dao.NoteDao;
import com.duan.greatweb.dao.NoteDaoImpl;
import com.duan.greatweb.entitly.Note;
import com.duan.greatweb.util.Utils;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by DuanJiaNing on 2017/10/11.
 * 数据增、删、改
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
     * 修改留言
     */
    private static final int CATEGORY_MODIFY_NOTES = 5;

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

    /**
     * 操作结果，为 1 时表示操作执行成功
     */
    private int resultCode = 1;
    private final StringBuilder builder = new StringBuilder();

    @Override
    protected String handleManipulate() {
        final NoteDao noteDao = new NoteDaoImpl();

        int category = getCode("category");

        if (category == -1) {
            error(-1, "要执行的操作不存在");
        } else {
            resultCode = 1;
            switch (category) {
                case CATEGORY_DELETE_NOTES: {
                    int[] noteIds = parseNoteIds(request);
                    if (!Utils.isArrayEmpty(noteIds)) {
                        for (int noteId : noteIds) {

                            int res = noteDao.delete(noteId);
                            if (res != NoteDao.STATE_DELETE_SUCCESS) {
                                error(-1, noteId + " ");
                            }
                        }
                    } else {
                        error(-1, "未正确传递要删除的留言id");
                    }

                    break;
                }

                case CATEGORY_ADD_NOTES: {

                    Note note = getNoteFromJsonStream(request);
                    if (note == null) {
                        error(-1, "json 参数错误");
                        break;
                    }

                    int res = noteDao.addNote(note);
                    if (res != NoteDao.STATE_DELETE_SUCCESS) {
                        error(-1, note.getTitle());
                    }

                    break;
                }
                case CATEGORY_ADD_NOTES_TO_RECYCLE_BIN: {
                    String res = updateNoteState(noteDao, request, NOTE_STATE_RECYCLE_BIN);
                    if (res != null) {
                        error(-1, res);
                    }
                    break;
                }
                case CATEGORY_RESTORE_NOTES_FROM_RECYCLE_BIN: {
                    String res = updateNoteState(noteDao, request, NOTE_STATE_NORMAL);
                    if (res != null) {
                        error(-1, res);
                    }
                    break;
                }
                case CATEGORY_MODIFY_NOTES: {
                    int noteId = getCode("noteId");
                    if (noteId != -1) {

                        Note note = getNoteFromJsonStream(request);
                        if (note == null) {
                            error(-1, "json 参数错误");
                            break;
                        }

                        if (noteDao.updateNote(noteId, note) != NoteDao.STATE_UPDATE_SUCCESS) {
                            error(-1, "更新失败");
                        }

                    } else {
                        error(-1, "未传递要修改留言的 id");
                    }
                    break;
                }
                default: {
                    error(-1, "要执行的操作不存在");
                    break;
                }
            }
        }

        return "{\"code\": " + resultCode + ",\"result\": \"" + builder.toString() + "\"}";
    }

    private Note getNoteFromJsonStream(ServletRequest request) {

        ServletInputStream stream = null;

        try {
            stream = request.getInputStream();

            String str = Utils.readStringFromInputStream(stream);
            if (!Utils.isReal(str)) {
                return null;
            }

            JSONObject jsonObject = JSONObject.fromObject(str);
            Note note = new Note();

            try {
                String id = jsonObject.getString("id");
                note.setId(Integer.valueOf(id));
            } catch (JSONException e) {
                note.setId(null);
            }

            try {
                String title = jsonObject.getString("title");
                note.setTitle(URLDecoder.decode(title,"utf-8"));
            } catch (JSONException e) {
                note.setTitle(null);
            }

            try {
                String content = jsonObject.getString("content");
                note.setContent(URLDecoder.decode(content,"utf-8"));
            } catch (JSONException e) {
                note.setContent(null);
            }

            try {
                String date = jsonObject.getString("dateTime");
                note.setDateTime(Long.valueOf(date));
            } catch (JSONException e) {
                note.setDateTime(null);
            }

            try {
                String uid = jsonObject.getString("userId");
                note.setUserId(Integer.valueOf(uid));
            } catch (JSONException e) {
                note.setUserId(null);
            }
            try {
                String state = jsonObject.getString("state");
                note.setState(Integer.valueOf(state));
            } catch (JSONException e) {
                note.setState(null);
            }

            return note;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            Utils.close(stream);
        }

    }

    private void error(int code, String msg) {
        resultCode = code;
        builder.append(msg);
    }

    private String updateNoteState(NoteDao noteDao, HttpServletRequest request, int state) {

        int[] noteIds = parseNoteIds(request);
        StringBuilder builder = null;

        if (!Utils.isArrayEmpty(noteIds)) {
            for (int noteId : noteIds) {

                int res = noteDao.updateNoteState(noteId, state);

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
