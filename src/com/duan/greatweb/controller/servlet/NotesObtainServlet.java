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

	private OutputStream outStream;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");

		String str = request.getParameter("category");
		String result = "";
		outStream = response.getOutputStream();
		int cate = Integer.valueOf(str);

		if (!Utils.isReal(str)) {
			LogErrorRequest("需要在请求中指明请求笔记类别,category=?");
			return;
		}

		switch (cate) {
		case CATEGORY_ALL_NOTES:
			String jsonNotes = queryAllNotes();
			if (Utils.isReal(jsonNotes)) {
				out(jsonNotes);
			} else {
				LogErrorRequest("没有获取到数据");
				return;
			}
			break;
		default:
			LogErrorRequest("请求的数据类型不存在,category=?");
			break;
		}
	}

	private String queryAllNotes() {
		NoteDao nd = new NoteDaoImpl();
		UserDao ud = new UserDaoImpl();
		Map<Note, User> data = new HashMap<Note, User>();
		
		List<Note> notes = nd.queryAll();
//		List<Map<>>
		for (Note note : notes) {
			int uid = note.getUserId();
			User u = ud.queryById(uid);
			data.put(note, u);
		}

		Gson gson = new Gson();
		return gson.toJson(data);
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
		if (outStream == null) {
			return;
		}

		try {
			outStream.write(str.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
