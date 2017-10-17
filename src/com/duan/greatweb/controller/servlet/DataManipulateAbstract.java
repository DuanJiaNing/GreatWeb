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
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.request = request;
        this.response = response;

        response.setContentType("text/html");

        outStream = response.getOutputStream();

        String errorInfo = "操作执行失败";
        String data = handleManipulate();
        if (Utils.isReal(data)) {
            out(data);
        } else {
            LogErrorRequest(errorInfo);
        }
        close();
    }

    protected int getCode(String name) {
        if (request != null) {
            String str = request.getParameter(name);
            if (Utils.isReal(str)) {
                return Integer.valueOf(str);
            }
        }

        return -1;
    }

    protected String getString(String name) {
        if (request != null) {
            return request.getParameter(name);
        }
        return null;
    }

    protected abstract String handleManipulate();

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
