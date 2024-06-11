$(function () {
  //네비게이션 //////////////////////////////////////////////
  /* 네비게이션바 dropdown mouseover효과 */
  /*$("#nav .nav-item").mouseover(function () {
    $(this).children().show();
  });
  $("#nav .nav-item").mouseout(function () {
    $(".dropdown-menu").hide();
  });*/
  /* 네비게이션바 dropdown시 active클래스 추가 */
  $("#nav .nav-item").click(function () {
    $("#nav .nav-link").addClass("active");
    $(this).siblings().children().removeClass("active");
    $(this).parent().siblings().children().children().removeClass("active");
  });
  /* 네비게이션바 스크롤 효과 추가 */
  $(window).scroll(function () {
    if ($(window).scrollTop()) {
      $("#nav").addClass("bg-brown");
    } else {
      $("#nav").removeClass("bg-brown");
    }
  });

  //커뮤니티 ////////////////////////////////////////////////
  let pathname = window.location.pathname;        // community/notice/notice_detail
  let pathnames = pathname.split("/");  // [0]:"", [1]:"community", [2]:"notice", [3]:"notice_detail"
  let url = pathnames[1]+"/"+pathnames[2];        // community/notice
  urlRegExp = new RegExp(url.replace(/\/$/, '')); // /community\/notice/
  $('#tab a').each(function () {
    if (urlRegExp.test(this.href.replace(/\/$/, ''))) { // href(http://localhost:8080/community/notice/notice_detail)에 urlRegExp가 포함되어 있는지 test하고 맞으면 참
      $(this).addClass('active');
    }
  });

  /* tab메뉴 */
  /*$("#page-select .page-item").click(function () {
    $("#page-select .page-link").addClass("active");
    $(this).siblings().children().removeClass("active");
  });*/

  /*이벤트 페이지 slick slider*/
  $(".post-wrapper").slick({
    slidesToShow: 2,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 2000,
    nextArrow: $(".next"),
    prevArrow: $(".prev"),
    responsive: [
      // 반응형 추가
      {
        breakpoint: 768, // 화면 사이즈 768px
        settings: {
          slidesToShow: 1,
        },
      },
    ],
  });

  /*비밀글 checkbox check확인 후 비밀번호 입력창 활성화*/
  function togglePasswordInput() {
    if($("#question-create #passwordChecked .form-check-input").is(':checked')){
      $("#question-create #passwordChecked .form-control").prop("disabled", false);
    } else {
      $("#question-create #passwordChecked .form-control").prop("disabled", true);
    }
  }
  // 초기 상태를 설정합니다.
  togglePasswordInput();
  // 체크박스 상태가 변경될 때마다 실행됩니다.
  $("#question-create #passwordChecked .form-check-input").change(function() {
    togglePasswordInput();
  });

  /**/
  $("#question-create #completeBtn").click(function (){
    if(!($("#question-create #isAgreeChecked").is(':checked'))){
      alert("개인정보처리방침에 동의해주십시오");
    }
  })

  //내정보//////////////////////////////////////////////////////
  /* 사이즈 바뀔때마다 myInfo Height의 -30px값으로 bg값 설정*/
  function myInfoBgHeight() {
    let myInfoH = $("#my-info .myInfo").outerHeight() - 30;
    $("#my-info .myInfo-bg").css("height", myInfoH + "px");
  }

  myInfoBgHeight();
  $(window).resize(function () {
    myInfoBgHeight();
  });

  $(".alarm").click(function (){
    alert("아직 작업되지 않은 곳입니다.");
  });
});

