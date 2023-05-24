const allRows = document.querySelectorAll('.table-row');

allRows.forEach(row => {
    row.addEventListener('click', () => {
        window.location.href = row.getAttribute('data-href')
    });
}
);