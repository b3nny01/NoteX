// css
import './SignedUserLoginPage.css'

// components
import NavBar from '../../components/NavBar'
import Logo from '../../components/Logo';
import SignedUserLoginFeature from './SignedUserLoginFeature'

function SignedUserLoginPage(props)
{
    return (
        <div className="signed-user-login-page">
            <NavBar currentPage="Login"/>
            <Logo/>
            <SignedUserLoginFeature/>
        </div>
    );
}

// export
export default SignedUserLoginPage;