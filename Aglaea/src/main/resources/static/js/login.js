document.addEventListener("DOMContentLoaded", function() {
    const loginForm = document.getElementById("loginForm");
    if (loginForm) {
        loginForm.addEventListener("submit", function() {
            document.getElementById("loading-screen").style.display = "flex";
        });
    }
});

document.addEventListener("DOMContentLoaded", function() {
    if (window.location.search.includes("error=true")) {
        window.history.replaceState({}, document.title, "/auth/login");
    }
});


