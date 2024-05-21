<div class="k-py-5">
    <div class="container">
        <div class="jumbotron">
            <h1 class="fs-2 text"><%=request.getAttribute("title")%></h1>
            <div class="align-items-end d-flex fs-6">
                <span class="small"><b>Author: </b><%=request.getAttribute("author")%></span>
                <span class="px-3 small"><b>CreatedAt: </b><%=request.getAttribute("createdAt")%></span>

            </div>
            <hr class="mt-2 mb-4">
            <p><%=request.getAttribute("content")%></p>

            <% if(request.getAttribute("loginUserName")!=null && request.getAttribute("loginUserName").equals(request.getAttribute("author")))
            {
            %>
            <div class="d-flex justify-content-between">
                <a class="btn btn-sm btn-outline-primary" href="/post/<%=request.getAttribute("id")%>" role="button"><i class="bi bi-pencil-square"></i> edit</a>
                <a class="btn btn-sm btn-danger" href="/delPost/<%=request.getAttribute("id")%>" role="button"><i class="bi bi-trash"></i> delete</a>
            </div>
            <%
                }
            %>
        </div>
    </div>
</div>