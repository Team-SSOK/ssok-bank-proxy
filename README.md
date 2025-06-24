# SSOK-BANK-PROXY

LG CNS Am Inspire Camp 1기 3조 금융팀의 최종 프로젝트 **SSOK-BANK-PROXY** 대외계 백엔드(Spring Boot) 애플리케이션입니다.  
이 프로젝트는 SSOK-BANK의 송금, 출금, 보상 요청을 대신 받아 처리하는 리버스 프록시입니다. 

---

## 🧱 기술 스택

| 구성 요소           | 설명                       |
|-----------------|--------------------------|
| Spring Boot     | RESTful API 서버 구현        |
| Spring Data JPA | 데이터 접근 계층 구성             |
| MariaDB         | 개발용 임베디드 DB 또는 운영용 RDBMS |
| Lombok          | 반복 코드 자동 생성              |
| Maven           | 빌드 및 의존성 관리              |
| Kafka           | 대외 송신 내역 관리              |

---

## ⚙️ 설치 및 실행 (개발 환경 기준)

1. **소스 코드 클론**
    ```bash
    git clone https://github.com/Team-SSOK/ssok-bank-proxy.git
    cd ssok-bank
    ```
2. **환경 설정**  
   `src/main/resources/application.yml`에 DB 연결 및 포트, 기타 설정 구성.
3. **의존성 설치 및 컴파일**
    ```bash
    mvn clean install
    ```
4. **서버 실행**
    ```bash
    mvn spring-boot:run
    ```
   또는
    ```bash
    java -jar target/ssok-bank-proxy-0.0.1-SNAPSHOT.jar
    ```
---

## 🧪 테스트 및 검증

- `mvn test` 또는 IDE 내 테스트 실행
- 통합 테스트: MockMvc 활용한 REST API 검증

---

## 🚀 배포

- Dockerfile 및 CI/CD 설정 필요 (DevOps/ssok-deploy 리포지토리 참고)
- 운영 DB(MySQL 등) 및 AWS/홈서버 배포 환경 연동

---

## 👨‍👩‍👧‍👦 팀 정보

- **Team‑SSOK** – LG CNS Am Inspire Camp 1기 3조 금융팀
- **관련 레포지토리**
    - SSOK 뱅크(CoreBanking - 계정계): `ssok-bank`
    - SSOK APP 프론트엔드: `ssok-frontend`
    - 배포 자동화: `ssok-deploy`
    - 오픈뱅킹 연동: `ssok-openbanking`

---



