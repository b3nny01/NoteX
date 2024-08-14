// css
import './RegistrationPage.css'

// components
import NavBar from '../../components/NavBar'
import Logo from '../../components/Logo';
import RegistrationFeature from './RegistrationFeature';

function RegistrationPage(props)
{
    return (
        <div className="registration-page">
            <NavBar currentPage="Registration"/>
            <Logo/>
            <RegistrationFeature/>
        </div>
    );
}

// export
export default RegistrationPage;