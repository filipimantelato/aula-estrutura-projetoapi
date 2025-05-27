const API_URL = "/info";

document.addEventListener("DOMContentLoaded", () => {
    const usuario = JSON.parse(localStorage.getItem("usuarioInfo"));

    if (!usuario) {
        alert("Usuário não encontrado.");
        window.location.href = "index.html";
        return;
    }

    // Preenche dados do usuário
    document.getElementById("nomeUser").innerText = usuario.nome;
    document.getElementById("usernameUser").innerText = usuario.username;
    document.getElementById("telefoneUser").innerText = usuario.telefone;
    document.getElementById("idUser").value = usuario.id;

    // Buscar se já existem informações para esse usuário
    fetch(`${API_URL}/listar`)
        .then(res => res.json())
        .then(infoList => {
            const info = infoList.find(i => i.idUser === usuario.id);
            if (info) {
                document.getElementById("id").value = info.id;
                document.getElementById("idade").value = info.idade;
                document.getElementById("altura").value = info.altura;
                document.getElementById("nacionalidade").value = info.nacionalidade;
                document.getElementById("endereco").value = info.endereco;
            }
        });

    // Salvar ou atualizar
    document.getElementById("formInfo").addEventListener("submit", function (e) {
        e.preventDefault();

    // Recupera as credenciais armazenadas
    const username = localStorage.getItem('currentUsername');
    const password = localStorage.getItem('currentPassword');
    
    if (!username || !password) {
        alert("Faça login novamente!");
        window.location.href = "form.html";
        return;
    }

    const usuario = JSON.parse(localStorage.getItem("usuarioInfo"));
    if (!usuario) {
        alert("Dados do usuário não encontrados!");
        return;
    }

    const info = {
        id: document.getElementById("id").value || null,
        idUser: usuario.id,
        username: usuario.username,
        idade: document.getElementById("idade").value,
        altura: document.getElementById("altura").value,
        nacionalidade: document.getElementById("nacionalidade").value,
        endereco: document.getElementById("endereco").value
    };

    const url = info.id ? `${API_URL}/atualizar` : `${API_URL}/criar`;
    const method = info.id ? "PUT" : "POST";

    fetch(url, {
        method: method,
        headers: { 
            "Content-Type": "application/json",
            "Authorization": "Basic " + btoa(username + ":" + password)
        },
        body: JSON.stringify(info)
    })
    .then(res => {
        if (!res.ok) {
            return res.text().then(text => { throw new Error(text) });
        }
        return res.json();
    })
    .then(data => {
        alert("Informações salvas com sucesso!");
        window.location.href = "index.html";
    })
    .catch(error => {
        console.error("Erro:", error);
        alert("Erro ao salvar: " + error.message);
    });
    });
    });

