package com.duan.controller.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.duan.util.Utils;

public class IdentifyCodeServlet extends HttpServlet {

	private final Random random = new Random();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 设置响应头为图片格式
		response.setContentType("image/jpeg");

		// 设置页面不缓存
		response.setHeader("pargma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("expires", 0);

		BufferedImage image = darwImage(response);

		OutputStream os = response.getOutputStream();
		ImageIO.write(image, "JPEG", os);
		os.flush();
		os.close();

	}

	private BufferedImage darwImage(HttpServletResponse response) {
		int width = 80;
		int height = 20;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

		Graphics graphics = image.getGraphics();
		Color color = graphics.getColor();
		graphics.fillRect(0, 0, width, height);

		String str = getRandomString();
		Cookie cookie = new Cookie("identifyCode", str);
		response.addCookie(cookie);

		// 绘制字符和背景颜色
		int d = width / str.length();
		for (int i = 0; i < str.length(); i++) {
			graphics.setFont(getRandomFont());
			graphics.setColor(getRandomColor());
			int x = i * d;
			graphics.drawString(str.charAt(i) + "", x, height);
		}

		// 绘制干扰点
		int count = 30;
		for (int i = 0; i < count; i++) {
			int x = getRandomIntInBound(0, width);
			int y = getRandomIntInBound(0, height);
			int w = getRandomIntInBound(0, 2);
			int h = getRandomIntInBound(0, 5);
			graphics.drawOval(x, y, w, h);
		}

		// 重置画笔
		graphics.setColor(color);
		graphics.dispose();
		return image;
	}

	private int getRandomIntInBound(int start, int end) {
		return start + random.nextInt(end - start);
	}

	private Color getRandomColor() {
		return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
	}

	private String getRandomString() {
		char[] ch = "3456789abcdefghijkmnpqrstuvwxyABCDEFGHJKLMNPQRSTUVWXY".toCharArray();
		int len = ch.length;
		final int size = 4;

		char[] cs = new char[size];
		for (int i = 0; i < size; i++) {
			cs[i] = ch[random.nextInt(len)];
		}
		return new String(cs);
	}

	private Font getRandomFont() {
		Font[] fs = { new Font("华文行楷", Font.PLAIN, 24), new Font("宋体", Font.PLAIN, 24), new Font("黑体", Font.PLAIN, 24),
				new Font("微软雅黑", Font.PLAIN, 24) };
		return fs[random.nextInt(fs.length)];
	}

}
