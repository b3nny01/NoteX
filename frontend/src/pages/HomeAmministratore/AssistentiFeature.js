// css
import "./AssistentiFeature.css";

// hooks
import React, { useEffect, useState } from "react";

// components
import AssistenteElementDiv from "./AssistenteElementDiv";
import IconButton from "../../components/IconButton";
import icons from "../../data/icons";
import ModalNuovoAssistente from "./ModalNuovoAssistente";

function AssistentiFeature() {
  // hooks
  const [assistenti, setAssistenti] = useState([]);
  const [nuovoAssistente, setNuovoAssistente] = useState({
    username: "",
    password: "",
  });
  const [showModalNuovoAssistente, setShowModalNuovoAssistente] =
    useState(false);

  // methods
  const handleUpdateAssistenti = () => {
    setAssistenti([
      { username: "Assistente_1" },
      { username: "Assistente_2" },
      { username: "Assistente_3" },
    ]);
  };

  const handleRimuoviAssistente = (i) => {
    const newAssistenti = { ...assistenti };
    newAssistenti.slice(0, i).concat(newAssistenti.slice(i + 1));
    setAssistenti(newAssistenti);
  };

  const handleOpenModalNuovoAssistente = () => {
    setShowModalNuovoAssistente(true);
  };

  const handleCloseModalNuovoAssistente = () => {
    setNuovoAssistente({
      username: "",
      password: "",
    });
    setShowModalNuovoAssistente(false);
  };

  const handleUsernameChange = (e) => {
    let newNuovoAssistente = { ...nuovoAssistente };
    newNuovoAssistente.username = e.target.value;
  };

  const handlePasswordChange = (e) => {
    let newNuovoAssistente = { ...nuovoAssistente };
    newNuovoAssistente.password = e.target.value;
  };

  const handleCreaNuovoAssistente = (e) => {
    handleCloseModalNuovoAssistente();
  };

  // effects
  useEffect(handleUpdateAssistenti, []);

  // variables
  const assistenteElementDivs = [];

  // logic
  for (let i = 0; i < assistenti.length; i++) {
    assistenteElementDivs[i] = (
      <AssistenteElementDiv
        assistente={assistenti[i]}
        key={assistenti[i].username}
        index={i}
      />
    );
  }
  assistenteElementDivs.push(
    <IconButton icon={icons["add"]} onClick={handleOpenModalNuovoAssistente} />
  );

  // render
  return (
    <div className="assistenti-feature">
      <div className="text">ASSISTENTI</div>
      {assistenteElementDivs}
      <ModalNuovoAssistente
        show={showModalNuovoAssistente}
        onClose={handleCloseModalNuovoAssistente}
        nuovoAssistente={nuovoAssistente}
        onUsernameChange={handleUsernameChange}
        onPasswordChange={handlePasswordChange}
        onSubmit={handleCreaNuovoAssistente}
      />
    </div>
  );
}

export default AssistentiFeature;
