<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%
    String loginUserName = (String) request.getAttribute("loginUserName");
    String author = (String) request.getAttribute("author");
    String id = (String) request.getAttribute("id");

    if (loginUserName == null) {
        response.sendRedirect("/");
    }else if(author!=null){
        if(!author.equals(loginUserName)){
            response.sendRedirect("/");
        }
    }
%>
<div class="k-py-5">
    <div class="container">
        <form id="editorForm" action="/post<%= (id!=null) ? "/" + id : "" %>" method="post">

        </form>
    </div>
</div>
<script>

    $(document).ready(function () {
        let username = "<%= request.getAttribute("author") == null? "": request.getAttribute("author") %>";
        let title = "<%= request.getAttribute("title") == null? "": request.getAttribute("title") %>";
        let content = "<%= request.getAttribute("content") == null? "": request.getAttribute("content") %>";
        let id = "<%= request.getAttribute("id") == null? "": request.getAttribute("id")%>";
        $("#editorForm").kendoForm({
            validatable: {
                validateOnBlur: false,
            },
            orientation: "vertical",
            formData: {
                title: title,
                Username: username===""?"<%=request.getAttribute("loginUserName")%>":username,
                content: content,
                Id:id,
            },
            items: [{
                type: "group",
                label: "Post Content",
                items: [
                    { field: "title", label: "Title:" , validation: { required: true }},
                    {
                        field: "content",
                        label: "Content:",
                        editor: "TextArea",
                        editorOptions: { rows: 5 },
                        validation: { required: true }
                    },
                    { field: "Username", label: "", attributes:{ class: "d-none"}},
                    { field: "Id",  label: "",attributes:{ class: "d-none"}},
              ]
            }],
            validateField: function (e) {

            },
            submit: function (e) {
                e.preventDefault();
                let form = $("#editorForm");
                let title = $("#Title");
                let content = $("#Content");
                if(title.val()==""||content.val()==""){
                    alert("It is invalid process")
                }else{
                    form.submit()
                }

            },
            clear: function (ev) {

            }
        });
    });
</script>