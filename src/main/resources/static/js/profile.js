import {deleteUser} from "./delete/delete_user.js";

const deleteButtons = document.querySelectorAll('.delete-user');

console.log(deleteButtons);
deleteButtons.forEach(button => {
        button.addEventListener('click', async () => {
            let id = parseInt(button.getAttribute('data-user-id'));
            console.log(id);
            console.log(typeof id)
            let response = await deleteUser(parseInt(id));
            if (response.status === 200) {
                window.location.href = "/dashboard/users";
            } else {
                alert("Something went wrong");
            }
        });
    }
);