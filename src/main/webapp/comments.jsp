<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
			<div class="col-md-10 offset-md-1">
				<h1>Comentários</h1>

				<a href="${pageContext.request.contextPath}/comments/new" class="btn btn-primary mb-3">Novo Comentário</a>
				<a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-secondary mb-3">Início</a>

				<table class="table table-striped">
					<thead>
						<tr>
							<th>Id</th>
							<th>Comentário</th>
							<th>Data</th>
							<th>Post</th>
							<th>Autor</th>
							<th>Ações</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="comment" items="${comments_list}">
							<tr>
								<td>${comment.id}</td>
								<td><c:out value="${comment.content}" /></td>
								<td><fmt:formatDate value="${comment.commentDate}" pattern="dd/MM/yyyy" /></td>
								<td>#${comment.post.id}</td>
								<td><c:out value="${comment.user.name}" /></td>
								<td>
									<a class="btn btn-sm btn-warning"
									   href="${pageContext.request.contextPath}/comments/update?commentId=${comment.id}">Editar</a>
									<form action="${pageContext.request.contextPath}/comments/delete"
									      method="POST" class="d-inline"
									      onsubmit="return confirm('Excluir o comentário ${comment.id}?');">
										<input type="hidden" name="commentId" value="${comment.id}">
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
