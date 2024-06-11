$(function () {
  $.ajax({
    method: "get",
    url: `https://api.odcloud.kr/api/15111389/v1/uddi:41944402-8249-4e45-9e9d-a52d0a7db1cc?
        page=1&perPage=10&
        returnType=json&serviceKey=WY4BAHDkXS4leBxDCI8gOoTqWgrg%2BAU60N%2BC6Fx4z%2BgfZA%2Fq6uHt7iYvmlEPMC6fCbGvY3g9auS%2FpreyQ8Fy5Q%3D%3D`,
  }).done(function (response) {
    console.log(response.data[0]);
  });
});
