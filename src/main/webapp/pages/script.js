document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("formularioUsuario");

  form.addEventListener("submit", function (event) {
    event.preventDefault(); // Evita que el formulario se envíe automáticamente

    const formData = new FormData(form);
    const jsonData = {};

    formData.forEach((value, key) => {
      jsonData[key] = value;
    });

    fetch("/administracionUsuarios/usuarios", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(jsonData),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Error al enviar los datos");
          console.log(formData);
        }
        return response.json();
      })
      .then((data) => {
        // Aquí puedes manejar la respuesta exitosa
        console.log("Usuario insertado:", data);
        alert("Usuario insertado correctamente");
        form.reset(); // Limpia el formulario después de insertar
      })
      .catch((error) => {
        // Manejo de errores
        console.error("Error:", error);
        alert("No se pudo insertar el usuario");
      });
  });
});
