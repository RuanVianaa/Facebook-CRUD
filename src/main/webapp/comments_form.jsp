<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Gestão de Comentários</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
</head>
<body>
	<div class="container">
		<div class="row mt-3">
			<div class="col-md-8 offset-md-2">
				<h1>Cadastro de Comentário</h1>

				<form action="${pageContext.request.contextPath}/comments/save" method="POST">

					<input type="hidden" name="comment_id" value="${comment.id == 0 ? '' : comment.id}">

					<div class="mb-3">
						<label for="comment_content_id" class="form-label">Comentário</label>
						<textarea id="comment_content_id" name="comment_content"
						          class="form-control" rows="3" required><c:out value="${comment.content}" /></textarea>
					</div>

					<div class="mb-3">
						<label for="comment_post_id" class="form-label">Post</label>
						<select id="comment_post_id" name="comment_post" class="form-select" required>
							<option value="">Selecione um post...</option>
							<c:forEach var="p" items="${posts_list}">
								<option value="${p.id}" ${comment.post.id == p.id ? 'selected' : ''}>
									#${p.id} - <c:out value="${p.content}" />
								</option>
							</c:forEach>
						</select>
					</div>

					<div class="mb-3">
						<label for="comment_user_id" class="form-label">Autor</label>
						<select id="comment_user_id" name="comment_user" class="form-select" required>
							<option value="">Selecione um usuário...</option>
							<c:forEach var="u" items="${users_list}">
								<option value="${u.id}" ${comment.user.id == u.id ? 'selected' : ''}>
									<c:out value="${u.name}" />
								</option>
							</c:forEach>
						</select>
					</div>

					<button type="submit" class="btn btn-primary">Salvar</button>
					<a href="${pageContext.request.contextPath}/comments" class="btn btn-secondary">Cancelar</a>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
