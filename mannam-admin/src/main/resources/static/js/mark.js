let markObject = {
    // init() 함수선언
    init: function () {
        let _this = this;

        $("#btn-markRegister").on("click", () => {
            _this.save();
        });
    },
    save: function () {
        alert("마커 등록 요청!!!");

        let mark = {
            markname: $("#markname").val(),
            markaddress: $("#markaddress").val(),
            markainfo: $("#markainfo").val(),
            category: $("#category").val(),
            tel: $("#tel").val(),
            markimage: $("#file").val(),
            latitude: $("#latitude").val(),
            longitude: $("#longitude").val()
        }

        $.ajax({
           type:"post",
           url:"/auth/kakaomapRegister",
           data:JSON.stringify(mark),
           contentType: "application/json; charset=utf-8"
        }).done(function(response){
            console.log(response);
            // location = "/";
        }).fail(function(error){
            alert("에러 발생 : " + error);
        });
    },
}

// userObject의 init함수 호출하기
markObject.init();