package com.tianshouzhi.xueditor.servlet;

import com.tianshouzhi.xueditor.ActionEnter;
import com.tianshouzhi.xueditor.Uploader;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tianshouzhi on 2017/6/14.
 */
public class XUeditorServlet extends HttpServlet {
	public static final String UPLOADER_IMPL_KEY = "UPLOAD_IMPL";

	public static final String CONFIG_PATH = "CONFIG_PATH";

	private Uploader uploader;

	private String configPath;

	@Override
	public void init(ServletConfig config) throws ServletException {
		String uploadImpl = config.getInitParameter(UPLOADER_IMPL_KEY);

		if (uploadImpl != null) {
			try {
				Class<?> clazz = Class.forName(uploadImpl);
				Uploader uploader = (Uploader) clazz.newInstance();
				this.uploader = uploader;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		configPath = config.getInitParameter(CONFIG_PATH);

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "text/html");
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		String result = new ActionEnter(request, rootPath, uploader,configPath).exec();
		response.getWriter().write(result);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
