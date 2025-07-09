document.addEventListener("DOMContentLoaded", function () {
    const password = document.getElementById("password");
    const rePassword = document.getElementById("re-password");
    const errorText = document.getElementById("password-error");
    const btnRegister = document.getElementById("register-btn");
    const registerForm = document.getElementById("register-form");


    function validatePassword() {
        if (rePassword.value === "") {
            errorText.style.display = "none";
            btnRegister.disabled = true;
            return;
        }
        if (password.value !== rePassword.value || password.value === "") {
            errorText.style.display = "block";
            btnRegister.disabled = true;
        } else {
            errorText.style.display = "none";
            btnRegister.disabled = false;
        }
    }

    rePassword.addEventListener("input", validatePassword);
    password.addEventListener("input", validatePassword);

    registerForm.addEventListener("submit", function (event) {
        if (password.value !== rePassword.value) {
            errorText.style.display = "block";
            event.preventDefault();
        }
    });

});

document.addEventListener("DOMContentLoaded", function () {
    const registerForm = document.getElementById("register-form");
    if (registerForm) {
        registerForm.addEventListener("submit", function () {
            document.getElementById("loading-screen").style.display = "flex";
        });
    }
});

document.addEventListener("DOMContentLoaded", function () {
    setTimeout(function () {
        let alerts = document.querySelectorAll(".alert");
        if (alerts.length > 0) {
            alerts.forEach(alert => {
                setTimeout(() => alert.style.display = "none", 5000);
            });
        }
    }, 5000);
});

document.addEventListener("DOMContentLoaded", function () {
    if (window.location.search.includes("error=true")) {
        window.history.replaceState({}, document.title, window.location.pathname);
    }
});




