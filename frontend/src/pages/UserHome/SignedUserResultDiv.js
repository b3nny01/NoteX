// css
import "./ResultDiv.css";
import "./SignedUserResultDiv.css";

// data
import icons from "../../data/icons";

// components
import IconDiv from "../../components/IconDiv";
import ExpandCheckBox from "./ExpandCheckBox";
import NotebookResultDiv from "./NotebookResultDiv";
import ScoreDiv from "../../components/ScoreDiv";

function SignedUserResultDiv(props) {
  // parameters
  const signedUser = props.signedUser;
  const onOpenReviewsModal = props.onOpenReviewsModal;

  // logic
  const notebookResultDivs = signedUser.notebooks.map((n, i) => {
    return (
      <NotebookResultDiv
        notebook={n}
        onOpenReviewsModal={onOpenReviewsModal}
        key={"notebookResultDiv_" + i}
      />
    );
  });

  // render
  return (
    <div className="signed-user result-div">
      <div className="first-line">
        <IconDiv icon={icons["user"]} />
        <div className="text username"><p>{signedUser.username}</p></div>
        <ScoreDiv score={signedUser.score} />
        <ExpandCheckBox />
      </div>
      <div className="notebooks expansible-box">
        {notebookResultDivs}
      </div>
    </div>
  );
}

// export
export default SignedUserResultDiv;
