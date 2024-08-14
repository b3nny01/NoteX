// css
import "./AssistenteElementDiv.css";

// data
import icons from "../../data/icons";

// components
import IconDiv from "../../components/IconDiv";
import IconButton from "../../components/IconButton";

function AssistenteElementDiv(props) {
  // params
  const assistente = props.assistente;
  const handleRimuoviAssistente = props.handleRimuoviAssistente;

  //render
  return (
    <div className="assistente-element-div">
      <div className="icon-username-div">
        <IconDiv icon={icons["assistente"]} />
        <div className="username">{assistente.username}</div>
      </div>
      <div className="actions">
        <IconButton icon={icons["delete"]} onClick={handleRimuoviAssistente} />
      </div>
    </div>
  );
}

export default AssistenteElementDiv;
