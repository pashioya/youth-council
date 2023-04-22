function scrollFunction1() {
    let e = document.getElementById("about-us");
    e.scrollIntoView({
        block: "start",
        behavior: "smooth",
        inline: "center",
    });
}

function scrollFunction2() {
    let e = document.getElementById("news");
    e.scrollIntoView({
        block: "start",
        behavior: "smooth",
        inline: "center",
    });
}

function scrollFunction3() {
    let e = document.getElementById("youth-councils");
    e.scrollIntoView({
        block: "start",
        behavior: "smooth",
        inline: "center",
    });
}


const hiddenElements = document.querySelectorAll(".hidden");
const observer = new IntersectionObserver((entries) => {
        entries.forEach((entry) => {
            if (entry.isIntersecting) {
                entry.target.classList.add("fade-in");
            }
        });
    }
);

hiddenElements.forEach((element) => observer.observe(element));