let userObject = {
    // init() 함수선언
    init: function () {
        let _this = this;

        $("#btn-save").on("click", () => {
            _this.insertUser();
        });
    },
    insertUser: function () {
        alert("회원 가입 요청!!!");

        let user = {
            id: $("#id").val(),
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        }
        console.log(user);
        $.ajax({
           type:"post",
           url:"register",
           data:JSON.stringify(user),
           contentType: "application/json; charset=utf-8"
        }).done(function(response){
            console.log(response);
            location = "/";
        }).fail(function(error){
            alert("에러 발생 : " + error);
        });
    },
}

// userObject의 init함수 호출하기
userObject.init();