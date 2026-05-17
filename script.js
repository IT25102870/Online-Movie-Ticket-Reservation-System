const signupText = document.getElementById("signupText");
const loginText = document.getElementById("loginText");

signupText.addEventListener("click", (e) => {

    e.preventDefault();

    signupForm.classList.add("active-form");
    loginForm.classList.remove("active-form");

    signupToggle.classList.add("active");
    loginToggle.classList.remove("active");

});

loginText.addEventListener("click", (e) => {

    e.preventDefault();

    loginForm.classList.add("active-form");
    signupForm.classList.remove("active-form");

    loginToggle.classList.add("active");
    signupToggle.classList.remove("active");

});



const forms = document.querySelectorAll(".needs-validation");

Array.from(forms).forEach(form => {

    form.addEventListener("submit", event => {

        if (!form.checkValidity()) {

            event.preventDefault();
            event.stopPropagation();

        }

        // PASSWORD MATCH CHECK

        if (form.id === "signupForm") {

            const password =
                document.getElementById("signupPassword").value;

            const confirmPassword =
                document.getElementById("confirmPassword").value;

            if (password !== confirmPassword) {

                event.preventDefault();

                showToast("Passwords do not match.");

            }

        }

        form.classList.add("was-validated");

    }, false);

});