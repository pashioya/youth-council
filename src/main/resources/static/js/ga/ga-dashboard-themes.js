var rows = document.querySelectorAll(".table-row");
rows.forEach(function (row) {
        row.addEventListener("click", function () {
                window.location.href = this.dataset.href;
            }
        );
    }
);