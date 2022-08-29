## 프로젝트 매칭 서비스

프로젝트 모집에 어려움을 겪는 사용자를 위해 프로젝트를 같이 진행할 인원을 편리하게 모집할 수 있는 서비스입니다.



## 프로젝트 사용기술

- Spring Boot 2.7.0
- Java 11
- MariaDB 10.6.7
- Spring Data Jpa 2.7.0 
- Gradle 7.4.1
- Redis 7.0.4
- AWS EC2
- AWS S3
- AWS RDS
- Docker
- Docker Hub
- Jenkins



## 프로젝트 구조
<img src = "https://project-matching-s3.s3.ap-northeast-2.amazonaws.com/apidocs/infra.png" width="100%" height="100%">

## 프로젝트 목표

- 사용자들이 불편함 없이 편리하게 사용할 수 있도록 구현하는 것이 목표입니다.
- JPA를 이용하여 N + 1 문제 없이 구현하여 성능이 좋게 구현하는 것이 목표입니다.
- 문서화 및 테스트를 작성하여 협업에 지장이 없는 프로젝트를 구현하는 것이 목표입니다.
- 젠킨스와 도커를 이용한 CI/CD를 구현하여 자동화된 배포 및 버전관리가 가능한 프로젝트를 구현하는 것이 목표입니다.
- REST API에 적합한  API를 구현하는 것이 목표입니다.
- 유지 보수를 위해 객체지향적 설계 및 읽기 좋은 코드를 작성하도록 노력하는 것이 목표입니다.

## 프로젝트 문제 해결

- [N + 1 문제 해결 과정](https://tidy-poet-085.notion.site/N-1-cf58274f5fe944c7bceb0e86964d60e0)
- [ExceptionHandler와 ControllerAdvice를 이용한 에러 처리](https://tidy-poet-085.notion.site/ExceptionHandler-ControllerAdvice-d983f9d2b47d46ba86444f9945466b4a)
- [JENKINS와 DOCKERHUB를 이용한 CI/CD 자동화 배포 구현 과정](https://tidy-poet-085.notion.site/JENKINS-DOCKERHUB-CI-CD-197c192273cd4bc8ae4e980050330277)
- [S3를 통한 이미지 업로드 해결과정](https://tidy-poet-085.notion.site/S3-c23cc2f2a1a54d0597b5a729e60c95c7)
- [JWT 해결과정](https://tidy-poet-085.notion.site/JWT-56576768c978491c92945b10b8ec7fd6)
- [OAuth 2.0 해결과정](https://tidy-poet-085.notion.site/OAuth-2-0-225d4689fd2d47af9c12ccd88d029ea5)
- [Jasyp을 이용한 암호화](https://tidy-poet-085.notion.site/JASYPT-8219370f128249378dc0103a72c9bf52)
- [AOP를 이용한 Logging](https://tidy-poet-085.notion.site/AOP-Logging-7047e5474fea44faadb13d2aa666ac41)



## 유즈케이스

- [유즈케이스 URL](https://tidy-poet-085.notion.site/5e6e4ff5d5ba462f8da22a56ee696740)



## 프로젝트 API 명세서

- [API 명세서 URL](https://project-matching-s3.s3.ap-northeast-2.amazonaws.com/apidocs/index.html)



## DB ERD

![ERD](https://project-matching-s3.s3.ap-northeast-2.amazonaws.com/apidocs/erd.png)



## 화면 정의서(와이어 프레임)

- [와이어프레임 URL](https://www.figma.com/file/oziJXYjB3leZwpyObhXsPx/project-matching?node-id=0%3A1)