import "./SendReviewFeature.css";
import React, { useContext, useState } from "react";
import { Link, useParams } from "react-router-dom";
import api from "../../data/api";
import APIParameter from "../../utils/APIParameter";
import API from "../../utils/API";
import APIs from "../../data/api";
import pages from "../../data/pages";

function SendReviewFeature() {
  const {
    usernameURL = "",
    notebookURL = "",
    noteURL = "",
  } = useParams();
  const [reviewedNoteId, setReviewedNoteId] = useState({
    username: usernameURL,
    notebook: notebookURL,
    note: noteURL,
  });
  const [score, setScore] = useState("");
  const [text, setText] = useState("");
  const [resultDiv, setResultDiv] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    const apiParams = [
      new APIParameter("reviewedUsername", reviewedNoteId.username),
      new APIParameter("reviewedNotebookName", reviewedNoteId.notebook),
      new APIParameter("reviewedNoteName", reviewedNoteId.note),
      new APIParameter("text", text),
      new APIParameter("score", score)
    ]
    APIs.sendReview.fetch(apiParams).then((res) => {
      res.json().then((apiRes) => {
        if (!apiRes.ok) {
          alert(apiRes.msg);
          setResultDiv(<div className="send-review-result-div"><p>Something has gone wrong</p></div>)
        } else {
          setResultDiv(<div className="send-review-result-div"><p>Review sent, click&nbsp;
            <Link to={pages["search"].path}>here</Link>
            &nbsp;to return to search page</p></div>)
        }
      })
    });
  };

  return (
    <div className="send-review-feature">
      <div className="title">Send Review</div>
      <form className="send-review-form" onSubmit={handleSubmit}>
        <div className="id-nota-div">
          <input
            type="text"
            name="reviewedNoteUsername"
            placeholder="username"
            value={reviewedNoteId.username}
            onChange={(e) =>
              setReviewedNoteId({ ...reviewedNoteId, username: e.target.value })
            }
          />
          <label> {'>'} </label>
          <input
            type="text"
            name="reviewedNoteNotebook"
            placeholder="notebook"
            value={reviewedNoteId.notebook}
            onChange={(e) =>
              setReviewedNoteId({
                ...reviewedNoteId,
                notebook: e.target.value,
              })
            }
          />
          <label>{'>'}</label>
          <input
            type="text"
            placeholder="note"
            name="reviewedNote"
            value={reviewedNoteId.note}
            onChange={(e) =>
              setReviewedNoteId({ ...reviewedNoteId, note: e.target.value })
            }
          />
        </div>

        <input
          type="number"
          name="score"
          placeholder="score"
          value={score}
          onChange={(e) => {
            setScore(e.target.value);
          }}
        />
        <input
          type="textarea"
          name="text"
          placeholder="text"
          value={text}
          onChange={(e) => {
            setText(e.target.value);
          }}
        />
        <input type="submit" name="submit" value="Send Review" />
      </form>
      {resultDiv}
    </div>
  );
}

export default SendReviewFeature;
