let button = document.getElementById("enter");
let input = document.getElementById("new-email");
let ul = document.getElementById("yc-admin-emails");
button.addEventListener("click", function() {
    if (input.value.length === 0) {
        return;
    }
    if (input.value.indexOf("@") === -1) {
        alert("Please enter a valid email address");
        return;
    }
    if (input.value.indexOf(".") === -1) {
        alert("Please enter a valid email address");
        return;
    }

    let emails = document.querySelectorAll("#yc-admin-emails li");
    for (let i = 0; i < emails.length; i++) {
        if (emails[i].textContent === input.value) {
            return;
        }
    }
    let li = document.createElement("li");
    li.className = 'list-group-item';
    console.log(input.value)
    li.textContent = input.value;
    ul.appendChild(li);
    input.value = "";
})