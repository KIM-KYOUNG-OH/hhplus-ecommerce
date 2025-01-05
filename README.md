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

## GET 잔액 조회

GET /api/`{{memberId}}`/balance

> Response Examples

```json
{
  "balance": 50000
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name| Type |Required|Restrictions|Title|description|
|---|------|---|---|---|---|
|» balance| long |true|none||none|

## PATCH 잔액 충전

PATCH /api/`{{memberId}}`/balance

> Body Parameters

```json
{
  "chargeAmount": long 
}
```
> Response Examples

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### Responses Data Schema

## GET 상품 조회

GET /api/products/`{{productId}}`

> Response Examples

```json
{
  "productId": 1,
  "productName": "셔츠",
  "brandId": 10,
  "brandName": "나이키",
  "productOptions": [
    {
      "productOptionId": 1,
      "optionName": "blue",
      "quantity": 50,
      "regularPrice": 19900
    },
    {
      "productOptionId": 2,
      "optionName": "black",
      "quantity": 10,
      "regularPrice": 29900
    },
    {
      "productOptionId": 3,
      "optionName": "green",
      "quantity": 30,
      "regularPrice": 9900
    }
  ]
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name| Type     |Required|Restrictions|Title|description|
|---|----------|---|---|---|---|
|» productId| long     |true|none||none|
|» productName| string   |true|none||none|
|» brandId| long  |true|none||none|
|» brandName| string   |true|none||none|
|» productOptions| [List] |true|none||none|
|»» productOptionId| long  |true|none||none|
|»» optionName| string   |true|none||none|
|»» quantity| long  |true|none||none|
|»» regularPrice| long  |true|none||none|

## GET 상위 상품 조회

GET /api/bestProducts

### Params

|Name|Location|Type|Required| Description |
|---|---|---|---|-------------|
|startDate|query|string| true | YYYYMMDD    |
|endDate|query|string| true | YYYYMMDD        |

> Response Examples

```json
{
  "bestProducts": [
    {
      "rank": 1,
      "productId": 1,
      "productName": "셔츠",
      "brandId": 10,
      "brandName": "나이키",
      "productOptions": [
        {
          "productOptionId": 1,
          "optionName": "blue",
          "quantity": 50,
          "regularPrice": 19900
        },
        {
          "productOptionId": 2,
          "optionName": "black",
          "quantity": 10,
          "regularPrice": 29900
        },
        {
          "productOptionId": 3,
          "optionName": "green",
          "quantity": 30,
          "regularPrice": 9900
        }
      ]
    },
    {
      "rank": 2,
      "productId": 1,
      "productName": "체크남방",
      "brandId": 14,
      "brandName": "아디다스",
      "productOptions": [
        {
          "productOptionId": 4,
          "optionName": "blue",
          "quantity": 50,
          "regularPrice": 19900
        },
        {
          "productOptionId": 5,
          "optionName": "black",
          "quantity": 10,
          "regularPrice": 29900
        },
        {
          "productOptionId": 6,
          "optionName": "green",
          "quantity": 30,
          "regularPrice": 9900
        }
      ]
    },
    {
      "rank": 3,
      "productId": 1,
      "productName": "가죽자켓",
      "brandId": 11,
      "brandName": "무신사",
      "productOptions": [
        {
          "productOptionId": 11,
          "optionName": "blue",
          "quantity": 50,
          "regularPrice": 19900
        }
      ]
    },
    {
      "rank": 4,
      "productId": 33,
      "productName": "구두",
      "brandId": 12,
      "brandName": "무신사",
      "productOptions": [
        {
          "productOptionId": 11,
          "optionName": "blue",
          "quantity": 50,
          "regularPrice": 19900
        }
      ]
    },
    {
      "rank": 5,
      "productId": 31,
      "productName": "야구모자",
      "brandId": 14,
      "brandName": "무신사",
      "productOptions": [
        {
          "productOptionId": 16,
          "optionName": "blue",
          "quantity": 50,
          "regularPrice": 19900
        }
      ]
    }
  ]
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» bestProducts|[List]|true|none||none|
|»» rank|long|true|none||none|
|»» productId|long|true|none||none|
|»» productName|string|true|none||none|
|»» brandId|long|true|none||none|
|»» brandName|string|true|none||none|
|»» productOptions|[List]|true|none||none|
|»»» productOptionId|long|true|none||none|
|»»» optionName|string|true|none||none|
|»»» quantity|long|true|none||none|
|»»» regularPrice|long|true|none||none|

## GET 보유 쿠폰 목록 조회

GET /api/`{{memberId}}`/coupons

> Response Examples

```json
{
  "coupons": [
    {
      "couponId": 1,
      "couponName": "10000원 할인 쿠폰",
      "discountType": "FIXED",
      "discountAmount": 10000,
      "quantity": 10
    },
    {
      "couponId": 2,
      "couponName": "10프로 할인 쿠폰",
      "discountType": "PERCENT",
      "discountAmount": 10,
      "quantity": 15
    }
  ]
}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### Responses Data Schema

HTTP Status Code **200**

|Name|Type|Required|Restrictions|Title|description|
|---|---|---|---|---|---|
|» coupons|[List]|true|none||none|
|»» couponId|long|true|none||none|
|»» couponName|string|true|none||none|
|»» discountType|string|true|none||none|
|»» discountAmount|long|true|none||none|
|»» quantity|long|true|none||none|

## POST 쿠폰 발급

POST /api/{{memberId}}/coupons

> Body Parameters

```json
{
  "couponId": long,
  "quantity": long
}
```

### Params

| Name      | Location     | Type | Required |Description|
|-----------|--------------|------|----------|---|
| counponId | request body | long | true      |none|
| quantity  | request body | long | true      |none|

> Response Examples

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### Responses Data Schema

## POST 주문

POST /api/`{{memberId}}`/order

> Body Parameters

```json
{
    "totalRegularPrice": long,
    "totalDiscountPrice": long,
    "totalSalePrice": long,
    "orderItems": [
        {
            "productId": long,
            "productOptionId": long,
            "orderCount": long,
            "regularPrice": long,
            "discountPrice": long,
            "salePrice": long,
            "appliedCouponId": long
        }
    ]
}
```

> Response Examples

```json
{}
```

### Responses

|HTTP Status Code |Meaning|Description|Data schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### Responses Data Schema

# Data Schema


