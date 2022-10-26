# [2Week] 조아영
***

## 미션 요구사항 분석 & 체크리스트
### 필수과제
#### 주문
- [x] 주문 생성
- [ ] 주문 리스트
- [ ] 주문 상세
- [ ] 주문 취소

#### 장바구니
- [x] 품목 리스트
- [x] 품목 삭제
- [x] 품목 추가

#### 결제
- [ ] 주문 결제

#### PG 연동
- [ ] PG 연동

## 1주차 미션 요약
### 접근 방법
* 장바구니 & 주문의 작동 과정   
① 회원이 도서 목록에서 원하는 도서를 장바구니에 담는다.  
② 장바구니에서 최종 주문할 도서를 결정한다. (취소 기능 구현)  
③ 주문을 하면 장바구니에 있는 도서들이 주문으로 넘어가고, 장바구니에 있는 품목들은 모두 삭제된다.  


* 이번주는 주문 생성시 작동하는 CartItem <-> OrderItem <-> Order 객체 사이의 과정을 이해하고,  
Service 작동을 확인하는 TestCase 작성하는 것에 집중하였습니다. 

[🔗 2주차 Mission 해결 일지(1)](https://like099.tistory.com/55)
[🔗 2주차 Mission 해결 일지(2)](https://like099.tistory.com/61)

(위 링크 내용을 정리한 내용입니다.)
## ⛳️ 장바구니 구현
### 🌟 품목추가
#### 1️⃣ 조건 확인
```text
⛳️ API : GET /cart/add/{productId}
⛳️ 요구사항
✔️ 상품이 곧 도서이다.
✔️ 도서를 장바구니에 담을 수 있다.
```
#### 2️⃣ 장바구니 Entity 추가 및 컬럼별 관계 정의 (기본 구현)
Member(회원) 가 존재해야 Product(상품) 이 존재하고, 둘이 존재해야 CartItem(장바구니 폼목) 이 존재할 수 있다.  
① CartItem Entity 생성
➡️ 회원이 같은 상품을 두번 장바구니에 담는 경우의 예외 처리 (🛠 리팩토링 필요)  
② addCart 로직 작성  
③ Test Case 작성 (CartServiceTests)  
```java
public class CartServiceTests {
//		(생략)
    @Test
    @DisplayName("상품 추가")
    void t1 () {
        Member member = memberService.findByUsername("user1").get();
        Product product = productService.findById(3L).get();

        CartItem cartItem = cartService.addCart(member, product);

        assertThat(cartItem.getId()).isEqualTo(3L);
        assertThat(cartItem.getMember()).isEqualTo(member);
        assertThat(cartItem.getProduct()).isEqualTo(product);
    }
}
```
④ DevInitData 에 CartItem 도 추가

### 🌟 품목 리스트
#### 1️⃣ 조건 확인
```text
⛳️ API : GET /cart/list
⛳️ 요구사항
✔️ 장바구니에서 도서를 제거할 수 있다.
```
#### 2️⃣ 기본 구현
① UI 추가  
② 기본 구현
③ Test Case 작성 (CartServiceTests)
```java
public class CartServiceTests {
    //		(생략)
    @Test
    @DisplayName("상품 목록 확인")
    void t2() {
        List<CartItem> cartItemList = cartService.findAllByMemberId(2L);

        assertThat(cartItemList.size()).isEqualTo(1);
        assertThat(cartItemList.get(0).getIndex_unique()).isEqualTo(23);
    }
}
```
④ 작동 확인
#### 3️⃣ 장바구니 품목 삭제
```text
⛳️ API : GET /cart/delete/{uniqueId}
```
➡️ 원래 요구 사항에 주어진 API 는 /cart/remove/{productId} 였지만, 그것보다는 unique 한 값인 indexUnique 를 쓰면 좋을 것 같아서 수정하였다.
① CartItem 의 Index_unique 를 사용하여 로직 구성
② Test Case 작성 (CartServiceTests)
```java
public class CartServiceTests {
    //		(생략)
    @Test
    @DisplayName("상품 삭제 확인")
    void t3() {
        // When
        cartService.deleteCartItem(23L);
        List<CartItem> cartItemList = cartService.findAllByMemberId(2L);
        // Then
        assertThat(cartItemList.size()).isEqualTo(0);
    }
}
```
## ⛳️ 주문 구현
### 🌟 주문 생성
#### 1️⃣ 조건 확인
```text
⛳️ API : GET /order/create
⛳️ 요구사항
✔️ 주문을 하면 일단 주문의 상태는 준비상태이다.
✔️ 여기서 결제를 하거나, 주문취소를 할 수 있다.
```
#### 2️⃣ 기본 구현
① Entity 생성 (Order & OrderItem)  
② Service 구현  
③ Test Case 작성 (OderServiceTests)  
```java
public class OrderServiceTests {
//		(생략)
    @Test
    @DisplayName("주문 생성")
    @Rollback(value = false)
    public void t1() {
        // Given
        Member member = memberService.findByUsername("user2").get();
        List<CartItem> cartItems = cartService.findAllByMemberId(member.getId());

        // When
        Order order = orderService.createFromCart(member);

        // Then
        assertThat(order.getOrderItems().size()).isEqualTo(cartItems.size());
    }
}
```
- Order 테이블  
<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Few1pu3%2FbtrPzCwJxs3%2FEnVKpJbqY3wNXoW2k67Zd1%2Fimg.png" width="700">  
- OrderItem 테이블  
<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FlCehu%2FbtrPDzyrI8A%2FdCAAOLyMgtcxa5J2GFGH6K%2Fimg.png" width="700">
- 주문 전 CartItem 테이블  
<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FBS2zY%2FbtrPzCi933S%2FiRzIXU33qhz53hb92G6F0K%2Fimg.png" width="700">
- 주문 후 CartItem 테이블  
<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FL2SAl%2FbtrPBqWOtuB%2F5sfMmEw1fv1YNeJFXjaWik%2Fimg.png" width="700">
### 특이사항
#### [아쉬웠던 점]  
- 결제 관련 로직을 작성하지 못했습니다.

#### [리팩토링]
- [ ] (🐛 버그) 장바구니에 같은 도서를 두번 담는 것에 대한 에러 처리

#### [피어 리뷰 후 진행한 내용]