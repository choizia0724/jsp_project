<%@ page import="com.zia.project.model.Post" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<div class="k-py-5">
    <div class="container">
        <table class="table table-bordered">
            <thead>
            <tr>

                <th>Subject</th>
                <th>Author</th>
                <th class="d-none d-sm-table-cell">Date</th>
            </tr>
            </thead>
            <tbody id="contents-wrapper">
                <%
                List<Post> posts = (List<Post>) request.getAttribute("posts");
                if (posts == null || posts.isEmpty()) {
            %>
            <tr>
                <td colspan="3">현재 데이타가 없습니다.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
        <div id="pager"></div>
        <div class="justify-content-end d-flex mt-2">
            <% if (request.getAttribute("userName") != null) { %>
            <a class="k-button k-button-md k-rounded-md k-button-solid k-button-solid-primary" href="/post">글쓰기</a>
            <% } %>
        </div>
    </div>

</div>
<script id="template" type="text/x-kendo-template">
    <tr>

            <td><a class="text text-decoration-none link-dark d-block" href="/read/#=id#">#= title #</a></td>
            <td>#= username #</td>
            <td class="d-none d-sm-table-cell">#= createdAt #</td>

    </tr>
</script>
<script>
    $(document).ready(function () {

        var template = kendo.template($("#template").html());

        var dataSource = new kendo.data.DataSource({

            data:[ <% for(Post post : (List<Post>)request.getAttribute("posts")){
                %>
                {
                    id : "<%=post.getId()%>",
                    title : "<%=post.getTitle()%>",
                    username: "<%=post.getUsername()%>",
                    createdAt: "<%=post.getFormattedCreatedAt()%>"
                },
                <%
                    }
                    %>
                ],
            pageSize: 10,
            change: function () {
                $("#contents-wrapper").html(kendo.render(template, this.view()));
            }
        });

        $("#pager").kendoPager({
            dataSource: dataSource
        });

        dataSource.read();

    });
</script>
