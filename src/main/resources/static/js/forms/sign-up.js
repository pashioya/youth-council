const form = document.querySelector('.needs-validation')


form.addEventListener('submit', event => {
    if (!form.checkValidity()) {
        event.preventDefault()
        event.stopPropagation()
    }

    // get all the usernames
    const username = document.querySelector('#username')
    fetch('/api/users')
        .then(response => response.json())
        .then(users => {
                console.log(users)
                for (let user of users) {
                    if (users[i].username === username.value) {
                        username.setCustomValidity('Username already exists')
                        event.preventDefault()
                        event.stopPropagation()
                    }
                }
            }
        )


    // check if the passwords match
    const password = document.querySelector('#password')
    const passwordConfirm = document.querySelector('#confirmPassword')
    if (password.value !== passwordConfirm.value) {
        console.log('passwords do not match')
        console.log("password: " + password.value)
        console.log("passwordConfirm: " + passwordConfirm.value)
        passwordConfirm.setCustomValidity('Passwords do not match')
        event.preventDefault()
        event.stopPropagation()
    }

    // fetch the zip codes from the server
    const zipCodeDataList = document.querySelector('datalist')
    fetch('/api/youth-councils/zip-codes')
        .then(response => response.json())
        .then(zipCodes => {
                for (let i = 0; i < zipCodes.length; i++) {
                    const option = document.createElement('option')
                    option.value = zipCodes[i].PostCode
                    zipCodeDataList.appendChild(option)
                }
            }
        )

    // if the entered zip code is not in the list
    const enteredPostCode = document.querySelector('#postCode')
    const postCodeOptions = zipCodeDataList.querySelectorAll('option')
    let postCodeIsValid = false
    for (let i = 0; i < postCodeOptions.length; i++) {
        if (postCodeOptions[i].value === enteredPostCode.value) {
            console.log('post code is valid')
            console.log(postCodeOptions[i].value)
            postCodeIsValid = true
            break
        } else {
            postCodeIsValid = false
        }
    }
    if (!postCodeIsValid) {
        enteredPostCode.setCustomValidity('Please enter a valid zip code')
        event.preventDefault()
        event.stopPropagation()
    }
    form.classList.add('was-validated')
}, false)


