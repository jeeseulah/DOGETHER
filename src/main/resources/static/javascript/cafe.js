$(function () {
  const siDo = $("#siDo");
  const siGunGu = $("#siGunGu");
  const dongEub = $("#dongEub");
  const result = $("#result");
  let map;
  let service;

  // 구글 지도를 초기화하는 함수
  async function initMap() {
    // 기본 위치 설정 (서울)
    const position = { lat: 37.5665, lng: 126.978 };

    //@ts-ignore
    const { Map } = await google.maps.importLibrary("maps");
    //@ts-ignore
    const { PlacesService } = await google.maps.importLibrary("places");

    // 지도 초기화
    map = new Map(document.getElementById("map"), {
      zoom: 10,
      center: position,
      mapId: "2af3e9f2cede00d7", // 여기에 유효한 지도 ID를 입력하세요
    });

    // PlacesService 객체 초기화
    service = new PlacesService(map);

    loadInitialCafes();
  }

  function loadInitialCafes() {
    const selectedSiDo = "서울특별시";
    const selectedSiGunGu = "강남구";
    const selectedDongEub = "논현동";
    $.ajax({
      url: "/api/cafe",
      method: "GET",
      data: {
        siDo: selectedSiDo,
        siGunGu: selectedSiGunGu,
        dongEub: selectedDongEub,
      },
      contentType: "application/x-www-form-urlencoded;charset=EUC-KR",
    })
      .done(function (data) {
        result.empty();
        const cafesToShow = data.slice(0, 5);
        $.each(cafesToShow, function (_, cafe) {
          result.append(`
        <button type="button" class="list-group-item list-group-item-action show-location-btn"
        data-lat="${cafe.lat}" data-lng="${cafe.lng}"
        data-facility_name="${cafe.facility_name}" data-operate_time="${cafe.operate_time}"
        data-day_off="${cafe.day_off}" data-tel="${cafe.tel}"
        data-road_address="${cafe.road_address}" data-parking="${cafe.parking}"
        data-pet_size="${cafe.pet_size}" data-image="${cafe.image}">
          ${cafe.facility_name}
          <span class="ms-auto">${cafe.road_address}</span>
        </button>
      `);
        });

        // 버튼 클릭 이벤트 처리
        $(".show-location-btn").on("click", function () {
          const lat = parseFloat($(this).data("lat"));
          const lng = parseFloat($(this).data("lng"));
          const facility_name = $(this).data("facility_name");
          const operate_time = $(this).data("operate_time");
          const day_off = $(this).data("day_off");
          const tel = $(this).data("tel");
          const road_address = $(this).data("road_address");
          const parking = $(this).data("parking");
          const pet_size = $(this).data("pet_size");
          const image = $(this).data("image");

          const cafe = {
            facility_name,
            operate_time,
            day_off,
            tel,
            road_address,
            parking,
            pet_size,
            image,
          };

          showLocation(lat, lng, cafe);
        });
        $(".show-location-btn").first().click();
      })
      .fail(function (error) {
        console.error("병원 목록 조회 실패:", error);
      });
  }

  // 특정 위치에 마커를 추가하고 정보창을 표시하는 함수
  async function showLocation(latitude, longitude, cafe) {
    if (!map || !service) {
      console.error(
        "지도 객체 또는 PlacesService 객체가 초기화되지 않았습니다.",
      );
      return;
    }

    const position = { lat: latitude, lng: longitude };

    const marker = new google.maps.Marker({
      position: position,
      map: map,
      title: cafe.facility_name,
    });

    const infoWindow = new google.maps.InfoWindow({
      content: `<div><strong>${cafe.facility_name}</strong><br>운영시간: ${cafe.operate_time}</div>`,
    });

    marker.addListener("click", () => {
      infoWindow.open(map, marker);
    });

    map.setCenter(position);
    map.setZoom(15);

    // 병원 정보 및 사진을 가져와서 업데이트하는 함수 호출
    updatecafeInfo(cafe);
    getPlaceDetails(latitude, longitude, cafe.facility_name);
  }

  // 병원 정보를 HTML에 업데이트하는 함수
  // 병원 정보를 HTML에 업데이트하는 함수
  function updatecafeInfo(cafe) {
    $("#facilityName").text(cafe.facility_name);
    $("#operateTime").text(cafe.operate_time);
    $("#dayOff").text(cafe.day_off || "휴무일 정보 없음");
    $("#tel").text(cafe.tel || "전화번호 정보 없음");
    $("#roadAddress").text(cafe.road_address || "주소 정보 없음");
    $("#parking").text(cafe.parking || "주차 정보 없음");
    $("#petSize").text(cafe.pet_size || "애완동물 크기 정보 없음");

    if (cafe.image) {
      $("#cafeImage").attr("src", cafe.image).addClass("img-fluid");
    } else {
      $("#cafeImage")
        .attr("src", "../../images/section_hospitalInfo.png")
        .addClass("img-fluid");
    }
  }

  // Google Places API를 사용하여 장소 정보를 가져오는 함수
  // Google Places API를 사용하여 장소 정보를 가져오는 함수
  // Google Places API를 사용하여 장소 정보를 가져오는 함수
  function getPlaceDetails(latitude, longitude, facilityname) {
    const request = {
      location: new google.maps.LatLng(latitude, longitude),
      radius: "5000",
      query: facilityname,
    };

    service.textSearch(request, (results, status) => {
      if (status === google.maps.places.PlacesServiceStatus.OK && results[0]) {
        const place = results[0];

        // place_id를 사용하여 장소 세부 정보를 가져오는 요청
        service.getDetails(
          { placeId: place.place_id },
          (placeDetails, status) => {
            if (
              status === google.maps.places.PlacesServiceStatus.OK &&
              placeDetails
            ) {
              // 병원 사진 업데이트
              if (placeDetails.photos && placeDetails.photos.length > 0) {
                $("#cafeImage")
                  .attr(
                    "src",
                    placeDetails.photos[0].getUrl({
                      maxWidth: 500,
                      maxHeight: 500,
                    }),
                  )
                  .addClass("img-fluid");
              } else {
                $("#cafeImage")
                  .attr("src", "../../images/section_hospitalInfo.png")
                  .addClass("img-fluid");
              }
            } else {
              console.error("Google Places API getDetails 응답 오류:", status);
              $("#cafeImage")
                .attr("src", "../../images/section_hospitalInfo.png")
                .addClass("img-fluid");
            }
          },
        );
      } else {
        console.error("Google Places API textSearch 응답 오류:", status);
        $("#cafeImage")
          .attr("src", "../../images/section_hospitalInfo.png")
          .addClass("img-fluid");
      }
    });
  }

  // 시/도 변경 시 시/군/구 목록 갱신
  siDo.on("change", function () {
    const selectedSiDo = $(this).val();
    $.ajax({
      url: "/api/locations",
      method: "GET",
      data: { siDo: selectedSiDo },
      contentType: "application/x-www-form-urlencoded;charset=EUC-KR",
    })
      .done(function (data) {
        siGunGu.empty().append("<option selected>시/군/구</option>");
        $.each(data, function (_, item) {
          siGunGu.append(`<option value="${item}">${item}</option>`);
        });
      })
      .fail(function (error) {
        console.error("시/도 변경 실패:", error);
      });
  });

  // 시/군/구 변경 시 동/읍/면 목록 갱신
  siGunGu.on("change", function () {
    const selectedSiDo = siDo.val();
    const selectedSiGunGu = $(this).val();
    $.ajax({
      url: "/api/locations",
      method: "GET",
      data: {
        siDo: selectedSiDo,
        siGunGu: selectedSiGunGu,
      },
      contentType: "application/x-www-form-urlencoded;charset=EUC-KR",
    })
      .done(function (data) {
        dongEub.empty().append("<option selected>동/읍/면</option>");
        $.each(data, function (_, item) {
          dongEub.append(`<option value="${item}">${item}</option>`);
        });
      })
      .fail(function (error) {
        console.error("시/군/구 변경 실패:", error);
      });
  });

  // 동/읍/면 변경 시 병원 목록 조회
  dongEub.on("change", function () {
    const selectedSiDo = siDo.val();
    const selectedSiGunGu = siGunGu.val();
    const selectedDongEub = $(this).val();
    $.ajax({
      url: "/api/cafe",
      method: "GET",
      data: {
        siDo: selectedSiDo,
        siGunGu: selectedSiGunGu,
        dongEub: selectedDongEub,
      },
      contentType: "application/x-www-form-urlencoded;charset=EUC-KR",
    })
      .done(function (data) {
        result.empty();
        $.each(data, function (_, cafe) {
          result.append(`
        <button type="button" class="list-group-item list-group-item-action show-location-btn"
        data-lat="${cafe.lat}" data-lng="${cafe.lng}"
        data-facility_name="${cafe.facility_name}" data-operate_time="${cafe.operate_time}"
        data-day_off="${cafe.day_off}" data-tel="${cafe.tel}"
        data-road_address="${cafe.road_address}" data-parking="${cafe.parking}"
        data-pet_size="${cafe.pet_size}" data-image="${cafe.image}">
          ${cafe.facility_name}
          <span class="ms-auto">${cafe.road_address}</span>
        </button>
      `);
        });

        // 버튼 클릭 이벤트 처리
        $(".show-location-btn").on("click", function () {
          const lat = parseFloat($(this).data("lat"));
          const lng = parseFloat($(this).data("lng"));
          const facility_name = $(this).data("facility_name");
          const operate_time = $(this).data("operate_time");
          const day_off = $(this).data("day_off");
          const tel = $(this).data("tel");
          const road_address = $(this).data("road_address");
          const parking = $(this).data("parking");
          const pet_size = $(this).data("pet_size");
          const image = $(this).data("image");

          const cafe = {
            facility_name,
            operate_time,
            day_off,
            tel,
            road_address,
            parking,
            pet_size,
            image,
          };

          showLocation(lat, lng, cafe);
        });
      })
      .fail(function (error) {
        console.error("병원 목록 조회 실패:", error);
      });
  });

  // 페이지 로드 시 지도 초기화
  window.initMap = function () {
    initMap();
  };

  // 페이지 로드 시 지도 초기화 함수 호출
  initMap();
});
