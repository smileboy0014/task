# 멀티모듈 테스트 프로젝트

멀티모듈을 구성하여 API 호출 및 JPA 와의 연동을 목표로 합니다.

- https://github.com/smileboy0014/task/blob/master/module-search/build/libs/module-search-0.0.1-SNAPSHOT.jar 이 링크를 통해 jar 를 다운받아 볼 수 있습니다.

## Getting Started

스프링 부트를 사용해 프로젝트를 진행하다 보면 여러 기능 별로 서버가 필요할 수 있습니다. 예를 들어 API 서버, 파일 서버, batch 서버 등. API 서버 내에서도 호출하는 영역이나 기능에 따라 분류될 수 있다.
하나의 서버 크기가 너무 커지면 기능간에 의존성도 커지고, 개발에 어려움을 겪을 수 밖에 없습니다. 그래서 이번에 한번 멀티모듈을 구성해보기로 하였습니다.
이 프로젝트에서는 예시로 총 3가지 기능을 테스트해 보실 수 있습니다.
- 카카오 블로그 검색 API, 인기 검색어 TOP10, 인기 도메인 TOP10

### Prerequisites

- IDE(vscode, intelliJ 등)

- postman
  - API 테스트를 위해서 설치해두면 좋습니다.
  - https://www.postman.com/ 공식사이트에서 쉽게 다운 받아 볼 수 있습니다.
  

### Installing


먼저 java 11 이상 있는지 확인합니다.(mac이나 linux 가 아닌 경우에는 확인을 어떻게 하는지 모르겠네요 ㅠㅠ)

```
java --version
```

만약 없는 경우 brew install 을 통해서 쉽게 다운 받아 보실 수 있습니다. 방법은 다음과 같습니다.
#### 1. homebres 설치 및 업데이트 
```
brew update
```

#### 2. adoptopenjdk/openjdk 추가하기
```
brew tap adoptopenjdk/openjdk
```

#### 3. 설치 가능한 jdk 목록 조회
```
brew search jdk
```

#### 4. 11 버전 자바 설치
```
brew install --cask adoptopenjdk11
```
----

### Project configuration

제가 구성한 프로젝트는 다음과 같습니다.

![image](https://user-images.githubusercontent.com/58767777/226917385-8ad5ea01-cabd-4dca-a3c4-37b8248e0969.png)

- Root 모듈의 settings.gradle에서 현재 Root 프로젝트가 하위 모듈로 어떤 프로젝트를 관리하는지를 명시해줍니다.
위 코드는 gradle-multi-module 프로젝트가 'module-core', 'module-search' 프로젝트를 하위 프로젝트로 관리하겠다는 의미입니다.

- module-core
  - 여기서는 공통 코드나 다른 모듈에 상속될 코드들을 관리합니다.

- module-search
  - 실제로 API 서버를 구성하기 위한 모듈입니다.


## Feature

### 1. h2 db(인메모리 DB) 사용

테스트용으로 검색 결과를 저장하여 검색어 순위를 보여주기 위해 따로 db를 설치하지 않아도 쓸 수 있는 h2 db를 사용하였습니다.
다음과 같이 설정하면 h2 db 를 인메모리로 사용해 볼 수 있습니다.

- application.yml 설정

```
spring:
  h2:
    console:
      enabled: true
  datasource: # db 관련 설정
    url: jdbc:h2:mem:testdb;MODE=MySQL;
    driver-class-name: org.h2.Driver
    username: sa
    password:
```

- build.gradle에 관련 dependency 추가

```
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-webflux'
}
```

### 2. 동시성 향상을 위해 스프링 캐시 사용

캐시의 경우 반복적으로 동일한 결과를 변환하는 경우 용이합니다. 인기 검색어 TOP10 이나 인기 도메인 TOP 10의 경우, update 없이 단순한 조회만 하기 때문에 이에 해당한다고 보았습니다.
사용방법은 다음과 같습니다.

- build.gradle에 dependency 추가

```
 dependencies {
        ...
        //동시성 향상을 위한 캐시 사용
        implementation 'org.springframework.boot:spring-boot-starter-cache'
        ...
    }
```

- main class에 EnableCaching annotation 추가 

![image](https://user-images.githubusercontent.com/58767777/226929201-13bf571f-fe9c-4393-b34f-35dafe94f281.png)

- cache를 사용하고 싶은 곳에 Cacheable annotation 추가

![image](https://user-images.githubusercontent.com/58767777/226929452-44cbd6f2-f821-4065-a815-5059f9409cae.png)

- 이와 같이 캐쉬에서 데이터를 가져오는 것을 확인해 볼 수 있습니다.

![image](https://user-images.githubusercontent.com/58767777/226930270-944cc29a-5f36-4cf1-a176-371dcc7f4613.png)

** 추후 모듈이 더 늘어나 local chche로 공유가 어려울 경우, global chache인 redis cache를 사용하는 것도 좋을 것 같습니다. **


## Functional Specification

이 프로젝트에서는 다음과 같은 기능들을 테스트 해보실 수 있습니다.
### 1. 카카오 블로그 조회

![image](https://user-images.githubusercontent.com/58767777/226920592-f71a1908-9904-444c-8fb6-6be4ef7bbbd0.png)

https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-blog 호출하는 카카오 블로그 검색 API 정보입니다.
들어가서 확인해보시면 아래와 똑같이 Request parameter 정보를 확인하여 API를 호출하면 같은 결과를 받아 볼 수 있습니다.

![image](https://user-images.githubusercontent.com/58767777/226921238-bbf9e8d7-4e5c-451c-a310-3473656990e2.png)

- 호출 endPoint
  - http://localhost:8080/search/blog

- 호출 결과

![image](https://user-images.githubusercontent.com/58767777/226921450-9c288b32-65ee-415e-9887-aa4291f5d32a.png)

- 만약 카카오 블로그 검색 API가 에러가 발생한다면 네이버 블로그 검색 API 결과를 대신 받아 볼 수도 있습니다. 
https://developers.naver.com/docs/serviceapi/search/blog/blog.md 네이버 검색 API 정보입니다.
![image](https://user-images.githubusercontent.com/58767777/226922609-32882649-32de-4a94-b6ff-383bca622b97.png)

같은 검색 조건이지만 결과값이 다른 것을 확인해 볼 수 있습니다.

### 2. 인기 검색어 Top 10 조회

![image](https://user-images.githubusercontent.com/58767777/226922900-48101b19-ad86-49fd-868d-1e3c5ac49bd9.png)

- 만약, 검색 데이터가 하나도 없을 경우 에러가 발생합니다.
![image](https://user-images.githubusercontent.com/58767777/226923186-6cd760e5-cde0-4c33-bd00-5e5d990317d7.png)

- 호출 endPoint
  - http://localhost:8080/popular/keyword

- 검색 데이터가 있을 경우 다음과 같은 결과값을 리턴해 줍니다.
![image](https://user-images.githubusercontent.com/58767777/226923499-dc52a9cc-e821-4ab4-9826-9d9b66863c92.png)

### 3. 인기 도메인 Top 10 조회

![image](https://user-images.githubusercontent.com/58767777/226924130-d281904a-6335-410a-8882-a0ce3a72b206.png)

- 만약, 검색 데이터가 하나도 없을 경우 위와 같은 에러가 발생합니다.
![image](https://user-images.githubusercontent.com/58767777/226924426-632c39e8-2863-4796-b166-111d5df56cc0.png)

- 호출 endPoint
  - http://localhost:8080/popular/domain

- 검색 데이터가 있을 경우 다음과 같은 결과값을 리턴해 줍니다.
![image](https://user-images.githubusercontent.com/58767777/226924796-2683b7f1-f055-4507-ad26-9c0206bb0e75.png)



