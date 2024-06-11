$(document).ready(function () {
  // Page Link Click Event
  $(".page-link").click(function () {
    $("#page").val($(this).data("page"));
    $("#searchForm").submit();
  });

  // Search Button Click Event
  $("#btnSearch").click(function () {
    $("#kWord").val($("#inputSearch").val());
    $("#page").val(0);
    $("#searchForm").submit();
  });

  // Select Change Event
  $("#searchOption").change(function () {
    $("#searchType").val($(this).val());
  });
});
