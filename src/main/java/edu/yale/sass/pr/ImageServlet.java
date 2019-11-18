package edu.yale.sass.pr;

import edu.yale.sass.pr.service.YalePhotoDirectoryService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.w3c.tools.jpeg.JpegCommentWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageServlet extends HttpServlet {

	public static final long serialVersionUID = -4;
	private YalePhotoDirectoryService yalePhotoDirectoryService;

	public void init(ServletConfig config) throws ServletException {

		super.init(config);

		try {
			ApplicationContext context = WebApplicationContextUtils
					.getRequiredWebApplicationContext(getServletContext());
			yalePhotoDirectoryService = (YalePhotoDirectoryService) context.getBean("YalePhotoDirectoryService");
		} catch (Throwable t) {
			throw new ServletException("Failed to initialise RosterTool servlet.", t);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/NotAllowed.jsf");
			requestDispatcher.forward(request, response);
			return;
		}

		String uid = request.getParameter("uid");

		if (uid == null)
			return;

		byte[] photo = loadPhotoFromCache(uid);
		if (photo == null)
			return;
		response.setContentType("image/jpeg");
		response.setContentLength(photo.length);
		response.getOutputStream().write(photo, 0, photo.length);

	}

	public byte[] loadPhotoFromCache(String netId) {
		try {
			byte[] image = yalePhotoDirectoryService.loadPhotoFromCache(netId);
			if (image != null) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream(image.length + 200);
				writeHeader(baos, image, netId);
				image = baos.toByteArray();
				baos.close();
			}
			return image;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	protected void writeHeader(OutputStream out, byte[] jpegBytes, String netId) throws IOException {
		String netIdCheckSum = yalePhotoDirectoryService.md5CheckSum(netId) + " " + System.currentTimeMillis();

		ByteArrayInputStream bais = null;
		JpegCommentWriter jpcw = null;
		try {
			bais = new ByteArrayInputStream(jpegBytes);
			jpcw = new JpegCommentWriter(out, bais);

			jpcw.write(
					"\u00A9 Yale University. All rights reserved. You may not copy or reproduce this photo in any form."
							+ "\n\n\n\n" + "|" + netIdCheckSum + "|");
		} finally {
			if (jpcw != null)
				jpcw.close();
			if (bais != null)
				bais.close();
		}
	}
}
