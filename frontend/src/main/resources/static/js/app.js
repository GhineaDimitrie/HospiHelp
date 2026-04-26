document.addEventListener('DOMContentLoaded', () => {
    const rows = document.querySelectorAll('tbody tr');
    rows.forEach((row) => {
        row.addEventListener('mouseenter', () => row.classList.add('is-hovered'));
        row.addEventListener('mouseleave', () => row.classList.remove('is-hovered'));
    });
});
