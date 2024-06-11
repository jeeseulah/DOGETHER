function initMap() {
  $(function () {
    const hospitalRecommend = $("#hospitalRecommend");
    const drugstoreRecommend = $("#drugstoreRecommend");
    const puppyshopRecommend = $("#puppyshopRecommend");
    const cafeRecommend = $("#cafeRecommend");
    let service; // PlacesService 객체를 전역 변수로 선언합니다.

    // Google PlacesService를 초기화합니다.
    function initPlacesService() {
      if (!service) {
        service = new google.maps.places.PlacesService(document.createElement("div"));
      }
    }

    // 공통적으로 데이터를 로드하고 DOM에 추가하는 함수입니다.
    function loadInitialData(url, selectedSiDo, selectedSiGunGu, selectedDongEub, recommendElement, updateImageCallback) {
      $.ajax({
        url: url,
        method: "GET",
        data: {
          siDo: selectedSiDo,
          siGunGu: selectedSiGunGu,
          dongEub: selectedDongEub,
        },
        contentType: "application/x-www-form-urlencoded;charset=EUC-KR",
      })
          .done(function (data) {
            const itemsToShow = data.slice(0, 3);
            $.each(itemsToShow, function (index, item) {
              recommendElement.append(`
              <a href="#" class="card mb-3 border border-0">
                <div class="row g-0">
                  <div class="col-sm-12 col-md-4 d-flex justify-content-center item-element"
                        data-lat="${item.lat}" data-lng="${item.lng}"
                        data-facility_name="${item.facility_name}" data-operate_time="${item.operate_time}"
                        data-day_off="${item.day_off}" data-tel="${item.tel}"
                        data-road_address="${item.road_address}" data-parking="${item.parking}"
                        data-pet_size="${item.pet_size}" data-image="${item.image}">
                        <img src="${item.image}" class="img-fluid rounded m-2 item-image-src" style="width:450px; height: 170px" alt="추천리스트1" id="${recommendElement.attr('id')}Image${index}">
                    </div>
                    <div class="col-sm-12 col-md-8">
                        <div class="card-body ms-3">
                            <h5 class="card-title">${item.facility_name}</h5>
                            <p class="card-text text-muted operate-time">
                                ${item.operate_time}
                            </p>
                            <p class="card-text text-muted">
                                ${item.road_address}<br/>
                                ${item.day_off}
                            </p>
                        </div>
                    </div>
                  </div>
                </a>
            `);
              // 업데이트 콜백 실행
              updateImageCallback(index, item.facility_name, recommendElement.attr('id'));
            });
          })
          .fail(function (error) {
            console.error("목록 조회 실패:", error);
          });
    }

    function updateImage(index, facilityname, type) {
      initPlacesService(); // Google PlacesService 초기화
      const imageElement = $(`#${type}Image${index}`);

      // 업체의 이름을 기반으로 사진 검색
      const request = {
        query: facilityname,
        fields: ["photos"],
      };

      service.findPlaceFromQuery(request, function (results, status) {
        if (status === google.maps.places.PlacesServiceStatus.OK && results && results[0].photos) {
          const place = results[0];
          if (place.photos.length > 0) {
            const photoUrl = place.photos[0].getUrl({ maxWidth: 500, maxHeight: 500 });
            imageElement.attr("src", photoUrl).addClass("img-fluid");
          }
        } else {
          imageElement.attr("src", "../../images/recommend-sample1.jpg").addClass("img-fluid");
          console.error("장소 검색 중 오류가 발생했습니다:", status);
        }
      });
    }

    function loadInitialHospitals() {
      const selectedSiDo = "서울특별시";
      const selectedSiGunGu = "강남구";
      const selectedDongEub = "역삼동";
      loadInitialData("/api/hospital", selectedSiDo, selectedSiGunGu, selectedDongEub, hospitalRecommend, updateImage);
    }

    function loadInitialDrugstores() {
      const selectedSiDo = "서울특별시";
      const selectedSiGunGu = "강남구";
      const selectedDongEub = "대치동";
      loadInitialData("/api/drugstore", selectedSiDo, selectedSiGunGu, selectedDongEub, drugstoreRecommend, updateImage);
    }

    function loadInitialPuppyshops() {
      const selectedSiDo = "서울특별시";
      const selectedSiGunGu = "강남구";
      const selectedDongEub = "역삼동";
      loadInitialData("/api/puppyshop", selectedSiDo, selectedSiGunGu, selectedDongEub, puppyshopRecommend, updateImage);
    }

    function loadInitialCafes() {
      const selectedSiDo = "서울특별시";
      const selectedSiGunGu = "강남구";
      const selectedDongEub = "역삼동";
      loadInitialData("/api/cafe", selectedSiDo, selectedSiGunGu, selectedDongEub, cafeRecommend, updateImage);
    }

    // 데이터 로드를 시작합니다.
    loadInitialHospitals();
    loadInitialDrugstores();
    loadInitialPuppyshops();
    loadInitialCafes();
  });
}

// Ensure the initMap function is called after the Google Maps API is loaded
if (google && google.maps && google.maps.importLibrary) {
  google.maps.importLibrary('places').then(() => {
    initMap();
  });
} else {
  console.error("Google Maps API failed to load.");
}
