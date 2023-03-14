document.addEventListener("DOMContentLoaded", function () {
    const showNavbar = (toggleId, navId, bodyElement, headerElement) => {
        const toggle = document.getElementById(toggleId),
            nav = document.getElementById(navId),
            body = document.querySelector(bodyElement),
            header = document.querySelector(headerElement);

        // Validate that all variables exist
        if (toggle && nav && body && header) {
            toggle.addEventListener("click", () => {
                // show navbar
                nav.classList.toggle("show");
                // change icon
                toggle.classList.toggle("bx-x");
                // add padding to body
                body.classList.toggle("body-pd");
                // add padding to header
                header.classList.toggle("body-pd");
            });
        }
    };

    showNavbar("header-toggle", "nav-bar", "body", "header");

    const linkColor = document.querySelectorAll(".nav_link");
    function colorLink() {
        if (linkColor) {
            linkColor.forEach((l) => l.classList.remove("active"));
            this.classList.add("active");
        }
    }
    linkColor.forEach((l) => l.addEventListener("click", colorLink));
});
