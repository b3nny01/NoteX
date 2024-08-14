// css
import "./ModalNuovoAssistente.css";

// data
import icons from "../../data/icons";

// components
import EtichetteGroupInput from "../../components/EtichetteGroupInput";
import IconButton from "../../components/IconButton";


function ModalNuovoAssistente(props) {
  // parameters
  const show=props.show;
  const nuovoAssistente=props.nuovoAssistente;
  const onClose=props.onClose;
  const onUsernameChange=props.onUsernameChange;
  const onPasswordChange=props.onPasswordChange;
  const onSubmit=props.onSubmit;

  // variables
  const combinedClass="modal-nuovo-assistente-background "+(show?"shown":"hidden");

  // render
  return (
    <div className={ combinedClass}>
      <div className="modal-nuovo-assistente">
        <IconButton icon={icons["cross"]} onClick={onClose}/>
        <div className="title">Creazione Assistente</div>
        <form className="nuovo-assistente-form" onSubmit={onSubmit}>
          <input
            type="text"
            name="username"
            onChange={onUsernameChange}
            value={nuovoAssistente.username}
            placeholder="username"
          />
           <input
            type="password"
            name="password"
            onChange={onPasswordChange}
            value={nuovoAssistente.password}
            placeholder="password"
          />

          <input type="submit" value="crea"/>
        </form>
      </div>
    </div>
  );
}

// export
export default ModalNuovoAssistente;
