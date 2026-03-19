1. DB 에 User 권한 세팅 init.sql 스크립트 사용하였습니다.
    - docker compose up 하면 user 생성 및 권한 자동 부여됩니다.
2. 가입 시 자유 입출금 계좌 개설
    - data.sql에 시드 데이터로 상품 3개 중 1번째가 해당 서비스에서 이용하는 기본 자유입출금 Product라고 가정하였습니다.
    - security.yml 파일에 DEFAULT_PRODUCT_ID로 설정해놓았습니다.