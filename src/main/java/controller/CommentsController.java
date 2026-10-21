package controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Comment;
import model.ModelException;
import model.Post;
import model.User;
import model.dao.CommentDAO;
import model.dao.DAOFactory;
import model.dao.PostDAO;
import model.dao.UserDAO;

@WebServlet(urlPatterns = {"/comments", "/comments/new", "/comments/save",
                           "/comments/update", "/comments/delete"})
public class CommentsController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String action = req.getServletPath();

		switch (action) {
		case "/comments": {
			loadComments(req);
			req.getRequestDispatcher("/comments.jsp").forward(req, resp);
			break;
		}
		case "/comments/new": {
			loadPostsAndUsers(req);
			req.setAttribute("comment", new Comment());
			req.getRequestDispatcher("/comments_form.jsp").forward(req, resp);
			break;
		}
		case "/comments/update": {
			loadPostsAndUsers(req);
			loadComment(req);
			req.getRequestDispatcher("/comments_form.jsp").forward(req, resp);
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
		case "/comments/save": {
			saveOrUpdateComment(req);
			resp.sendRedirect(req.getContextPath() + "/comments");
			break;
		}
		case "/comments/delete": {
			deleteComment(req);
			resp.sendRedirect(req.getContextPath() + "/comments");
			break;
		}
		default:
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private void loadComments(HttpServletRequest req) {
		CommentDAO dao = DAOFactory.createDAO(CommentDAO.class);
		List<Comment> comments = List.of();
		try {
			comments = dao.listAll();
		} catch (ModelException e) {
			e.printStackTrace();
		}
		req.setAttribute("comments_list", comments);
	}

	private void loadPostsAndUsers(HttpServletRequest req) {
		PostDAO postDAO = DAOFactory.createDAO(PostDAO.class);
		UserDAO userDAO = DAOFactory.createDAO(UserDAO.class);
		List<Post> posts = List.of();
		List<User> users = List.of();
		try {
			posts = postDAO.listAll();
			users = userDAO.listAll();
		} catch (ModelException e) {
			e.printStackTrace();
		}
		req.setAttribute("posts_list", posts);
		req.setAttribute("users_list", users);
	}

	private void loadComment(HttpServletRequest req) {
		int commentId = Integer.parseInt(req.getParameter("commentId"));
		CommentDAO dao = DAOFactory.createDAO(CommentDAO.class);
		Comment comment = null;
		try {
			comment = dao.findById(commentId);
		} catch (ModelException e) {
			e.printStackTrace();
		}
		req.setAttribute("comment", comment);
	}

	private void saveOrUpdateComment(HttpServletRequest req) {
		String idStr = req.getParameter("comment_id");
		boolean isUpdate = idStr != null && !idStr.isBlank();

		Comment comment = isUpdate ? new Comment(Integer.parseInt(idStr)) : new Comment();
		comment.setContent(req.getParameter("comment_content"));
		comment.setPost(new Post(Integer.parseInt(req.getParameter("comment_post"))));
		comment.setUser(new User(Integer.parseInt(req.getParameter("comment_user"))));

		CommentDAO dao = DAOFactory.createDAO(CommentDAO.class);
		try {
			if (isUpdate) dao.update(comment);
			else dao.save(comment);
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}

	private void deleteComment(HttpServletRequest req) {
		int commentId = Integer.parseInt(req.getParameter("commentId"));
		CommentDAO dao = DAOFactory.createDAO(CommentDAO.class);
		try {
			dao.delete(new Comment(commentId));
		} catch (ModelException e) {
			e.printStackTrace();
		}
	}
}
