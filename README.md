# 👣함께하개

![Group 1009748](https://github.com/jeeseulah/DOGETHER/assets/165135312/187e6ee8-1c47-47a6-b42d-91f3b506faf1)

<h3>반려동물 동반 문화시설 "함께하개"</h3>
함께하개는 반려동물과 함께할 수 있는 다양한 문화시설을 제공하고,<br />
이를 통해 사람들이 반려동물과 함께 시간을 보내는 것을 즐길 수 있도록 돕는 것을 목표로 합니다.<br />
이를 위해, 우리는 반려동물이 동반 가능한 카페, 병원, 약국 등 다양한 시설에 대한 정보를 제공하고 있습니다.<br /><br/>
공공데이터 포털 반려동물 동반가능 문화시설의 Open API를 바탕으로 만들었습니다
<br/><br/>

> 🗝️ **함께하개 배포 링크 및 테스트 계정**

👉 [함께하개 시작하기](https://m.site.naver.com/1oR8r)

```md
ID: test@gmail.com
PW: 1234
```

> 🗝️ **함께하개 접속 QR**

![1oR8r](https://github.com/jeeseulah/DOGETHER/assets/165135312/fdcb2e6a-bc7d-47db-826b-f328b8339bec)

<br/>

## 0. 👣팀원 소개 & 역할 분담

```
안녕하세요, 저희는 2조 퍼피코더스👣입니다 !
"퍼피(Puppy)"는 영어로 강아지를 뜻하며, "코더스(Coders)"는 개발자를 의미합니다.
"강아지 코더들"을 의미하는 뜻으로 "함께하개" 주제와 맞는 팀명을 짓게 되었습니다 :)
```

![Slide 16_9 - 183](https://github.com/jeeseulah/DOGETHER/assets/165135312/777d6146-86b4-488e-9f8d-1684b1d91fde)

## 0-1. 📅 팀프로젝트 진행 기간

- 2024.05.13 ~ 2024.06.07<br/><br/>
  ![Slide 16_9 - 202](https://github.com/jeeseulah/DOGETHER/assets/165135312/71024d1a-d45e-4ef1-ad3a-28540b6ef8cc)

## 0-2. ⚙️ 개발 환경 및 기술 스택

![Slide 16_9 - 201](https://github.com/jeeseulah/DOGETHER/assets/165135312/a4ee30d9-ac11-4e6d-b8e3-20e3df45c887)

## 0-3. ⚙️ 프로젝트 중에는..

- 진행 상황을 공유하기 위해 Notion을 사용했어요
- 코드는 Project파일을 압축하여 update내용과 함께 공유했어요.
- 회의는 간략하게 30분 이내로 마치도록 해요
- 👉 [기능 명세 정리 바로가기](https://www.notion.so/1f96dcbf76b444c1bb1f88b5569223ec)

  <br/><br/>

## 0-4. ✴️사용한 외부 API

![Slide 16_9 - 207](https://github.com/jeeseulah/DOGETHER/assets/165135312/ba3f5062-1479-486d-bcc4-a29e4962c09a)

## 0-5. ✴️클래스다이어그램

![Slide 16_9 - 199](https://github.com/jeeseulah/DOGETHER/assets/165135312/493b8b50-90f9-4abd-969f-a78a79a907a7)

## 0-6. ✴️와이어프레임 작성 (피그마)

![와이어프레임](https://github.com/jeeseulah/DOGETHER/assets/165135312/774456c1-a07e-4e3d-95eb-12e44b09098e)

- 피그마를 이용해 와이어프레임을 제작했습니다.
- 반응형 페이지를 위해 lg, md, sm 사이즈로 제작했습니다.
- 👉[피그마링크](<https://www.figma.com/design/pQOygJkjMHU3DFu6aJynLU/%EC%9D%B4%EC%A0%A0%ED%8C%80%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-(%ED%95%A8%EA%BB%98%ED%95%98%EA%B0%9C_%EC%A7%80%EC%8A%AC%EC%95%84)?node-id=0-1&t=YkrZbeHlXC3DLXm7-0>)

## 0-7. 📂프로젝트 폴더 구조

```
👣 Dogether
│
├─📂java
│  └─📂com
│      └─📂example
│          └─📂dogether
│              │  CommonUtil.java
│              │  DogetherApplication.java
│              ├─📂controller
│              ├─📂domain
│              │  │  PetDB.java   ────────────────────── 공공데이터 포털 반려동반 domain
│              │  ├─📂admin
│              │  └─📂member
│              ├─📂dto
│              ├─📂repository
│              └─📂service
└─📂resources
    │  application.yml
    ├─📂static
    │  ├─📂css
    │  ├─📂images
    │  ├─📂javascript
    │  └─📂upload   ──────────────────────────────────── pet image upload 폴더
    └─📂templates
		│  index.html   ──────────────────────────────── 메인 페이지
        ├─📂admin   ──────────────────────────────────── 관리자 페이지 HTML파일
        ├─📂cafe   ───────────────────────────────────── 반려동반 카페 관련 HTML파일
        ├─📂community
        │  ├─📂board   ───────────────────────────────── 게시판 관련 HTML파일
        │  │      board_create.html
        │  │      board_detail.html
        │  │      board_list.html
        │  │
        │  ├─📂eventPage   ───────────────────────────── 이벤트페이지 관련 HTML파일
        │  │      event_list.html
        │  │
        │  ├─📂notice   ──────────────────────────────── 공지사항 관련 HTML파일
        │  │      notice_create.html
        │  │      notice_detail.html
        │  │      notice_list.html
        │  │
        │  └─📂question   ────────────────────────────── Q&A 관련 HTML파일
        │          question_create.html
        │          question_detail.html
        │          question_list.html
        │          question_pwd.html
        ├─📂diary   ──────────────────────────────────── My Pet Diary HTML파일
        ├─📂fragments   ──────────────────────────────── 커뮤니티 페이지에서 사용하는 공통 fragments
        ├─📂hospital   ───────────────────────────────── 반려동반 병원 관련 HTML파일
        ├─📂layout   ─────────────────────────────────── 공통 layout
        ├─📂puppyshop   ──────────────────────────────── 반려동반 용품점 관련 HTML파일
        └─📂user   ───────────────────────────────────── 로그인, 회원가입, 내정보 관련  HTML파일

```

<br/>

## 1. 🤗 주요 기능 소개

### 1) 반응형 웹 페이지

![반응형](https://github.com/jeeseulah/DOGETHER/assets/165135312/e2ce1afe-17c8-4fee-85bf-38bc636f4a6d)

- CINEPEDIA는 반응형 웹 페이지로 제작 되어 어떤 디바이스를 사용하든지, 화면에 맞춰 자동으로 최적화 되어 있습니다.
- 데스크톱 컴퓨터, 테블릿, 스마트폰 등 다양한 환경에서 이용가능 합니다.

### 2) Bootstrap 사용

- 반응형 웹 페이지를 만들기 위해 Bootstrap을 사용했습니다.
- 개발 시간을 단축하고, 일관된 디자인을 유지할 수 있도록 도와 주었습니다.

### 3) 홈

| 시작 화면                                                                                          | 내정보 페이지                                                                                          |
| -------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------ |
| ![홈](https://github.com/jeeseulah/DOGETHER/assets/165135312/9bc0b3da-2a9b-4b1a-8a46-5ef04f530855) | ![내정보](https://github.com/jeeseulah/DOGETHER/assets/165135312/33c7fd4f-dac2-4a4f-88c8-4ffa65c0dc27) |

| 회원가입 페이지                                                                                          | 로그인 페이지                                                                                          |
| -------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------ |
| ![회원가입](https://github.com/jeeseulah/DOGETHER/assets/165135312/8a7a9842-c7f4-4b77-8347-ee75b6577259) | ![로그인](https://github.com/jeeseulah/DOGETHER/assets/165135312/0f853ed6-89b4-4eeb-8d6a-bafa7e451c16) |

### 4) 업체 정보 확인

| 지역으로 검색                                                                                                | 업체 정보 확인                                                                                           |
| ------------------------------------------------------------------------------------------------------------ | -------------------------------------------------------------------------------------------------------- |
| ![병원지역검색](https://github.com/jeeseulah/DOGETHER/assets/165135312/975fbaac-a940-4a21-9a99-08e7ba856ec4) | ![병원정보](https://github.com/jeeseulah/DOGETHER/assets/165135312/345d9359-4145-4f22-bcac-46ab3f224494) |

### 5) 다이어리

| 다이어리 페이지                                                                                          | 펫 프로필 등록 페이지                                                                                    |
| -------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------- |
| ![다이어리](https://github.com/jeeseulah/DOGETHER/assets/165135312/ae41647e-4fda-49af-a696-e0a28b088208) | ![펫프로필](https://github.com/jeeseulah/DOGETHER/assets/165135312/2d1830f9-0fbb-4932-aef1-428b9583c6ea) |

### 6) 커뮤니티

| 공지사항 페이지                                                                                          | 공지사항 상세 페이지                                                                                         |
| -------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------ |
| ![공지사항](https://github.com/jeeseulah/DOGETHER/assets/165135312/776e70d6-0d89-433f-85fe-95a2958eeba5) | ![공지사항상세](https://github.com/jeeseulah/DOGETHER/assets/165135312/682aa3b5-846b-4a49-99c6-be57a9fcf087) |

| 게시판 페이지                                                                                          | Q&A 페이지                                                                                          |
| ------------------------------------------------------------------------------------------------------ | --------------------------------------------------------------------------------------------------- |
| ![게시판](https://github.com/jeeseulah/DOGETHER/assets/165135312/42236e7f-4498-4ee5-a8ac-ec629272849d) | ![Q A](https://github.com/jeeseulah/DOGETHER/assets/165135312/e398fca2-0f68-4359-a546-803eb375baff) |

| 게시판 글작성 페이지                                                                                         | Q&A 글작성 페이지                                                                                         |
| ------------------------------------------------------------------------------------------------------------ | --------------------------------------------------------------------------------------------------------- |
| ![게시판글작성](https://github.com/jeeseulah/DOGETHER/assets/165135312/024120f0-44de-4ad6-8cab-f4d9ad1e1990) | ![Q A글등록](https://github.com/jeeseulah/DOGETHER/assets/165135312/7274928f-7c1f-456a-a6a4-5746b4f145e4) |

| Q&A 비밀번호입력 페이지                                                                                     | 이벤트 페이지                                                                                          |
| ----------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------ |
| ![Q A비밀번호](https://github.com/jeeseulah/DOGETHER/assets/165135312/c06f218c-312e-4877-be01-8ab4b91c5291) | ![이벤트](https://github.com/jeeseulah/DOGETHER/assets/165135312/87f17678-2ac0-4e40-acfc-dfafc7f0059c) |

## 2. ✴️ 핵심 코드

<details>
    <summary><b>2-(1) 주어진 API에서 데이터 필터링하기</b></summary>
```json

````

```jsx

````

</details>

## 3. ✴️ 보완해야할 부분

- 페이지 이동 시 nav bar 항상 표시되도록 변경합니다.
- 메인페이지에서 업체이미지가 없을 경우 기본이미지가 자동으로 표시되도록 수정합니다.
- 지역 검색 시 시/군/구를 변경하더라도 검색이 가능하도록 전체 지역 검색 기능을 구현합니다.
- 지도에 한번 표시된 지점이 자동으로 사라지도록 조치합니다.
- 다이어리에는 수정 기능이 추가되어 있지 않습니다.
- 다이어리를 작성한 시간을 확인할 수 없도록 설정합니다.
- 펫을 등록한 후에도 수정할 수 있는 기능을 추가합니다.
- 등록한 펫으로 다이어리를 작성할 수 있도록 설정합니다.
- 캘린더 기능을 추가하여 건강 검진일을 손쉽게 추가할 수 있도록 구현합니다.

## 4. ✴️ 느낀점

<!-- Top Button -->
<p style='background: black; width: 32px; height: 32px; border-radius: 50%; display: flex; justify-content: center; align-items: center; margin-left: auto;'><a href="#top" style='color: white; '>▲</a></p>

<br />
