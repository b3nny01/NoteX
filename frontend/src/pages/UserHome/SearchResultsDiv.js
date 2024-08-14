// css
import "./SearchResultsDiv.css";

// components
import SignedUserResultDiv from "./SignedUserResultDiv";
import NotebookResultDiv from "./NotebookResultDiv";
import NoteResultDiv from "./NoteResultDiv";
import ReviewsModal from "./ReviewsModal";

function SearchResultsDiv(props) {
  // parameters
  const searchResult = props.searchResult;
  const showReviewsModal = props.showReviewsModal;
  const reviewsModalNote = props.reviewsModalNote;
  const reviews=props.reviews;
  const onOpenReviewsModal = props.onOpenReviewsModal;
  const onCloseReviewsModal = props.onCloseReviewsModal;

  // variables
  const resultDivs = [];

  // logic
  if (searchResult.type === "signedUsers") {
    for (let i = 0; i < searchResult.results.length; i++) {
      resultDivs[i] = (
        <SignedUserResultDiv
          signedUser={searchResult.results[i]}
          onOpenReviewsModal={onOpenReviewsModal}
          key={"signedUserResultDiv_" + i}
        />
      );
    }
  } else if (searchResult.type === "notebooks") {
    for (let i = 0; i < searchResult.results.length; i++) {
      resultDivs[i] = (
        <NotebookResultDiv
          notebook={searchResult.results[i]}
          onOpenReviewsModal={onOpenReviewsModal}
          key={"notebookResultDiv_" + i}
        />
      );
    }
  } else if (searchResult.type === "notes") {
    for (let i = 0; i < searchResult.results.length; i++) {
      resultDivs[i] = (
        <NoteResultDiv
          note={searchResult.results[i]}
          onOpenReviewsModal={onOpenReviewsModal}
          key={"noteResultDiv_" + i}
        />
      );
    }
  }

  // render
  return (
    <div className="search-results-div">
      {resultDivs}
      <ReviewsModal
        note={reviewsModalNote}
        reviews={reviews}
        show={showReviewsModal}
        onClose={onCloseReviewsModal}
      />
    </div>
  );
}

// export
export default SearchResultsDiv;
