package com.duan.greatweb.controller.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.duan.greatweb.entitly.Note;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.duan.greatweb.dao.NoteDao;
import com.duan.greatweb.dao.NoteDaoImpl;
import com.duan.greatweb.util.Utils;

public class PatchImportNoteServlet extends HttpServlet {

	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");

		if (ServletFileUpload.isMultipartContent(request)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置内存临界
			factory.setSizeThreshold(MEMORY_THRESHOLD);
			// 设置临时存储目录
			factory.setRepository((File) (request.getServletContext().getAttribute("javax.servlet.context.tempdir")));
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(MAX_FILE_SIZE);
			upload.setFileSizeMax(MAX_REQUEST_SIZE);
			upload.setHeaderEncoding("utf-8");

			try {

				List<FileItem> files = upload.parseRequest(request);
				if (files != null) {
					NoteDao uDao = new NoteDaoImpl();
					for (FileItem item : files) {
						if (!item.isFormField()) {
							InputStream is = item.getInputStream();
							String str = readString(is);
							List<Note> notes = parseStringToUserArray(str);
							if (notes != null && notes.size() > 0) {
								notes.forEach(note -> uDao.addNote(note));
								request.setAttribute("notes", notes);
							}
						}
					}
				}

			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		request.getRequestDispatcher("patch.jsp").forward(request, response);

	}

	// 文件保存在txt文件中，txt文件编码为 utf-8 ，一条数据 ; （英文）结尾，字段数据 : （英文）结尾，
	// 如 title:content:uid;title1:content1:uid1;
	private List<Note> parseStringToUserArray(String str) {
		if (!Utils.isReal(str)) {
			return null;
		}

		String[] raws = str.split(";");
		List<Note> notes = new ArrayList<Note>();
		for (String raw : raws) {
			String[] ds = raw.split(":");
			if (ds != null && ds.length == 3) {
				String title = ds[0];
				String content = ds[1];
				Integer uid = Integer.valueOf(ds[2]);

				title.replaceAll("\r", "");
				title.replaceAll("\n", "");

				content.replaceAll("\r", "");
				content.replaceAll("\n", "");

				if (Utils.isReal(title) && Utils.isReal(content) && uid != null) {
					Note note = new Note(title, content, System.currentTimeMillis(), uid);
					notes.add(note);
					Utils.log(note.toString());
				}
			}
		}

		return notes;
	}

	private String readString(InputStream is) {
		if (is == null) {
			return "";
		}

		try {

			StringBuilder builder = new StringBuilder();
			byte[] buff = new byte[1024];
			int len = 0;
			while ((len = is.read(buff)) > 0) {
				builder.append(new String(buff, 0, len, "utf-8"));
			}
			Utils.log(builder.toString());
			return builder.toString();

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

}
