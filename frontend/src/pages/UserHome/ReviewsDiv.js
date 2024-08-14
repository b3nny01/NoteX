import "./ReviewsDiv.css";
import ReviewDiv from "./ReviewDiv";

function ReviewsDiv(props) {  
  // params
  const reviews = props.reviews;


   const reviewDivs = reviews.map(r => {
     return <ReviewDiv review={r} />
   });
  
  return (
    <div className="reviews-div">
      {reviewDivs}
    </div>
  );
}

export default ReviewsDiv;
