# hhplus-ecommerce

### Running Docker Containers

`local` profile 로 실행하기 위하여 인프라가 설정되어 있는 Docker 컨테이너를 실행해주셔야 합니다.

```bash
docker-compose up -d
```

# Milestone
1MD = 3MH
- 3주차 설계 & Mock API 작성
  - 설계 문서 작성(3MD)
    - 시퀀스 다이어그램
    - 플로우차트
    - ERD
    - API 명세서
  - Mock API(0.5MD)
- 4주차 기본 기능 구현
  - API 기본 구현(4MD)
    - 잔액 충전/조회 API
    - 상품 조회 API
    - 선착순 쿠폰 발급/목록 조회 API
    - 주문/결제 API
    - 상위 상품 조회 API
    - 단위 테스트 작성
  - 통합 테스트 구현(1MD)
    - 동시성 테스트
- 5주차 리팩터링 & 고도화
  - 코드 리팩터링(1MD)
  - 조회 성능 개선(2MD)
    - 캐싱
    - 비정규화
  - 동시성 이슈 개선(2MD)
    - 다중 서버 환경 동시성 제어용 인프라 분리(메시지큐, in memory db) 

# 시퀀스 다이어그램
![잔액조회API](https://github.com/user-attachments/assets/1b14f424-4ed1-4361-93c6-3ace02e68689)    
![잔액충전API](https://github.com/user-attachments/assets/ff36fa68-b682-484e-adbd-dac2a60b56c1)    
![상품조회API](https://github.com/user-attachments/assets/b3dc714b-a34d-4bb7-a0e0-cf203eaacd3f)    
![상위상품조회API](https://github.com/user-attachments/assets/812fe201-f030-4257-86be-0d4598198aae)    
![상품 통계 배치](https://github.com/user-attachments/assets/3b6cd2f3-e8ea-4780-82a2-3df31331fbbe)    
![보유쿠폰목록조회API](https://github.com/user-attachments/assets/2c8dd6d1-dd57-44d4-9e85-1712dc78a1f8)    
![쿠폰발급API](https://github.com/user-attachments/assets/33a53535-2ab0-468c-b3b9-f10014ef1d25)    
![주문 API](https://github.com/user-attachments/assets/0a1f414b-dac7-4e0d-8b2a-c5325d64baf2)    

# 플로우차트
![잔액조회 drawio](https://github.com/user-attachments/assets/8fd3f649-cb56-4264-acc9-ecd43a08ecd3)  
![잔액충전 drawio](https://github.com/user-attachments/assets/2fe960c6-8595-4f86-bb85-952333ea5d7f)  
![상품조회 drawio](https://github.com/user-attachments/assets/ad89a65d-3f04-49f8-bf17-c4fc379c8c50)  
![상위상품조회 drawio](https://github.com/user-attachments/assets/b180a52f-165d-40d6-9824-c5f829d2af85)  
![보유쿠폰조회 drawio](https://github.com/user-attachments/assets/0ea8387b-67d5-4581-9dd5-b439d2977227)  
![쿠폰발급 drawio](https://github.com/user-attachments/assets/2c8ad779-aa6b-4ab5-992a-089b29c89aa0)  
![주문 drawio](https://github.com/user-attachments/assets/d5204615-19bd-4242-855b-c960b189e7ad)  

# ERD
![항해 E-commerce ERD drawio (3)](https://github.com/user-attachments/assets/15722a63-fecf-42fb-bd5e-c332184053e4)  

# API 명세서

![스크린샷 2025-01-06 040507](https://github.com/user-attachments/assets/2b80f47a-f95f-4e3f-9914-c9cd02a057b5)  
![스크린샷 2025-01-06 040521](https://github.com/user-attachments/assets/5c068b6e-54e9-4d21-a838-93e4c3da50f4)  
![스크린샷 2025-01-06 040541](https://github.com/user-attachments/assets/1f5a07a5-deb0-4d99-85c7-a520c863c5d2)  
![스크린샷 2025-01-06 040551](https://github.com/user-attachments/assets/96680a32-74a7-4d7d-bb92-2328ae84f382)  
![스크린샷 2025-01-06 040613](https://github.com/user-attachments/assets/6c05cddc-61e7-4545-af00-7299e6440ec7)  
![스크린샷 2025-01-06 040604](https://github.com/user-attachments/assets/614a016a-94b4-4134-8569-28c474853938)  
![스크린샷 2025-01-06 040623](https://github.com/user-attachments/assets/d7c2abd4-6ba3-467e-95db-821ae9fcab48)  
![스크린샷 2025-01-06 040634](https://github.com/user-attachments/assets/834b6423-59eb-42f5-b9ce-3931816fc302)  

