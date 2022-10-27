    //-------- HTML 요소 셀렉팅 ---------//

const nickname = document.querySelector('#nickname')

const nicknameFMessage = document.querySelector('.nickF-message');
const nicknameSMessage = document.querySelector('.nickS-message');

const name1 = document.querySelector('#name');

const elIllegibleMessage = document.querySelector('.illegible-message');
const elReadableMessage = document.querySelector('.readable-message');

const zipcode = document.querySelector('#zipcode');

const zipcodeFMessage = document.querySelector('.zipcodeF-message');
const zipcodeSMessage = document.querySelector('.zipcodeS-message');

const address = document.querySelector('#address');

const addressFMessage = document.querySelector('.addressF-message');
const addressSMessage = document.querySelector('.addressS-message');

const detailAddress = document.querySelector('#detailAddress');

const dAddressFMessage = document.querySelector('.dAddressF-message');
const dAddressSMessage = document.querySelector('.dAddressS-message');

const elSubmitButton = document.querySelector('#subit-button');

let nickChk = null;


//-------- 유효성 검사 ---------//

// 닉네임
function nicknameFn() {
    if (isMoreThan4Length(nickname.value)) {
        nicknameSMessage.classList.remove('hide');
        nicknameFMessage.classList.add('hide')
    } else {
        nicknameFMessage.classList.remove('hide');
        nicknameSMessage.classList.add('hide');
    }

    isSubmitButton();
}

nickname.addEventListener('click', nicknameFn);
nickname.addEventListener('keyup', nicknameFn);


// 이름 유효성 검사
function name1Fn() {
    if (isName1Char(name1.value)) {
        elIllegibleMessage.classList.add('hide');
        elReadableMessage.classList.remove('hide');
    } else {
        elIllegibleMessage.classList.remove('hide');
        elReadableMessage.classList.add('hide');
    }

    isSubmitButton();
}

name1.addEventListener('click', name1Fn);
name1.addEventListener('keyup', name1Fn);

function zipcodeFn() {
    if (nullChk(zipcode.value)) {
        zipcodeSMessage.classList.remove('hide');
        zipcodeFMessage.classList.add('hide')
    } else {
        zipcodeFMessage.classList.remove('hide');
        zipcodeSMessage.classList.add('hide');
    }

    isSubmitButton();
}

zipcode.addEventListener('click', zipcodeFn);
zipcode.addEventListener('keyup', zipcodeFn);

function addressFn() {
    if (nullChk(address.value)) {
        addressSMessage.classList.remove('hide');
        addressFMessage.classList.add('hide')
    } else {
        addressFMessage.classList.remove('hide');
        addressSMessage.classList.add('hide');
    }

    isSubmitButton();
}

address.addEventListener('click', addressFn);
address.addEventListener('keyup', addressFn);

function detailAddressFn() {
    if (nullChk(detailAddress.value)) {
        dAddressSMessage.classList.remove('hide');
        dAddressFMessage.classList.add('hide')
    } else {
        dAddressFMessage.classList.remove('hide');
        dAddressSMessage.classList.add('hide');
    }

    isSubmitButton();
}

detailAddress.addEventListener('click', detailAddressFn);
detailAddress.addEventListener('keyup', detailAddressFn);

function execPostCode() {
    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 도로명 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullRoadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 도로명 조합형 주소 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if (data.buildingName !== '' && data.apartment === 'Y') {
                extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 도로명, 지번 조합형 주소가 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if (extraRoadAddr !== '') {
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }
            // 도로명, 지번 주소의 유무에 따라 해당 조합형 주소를 추가한다.
            if (fullRoadAddr !== '') {
                fullRoadAddr += extraRoadAddr;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            console.log(data.zonecode);
            console.log(fullRoadAddr);


            $("[name=zipcode]").val(data.zonecode);
            $("[name=address]").val(fullRoadAddr);

            document.getElementById('zipcode').value = data.zonecode; //5자리 새우편번호 사용
            document.getElementById('address').value = fullRoadAddr;
            // document.getElementById('signUpUserCompanyAddressDetail').value = data.jibunAddress;
        }
    }).open();
}

$('#nickname').on("propertychange, change keyup paste input", function () {

    var nickname = $('#nickname').val();
    var data = {nickname: nickname}
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $(document).ajaxSend(function (event, xhr, options) {
        xhr.setRequestHeader(header, token);
    })

    $.ajax({
        type: "post",
        url: "/auth/user/saveProc/nicknameCheck",
        data: data,
        success: function (resultNick) {
            console.log("성공 여부 " + resultNick);
            if (resultNick == "true") {
                $('.nick_input_re_1').css("display", "inline-block");
                $('.nick_input_re_2').css("display", "none");
                nickChk = "true";
            } else {
                $('.nick_input_re_2').css("display", "inline-block");
                $('.nick_input_re_1').css("display", "none");
                nickChk = "false";
            }
        }
    });
});

//-------- 최종 유효성 검사에서 사용하는 함수 ---------//

// 모든 조건이 충족되었는지 확인하는 함수
function isAllCheck() {
    console.log("validationstart");
    if (isName1Char(name1.value)) {
        if (isMoreThan4Length(nickname.value)) {
            if (nullChk(zipcode.value)) {
                if (nullChk(address.value)) {
                    if (nullChk(detailAddress.value)) {
                        if (nickChk === "true") {
                            console.log("validationend");
                            return true;
                        }
                    }
                }
            }
        }
    } else {
        console.log('false!!');
        return false;
    }
}


// [회원가입 버튼] 배경 활성화 함수
function isSubmitButton() {
    if (isAllCheck()) {
        elSubmitButton.classList.add('allCheck');
    } else {
        elSubmitButton.classList.remove('allCheck');
    }
}

// let index = {
//     init: function () {
//         $("#subit-button").on("click", () => {
//             if (isAllCheck() == true) {
//                 this.save();
//             } else {
//                 console.log("회원가입안됨")
//             }
//         });
//     },
//
//     save: function () {
//         let data = {
//             username,
//             password,
//             email,
//             nickname,
//             zipcode,
//             address,
//             detailAddress
//         }
//
//         $.ajax({
//             type: "POST",
//             url: "/auth/api/v1/user",
//             data: JSON.stringify(data),
//             contentType: "application/json; charset=utf-8",
//             dataType: "json"
//         }).done(function (res) {
//             alert("회원가입 완료");
//             location.href = "/auth/user/login";
//         }).fail(function (err) {
//             alert(JSON.stringify(err));
//         });
//
//     }
// }
// index.init();

document.getElementById("joinForm").onsubmit = function () {
    if (isAllCheck()) {
        return true;
    } else {
        alert('모든 조건이 충족되어야합니다.');
        return false;
    }
}
;


let index = {
    init: function () {
        $("#subit-button").on("click", () => {
            let form = document.querySelector("#joinForm");
            if (form.checkValidity() == false) {
                console.log("수정 안됨")
            } else {
                this.update();
            }
        })
    },

    update: function () {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        var url = "/api/v1/oauthUser";

        let data = {
            id: $("#id").val(),
            nickname: $("#nickname").val(),
            name: $("#name").val(),
            zipcode: $("#zipcode").val(),
            address: $("#address").val(),
            detailAddress: $("#detailAddress").val()
        }

        $.ajax({
            type: "PUT",
            url: url,
            data: JSON.stringify(data),
            beforeSend: function (xhr) {
                /* 데이터를 전송하기 전에 헤더에 csrf값을 설정 */
                xhr.setRequestHeader(header, token);
            },
            dataType: "json",
            cache: false,
            contentType: 'application/json; charset=utf-8',
            success: function (res) {
                alert("회원수정이 완료되었습니다")
                location.href = "/";
            }, error: function (err) {
                alert(JSON.stringify(err))
            }
        })
    }
}
index.init();


//-------- 유효성 검사에서 사용하는 함수다 ---------//

function isMoreThan4Length(value) {
// 아이디 입력창에 사용자가 입력을 할 때
// 글자 수가 4개이상인지 판단한다.
// 글자가 4개 이상이면 success메세지가 보여야 한다.
    return value.length >= 4;
}

// null 이 아닌경우
function nullChk(value) {
    return value.length >= 1;
}

// 이름이 숫자가 들어간경우
function isName1Char(name1) {
    var letters = /^[가-힣]+$/;
    if (name1.match(letters)) {
        return true;
    } else {
        return false;
    }
}

