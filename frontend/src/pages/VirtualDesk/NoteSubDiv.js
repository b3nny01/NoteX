// css
import "./NoteSubDiv.css";

// data
import icons from "../../data/icons";

// components
import IconButton from "../../components/IconButton";
import IconDiv from "../../components/IconDiv";

function NoteSubDiv(props) {
  // parameters
  const index = props.index;
  const note = props.note;
  const onUpdateNote = props.onUpdateNote;
  const onDeleteNote = props.onDeleteNote;

  // render
  return (
    <div className="note-sub-div">
      <div className="icon-name-div">
        <img className="icon-img" src={icons["note"].src} />
        <div className="name">{note.name}</div>
      </div>

      <div className="actions">
        <IconButton
          icon={icons["delete"]}
          onClick={(e) => {
            onDeleteNote(index);
          }}
        />
        <IconButton
          icon={icons["edit"]}
          onClick={() => {
            onUpdateNote(index);
          }}
        />
        <a
          href={note.serverContextFile + "/" + note.filePath}
          target="_blank"
          download={note.name}
        >
          <IconDiv icon={icons["download"]} />
        </a>
      </div>
    </div>
  );
}

// export
export default NoteSubDiv;
