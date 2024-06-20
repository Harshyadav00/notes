import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../actions/AuthProvider';

function Header() {
    const { user, signOut } = useAuth() ;
    const navigate = useNavigate();

    const handleSignOut = () => {
        console.log("signing out...")
        signOut() ;
        navigate("/signin");
    }

    return (
        <header>
            <nav className='main-navbar'>
            <h1 className="sidebar__header-logo">Notes</h1>

                {!user ?
                    <ul className='navbar'>

                        <li>
                            <Link to="/signin" >Sign In</Link>
                        </li>
                        <li>
                            <Link to="/signup" >Sign Up</Link>
                        </li>
                    </ul>
                    :
                    <ul className='navbar'>
                        <li>
                            LoggedIn as : {user.name}
                        </li>
                        <li>
                            <Link onClick={handleSignOut}>Sign Out</Link>
                        </li>
                    </ul>
                }
            </nav>
        </header>
    );
}

export default Header;