이 프로젝트는, 김영한 강사님의 DB 1강 강의의 최종본이다.

해당 프로젝트를 강의 순서로 정리한다.



## JDBC

- JDBC
- SQL Mapper
- ORM
  - JPA / Hibernate



## Connection Pool

- Connection을 만들 때 사용되는 리소스가 많음
  - tcp/ip, id/pw, db inner section, connection return repeat.
- 그걸 방지하고자 커넥션 풀 개념이 생김
- DataSource
  - DB Connection 관리 인터페이스
  - 여기에 커넥션 풀 개념이 들어감
  - Hikaricp라는 라이브러리를 Spring에서 제공



## Transaction

- DB에서 하나의 작업 절차를 처리하는 과정임.
- 특징
  - ACID
    - 원자성
    - 일관성
    - 격리성
      - 4가지 정도 격리 레벨 존재
    - 지속성
  - 커밋
    - 수동
    - 자동 (트랜잭션을 시작한다.)
  - 롤백
  - DB 세션 역할
    - sql 실행
    - 트랜잭션 시작 및 종료
- DB Lock
  - 다른 작업이 하나의 같은 row 동시 접근 금지
  - 락타임아웃
  - 락 조회
    - 데이터 수정 방지 목적



## 코드 리팩토링

- 서비스는 온전히 비지니스 로직 코드만 존재 해야함.

  ### 트랜잭션 코드 삭제

  - 각 다른 기술 트랜잭션을 감싸는 인터페이스 사용

    - PlatformTransactionManager
    - 쓰레드 로컬 개념
      - 쓰레드마다 별도 저장소 있음

  - 커밋 롤백 삭제

    - TranscationTemplate

  - TranscationTemplate 의존성 삭제

    - @Transcational
    - AOP Proxy 
    - 해당 어노테이션은 새로운 Proxy 클래스를 Spring에서 생성

    #### AOP 전체 흐름

    1. 트랜잭션 시작(AOP 프록시)
    2. 트랜잭션 매니저는 데이터 소스에서 커넥션가져옴(auto commit false)
    3. 트랜잭션 동기화 매니저(트랜잭션 매니저 내부)는 해당 커넥션을 가지고 있음
    4. 데이터 접근 로직에서 트랜잭션 동기화 매니저가 가지고 있는 커넥션으로 트랜잭션 시작
    5. 트랜잭션 종료(AOP 프록시)

  ### 예외

  - 언체크 예외 사용
    - 대부분 체크예외는 복구가 불가능
  - 체크 예외가 있으면, 언체크 예외로 변경
    - 대신 예외 스택트레이스 필수임.
  - 이유는 Exception도 의존성을 받음.
    - 예시로 SqlException은 jdbc 기술임.
  - 예외 구분이 필요할 경우
    - db마다 에러코드가 다름
    - 이 때 spring 예외 추상화를 통해 해결
    - DataAcessException(스프링 예외 변환기)
      - SQLExceptionTranslator 인터페이스 사용
      - throw sqlExceptionTranslator.translate(...) 

  ### JDBC 코드 반복

  - JdbcTemplate을 통해 해결하면 됨
    - 커넥션 조회
    - 스프링 예외 처리
    - 트랜잭션 동기화
    - 쿼리 실행
    - 결과 바인딩
    - 템플릿콜백패턴(?)
    - 모두 해결가능.