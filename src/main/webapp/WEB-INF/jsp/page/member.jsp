<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<div class="demo-section k-d-flex k-justify-content-center k-relative k-py-5">
    <div id="validation-success k-absolute"></div>

    <form id="loginForm" class="k-w-1/3" action="/${direct}" method="post" style="max-width: 350px;width: 100%;"></form>
</div>

<script>
    $(document).ready(function () {
        var validationSuccess = $("#validation-success");

        $("#loginForm").kendoForm({
            orientation: "vertical",
            items: [{
                type: "group",
                label: "${direct}",
                items: [
                    { field: "Username", label: "Username:", validation: { required: true } },
                    {
                        field: "Password",
                        label: "Password:",
                        validation: { required: true },
                        //hint: "Hint: enter alphanumeric characters only.",
                        editor: function (container, options) {
                            $('<input type="password" id="Password" name="' + options.field + '" title="Password" required="required" autocomplete="off" aria-labelledby="Password-form-label" data-bind="value: Password" aria-describedby="Password-form-hint"/>')
                                .appendTo(container)
                                .kendoTextBox();
                        }
                    },
                ]
            }],
            validateField: function(e) {
                validationSuccess.html("");
            },
            submit: function(e) {
                validationSuccess.html("<div class='k-messagebox k-messagebox-success'>Form data is valid!</div>");
            },
            clear: function(ev) {
                validationSuccess.html("");
            }
        });
    });
</script>
