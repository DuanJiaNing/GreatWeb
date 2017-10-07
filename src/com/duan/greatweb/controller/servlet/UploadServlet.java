package com.duan.greatweb.controller.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.duan.greatweb.util.Utils;

public class UploadServlet extends HttpServlet {

	// 上传文件存储目录
	private static final String UPLOAD_DIRECTORY = "upload";

	// 上传配置
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		final StringBuilder info = new StringBuilder();
		// 检查是否有文件传递过来
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

			// 构造临时路径来存储上传的文件
			// ./ 这个路径相对当前应用的目录
			// / !!
			String uploadPath = request.getServletContext().getRealPath("./") + UPLOAD_DIRECTORY;
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}

			try {

				List<FileItem> items = upload.parseRequest(request);
				for (FileItem item : items) {
					if (!item.isFormField()) { // 不是表单里的普通字符串
						String fileName = new File(item.getName()).getName();
						String filePath = uploadPath + File.separator + fileName;
						File storeFile = new File(filePath);
						Utils.log(storeFile.getAbsolutePath());
						// 保存文件
						item.write(storeFile);
						request.getSession().setAttribute("image", "upload/" + fileName);
						Utils.append(info, "file", "<a href=\"info.jsp\">查看文件</a>");
					} else {
						String name = request.getParameter("name");
						Utils.log(name);
						Utils.append(info, "name", name);
					}
				}

			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Utils.append(info, "exception", e.getMessage());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Utils.append(info, "exception", e.getMessage());
			}

		} else {
			Utils.append(info, "错误", "没有文件");
		}

		out(out, info.toString());

	}

	private void out(PrintWriter out, String msg) {
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.println(msg);
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

}
