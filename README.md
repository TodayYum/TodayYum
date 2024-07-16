![배너](/readmeSrc/오늘얌배너.png)

# 오늘얌

> 식사 사진을 공유함으로서 메뉴 선택에 도움을 주는 SNS 서비스

- 식사 기록을 평점과 함께 기록하고 사용자들은 얌(좋아요)를 통해 공감할 수 있습니다.
- 얌 수치가 높아지면 식사 메뉴 카테고리별 오늘의 음식이 선정됩니다.
- 사용자들은 오늘의 얌 목록 뿐만 아니라 원하는 음식 종류에 대해 살펴볼 수 있습니다.

## 팀원

| <img src="https://avatars.githubusercontent.com/u/89844277?v=4" width="100" height="100" alt="김용균"/> | <img src="https://avatars.githubusercontent.com/u/108918495?v=4" width="100" height="100" alt="김봉준"/> |
| :-------------------------------------------------------------: | :--------------------------------------------------------------: |
|             [김용균](https://github.com/DeadBBall)              |              [김봉준](https://github.com/hehezune)               |
|                               BE                                |                                FE                                |

## 시스템 아키텍쳐

![아키텍쳐](/readmeSrc/시스템아키텍처.PNG)

## 기술 스택

**💻 FRONTEND**

<img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=white">
<img src="https://img.shields.io/badge/typescript-3178C6?style=for-the-badge&logo=typescript&logoColor=white">
<img src="https://img.shields.io/badge/react query-FF4154?style=for-the-badge&logo=reactquery&logoColor=white">
<img src="https://img.shields.io/badge/jotai-000000?style=for-the-badge&logoColor=white">
<img src="https://img.shields.io/badge/tailwindcss-06B6D4?style=for-the-badge&logo=tailwindcss&logoColor=white">

**💻 BACKEND**

<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"> <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"> <img src="https://img.shields.io/badge/JPA-000000?style=for-the-badge&logo=JPA&logoColor=white"> <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JWT&logoColor=white">

**🌐 CI/CD**

<img src="https://img.shields.io/badge/jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white"> <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white">

**🗄 DB**

<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white"> <img src="https://img.shields.io/badge/amazons3-569A31?style=for-the-badge&logo=amazons3&logoColor=white">

## ERD

![ERD](/readmeSrc/erd.png)

## 서비스 화면

### 회원가입 과정

![로그인](/readmeSrc/서비스-회원가입.png)

### 글 검색

![글검색](/readmeSrc/서비스-검색.png)

### 글 상세

![글상세](/readmeSrc/서비스-상세.png)

### 글 장석

![글작성](/readmeSrc/서비스-글생성.png)

### 마이페이지

![마이페이지](/readmeSrc/서비스-마이페이지.png)

## Git Convention

### 브랜치명

> 파트/develop/이슈번호

예시 : fe/develop/#88

### 커밋 메세지

> [파트] 분류 : 메세지 내용

- 분류  
  fix: 버그, 오류 해결  
  feat: 새로운 기능 구현  
  refactor: 코드 개선하는 리팩토링  
  env: 기타 환경 설정  
  test: 테스트 코드 추가  
  chore: 그 외의 일  
  docs: README나 WIKI 등 내용 추가 및 변경  
  style: 레이아웃 등 스타일  
  merge: 브랜치 병합

예시 : [FE] fix : 스크롤 오류 해결
