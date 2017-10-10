package com.duan.greatweb.controller.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginSuccess extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        countSuccessLoginUser(session);

        request.getRequestDispatcher("/note-os/manage/manage_os.jsp").forward(request,response);
    }

    private void countSuccessLoginUser(HttpSession session) {
        Object count = session.getAttribute("success_login_user_count");
        if (count == null) { // 第一个用户
            session.setAttribute("success_login_user_count", 1);
        } else {
            session.setAttribute("success_login_user_count", (int) count + 1);
        }
    }
}
