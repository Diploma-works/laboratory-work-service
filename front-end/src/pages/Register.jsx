import React, {useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import '../styles/register.css';
import {toast} from "react-toastify";

const Register = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const navigate = useNavigate();

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
        if (!checkPasswords()) {
            toast.error("Введенные пароли не совпадают");
            return;
        }
            try {
                const response = await fetch('http://localhost:8080/auth/register', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({username, password})
                });

                if (!response.ok) {
                    const errorText = await response.text();
                    throw new Error(errorText || "Не удалось зарегистрироваться");
                }
                navigate('/login');
                toast.success("Регистрация выполнена успешно")
            } catch (error) {
                console.error('Ошибка:', error.message);
                toast.error(error.message || "Не удалось зарегистрироваться");
            }
    };

    function isValidUsername(username) {
        return username.length >= 3 && username.length <= 32;
    }

    function isValidPassword(password) {
        return password.length >= 6;
    }

    function checkPasswords() {
        return password === confirmPassword;
    }

    return (
        <div className="page-container">
            <div className="register-form-container">
                <h2 className="auth-label">Регистрация</h2>
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
                    <div>
                        <label htmlFor="password">Подтверждение пароля:</label>
                        <br/>
                        <input
                            type="password"
                            id="password"
                            className="auth-input"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                        />
                    </div>
                    <div className="submit-container">
                        <button type="submit">Зарегистрироваться</button>
                    </div>
                </form>
                <p>Уже зарегистрированы? <Link to="/login">Войти</Link></p>
            </div>
        </div>
    );
};

export default Register;