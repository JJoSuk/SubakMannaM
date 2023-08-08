let markObject = {
    // init() 함수선언
    init: function () {
        let _this = this;

        $("#btn-markRegister").on("click", () => {
            _this.save();
        });
        $("#modal_update_btn").on("click", () => {
                            _this.updateMark();
                        });

         $("#modal_delete_btn").on("click", () => {
                    _this.deleteMark();
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
     updateMark: function () {
            alert("마크 수정 요청!!!");
            let mark = {
                mid: $("#mid").val(),
                markname: $("#markname").val()
            }
            let userid = $("#userid").val();
            console.log(userid);
            $.ajax({
                type:"PUT",
                url:"/markupdate/",
                data: JSON.stringify(mark),
                contentType: "application/json; charset=utf-8"
            }).done(function(response){
                location = "/kakaomarkmap/"+userid;
            }).fail(function(error){
                alert("에러 발생 : " + error);
            });
        },

//    deleteMark: function () {
//            alert("마크 삭제 요청!!!");
//            let mid = $("#mid").val();
//            let userid = $("#userid").val();
//
//            $.ajax({
//                type:"DELETE",
//                url:"/markdelete/"+ mid,
//                contentType: "application/json; charset=utf-8"
//            }).done(function(response){
//                location = "/kakaomarkmap/"+userid;
//            }).fail(function(error){
//                alert("에러 발생 : " + error);
//            });
//        }

       deleteMark: function () {
                   alert("마크 삭제 요청!!!");

                   let mid = $("#mid").val();
                   let userid = $("#userid").val();

                   $.ajax({
                       type:"DELETE",
                       url:"/markdelete/"+ mid,
                       contentType: "application/json; charset=utf-8"
                   }).done(function(response){
                       location = "/kakaomarkmap/"+userid;
                   }).fail(function(error){
                       alert("에러 발생 : " + error);
                   });
               }
}

// userObject의 init함수 호출하기
markObject.init();