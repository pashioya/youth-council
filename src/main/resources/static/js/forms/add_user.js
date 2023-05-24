const form = document.querySelector('.needs-validation')

export async function getUsers() {
    const response = await fetch('/api/youth-councils/users')
    return await response.json()
}

async function checkUsername(username) {
    const users = await getUsers();
    for (let user of users) {
        if (user.username === username) {
            return 'Username already exists'
        }
    }
    return null
}

async function submitForm(event) {
    if (!form.checkValidity()) {
        event.preventDefault()
        event.stopPropagation()
    }

    // check if the username already exists
    const username = document.querySelector('#username')
    const usernameError = await checkUsername(username.value)
    if (usernameError) {
        username.setCustomValidity(usernameError)
        username.parentElement.querySelector('.invalid-feedback').innerText = usernameError
        event.preventDefault()
        event.stopPropagation()
    } else {
        username.setCustomValidity('')
    }
    // check if the passwords match
    const password = document.querySelector('#password')
    const passwordConfirm = document.querySelector('#confirmPassword')
    if (password.value !== passwordConfirm.value) {
        passwordConfirm.setCustomValidity('Passwords do not match')
        passwordConfirm.parentElement.querySelector('.invalid-feedback').innerText = 'Passwords do not match'
        event.preventDefault()
        event.stopPropagation()
    } else {
        passwordConfirm.setCustomValidity('')
    }

    form.classList.add('was-validated')
}

form.addEventListener('submit', submitForm)


