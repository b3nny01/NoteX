// css
import './UtenteRimossoElementDiv.css'

// data
import icons from "../../data/icons";

// components
import IconDiv from "../../components/IconDiv"
import IconButton from '../../components/IconButton';

function UtenteRimossoElementDiv(props) {
  // params
  const utenteRimosso = props.utenteRimosso;

  // render
  return (
    <div className="utente-rimosso-element-div">
      <div className="icon-username-div">
        <IconDiv icon={icons["utente"]}/>
        <div className="username">{utenteRimosso.username}</div>
      </div>
      <div className="actions">
        <IconButton icon={icons["upload"]}/>
      </div>
    </div>
  );
}

export default UtenteRimossoElementDiv;
