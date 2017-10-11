package com.duan.greatweb.controller.servlet;

import com.duan.greatweb.util.Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by DuanJiaNing on 2017/10/11.
 */
public abstract class DataManipulateAbstract extends HttpServlet {

    private OutputStream outStream;

    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        String str = request.getParameter("category");
        outStream = response.getOutputStream();

        if (!Utils.isReal(str)) {
            LogErrorRequest("需要在请求中指明需要的操作类别,category=?");
            return;
        }

        String errorInfo = "操作执行失败";

        int cate = Integer.valueOf(str);
        String data = handleManipulate(cate,request,response);

        if (Utils.isReal(data)) {
            out(data);
        } else {
            LogErrorRequest(errorInfo);
        }
        close();
    }

    protected abstract String handleManipulate(int category,HttpServletRequest request,HttpServletResponse response);

    protected void LogErrorRequest(String msg) {
        Utils.log(msg);
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
        if (outStream == null || !Utils.isReal(str)) {
            return;
        }

        try {
            // 中文乱码 ***** spend 2 hour
            outStream.write(str.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
