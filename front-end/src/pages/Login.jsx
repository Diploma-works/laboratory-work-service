import React, {useContext, useState} from 'react';
import {AuthContext} from "../context";
import {Link} from 'react-router-dom';
import '../styles/login.css';
import {jwtDecode} from "jwt-decode";
import {toast} from "react-toastify";

const Login = () => {
    const {setRole} = useContext(AuthContext);
    const {setUser} = useContext(AuthContext);
    const {setToken} = useContext(AuthContext);

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();
        if (!isValidUsername(username)) {
            toast.error("Имя пользователя должно содержать от 3 до 32 символов");
            return;
        }
        if (!isValidPassword(password)) {
            toast.error("Пароль должен содержать не менее 6 символов");
            return;
        }
        try {
            const response = await fetch('http://localhost:8080/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({username, password})
            });

            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText || "Не удалось войти");
            }

            const data = await response.json();
            localStorage.setItem('token', data.token);
            setToken(data.token);
            const decodedToken = jwtDecode(data.token);
            const authRole = decodedToken.roles[0];
            setRole(authRole);
            setUser(decodedToken.sub);
            toast.success("Вход выполнен успешно")
        } catch (error) {
            console.error(error.message);
            toast.error(error.message);
        }
    };

    function isValidUsername(username) {
        return username.length >= 3 && username.length <= 32;
    }

    function isValidPassword(password) {
        return password.length >= 6;
    }

    return (
        <div className="page-container">
            <div className="login-form-container">
                <h2 className="auth-label">Вход</h2>
                <form onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="username">Имя пользователя:</label>
                        <br/>
                        <input
                            type="text"
                            id="username"
                            className="auth-input"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </div>
                    <div>
                        <label htmlFor="password">Пароль:</label>
                        <br/>
                        <input
                            type="password"
                            id="password"
                            className="auth-input"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>
                    <div className="submit-container">
                        <button type="submit">Войти</button>
                    </div>
                </form>
                <p>Еще не зарегистрированы? <Link to="/register">Регистрация</Link></p>
            </div>
        </div>
    );
};

export default Login;