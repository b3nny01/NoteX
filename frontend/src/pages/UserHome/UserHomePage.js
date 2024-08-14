// css
import './UserHomePage.css';

// components
import NavBar from '../../components/NavBar';
import Logo from '../../components/Logo';
import SearchFeature from './SearchFeature';
import CarouselDiv from './CarouselDiv'


function UserHomePage()
{
    // render
    return (
        <div className="user-home-page">
            <div className="left-div">
                <CarouselDiv/>
            </div>
            <div className="right-div">
                <NavBar currentPage="Search"/>
                <Logo/>
                <SearchFeature/>
            </div>
        </div>
    );
}

// export
export default UserHomePage;