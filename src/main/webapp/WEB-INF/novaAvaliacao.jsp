<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
    <title>Nova Avaliação</title>

    <style>
        .questao-block {
            border: 1px solid #888;
            padding: 15px;
            margin: 15px 0;
            border-radius: 8px;
        }
        .alt-block {
            margin-left: 20px;
            margin-top: 5px;
        }
    </style>

<script>
    let questaoIdCounter = 0;  // unique IDs forever

    function addQuestao() {
        const container = document.getElementById("questoesContainer");
        const qId = questaoIdCounter++;   // unique ID

        const html = `
        <div class="questao-block" id="questao_${qId}">
            <h3>Questão</h3>

            <label>Enunciado:</label><br/>
            <textarea name="questao[${qId}].enunciado" rows="3" cols="60" required></textarea><br/><br/>

            <label>Valor:</label>
            <input type="number" step="0.1" name="questao[${qId}].valor" required/><br/><br/>

            <label>Tipo:</label>
            <select name="questao[${qId}].tipo"
                    onchange="toggleAlternativas(${qId}, this.value)">
                <option value="DISCURSIVA">Discursiva</option>
                <option value="OBJETIVA_UNICA">Objetiva Única</option>
                <option value="OBJETIVA_MULTIPLA">Objetiva Múltipla</option>
            </select>

            <div id="alternativas_${qId}" style="display:none; margin-top:10px;">
                <h4>Alternativas</h4>
                <div id="altContainer_${qId}"></div>

                <button type="button" onclick="addAlternativa(${qId})">
                    Adicionar Alternativa
                </button>
            </div>

            <br/>
            <button type="button"
                    onclick="removeQuestao(${qId})"
                    style="color:red;">
                Remover Questão
            </button>
        </div>
        `;

        container.insertAdjacentHTML("beforeend", html);
    }

    function removeQuestao(qId) {
        const el = document.getElementById(`questao_${qId}`);
        if (el) el.remove();
    }

    function toggleAlternativas(qId, tipo) {
        const div = document.getElementById(`alternativas_${qId}`);
        div.style.display = (tipo === "DISCURSIVA") ? "none" : "block";
    }

    function addAlternativa(qId) {
        const container = document.getElementById(`altContainer_${qId}`);
        const altIndex = container.children.length;

        const html = `
        <div class="alt-block" id="questao_${qId}_alt_${altIndex}">
            <label>Alternativa ${altIndex + 1}:</label><br/>
            <input type="text"
                   name="questao[${qId}].alternativa[${altIndex}].enunciado"
                   required/>

            <label style="margin-left:10px;">
                Correta?
                <input type="checkbox"
                       name="questao[${qId}].alternativa[${altIndex}].correta"/>
            </label>

            <button type="button"
                    onclick="removeAlternativa(${qId}, ${altIndex})"
                    style="color:red; margin-left:10px;">
                Remover
            </button>
        </div>
        `;

        container.insertAdjacentHTML("beforeend", html);
    }

    function removeAlternativa(qId, altIndex) {
        const el = document.getElementById(`questao_${qId}_alt_${altIndex}`);
        if (el) el.remove();
    }
</script>
</head>

<body>
    <h1>Criar Nova Avaliação</h1>

    <form action="avaliacao-create" method="post">

        <label>Avaliação Aberta?</label>
        <input type="checkbox" name="isAberta"/> <br/><br/>

        <label>Avaliação Anônima?</label>
        <input type="checkbox" name="isAnon"/> <br/><br/>

        <h2>Questões</h2>

        <div id="questoesContainer"></div>

        <button type="button" onclick="addQuestao()">Adicionar Questão</button>

        <br/><br/>
        <button type="submit">Salvar Avaliação</button>

    </form>

</body>
</html>
