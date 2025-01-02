# hhplus-ecommerce

## Getting Started

### Prerequisites

#### Running Docker Containers

`local` profile 로 실행하기 위하여 인프라가 설정되어 있는 Docker 컨테이너를 실행해주셔야 합니다.

```bash
docker-compose up -d
```

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


