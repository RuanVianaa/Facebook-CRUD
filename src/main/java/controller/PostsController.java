package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.Post;
import model.User;
import model.dao.DAOFactory;
import model.dao.PostDAO;
import model.dao.UserDAO;

@WebServlet(urlPatterns = {"/posts", "/posts/new", "/posts/save",
                           "/posts/update", "/posts/delete"})
public class PostsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// getServletPath() não inclui o contexto (/facebook), então funciona em qualquer path
		String action = req.getServletPath();

		switch (action) {
		case "/posts": {
			loadPosts(req);
			req.getRequestDispatcher("/posts.jsp").forward(req, resp);
			break;
		}
		case "/posts/new": {
			loadUsers(req);
			req.setAttribute("post", new Post());
			req.getRequestDispatcher("/posts_form.jsp").forward(req, resp);
			break;
		}
		case "/posts/update": {
			loadUsers(req);
			loadPost(req);
			req.getRequestDispatcher("/posts_form.jsp").forward(req, resp);
			break;
		}
		default:
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getServletPath();

		switch (action) {
		case "/posts/save": {
			saveOrUpdatePost(req);
			resp.sendRedirect(req.getContextPath() + "/posts");
			break;
		}
		case "/posts/delete": {
			deletePost(req);
			resp.sendRedirect(req.getContextPath() + "/posts");
			break;
		}
		default:
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private void loadPosts(HttpServletRequest req) {
		PostDAO dao = DAOFactory.createDAO(PostDAO.class);
		List<Post> posts = List.of();
		try {
			posts = dao.listAll();
		} catch (ModelException e) {
			e.printStackTrace();
		}
		req.setAttribute("posts_list", posts);
	}

	private void loadUsers(HttpServletRequest req) {
		UserDAO dao = DAOFactory.createDAO(UserDAO.class);
		List<User> users = List.of();
		try {
			users = dao.listAll();
		} catch (ModelException e) {
			e.printStackTrace();
		}
		req.setAttribute("users_list", users);
	}

	private void loadPost(HttpServletRequest req) {
		int postId = Integer.parseInt(req.getParameter("postId"));
		PostDAO dao = DAOFactory.createDAO(PostDAO.class);
		Post post = null;
		try {
			post = dao.findById(postId);
		} catch (ModelException e) {
			e.printStackTrace();
		}
		req.setAttribute("post", post);
	}

	private void saveOrUpdatePost(HttpServletRequest req) {
		String postIdStr = req.getParameter("post_id");
		boolean isUpdate = postIdStr != null && !postIdStr.isBlank();

		Post post = isUpdate ? new Post(Integer.parseInt(postIdStr)) : new Post();
		post.setContent(req.getParameter("post_content"));
		post.setUser(new User(Integer.parseInt(req.getParameter("post_user"))));

		PostDAO dao = DAOFactory.createDAO(PostDAO.class);
		try {
			if (isUpdate) dao.update(post);
			else dao.save(post);
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

	private void deletePost(HttpServletRequest req) {
		int postId = Integer.parseInt(req.getParameter("postId"));
		PostDAO dao = DAOFactory.createDAO(PostDAO.class);
		try {
			dao.delete(new Post(postId));
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}
}
