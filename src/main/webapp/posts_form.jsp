<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Gestão de Posts</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
</head>
<body>
	<div class="container">
		<div class="row mt-3">
			<div class="col-md-8 offset-md-2">
				<h1>Cadastro de Post</h1>

				<form action="${pageContext.request.contextPath}/posts/save" method="POST">

					<input type="hidden" name="post_id" value="${post.id == 0 ? '' : post.id}">

					<div class="mb-3">
						<label for="post_content_id" class="form-label">Conteúdo</label>
						<textarea id="post_content_id" name="post_content"
						          class="form-control" rows="4" required><c:out value="${post.content}" /></textarea>
					</div>

					<div class="mb-3">
						<label for="post_user_id" class="form-label">Autor</label>
						<select id="post_user_id" name="post_user" class="form-select" required>
							<option value="">Selecione um usuário...</option>
							<c:forEach var="u" items="${users_list}">
								<option value="${u.id}" ${post.user.id == u.id ? 'selected' : ''}>
									<c:out value="${u.name}" />
								</option>
							</c:forEach>
						</select>
					</div>

					<button type="submit" class="btn btn-primary">Salvar</button>
					<a href="${pageContext.request.contextPath}/posts" class="btn btn-secondary">Cancelar</a>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
