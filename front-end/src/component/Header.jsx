import {Link} from "react-router-dom";
import '../styles/header.css';
import {useContext, useState} from "react";
import {AuthContext} from "../context";
import {toast} from "react-toastify";

const Header = () => {
    const {role, user, setRole, setUser} = useContext(AuthContext);
    const [showLogout, setShowLogout] = useState(false);

    const handleMouseEnter = () => {
        setShowLogout(true);
    };

    const handleMouseLeave = () => {
        setShowLogout(false);
    };

    const handleLogout = () => {
        setShowLogout(false);
        setRole(null);
        setUser(null);
        localStorage.removeItem('token');
        toast.success("Выход выполнен успешно");
    };

    return (
        <div className="header">
            <div className="header-container">
                <div className="header-left">
                    <Link to="/labworks">
                        <div className="header-navigation">Главная страница</div>
                    </Link>
                    {role === 'ADMIN' && (
                        <>
                            <Link to="/labworks/expand">
                                <div id="expand" className="header-navigation">Расширить лабораторные работы</div>
                            </Link>
                            <Link to="/answers">
                                <div id="answers" className="header-navigation">Ответы</div>
                            </Link>
                        </>
                    )}
                </div>
                <div className="header-right">
                    {user !== null ? (
                        <div className="header-username"
                             onMouseEnter={handleMouseEnter}
                             onMouseLeave={handleMouseLeave}>
                            {user}
                            {showLogout && <div className="dropdown-menu" onClick={handleLogout}>Выйти</div>}
                        </div>
                    ) : (
                        <Link to="/login">
                            <button className="login-button">Войти</button>
                        </Link>
                    )}
                </div>
            </div>
        </div>
    );
};

export default Header;