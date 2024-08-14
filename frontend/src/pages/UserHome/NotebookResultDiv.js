// css
import "./ResultDiv.css";
import "./NotebookResultDiv.css";

// data
import icons from "../../data/icons";

// components
import IconDiv from "../../components/IconDiv";
import ExpandCheckBox from "./ExpandCheckBox";
import NoteResultDiv from "./NoteResultDiv";
import ScoreDiv from "../../components/ScoreDiv";

function NotebookResultDiv(props) {
  // parameters
  const notebook = props.notebook;
  const onOpenReviewsModal = props.onOpenReviewsModal;

  // logic
  const noteResultDivs = notebook.notes.map((n, i) => {
    return (
      <NoteResultDiv
        note={n}
        onOpenReviewsModal={onOpenReviewsModal}
        key={"noteResultDiv_" + i}
      />
    );
  });

  // render
  return (
    <div className="notebook result-div">
      <div className="first-line">
        <IconDiv icon={icons["notebook"]} />
        <div className="text name">{notebook.name}</div>
        <ScoreDiv score={notebook.score} />
        <ExpandCheckBox />
      </div>
      <div className="notes expansible-box">{noteResultDivs}</div>
    </div>
  );
}

//export
export default NotebookResultDiv;
