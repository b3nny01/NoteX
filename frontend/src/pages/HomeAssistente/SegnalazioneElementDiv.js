// css
import "./SegnalazioneElementDiv.css"

// data
import icons from "../../data/icons";

// components
import IconDiv from "../../components/IconDiv";
import IconButton from "../../components/IconButton";

function SegnalzioneElementDiv(props) {
  // params
  const segnalzione = props.segnalzione;
  const handleRimuovisegnalzione = props.handleRimuovisegnalzione;

  //render
  return (
    <div className="segnalzione-element-div">
      <div className="icon-id-div">
        <IconDiv icon={icons["report"]} />
        <div className="id">{"ciao"}</div>
      </div>
      <div className="actions">
        <IconButton icon={icons["delete"]} onClick={handleRimuovisegnalzione} />
      </div>
    </div>
  );
}

export default SegnalzioneElementDiv;
