// css
import "./SearchFeature.css";

// data
import icons from "../../data/icons";

// components
import React, { useState } from "react";
import SearchTypeRadioDiv from "./SearchTypeRadioDiv";
import IconButton from "../../components/IconButton";
import SearchResultsDiv from "./SearchResultsDiv";
import TagListInput from "../../components/TagListInput";
import api from "../../data/api";
import APIParameter from "../../utils/APIParameter";

function SearchFeature() {
  // hooks
  const [featureState, setFeatureState] = useState("inactive");
  const [searchText, setSearchText] = useState("");
  const [searchType, setSearchType] = useState("signedUsers");
  const [tags, setTags] = useState([]);
  const [searchResult, setSearchResult] = useState({
    type: "signedUsers",
    results: [],
  });
  const [showReviewsModal, setShowReviewsModal] = useState(false);
  const [reviewsModalNote, setReviewsModalNote] = useState({
    username: "",
    notebook: "",
    name: "",
  });

  const [reviews, setReviews] = useState([]);

  // variables
  const searchTypes = [
    {
      value: "signedUsers",
      icon: icons["user"],
    },
    {
      value: "notebooks",
      icon: icons["notebook"],
    },
    {
      value: "notes",
      icon: icons["note"],
    },
  ];

  const handleSearch = (e) => {
    e.preventDefault();

    const apiParams = [
      new APIParameter("searchType", searchType),
      new APIParameter("searchText", searchText),
      new APIParameter("tags", JSON.stringify(tags)),
    ];
    api.search.fetch(apiParams).then((res) => {
      res.json().then((apiRes) => {
        if (apiRes.ok) {
          setSearchResult(apiRes.obj);
        } else {
          alert(apiRes.msg);
        }
      });
    });
  };

  const handleGetReviews = (note) => {
    const apiParams = [
      new APIParameter("reviewedUsername", note.username),
      new APIParameter("reviewedNotebookName", note.notebook),
      new APIParameter("reviewedNoteName", note.name),
    ];
    api.retrieveReviews.fetch(apiParams).then((res) => {
      res.json().then((apiRes) => {
        if (apiRes.ok) {
          setReviews(apiRes.obj);
        } else {
          alert(apiRes.msg);
        }
      });
    });
  };

  const handleTagNameChange = (i, e) => {
    let newTags = [...tags];
    newTags[i].name = e.target.value;
    setTags(newTags);
  };

  const handleTagValueChange = (i, e) => {
    let newTags = [...tags];
    newTags[i].value = e.target.value;
    setTags(newTags);
  };

  const handleNewTag = () => {
    if (tags.length < 2) {
      let newTags = [...tags];
      newTags.push({ name: "", value: "" });
      setTags(newTags);
    }
  };

  const handleDeleteTag = () => {
    let newTags = [...tags];
    newTags.pop();
    setTags(newTags);
  };

  const handleOpenReviewsModal = (note) => {
    handleGetReviews(note);
    setReviewsModalNote({ ...note });
    setShowReviewsModal(true);
  };

  const handleCloseReviewsModal = () => {
    setReviewsModalNote({
      username: "",
      notebook: "",
      note: "",
      reviews: [],
    });
    setShowReviewsModal(false);
  };

  // render
  return (
    <div
      className={"search-feature " + featureState}
      onFocus={() => {
        setFeatureState("active");
      }}
    >
      <form className="search-form" onSubmit={(e)=>{handleSearch(e)}}>
        <input
          type="text"
          className="search-bar"
          onChange={(e) => {
            setSearchText(e.target.value);
          }}
          value={searchText}
        />
        <IconButton
          onClick={() => {
            setFeatureState("inactive");
          }}
          icon={icons["cross"]}
        />
        <IconButton onClick={handleSearch} icon={icons["search"]} />
        <SearchTypeRadioDiv
          onChange={(e) => {
            setSearchType(e.target.value);
          }}
          searchTypes={searchTypes}
          searchTypeChecked={searchType}
        />
        <TagListInput
          tags={tags}
          onNameChange={handleTagNameChange}
          onValueChange={handleTagValueChange}
          onNewTag={handleNewTag}
          onDeleteTag={handleDeleteTag}
        />
        <input type="submit" style={{display:"none"}}/>
      </form>
      {
        <SearchResultsDiv
          searchResult={searchResult}
          showReviewsModal={showReviewsModal}
          reviewsModalNote={reviewsModalNote}
          reviews={reviews}
          onOpenReviewsModal={handleOpenReviewsModal}
          onCloseReviewsModal={handleCloseReviewsModal}
        />
      }
    </div>
  );
}

// export
export default SearchFeature;
