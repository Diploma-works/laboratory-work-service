import React, {useContext} from 'react';
import {BrowserRouter as Router, Navigate, Route, Routes} from "react-router-dom";
import Header from "./Header";
import Login from "../pages/Login";
import Register from "../pages/Register";
import LabWorkList from "../pages/LabWorkList";
import LabWorkDetail from "../pages/LabWorkDetail";
import LabWorkEdit from "../pages/LabWorkEdit";
import ExpandLabs from "../pages/ExpandLabs";
import AddLabWork from "../pages/AddLabWork";
import EditTopics from "../pages/EditTopics";
import {AuthContext} from "../context";
import Answers from "../pages/Answers";
import EditTasks from "../pages/EditTasks";

const AppRouter = () => {
    const {role} = useContext(AuthContext);

    return (
        <Router>
            <div>
                <Header/>
                <Routes>
                    <Route path="/login" element={role ? <Navigate to="/"/> : <Login/>}/>
                    <Route path="/register" element={role ? <Navigate to="/"/> : <Register/>}/>
                    <Route path="/labworks" element={<LabWorkList/>}/>
                    <Route path="/labworks/:id" element={<LabWorkDetail/>}/>
                    <Route path="/labworks/:id/edit" element={role === 'ADMIN' ? <LabWorkEdit/> : <Navigate to="/"/>}/>
                    <Route path="/labworks/expand" element={role === 'ADMIN' ? <ExpandLabs/> : <Navigate to="/"/>}/>
                    <Route path="/labworks/add-labwork" element={role === 'ADMIN' ? <AddLabWork/> : <Navigate to="/"/>}/>
                    <Route path="/topics/edit" element={role === 'ADMIN' ? <EditTopics/> : <Navigate to="/"/>}/>
                    <Route path="/tasks/edit" element={role === 'ADMIN' ? <EditTasks/> : <Navigate to="/"/>}/>
                    <Route path="/answers" element={role === 'ADMIN' ? <Answers/> : <Navigate to="/"/>}/>
                    <Route path="*" element={<LabWorkList/>}/>
                </Routes>
            </div>
        </Router>
    );
};

export default AppRouter;