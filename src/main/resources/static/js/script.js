const API_URL = "/user";

// Carrega a lista de usuários
if (document.getElementById("tabela-usuarios")) {
    fetch(`${API_URL}/listar`)
        .then(res => res.json())
        .then(usuarios => {
            const tbody = document.getElementById("tabela-usuarios");
            usuarios.forEach(u => {
                const tr = document.createElement("tr");
                tr.innerHTML = `
                    <td>${u.nome}</td>
                    <td>${u.username}</td>
                    <td>${u.telefone}</td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn-primary" onclick='editarUsuario(${JSON.stringify(u)})'>Editar</button>
                            <button class="btn-danger" onclick='deletarUsuario("${u.id}")'>Deletar</button>
                            <button class="btn-success" onclick='abrirInfoUsuario(${JSON.stringify(u)})'>Informações</button>
                        </div>
                    </td>
                `;
                tbody.appendChild(tr);
            });
        });
}

// Editar usuário (redireciona com dados salvos em localStorage)
function editarUsuario(usuario) {
    localStorage.setItem("usuarioEditando", JSON.stringify(usuario));
    window.location.href = "form.html";
}

function abrirInfoUsuario(usuario) {
    localStorage.setItem("usuarioInfo", JSON.stringify(usuario));
    window.location.href = "info-form.html"; // Corrigido
}

// Deletar usuário
function deletarUsuario(id) {
    if (!confirm("Tem certeza que deseja excluir este usuário e todas suas informações?")) {
        return;
    }
    
    fetch(`${API_URL}/deletar/${id}`, {
        method: "DELETE"
    })
    .then(res => {
        if (!res.ok) {
            return res.text().then(text => { throw new Error(text) });
        }
        return res.text();
    })
    .then(message => {
        alert(message);
        location.reload();
    })
    .catch(error => {
        alert("Erro ao deletar: " + error.message);
    });
}

// Carregar dados no form se estiver editando
if (document.getElementById("formUsuario")) {
    const usuario = JSON.parse(localStorage.getItem("usuarioEditando"));
    if (usuario) {
        document.getElementById("id").value = usuario.id;
        document.getElementById("nome").value = usuario.nome;
        document.getElementById("username").value = usuario.username;
        document.getElementById("telefone").value = usuario.telefone;
        document.getElementById("senha").value = ""; // senha vazia
        localStorage.removeItem("usuarioEditando");
        document.getElementById("titulo").innerText = "Editar Usuário";
    }

    document.getElementById("formUsuario").addEventListener("submit", function (e) {
        e.preventDefault();

        const id = document.getElementById("id").value;
        const usuario = {
            id: id || null,
            nome: document.getElementById("nome").value,
            username: document.getElementById("username").value,
            telefone: document.getElementById("telefone").value,
            senha: document.getElementById("senha").value
        };

        const url = id ? `${API_URL}/atualizar` : `${API_URL}/criar`;
        const method = id ? "PUT" : "POST";

       fetch(url, {
        method: method,
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(usuario)
        }).then(res => {
            if (res.ok) {
                // Armazena as credenciais para uso futuro
                localStorage.setItem('currentUsername', usuario.username);
                localStorage.setItem('currentPassword', usuario.senha);
                return res.json();
            } else {
                return res.text().then(text => { throw new Error(text) });
            }
        }).then(data => {
            window.location.href = "index.html";
        }).catch(error => {
            alert("Erro: " + error.message);
        });
    });
}
