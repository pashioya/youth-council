const form = document.querySelector('.needs-validation')

async function checkUsername(username) {
    const response = await fetch('/api/users')
    const users = await response.json()
    console.log(users)
    for (let user of users) {
        if (user.username === username) {
            return 'Username already exists'
        }
    }
    return null
}

function checkPasswordsMatch(password, passwordConfirm) {
    if (password !== passwordConfirm) {
        return 'Passwords do not match'
    }
    return null
}

form.addEventListener('submit', async event => {
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
    }
    // check if the passwords match
    const password = document.querySelector('#password')
    const passwordConfirm = document.querySelector('#passwordConfirm')
    const passwordError = checkPasswordsMatch(password.value, passwordConfirm.value)
    if (passwordError) {
        password.setCustomValidity(passwordError)
        passwordConfirm.setCustomValidity(passwordError)
        event.preventDefault()
        event.stopPropagation()
    }
    form.classList.add('was-validated')
}, false)


