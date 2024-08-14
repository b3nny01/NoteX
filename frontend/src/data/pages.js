import { Link } from "react-router-dom";
const pages=[];
pages["search"]={
    name: "Search",
    path: "/",
};

pages["virtualDesk"]={
    name:"Virtual Desk",
    path:"/VirtualDesk",
};

pages["sendReview"]={
    name:"Send Review",
    path:"/SendReview",
};

pages["registration"]={
    name:"Sign In",
    path:"/Registration",
};

pages["signedUserLogin"]={
    name:"Login",
    path:"/Login",
};
export default pages;