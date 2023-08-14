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

        $.ajax({
           type:"post",
           url:"/user/auth/register",
           data:JSON.stringify(user),
           contentType: "application/json; charset=utf-8"
        }).done(function(response){
//            console.log(response);
            // location = "/";

            // validation 후
            let status = response["status"]; // 응답 상태코드
            if(status == 200){
                let message = response["data"];
                alert(message);
                location = "/";
            }else{
            let warn = "";
            let errors = response["data"]; // 리턴된 Map 데이터
            if(errors.id != null) warn = warn + errors.id + "\n";
            if(errors.username != null) warn = warn + errors.username + "\n";
            if(errors.password != null) warn = warn + errors.password + "\n";
            if(errors.email != null) warn = warn + errors.email + "\n";
            else warn = warn + errors + "\n";
            alert(warn);
            }
        }).fail(function(error){
            error = "이름 중복"
            alert("에러 발생 : " + error);
        });
    },
}

// userObject의 init함수 호출하기
userObject.init();