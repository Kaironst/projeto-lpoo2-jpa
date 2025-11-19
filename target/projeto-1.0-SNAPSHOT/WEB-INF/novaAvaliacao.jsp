<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>Criar Nova Avaliação</title>
  <style>
    .questao-block { border: 1px solid #ccc; padding: 12px; margin-bottom: 12px; }
    .alternativas { margin-top: 10px; padding-left: 20px; }
    .alt-block { margin-top: 6px; }
  </style>
</head>
<body>

  <h1>Nova Avaliação</h1>

  <form action="nova-avaliacao" method="post">

    <div>
      <label>
        <input type="checkbox" name="isAberta" /> Aberta
      </label>
      <label style="margin-left: 20px;">
        <input type="checkbox" name="isAnon" /> Anônima
      </label>
    </div>

    <h2>Questões</h2>
    <div id="questoesContainer">
      <!-- Dinâmico: questões serão adicionadas por JS -->
    </div>

    <button type="button" onclick="addQuestao()">+ Adicionar Questão</button>

    <br /><br />
    <button type="submit">Salvar Avaliação</button>
  </form>

  <script>
    let questaoCounter = 0;

    function addQuestao() {
      const qId = questaoCounter++;
      const container = document.getElementById("questoesContainer");

      const questaoDiv = document.createElement("div");
      questaoDiv.className = "questao-block";
      questaoDiv.dataset.qid = qId;

      questaoDiv.innerHTML = `
        <h3>Questão</h3>
        <div>
          <label>Enunciado:</label><br>
          <textarea name="questao[\${qId}].enunciado" rows="3" cols="60" required></textarea>
        </div>
        <div>
          <label>Valor:</label>
          <input type="number" step="0.1" name="questao[\${qId}].valor" required>
        </div>
        <div>
          <label>Tipo:</label>
          <select name="questao[\${qId}].tipo" class="tipo-select">
            <option value="DISCURSIVA">Discursiva</option>
            <option value="OBJETIVA_UNICA">Objetiva Única</option>
            <option value="OBJETIVA_MULTIPLA">Objetiva Múltipla</option>
          </select>
        </div>
        <div class="alternativas" style="display: none;">
          <h4>Alternativas</h4>
          <div class="alternativas-list"></div>
          <button type="button" class="add-alt">Adicionar Alternativa</button>
        </div>
        <div>
          <button type="button" class="remove-questao" style="color:red;">Remover Questão</button>
        </div>
      `;

      container.appendChild(questaoDiv);
    }

    // Delegation for “tipo” select change & add/remove buttons:
    document.addEventListener("click", function (e) {
      const addAltBtn = e.target.closest(".add-alt");
      if (addAltBtn) {
        const questaoEl = addAltBtn.closest(".questao-block");
        addAlternativa(questaoEl);
        return;
      }
      const remQuestaoBtn = e.target.closest(".remove-questao");
      if (remQuestaoBtn) {
        const questaoEl = remQuestaoBtn.closest(".questao-block");
        questaoEl.remove();
        return;
      }
      const remAltBtn = e.target.closest(".remove-alt");
      if (remAltBtn) {
        remAltBtn.closest(".alt-block").remove();
        return;
      }
    });

    document.addEventListener("change", function (e) {
      const sel = e.target.closest(".tipo-select");
      if (!sel) return;
      const questaoEl = sel.closest(".questao-block");
      const altContainer = questaoEl.querySelector(".alternativas");
      if (sel.value === "DISCURSIVA") {
        altContainer.style.display = "none";
        altContainer.querySelector(".alternativas-list").innerHTML = "";
      } else {
        altContainer.style.display = "block";
      }
    });

    function addAlternativa(questaoEl) {
      const qId = questaoEl.dataset.qid;
      const list = questaoEl.querySelector(".alternativas-list");
      const altIndex = list.children.length;

      const altDiv = document.createElement("div");
      altDiv.className = "alt-block";

      altDiv.innerHTML = `
        <input type="text" name="questao[\${qId}].alternativa[\${altIndex}].enunciado" required>
        <label>
          Corre­ta?
          <input type="checkbox" name="questao[\${qId}].alternativa[\${altIndex}].correta">
        </label>
        <button type="button" class="remove-alt" style="color:red;">Remover Alternativa</button>
      `;

      list.appendChild(altDiv);
    }
  </script>

</body>
</html>
