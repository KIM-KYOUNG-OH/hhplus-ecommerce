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

# 동시성 제어 방식
1. 낙관적락 vs 비관적락 
2. s-lock vs x-lock

## 낙관적락(Optimistic Lock)
- 데이터를 수정할 때 충돌이 발생하지 않는다고 가정하고 작업을 수행한 뒤, 최종적으로 충돌 여부를 확인하는 방식입니다.
- 데이터를 읽는 시점에 버전 정보를 함께 가져오고 업데이트 시점에 다른 트랜잭션에 의해 버전 정보가 바뀌었는지 검증합니다. 만악, 바뀌었다면 충돌로 간주하고 롤백합니다.
- DB Lock을 사용하지 않아서 동시 처리량이 높습니다.
- 예시 코드
```java
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int stock;

    @Version // 낙관적 락을 위한 필드
    private int version;
}
```

## 비관적락(Pessimistic Lock)
- 데이터 충돌이 자주 발생할 것으로 가정하고 트랜잭션이 읽거나 수정할 때 즉시 락을 걸어 다른 트랜잭션이 데이터에 접근하지 못하게 하는 방식입니다.
- 종류에는 s-lock과 x-lock이 있습니다.

### s-lock(공유락)  
- 공유락은 데이터를 여러 트랜잭션이 동시에 읽을 수 있도록 허용하는 락입니다.
- 읽기 작업만 가능하고 데이터 수정이 불가합니다.
- 다른 트랜잭션도 동일 데이터에 대해 공유락을 획득할 수 있지만, 베타락을 걸 수는 없습니다.  
- 예시 코드
```java
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 공유락(S-LOCK) 설정
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<Product> findByIdWithLock(Long id);
}
```

### x-lock(베타락)
- 베타락은 특정 트랜잭션만 데이터를 수정할 수 있도록 허용하는 락입니다.
- 하나의 트랙잭션이 데이터에 대한 읽기, 쓰기를 독점합니다. 
- 다른 트랜잭션은 어떠한 락(s-lock, x-lock)도 걸 수 없습니다.
- 예시 코드
```java
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 베타락(X-LOCK) 설정
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Product> findByIdWithLock(Long id);
}
```

# 동시성 문제
1. 락 충돌
2. 데드락

## 락 충돌
여러 트랜잭션이 동일한 데이터에 대해 동시에 작업하려고 할 때 발생합니다.
예를 들어, 트랜잭션 A가 데이터에 X-lock을 설정하였을 때 트랜잭션 B가 동일 데이터가 접근하려고 하면 충돌이 발생합니다.

### 낙관적 락
낙관적 락에서 충돌이 발생하면 예외 처리를 통해 충돌 감지 후 재시도합니다.
```java
try {
    productRepository.save(product);
} catch (OptimisticLockException e) {
    // 충돌 시 재시도 로직
    retrySave(product);
}
```

### 비관적 락
잠금 범위를 최소한으로 줄이고 필요한 경우 락 타임아웃을 설정합니다.
```java
@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")})
@Lock(LockModeType.PESSIMISTIC_WRITE)
Optional<Product> findByIdWithTimeout(Long id);
```

## 데드락
두 개 이상의 트랜잭션이 서로가 점유한 자원에 대한 락이 풀리길 영원히 기다리고 있는 상태를 말합니다.  
이를 방지하기 위해 락의 범위를 최소화하고 락의 타임아웃을 설정할 수 있습니다. 

---

# 인덱스를 이용한 쿼리 성능 개선
- 적용 유스케이스: 인기 상품 목록 조회
- 테스트용 표본 데이터수 : 1000만건 
### 네이티브 쿼리  
```sql
/* ProductStatisticsRepository.findListBetween */
select
    ps1_0.product_statistics_id,
    ps1_0.created_at,
    ps1_0.order_quantity,
    ps1_0.product_id,
    p1_0.product_id,
    p1_0.brand_id,
    p1_0.created_at,
    p1_0.product_name,
    po1_0.product_id,
    po1_0.product_option_id,
    po1_0.created_at,
    po1_0.option_name,
    po1_0.quantity,
    po1_0.regular_price,
    po1_0.updated_at,
    p1_0.updated_at,
    ps1_0.target_date,
    ps1_0.updated_at 
from
    product_statistics ps1_0 
join product p1_0 on p1_0.product_id=ps1_0.product_id 
join product_option po1_0 on p1_0.product_id=po1_0.product_id 
where
    ps1_0.target_date >= STR_TO_DATE(?, '%Y-%m-%d')
    and ps1_0.target_date<= STR_TO_DATE(?, '%Y-%m-%d')
```
### 인덱스 추가 DDL 쿼리
```sql
ALTER TABLE product_option ADD INDEX index_product_id(product_id);
ALTER TABLE product_statistics ADD INDEX index_target_date(target_date);
```

## 성능 비교
### ASIS(인덱스 추가 전)
- 쿼리 실행 시간: 8649 ms  
- 실행 계획:
```sql
id|select_type|table|partitions|type  |possible_keys|key    |key_len|ref                    |rows   |filtered|Extra                                     |
--+-----------+-----+----------+------+-------------+-------+-------+-----------------------+-------+--------+------------------------------------------+
 1|SIMPLE     |po1_0|          |ALL   |             |       |       |                       |   1508|   100.0|                                          |
 1|SIMPLE     |p1_0 |          |eq_ref|PRIMARY      |PRIMARY|8      |hhplus.po1_0.product_id|      1|   100.0|                                          |
 1|SIMPLE     |ps1_0|          |ALL   |             |       |       |                       |9725520|    1.11|Using where; Using join buffer (hash join)|
```

### TOBE(인덱스 추가 후)
- 쿼리 실행 시간: 5836 ms  
- 실행 계획:
```sql
id|select_type|table|partitions|type  |possible_keys    |key              |key_len|ref                    |rows  |filtered|Extra                |
--+-----------+-----+----------+------+-----------------+-----------------+-------+-----------------------+------+--------+---------------------+
 1|SIMPLE     |ps1_0|          |range |index_target_date|index_target_date|3      |                       |160040|   100.0|Using index condition|
 1|SIMPLE     |p1_0 |          |eq_ref|PRIMARY          |PRIMARY          |8      |hhplus.ps1_0.product_id|     1|   100.0|                     |
 1|SIMPLE     |po1_0|          |ref   |index_product_id |index_product_id |8      |hhplus.ps1_0.product_id|     1|   100.0|                     |
```

## 결과 분석
- 인덱스 추가를 통해 전체 스캔 범위를 1,000만 건에서 16만 건으로 대폭 감소
- 쿼리 실행 시간이 8.5초에서 5.8초로 단축 (약 29.41% 성능 개선)
- product_statistics 테이블에서 target_date 컬럼을 기준으로 range scan을 수행하여 불필요한 Full Table Scan을 방지
- product_option 테이블에서 product_id를 ref 방식으로 조회하여 조인 성능 향상

---

# MSA 환경에서의 트랜잭션 처리 전략

서비스의 규모가 확장됨에 따라 MSA(Microservices Architecture) 형태로 도메인별 배포 단위를 분리해야 합니다.  
하지만 기존의 단일 데이터베이스 트랜잭션이 불가능해지고, 분산 환경에서의 트랜잭션 일관성을 유지하는 것이 핵심 과제가 됩니다.  
이를 해결하기 위해 대표적인 트랜잭션 관리 기법으로 **2PC, SAGA, TCC, 이벤트 기반 + Outbox 패턴**을 고려할 수 있습니다.

## 분산 트랜잭션 처리 전략

### 1. 2PC (Two-Phase Commit, 2단계 커밋)
중앙 조정자(Coordinator)가 모든 마이크로서비스의 트랜잭션을 2단계(Prepare, Commit)로 처리하는 기법
![Image](https://github.com/user-attachments/assets/26d641ad-cc10-472a-a52d-41a7e29a18d3)

**동작 방식:**
1. **Prepare 단계:** 모든 서비스에 트랜잭션 수행 가능 여부를 확인 (YES/NO 응답)  
2. **Commit 단계:** 모든 서비스가 YES를 응답하면 커밋, 하나라도 NO면 롤백

**장점:**
- 분산 환경에서도 하나의 트랜잭션 처럼 원자성 보장

**단점:**
- 성능 저하 (전체 트랜잭션 완료까지 리소스 잠금)
- 단일 장애점 (Coordinator 장애 시 전체 트랜잭션 실패)
- 확장성이 낮음 (MSA 환경에서 비효율적)

**적용 시나리오:**
- **핵심 금융 서비스** (은행 송금, 결제 시스템)
- **데이터 정합성이 가장 중요한 경우** (주문 → 결제 → 배송 순으로 철저히 보장해야 할 때)

### 2. SAGA 패턴
긴 프로세스를 여러 개의 로컬 트랜잭션으로 나누고, 실패 시 보상 트랜잭션을 실행하는 방식
![Image](https://github.com/user-attachments/assets/2aef3e88-99fb-47df-a92a-b2a49b201f63)

**동작 방식:**
1. **Choreography (오케스트레이션 없이 이벤트 기반으로 처리)**
  - 서비스 간 이벤트를 발행/구독하여 트랜잭션 수행
2. **Orchestration (오케스트레이터가 직접 트랜잭션을 조정)**
  - 중앙 컨트롤러(Service Coordinator)가 각 서비스의 트랜잭션을 순차적으로 실행

**장점:**
- 비동기 처리로 성능 최적화 가능
- 확장성이 뛰어나고 서비스 간 결합도를 줄일 수 있음

**단점:**
- 보상 트랜잭션이 필요하여 설계가 복잡해짐
- 이벤트 손실 시 트랜잭션 불일치 가능성이 존재

**적용 시나리오:**
- 전자상거래 주문 처리 (결제 → 배송 → 알림)
- 사용자 가입 프로세스 (이메일 확인 → 계정 생성 → 권한 부여)

### 3. TCC (Try-Confirm-Cancel) 패턴
![Image](https://github.com/user-attachments/assets/9380eeeb-13cc-4b34-bbcf-20722e354d5c)
2PC의 개념을 확장하여 트랜잭션을 Try(예약), Confirm(확정), Cancel(취소) 세 단계로 나누는 방식

**동작 방식:**
1. **Try:** 모든 서비스가 예약 상태를 생성 (리소스 확보)
2. **Confirm:** 모든 서비스가 예약한 리소스를 확정
3. **Cancel:** 실패 시 예약된 리소스를 취소

**장점:**
- 데이터 정합성이 뛰어나면서도 2PC보다 성능 부담이 적음
- 트랜잭션을 개별적으로 롤백 가능

**단점:**
- 서비스마다 Try/Confirm/Cancel 로직을 구현해야 함
- 임시 리소스 잠금(locking)으로 인해 동시성 처리에 부담 발생 가능

**적용 시나리오:**
- **항공권, 호텔 예약 시스템** (좌석/객실을 일시적으로 확보하고 확정 여부에 따라 처리)
- **재고 관리 시스템** (상품 구매 요청 시 재고를 선점한 후 확정 or 취소 처리)

### 4. 이벤트 기반 + Outbox 패턴
비동기 이벤트 기반 트랜잭션 처리 + Outbox 테이블을 활용한 이중 저장(dual write) 문제 해결
![Image](https://github.com/user-attachments/assets/296da333-dd86-4136-aafb-318667d3c3d5)

**동작 방식:**
1. 서비스 A에서 트랜잭션과 함께 Outbox 테이블에 이벤트를 저장
2. Outbox 테이블에서 메시지 브로커(Kafka 등)로 이벤트 발행
3. 서비스 B, C는 해당 이벤트를 구독하고 비동기 처리

**장점:**
- 높은 확장성 및 성능 (완전 비동기)
- 장애 발생 시에도 Outbox 테이블을 기반으로 재처리 가능

**단점:**
- 데이터 정합성을 보장하려면 Outbox 테이블과 메시지 큐 동기화 필요
- **중복 처리 방지(Idempotency) 전략** 필요

**적용 시나리오:**
- **고객 알림 서비스** (주문 생성 시 이메일/SMS 알림 발송)
- **분산 로그 처리** (여러 마이크로서비스의 이벤트 기록 및 분석)

## 트랜잭션 처리 전략 비교
| 패턴 | 일관성(강함/약함) | 성능 | 확장성 | 실패 복구 |
|------|--------------|------|------|----------|
| **2PC** | 강한 일관성 | 낮음 | 낮음 | 자동 롤백 |
| **SAGA** | 약한 일관성 | 높음 | 높음 | 보상 트랜잭션 필요 |
| **TCC** | 강한 일관성 | 중간 | 중간 | 예약 취소 필요 |
| **이벤트+Outbox** | 약한 일관성 | 매우 높음 | 매우 높음 | 재처리 가능 |

## 결론
- **데이터 정합성이 가장 중요한 경우 → 2PC, TCC 적용**
- **고성능 및 확장성이 필요한 경우 → SAGA, 이벤트 기반 + Outbox 적용**
- **비동기 이벤트 중심의 처리가 가능하다면 → 이벤트 기반 아키텍처 고려**


