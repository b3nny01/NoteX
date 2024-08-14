// css
import './SendReviewPage.css'

// components
import NavBar from '../../components/NavBar'
import Logo from '../../components/Logo';
import SendReviewFeature from "./SendReviewFeature"

function SendReviewPage(props)
{
    return (
        <div className="send-review-page">
            <NavBar currentPage="sendReview"/>
            <Logo/>
            <SendReviewFeature/>
        </div>
    );
}

// export
export default SendReviewPage;