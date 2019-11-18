package edu.yale.sass.pr.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String uri = req.getRequestURI();
		 
		if (uri.indexOf("/admin") >=0){//only filter in this case
			HttpSession session = req.getSession(false);
			if (session ==null){
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/login");

				dispatcher.forward(req, resp);
				chain.doFilter(request, response);
			}
			else{
				String netId =(String) session.getAttribute("netId");
				if (netId ==null){
					resp.sendRedirect("/photoroster/start.jsf");
				}
				else{
					chain.doFilter(request, response);
				}
			}
		}
		else{
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
