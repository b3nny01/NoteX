// css
import "./ReviewsModal.css";

// data
import icons from "../../data/icons";
import pages from "../../data/pages";

// components
import IconButton from "../../components/IconButton";
import ReviewsDiv from "./ReviewsDiv";
import { Link } from "react-router-dom";


function ReviewsModal(props) {
  // parameters
  const note=props.note;
  const reviews=props.reviews;
  const show=props.show;
  const onClose=props.onClose;

  // variables
  const combinedClass="reviews-modal-background "+(show?"shown":"hidden");

  // render
  return (
    <div className={ combinedClass}>
      <div className="reviews-modal">
        <IconButton icon={icons["cross"]} onClick={onClose}/>
        <div className="title">Reviews</div>
        <ReviewsDiv reviews={reviews}/>
        <Link to={pages["sendReview"].path+"/"+note.username+"/"+note.notebook+"/"+note.name}>
          <IconButton icon={icons["send"]}/>
        </Link>
      </div>
    </div>
  );
}

// export
export default ReviewsModal;
