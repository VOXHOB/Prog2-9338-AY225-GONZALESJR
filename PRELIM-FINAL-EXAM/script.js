const csvData = `231372183\tBritney\tBlackesland\t78
263190634\tTheda\tMenco\t50
21606580\tCarin\tSchrader\t77`;

let students = [];

// Parse CSV
csvData.split("\n").forEach(line => {
    const parts = line.split("\t");
    students.push({
        id: parts[0],
        name: parts[1] + " " + parts[2],
        grade: parts[3]
    });
});

function render() {
    const tbody = document.getElementById("tableBody");
    tbody.innerHTML = "";

    students.forEach((s, index) => {
        const status = s.grade >= 50 ? "Passed" : "Failed"; // Set status based on grade
        tbody.innerHTML += `
            <tr>
                <td>${s.id}</td>
                <td>${s.name}</td>
                <td>${s.grade}</td>
                <td>${status}</td>
                <td><button onclick="deleteStudent(${index})">Delete</button></td>
            </tr>
        `;
    });
}

function addStudent() {
    const id = document.getElementById("id").value.trim();
    const name = document.getElementById("name").value.trim();
    const grade = document.getElementById("grade").value.trim();

    // Validate inputs
    if (!id || !name || !grade) {
        alert("Please fill in all fields.");
        return;
    }

    if (!/^\d{9}$/.test(id)) {
        alert("Student ID must be exactly 9 digits.");
        return;
    }

    if (isNaN(grade) || grade < 0 || grade > 100) {
        alert("Grade must be between 0 and 100.");
        return;
    }

    students.push({ id, name, grade });
    resetForm();
    render();
}

function deleteStudent(index) {
    if (confirm("Are you sure you want to delete this record?")) {
        students.splice(index, 1);
        render();
    }
}

function resetForm() {
    document.getElementById("id").value = "";
    document.getElementById("name").value = "";
    document.getElementById("grade").value = "";
}

render();
