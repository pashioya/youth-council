import {deleteUserAccount} from "./delete_user.js";

const deleteButton = document.getElementById('delete_user');
deleteButton.addEventListener('click', async () => {
    let div = deleteButton.parentNode.parentNode;
    let one = div.parentNode.parentNode;
    let two = one.parentNode.parentNode;
    let three = two.parentNode;
    let id = three.id;
    let response = await deleteUserAccount(id);
    if (response.status === 200) {
        window.location.href = '/';
    } else {
        alert("Something went wrong");
    }
});
