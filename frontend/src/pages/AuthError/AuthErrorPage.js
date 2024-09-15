import './AuthErrorPage.css'
import React from 'react'
import Logo from '../../components/Logo'
import { Link } from 'react-router-dom'
import IconDiv from '../../components/IconDiv'
import icons from '../../data/icons'

function AuthErrorPage() {
  return (
    <div className='auth-error-page'>
        <Logo/>
        <div className="message-div">
            <IconDiv icon={icons["locker"]}/>
           <div className="message">To access this page you need to <Link className="page-link" to="/Login">login</Link>.<br/>if not signed click <Link className="page-link" to="/">here</Link> to return to the home page</div> 
        </div>
    </div>
  )
}

export default AuthErrorPage