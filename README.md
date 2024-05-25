![header](https://capsule-render.vercel.app/api?type=waving&color=auto&height=300&section=header&text=Jsp%20Project&fontSize=90)

<div align="center">
📚 <b>Tech Stack</b> 📚


✨ <b>Platforms & Languages</b> ✨

![image](https://img.shields.io/badge/springboot-6DB33F?style=flat&logo=springboot&logoColor=white)
![image](https://img.shields.io/badge/java-003D8F?style=flat&logo=java&logoColor=white)
![image](https://img.shields.io/badge/mongodb-47A248?style=flat&logo=mongodb&logoColor=white)
![image](https://img.shields.io/badge/jquery-0769AD?style=flat&logo=jquery&logoColor=white)
![image](https://img.shields.io/badge/bootstrap-7952B3?style=flat&logo=bootstrap&logoColor=white)

🛠 <b>Tools</b> 🛠


![image](https://img.shields.io/badge/github-181717?style=flat&logo=github&logoColor=white)
![image](https://img.shields.io/badge/intellijidea-000000?style=flat&logo=intellijidea&logoColor=white)
</div>



## 개요
<b>JSP PROJECT는 SpringBoot와 Kendo UI로 만든 게시판 웹프로그램입니다.</b>
<ol>
<li>Jwt를 쿠키에 저장하여 인증방식</li>
<li>회원가입시 아이디중복체크</li>
<li>로그인시 아이디 비밀번호 유효체크 </li>
</ol>
등의 기능이 포함되어있습니다.

해당 프로젝트의 로그인 기능은 JWT를 쿠키에 저장하여 회원정보를 조회합니다.

MongoDB를 사용하였으며 JPA를 사용해 가독성있는 코드로 구성되어있습니다.

## 기획
![image](https://github.com/choizia0724/jsp_project/assets/107836206/5a5c73b7-1f2a-4515-99be-90a6da2bea28)

SpringBoot로 서버를 구축하고 JWT로 회원의 정보를 보호하고 쿠키에 저장하여 만료기간을 설정합니다.

이를 심도있게 공부하는 것을 목적으로 합니다.

## DB Model
MongoRepository를 사용하여 쿼리문이 아닌 메소드를 이용하여 db를 조작합니다.

![image](https://github.com/choizia0724/jsp_project/assets/107836206/8cc2840c-b575-4c55-8f9f-2f4fccc0139a)

```
@Data
@Document(collection = "posts")
public class Post {
    @Id
    private String id;
    private String title;
    private String content;
    private String username;
    private Date createdAt;
    public String getFormattedCreatedAt() {
        SimpleDateFormat sdf = new SimpleDateFormat("yy.MM.dd HH:mm");
        return sdf.format(createdAt);
    }
}
```
```
@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
}
```

## ViewTable
|게시글 내부|로그인 화면|게시판 화면|
|---|---|---|
|![image](https://github.com/choizia0724/jsp_project/assets/107836206/367fee99-a321-460a-a3f8-d1cb9f097683)|![image](https://github.com/choizia0724/jsp_project/assets/107836206/8ac4bbe9-8a2b-4b0d-853c-276d4bd0341e)|![image](https://github.com/choizia0724/jsp_project/assets/107836206/2bcabe39-6338-40ab-a939-e60d92770c73)|
## Framework 설계
- Spring Boot: Spring Boot의 다양한 스타터 의존성을 활용하여 빠르고 효율적으로 웹 애플리케이션을 개발하였습니다.
- MongoDB: 데이터베이스와의 통합을 통해 데이터 저장 및 관리를 효율적으로 수행하였습니다.
- JSON Web Token(JWT): 사용자 인증 및 권한 부여 시스템을 설계하고 구현하였습니다. 이를 통해 안전한 데이터 접근과 사용자 관리를 가능하게 하였습니다.
- JSP: 사이드 렌더링을 처리하였습니다.
- lombok: 보일러플레이트 코드를 줄이고 코드의 가독성을 향상시켰습니다.

## 개발
![image](https://github.com/choizia0724/jsp_project/assets/107836206/72729086-b194-48de-868d-e9e96355c3a6)

MVC를 분리하여 가독성을 향상시켰습니다.

![image](https://github.com/choizia0724/jsp_project/assets/107836206/9c6c6745-53d2-408e-8348-44c89e2ea27e)

Spring MVC의 @RestController와 @RequestMapping 어노테이션을 사용하여 RESTful API 엔드포인트를 구현하였습니다.

