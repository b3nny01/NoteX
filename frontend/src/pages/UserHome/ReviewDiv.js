import "./ReviewDiv.css";
import IconDiv from "../../components/IconDiv";
import icons from "../../data/icons";
import ScoreDiv from "../../components/ScoreDiv";

function ReviewDiv(props) {
  const review=props.review;
  
  return (
    <div className="review-div">
      <div className="first-line">
        <IconDiv icon={icons["user"]} />
        <div className="username">{review.reviewerUsername}</div>
        <ScoreDiv score={review.score} />
      </div>
      <div className="text">{review.text}</div>
    </div>
  );
}

export default ReviewDiv;
