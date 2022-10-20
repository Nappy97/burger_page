'use strict';

(function () {
    window.addEventListener("load", function () {
        let form = this.document.querySelector("#joinForm");
        let btnUpdate = this.document.querySelector("#subit-button");

        btnUpdate.addEventListener("click", function (event) {
            if (form.checkValidity() == false) {
                event.preventDefault();
                event.stopPropagation();
                form.classList.add("was-validated");
            }
        }, false);
    }, false);
})();