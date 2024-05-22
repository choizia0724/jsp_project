db = db.getSiblingDB('testdb'); // 'testdb' 데이터베이스 선택

// users 컬렉션에 데이터 삽입
db.users.insertMany([
    { username: "user1", password: "password1" },
    { username: "user2", password: "password2" }
]);

// posts 컬렉션에 데이터 삽입
db.posts.insertMany([
    { title: "First Post", content: "This is the content of the first post" },
    { title: "Second Post", content: "This is the content of the second post" }
]);
