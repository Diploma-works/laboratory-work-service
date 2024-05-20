import React, {useEffect, useState} from 'react';
import {jwtDecode} from 'jwt-decode';
import AppRouter from "./component/AppRouter";
import {AuthContext} from "./context";
import {ToastContainer} from "react-toastify";

function App() {
    const [role, setRole] = useState(null);
    const [user, setUser] = useState(null);
    const [token, setToken] = useState(null);

    useEffect(() => {
        async function verifyToken(jwtToken) {
            const response = await fetch(`http://localhost:8080/auth/verify-token`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({jwtToken})
            });
            if (response.ok) {
                const decodedToken = jwtDecode(jwtToken);
                const authRole = decodedToken.roles[0];
                setRole(authRole);
                setUser(decodedToken.sub);
                setToken(jwtToken);
            } else {
                setRole(null);
                setUser(null);
                setToken(null);
                localStorage.removeItem('token');
            }
        }
        const jwt = localStorage.getItem('token');
        if (jwt) {
            verifyToken(jwt);
        }
    }, [],);

    return (
        <AuthContext.Provider value={{
            role,
            setRole,
            user,
            setUser,
            token,
            setToken
        }}>
            <AppRouter/>
            <ToastContainer />
        </AuthContext.Provider>
    );
}

export default App;