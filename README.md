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

# 캐시란
캐시(Cache)란 자주 사용하는 데이터나 연산 결과를 임시로 저장하여, 이후에 동일한 요청이 발생할 때 더 빠르게 접근할 수 있도록 도와주는 저장소 또는 기술입니다.  
예를 들면, DNS, CDN, 데이터베이스 캐시 등이 있습니다.

### application level 캐시
분산 서버 환경에서 각 인스턴스마다 별도로 할당된 캐시입니다.  
네트워크 비용이 발생하지 않아 빠르고 비용이 적다는 장점이 있습니다.  
다만, 메모리를 애플리케이션과 공유하기 때문에 메모리 부족으로 인해 OOME를 유발할 수 있습니다.  
또한 분산 환경에서 어떤 서버로 라우팅되냐에 따라 데이터 불일치 문제가 발생할 수 있습니다.

### external level 캐시
별도의 서버에 할당된 캐시입니다.
분산 환경에서도 데이터 정합성을 보장할 수 있습니다.  
HA 전략을 통해 고가용성을 보장할 수 있습니다.  
하지만 네트워크 통신을 통해 캐시 서비스와 통신해야하므로 네트워크 비용이 발생합니다.
![Image](https://github.com/user-attachments/assets/a244b563-5a12-4540-9676-7f64a9b88e02)

# 캐시 읽기 전략

## Look Aside 패턴
데이터를 찾을 때 우선 캐시에 저장되어있는지 확인하고 만일 캐시에 없으면 DB에서 조회하는 전략입니다.  
가장 일반적으로 사용하는 캐시 읽기 전략입니다.  
반복 읽기가 많은 경우에 적합합니다.  
캐시가 크래쉬되면 순간적으로 DB로 부하가 몰릴 수 있는 캐시 스탬피드(Cache Stampede) 현상이 발생할 수 있습니다.  

## Read Through 패턴
캐시에서만 데이터를 읽어오는 전략입니다.  
데이터 동기화(Cache Warming)를 전적으로 라이브러리 또는 캐시 제공자에게 위임하는 방식입니다.  
캐시와 DB간 동기화가 항상 이루어져 정합성은 높으나 조회 성능이 낮습니다.  
조회를 전적으로 redis에 의존하므로 캐시가 크래쉬되면 서비스 이용이 불가합니다.  

# 캐시 쓰기 전략

## Write Back 패턴
DB에 반영할 작업들을 바로 쿼리하지 않고, 캐시에 모아놨다가 일정 주기 배치 작업을 통해 DB에 반영하는 전략입니다.  
캐시가 일종의 Queue 역할을 겸하게 됩니다.  
캐시가 크래쉬되면 데이터 유실이 발생할 수 있습니다.  

## Write Through 패턴
데이터를 캐시에 먼저 저장한 다음 바로 DB에 반영하는 전략입니다.  
일관성을 보장할 수 있으나, 매 요청마다 두번의 Write 요청이 발생하므로 빈번한 쓰기 요청이 발생하는 서비스에는 성능 이슈가 발생할 수 있습니다.  

## Write Around 패턴
모든 데이터를 캐시가 아닌 DB에 저장하는 전략입니다.  
Cache Miss가 발생하는 경우에만 DB와 캐시에도 데이터를 저장합니다.  

# 캐시 읽기 + 쓰기 전략 조합

## Look Aside + Write Around
가장 일반적으로 자주 쓰이는 조합입니다.  

## Read Through + Write Around
항상 DB에 쓰고, 항상 동기화된 캐시를 읽으므로 데이터 정합성이 중요한 서비스에 적합합니다.  

## Read Through + Write Through
쓰기 요청시 캐시 먼저 저장하고나서 DB에 반영합니다.  

# 캐시 스탬피드 현상
Key가 만료되는 순간 여러 요청들에서 해당 key를 보고 있었다면 모든 요청들이 같은 Key로 DB에서 데이터를 찾게되는 Duplicate Read가 발생합니다.  
순간적으로 불필요한 작업이 굉장히 늘어나 장애로 이어질 수 있습니다.

# API 성능 및 동시성 문제 개선을 위한 Redis 적용 방안

## 1. 상품조회 API 캐싱 전략

### 문제점:
상품조회 API는 자주 호출되며, 제품 정보가 자주 변하지 않는 경우가 많습니다.  
데이터베이스에서 상품 정보를 조회하는 데 시간이 걸리고, 높은 트래픽에서 DB 부하가 커질 수 있습니다.

### 해결 방안: 
상품조회 API에 Redis 캐싱을 적용하여 성능을 개선할 수 있습니다.  
캐싱을 통해 반복되는 상품 조회에 대해 데이터베이스의 부하를 줄이고 응답 속도를 높일 수 있습니다.

### 주요 고려 사항:
- 캐시 키 구성: 각 상품에 대한 고유한 키를 사용합니다.  
예를 들어, 상품 ID를 기반으로 캐시 키를 설정 (product:{product_id}).  
- TTL 설정: 상품 정보는 자주 변경되지 않으므로, 캐시의 TTL(Time to Live)을 일정 시간 동안 설정하여, 만료된 캐시는 다시 DB에서 조회하도록 할 수 있습니다.  
예를 들어, 1시간 TTL 설정.  
- 캐시 업데이트: 상품 정보가 갱신되거나 변경될 때마다 해당 상품에 대한 캐시를 강제로 삭제하거나 갱신합니다.  
예를 들어, 상품 가격이나 상태가 바뀔 때마다 캐시를 새롭게 업데이트해야 합니다.  

## 2. 선착순 쿠폰발급 API에 Redis와 분산락 적용

### 문제점:
선착순 쿠폰발급 API는 제한된 수량만큼 쿠폰을 발급합니다. 이러한 경우 다수의 사용자가 동시에 쿠폰을 발급하려 할 때, 중복 발급을 방지하고, 성능을 유지하는 것이 중요합니다.
특히 높은 트래픽 상황에서 동시성 문제(race condition)가 발생할 수 있으며, 이를 해결하기 위한 분산 락이 필요합니다.

### 해결 방안: 
Redis에서 제공하는 분산락을 사용하여 쿠폰 발급 API의 성능을 개선하고, 중복 발급을 방지할 수 있습니다.

### 주요 고려 사항:
- 락 Timeout: 락을 획득한 후 너무 오래 락을 걸어두면 다른 사용자가 대기 상태가 될 수 있습니다. 적절한 락 시간(예: 10초)을 설정하여 락을 일정 시간 후 자동으로 해제합니다.
- 재고 관리: Redis에서 쿠폰 재고를 관리합니다. 쿠폰이 발급될 때마다 재고값을 줄여 나가고, 남은 쿠폰 수량을 체크합니다.
- 동시성 관리: 다수의 요청이 동시에 들어와도 분산락을 사용하여 중복 발급을 방지하고, 안정적으로 쿠폰 발급 처리를 할 수 있습니다.

## 3. 성능 개선 결과

### 상품조회 API
- 응답 시간 단축: Redis 캐시를 활용하여 상품 조회 시 DB 부하를 줄이고, 응답 속도를 획기적으로 개선할 수 있습니다.  
- 서버 부하 감소: 자주 조회되는 상품 데이터에 대해 Redis에서 빠르게 응답함으로써 서버 자원 소모를 줄일 수 있습니다.

### 쿠폰발급 API
- 중복 발급 방지: Redis 분산락을 사용하여 선착순 쿠폰 발급 시 동시성 문제를 해결하고, 중복 발급을 방지할 수 있습니다.  
- 성능 개선: Redis를 사용하여 실시간으로 쿠폰 발급 상태를 관리하고, 고속 처리할 수 있습니다. 높은 트래픽 상황에서도 안정적인 쿠폰 발급이 가능합니다.   

