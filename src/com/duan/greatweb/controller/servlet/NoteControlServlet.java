package com.duan.greatweb.controller.servlet;

import com.duan.greatweb.entitly.Note;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by DuanJiaNing on 2017/10/11.
 */
public class NoteControlServlet extends DataManipulateAbstract {

    private static final int CATEGORY_DELETE_NOTES = 1;

    @Override
    protected String handleManipulate(int category, HttpServletRequest request, HttpServletResponse response) {
        String data = null;
        switch (category){
            case CATEGORY_DELETE_NOTES:

                break;
            default:
                break;
        }

        return data;
    }

    private boolean deleteNote(Note...notes){

        return false;
    }

}
