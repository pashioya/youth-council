// ****
// SideBar Functionality Starts
// ****


// This code adds event listeners to the DOM (Document Object Model) when the content has finished loading.
document.addEventListener("DOMContentLoaded", function () {
//  function takes four parameters:
//  toggleId is the ID of the button that will toggle the navigation bar,
//  navId is the ID of the navigation bar to be shown or hidden,
//  bodyElement is the element selector for the HTML body,
//  headerElement is the element selector for the HTML header.

// the toggle, nav, body, and header variables are assigned the corresponding HTML elements by their IDs and selectors.
// If these elements exist, the function adds an event listener to the toggle button that toggles the show class on the nav element,
// and also toggles the bx-x, body-pd, and header-pd classes on the toggle, body, and header elements,
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


// The linkColor variable is assigned all elements with the class .nav_link.
// The colorLink() function removes the active class from all elements with the .nav_link class and adds it to the clicked element.

    const linkColor = document.querySelectorAll(".nav_link");

    function colorLink() {
        if (linkColor) {
            linkColor.forEach((l) => l.classList.remove("active"));
            this.classList.add("active");
        }
    }

//Adds an event listener to each element with the .nav_link class that calls the colorLink() function when clicked.
    linkColor.forEach((l) => l.addEventListener("click", colorLink));
});

// ****
// SideBar Functionality Ends
// ****