<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
			<div class="col-md-10 offset-md-1">
				<h1>Posts</h1>

				<a href="${pageContext.request.contextPath}/posts/new" class="btn btn-primary mb-3">Novo Post</a>
				<a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-secondary mb-3">Início</a>

				<table class="table table-striped">
					<thead>
						<tr>
							<th>Id</th>
							<th>Conteúdo</th>
							<th>Data</th>
							<th>Autor</th>
							<th>Ações</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="post" items="${posts_list}">
							<tr>
								<td>${post.id}</td>
								<td><c:out value="${post.content}" /></td>
								<td><fmt:formatDate value="${post.postDate}" pattern="dd/MM/yyyy" /></td>
								<td><c:out value="${post.user.name}" /></td>
								<td>
									<a class="btn btn-sm btn-warning"
									   href="${pageContext.request.contextPath}/posts/update?postId=${post.id}">Editar</a>
									<form action="${pageContext.request.contextPath}/posts/delete"
									      method="POST" class="d-inline"
									      onsubmit="return confirm('Excluir o post ${post.id}?');">
										<input type="hidden" name="postId" value="${post.id}">
										<button type="submit" class="btn btn-sm btn-danger">Excluir</button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
