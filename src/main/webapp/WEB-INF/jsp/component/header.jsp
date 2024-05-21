<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<div id="appbar" class="border-bottom"></div>


<script>
    // <![CDATA[
    $(document).ready(function () {
        var appbar = $("#appbar");

        var userName ="<%=(String) request.getAttribute("userName")%>";
        var rightTemp = []
        if(userName==="null"){
            rightTemp.push({ template: '<a class="k-clear-search k-button k-button-flat-base k-button-flat k-button-md k-rounded-md" href="/login">Login</a>', type: "contentItem" })
            rightTemp.push( { template: '<a class="k-clear-search k-button k-button-flat-base k-button-flat k-button-md k-rounded-md" href="/signup">signup</a>', type: "contentItem" })
        }else{
            rightTemp.push({ template: '<h5 class="m-0">'+userName+'</h5>', type: "contentItem" })
            rightTemp.push({ template: '<a class="k-clear-search k-button k-button-flat-base k-button-flat k-button-md k-rounded-md" href="/logout">Logout</a>', type: "contentItem" })
        }

        appbar.kendoAppBar({
            themeColor: "inherit",
            items: [
                { template: '<a href="/"><img class="k-image k-w-24" src="/img/logo.png"></a>', type: "contentItem" },
                { type: "spacer" },
                ...rightTemp
              ]
        }).on("input", "input.k-input-inner", function (e) {
            var input = e.currentTarget;
            var grid = $("#grid").getKendoGrid();
            clearTimeout(grid.searchTimeOut);
            grid.searchTimeOut = setTimeout(function () {
                grid.searchTimeOut = null;
                var expression = { filters: [], logic: "or" };
                var value = input.value;

                if (value) {
                    expression.filters.push({ field: "ProductName", operator: "contains", value: value });
                } else {
                    expression = {};
                }

                grid.dataSource.filter(expression);

            }, 300);
        }).on("click", ".k-button", function (e) {
        }).on("click", ".k-clear-search", function (e) {
            $("#appbar input.k-input").val("").trigger("input");
        }).on("click", ".k-toggle-button", function (e) {
            var chartElement = $("#chart");
            var gridElement = $("#grid");

            if (chartElement.is(":visible")) {
                chartElement.hide();
                gridElement.css("width", "100%");
            } else {
                chartElement.show();
                gridElement.css("width", "");
            }
        });
    });
    // ]]>
</script>
