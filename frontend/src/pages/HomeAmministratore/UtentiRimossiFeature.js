// css
import "./UtentiRimossiFeature.css";

// hooks
import React, { useEffect, useState } from "react";

// components
import UtenteRimossoElementDiv from "./UtenteRimossoElementDiv";

function UtentiRimossiFeature() {
  // hooks
  const [utentiRimossi, setUtentiRimossi] = useState([]);
  const handleUpdateAssistenti = () => {
    setUtentiRimossi([
      { username: "Utente_1" },
      { username: "Utente_2" },
      { username: "Utente_3" },
    ]);
  };

  // methods
  const handleRimuoviutente = (i) => {
    const newAssistenti = { ...utentiRimossi };
    newAssistenti.slice(0, i).concat(newAssistenti.slice(i + 1));
    setUtentiRimossi(newAssistenti);
  };

  // effects
  useEffect(handleUpdateAssistenti, []);

  // variables
  const UtenteRimossoElementDivs = [];

  // logic
  for (let i = 0; i < utentiRimossi.length; i++) {
    UtenteRimossoElementDivs[i] = (
      <UtenteRimossoElementDiv
        utenteRimosso={utentiRimossi[i]}
        key={utentiRimossi[i].username}
        index={i}
      />
    );
  }

  // render
  return (
    <div className="utenti-rimossi-feature">
      <div className="text">UTENTI RIMOSSI</div>
      {UtenteRimossoElementDivs}
    </div>
  );
}

export default UtentiRimossiFeature;
