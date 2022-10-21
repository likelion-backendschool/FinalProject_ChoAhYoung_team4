# [1Week] 조아영
***

## 미션 요구사항 분석 & 체크리스트
### 필수과제
#### 회원 관련
- [ ] 회원 가입
- [ ] 로그인
- [ ] 로그아웃
- [ ] 회원정보수정
- [ ] 아이디찾기

#### 글 CRUD
- [x] 글 작성
- [x] 글 수정
- [x] 글 리스트
- [x] 글 삭제

## 1주차 미션 요약
### 접근 방법
회원 가입 과정은 시간 내에 마무리가 힘들 것 같아 글 관련 미션을 완료하는 것에 목표를 두었습니다.  
최근 DTO 의 필요성에 대해 공부하고 있어서 DTO를 활용한 구현에 집중하였습니다.

[🔗 1주차 Mission 해결 일지](https://like099.tistory.com/40)

(위 링크 내용을 정리한 내용입니다.)  
## ⛳️ 글 CRUD 구현  
### 🌟 글 저장하기  

#### 1️⃣ 조건 확인  
```text
⛳️ API : GET /post/write  
✔️ 폼 입력 ① subject ② content ③ keywords  (입력예시 : #자바 #스프링부트 #스프링배치)  
⛳️ 요구사항  
✔️ 마크다운 원문과 렌더링 결과(HTML)까지 같이 저장한다.  
✔️ 글 작성은 토스트에디터 사용한다.  
✔️ 글은 최소 1000자 이상의 텍스트가 존재해야 발행이 가능하다.  
✔️ 이미지만 있는 글은 발행할 수 없다.  
```  

#### 2️⃣ 기본 구현  
but 아직 키워드 관련 부분은 해결하지 못했다.  

#### 3️⃣ Dto 적용 _ RequestPostDto  
① PostDto class 생성 후 RequestPostDto 내부 클래스 생성  
```java
// RequestPostDto 구성
@Getter
@Setter
public static class RequestPostDto { // Post 작성 폼을 받기위한 DTO
    private String subject;
    private String content;
}
```
```java
public RequestPostDto (String subject, String content) {
        this.subject = subject;
        this.content = content;
}
```  

➡️ 고민중인 부분 : Controller 에 Mapping 은 잘 해두었는데, Service 에서 결국 전체 조립 필요 (아래 코드)  
```java
public void writePost(RequestPostDto requestPostDto) {
    Post post = new Post();
    post.setSubject(requestPostDto.getSubject());
    post.setContent(requestPostDto.getContent());
    post.setCreatedDate(LocalDateTime.now());
    post.setUpdatedDate(LocalDateTime.now());
    postRepository.save(post);
}
```
➡️ 1차 해결 :  Post Entity 에 생성자 생성, but 추가로 생성될 컬럼들이 있어서 형태가 달라질 수 있음.
```java
public Post (String subject, String content) {
    super(); // 상속받은 BaseEntity 의 생성자 호출
    this.subject = subject;
    this.content = content;
}
```
```java
// 개선된 Service 의 writePost 함수
public void writePost(RequestPostDto requestPostDto) {
    Post post = new Post(requestPostDto.getSubject(), requestPostDto.getContent());
    postRepository.save(post);
}
```  
#### 4️⃣ 토스트 에디터 적용  
① 매번 모든 html 파일에 모든 설정을 주입하는것은 번거로움으로 layout 적용 (동시에 header, footer 도 추가)  

### 🌟 글 리스트  

#### 1️⃣ 조건 확인  
```text
⛳️ API : GET /post/list  
✔️ 글 리스트, 전체 노출  
✔️ 리스트 아이템 구성요소 : 번호, 제목, 작성자, 작성날짜, 수정날짜  
✔️ 페이징 없음  
➡️ 아직 회원 적용 전이므로 작성자 제외 나머지 리스팅
⛳️ 요구사항  
✔️ 제목과 글의 해시태그들을 볼 수 있다.  
✔️ 해시태그를 클릭하면 내가 작성한 글 중 해당 해시태그와 관련된 글들을 볼 수 있다.  
```

#### 2️⃣ 기본 구현  

### 🌟 글 상세  

#### 1️⃣ 조건 확인  
```text
⛳️ API : GET /post/{id}  
✔️ 번호, 제목, 작성자, 작성날짜, 수정날짜, 내용  
✔️ 해시태그  
⛳️ 요구사항  
✔️ 글의 제목, 내용, 해시태그를 모두 출력  
✔️ 글의 내용은 마크다운 해석이 되어야 한다.  
```  

#### 2️⃣ 기본 구현  

#### 3️⃣  Dto 적용 _ ResponsetPostDto  

### 🌟글 삭제  

#### 1️⃣ 조건 확인  
```text
⛳️ API : GET /post/{id}/delete  
⛳️ 요구사항  
✔️ 글이 삭제되면 글 리스트로 리다이렉트 한다.  
✔️ 삭제버튼 눌렀을 때 confirm 창으로 삭제여부를 한 번 더 물어본다. 
```   

#### 2️⃣ 기본 구현  

#### 3️⃣ 삭제버튼 눌렀을 때 confirm 창으로 삭제 여부 확인  
```html
<a th:href="@{|/post/${post.id}/delete|}" onclick="return confirm('정말로 삭제할까요?');" class="btn btn-danger" th:text="삭제"></a>
```  

### 🌟글 수정  

#### 1️⃣ 조건 확인  
```text
⛳️ API : GET /post/{id}/modify  
✔️ 폼 입력 ① subject ② content ③ keywords  (입력예시 : #자바 #스프링부트 #스프링배치)
⛳️ API : POST /post/{id}/modify  
⛳️ 요구사항  
✔️ 마크다운 원문과 렌더링 결과(HTML)까지 같이 저장한다.  
```  

#### 2️⃣ 기본 구현  

### 특이사항
#### [아쉬웠던 점]  
- 회원 관련 미션을 아직 완료하지 못했습니다.
- Post 엔티티의 구성 요소 중 마크 다운 내용을 담은 ① content 와 토스트 에디터 렌더링 HTML 결과를 담은 ② contentHtml 의 상세 구현을 진행하지 못했습니다.
- 글 관련 미션 중 카테고리 해시태그 상세 구현을 진행하지 못했습니다.  

#### [리팩토링]
- [ ] (✨ 개선) 글 리스팅의 Controller 부분에도 DTO 적용 시키기
- [ ] (🐛 버그) 토스트 에디터 뷰어에서 이미지 수정 시 이미지가 정상적으로 보이도록

#### [피어 리뷰 후 진행한 내용]
