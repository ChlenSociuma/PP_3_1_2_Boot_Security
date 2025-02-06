document.addEventListener("DOMContentLoaded", () => {
    const openModalBtn = document.getElementById("openModal");
    const modal = document.getElementById("myModal");
    const closeModalBtn = modal.querySelector(".close");
    const editModal = document.getElementById("editModal");
    const closeEditModalBtn = editModal.querySelector(".close2");

    openModalBtn.addEventListener("click", () => {
        document.getElementById('age').value = '';
        modal.style.display = "block";
    });

    closeModalBtn.addEventListener("click", () => {
        modal.style.display = "none";
    });

    closeEditModalBtn.addEventListener("click", () => {
        editModal.style.display = "none";
    });

    const userTable = document.getElementById("userTable");
    userTable.addEventListener("click", (event) => {
        if (event.target && event.target.classList.contains("edit-btn")) {
            const userId = event.target.dataset.userId;
            const row = event.target.closest('tr');
            const username = row.querySelector('.username').textContent.trim();
            const firstName = row.querySelector('td:nth-child(4)').textContent;
            const lastName = row.querySelector('td:nth-child(5)').textContent;
            const age = row.querySelector('td:nth-child(6)').textContent;
            const roles = row.querySelector('td:nth-child(7)').textContent;

            document.getElementById('editUserId').value = userId;
            document.getElementById('editUsername').value = username;
            document.getElementById('editFirstName').value = firstName;
            document.getElementById('editLastName').value = lastName;
            document.getElementById('editAge').value = age;
            document.getElementById('editPassword').value = "";
            document.getElementById('isAdmin2').checked = false;
            document.getElementById('isAdmin').checked = roles.includes('Admin');

            editModal.style.display = "block";
        }
    });

    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            modal.style.display = "none";
        } else if (event.target === editModal) {
            editModal.style.display = "none";
        }
    });
});