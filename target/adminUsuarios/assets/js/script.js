document.addEventListener("DOMContentLoaded", function () {
  cargarUsuarios();
});

function cargarUsuarios() {
  fetch("/administracionUsuarios/usuarios")
    .then((response) => response.json())
    .then((data) => {
      renderizarUsuarios(data);
    })
    .catch((error) => {
      console.error("Error al cargar usuarios:", error);
    });
}

function renderizarUsuarios(usuarios) {
  const usuariosTable = document.getElementById("usuariosTable");
  const tbody = usuariosTable.querySelector("tbody");
  tbody.innerHTML = ""; // Limpiar tabla antes de actualizar

  usuarios.forEach((usuario) => {
    const tr = document.createElement("tr");
    tr.innerHTML = `
            <td>${usuario.id}</td>
            <td>${usuario.name}</td>
            <td>${usuario.email}</td>
            <td>${usuario.userName}</td>
            <td>${usuario.password}</td>
            <td>${usuario.contactNo}</td>
            <td>${usuario.city}</td>
            <td>${usuario.address}</td>
            <td>${usuario.role}</td>
            <td>${new Date(usuario.created_at).toLocaleString()}</td>
            <td>${new Date(usuario.updated_at).toLocaleString()}</td>
            <td>
                <button class="delete-btn" data-id="${
                  usuario.id
                }">Eliminar</button>
            </td>
        `;
    tbody.appendChild(tr);
  });

  // Agregar event listeners a los botones de eliminar
  tbody.querySelectorAll(".delete-btn").forEach((btn) => {
    btn.addEventListener("click", eliminarUsuario);
  });
}

function redirigirInsertarUsuario() {
  window.location.href = "/administracionUsuarios/pages/insertarUsuario.html";
}
function eliminarUsuario(event) {
  const id = event.target.getAttribute("data-id");
  console.log("Eliminar usuario con ID:", id);
  if (confirm("¿Estás seguro de que quieres eliminar este usuario?")) {
    fetch(`/administracionUsuarios/usuarios?id=${id}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (response.ok) {
          alert("Usuario eliminado correctamente");
          cargarUsuarios(); // Actualizar la tabla después de eliminar
        } else {
          console.error("Error al eliminar usuario");
          alert("Ocurrió un error al eliminar el usuario");
        }
      })
      .catch((error) => {
        console.error("Error en la solicitud DELETE:", error);
        alert("Ocurrió un error en la solicitud para eliminar el usuario");
      });
  }
}
