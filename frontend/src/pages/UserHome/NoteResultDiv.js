// css
import "./ResultDiv.css";
import "./NoteResultDiv.css";

// data
import icons from "../../data/icons";

// components
import IconDiv from "../../components/IconDiv";
import ScoreDiv from "../../components/ScoreDiv";
import IconButton from "../../components/IconButton";

function NoteResultDiv(props) {
  // proprieties
  const note = props.note;
  const onOpenReviewsModal = props.onOpenReviewsModal;

  // render
  return (
    <div className="note result-div">
      <div className="first-line">
        <IconDiv icon={icons["note"]} />
        <div className="text nome"><p>{note.name}</p></div>
        <IconButton icon={icons["review"]} onClick={()=>{onOpenReviewsModal(note)}} />
        <ScoreDiv score={note.score} />
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
export default NoteResultDiv;
