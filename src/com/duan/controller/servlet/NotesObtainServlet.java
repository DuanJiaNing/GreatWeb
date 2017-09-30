package com.duan.controller.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.duan.util.Utils;

public class NotesObtainServlet extends HttpServlet {

	private static final int CETEGORY_ALL_NOTES = 1;

	private OutputStream outStream;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");

		String str = request.getParameter("cetegory");
		String result = "";
		outStream = response.getOutputStream();
		int cate = Integer.valueOf(str);

		if (!Utils.isReal(str)) {
			errorRequest("需要在请求中指明请求笔记类别,category=?");

			return;
		}

		switch (cate) {
		case CETEGORY_ALL_NOTES:

			break;
		}

	}

	private void errorRequest(String msg) {
		out(msg);
		close();
	}

	private void close() {
		if (outStream != null) {
			try {
				outStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void out(String str) {
		if (outStream == null) {
			return;
		}

	}

}
