const searchBar = document.getElementById('searchBar');

searchBar.addEventListener('keyup', (e) => {
    const searchString = e.target.value.toLowerCase();

    // if search bar is empty, display all containers
    if (searchString === '') {
        const containers = document.querySelectorAll('#container');
        containers.forEach((container) => {
            container.style.display = 'block';
        });
        return;
    }

    const span = document.querySelectorAll('span');

    // filter spans
    const filteredSpans = Array.from(span).filter((span) => {
        return span.textContent.toLowerCase().includes(searchString);
    });

    // hide all containers
    const containers = document.querySelectorAll('#container');
    containers.forEach((container) => {
        container.style.display = 'none';
    });

    // display containers with span
    filteredSpans.forEach((span) => {
        const container = span.closest('#container')
        container.style.display = 'block';
    });

});
