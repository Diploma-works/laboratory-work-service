import React, {useContext, useState} from 'react';
import { Link } from 'react-router-dom';
import '../styles/expandlabs.css';
import {AuthContext} from "../context";
import {toast} from "react-toastify";

const ExpandLabs = () => {
    const [modalOpen, setModalOpen] = useState(false);
    const {token} = useContext(AuthContext);

    const handleResetVariants = async () => {
        try {
            const response = await fetch('http://localhost:8080/lab-work-tasks/reset-variants', {
                method: 'DELETE',
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            if (!response.ok) {
                throw new Error("Не удалось сбросить варианты");
            }
            closeModal();
            toast.success("Варианты успешно сброшены");
        } catch (error) {
            console.error(error.message);
            toast.error(error.message);
        }
    };

    const openModal = () => {
        setModalOpen(true);
    };

    const closeModal = () => {
        setModalOpen(false);
    };

    return (
        <div className="expand-main">
            <h1>Меню</h1>
            <Link to="/labworks/add-labwork">
                <div id="add-labwork" className="expand-menu-link">Добавить лабораторную работу</div>
            </Link>
            <Link to="/topics/edit">
                <div id="edit-topics" className="expand-menu-link">Редактировать темы</div>
            </Link>
            <Link to="/tasks/edit">
                <div id="edit-tasks" className="expand-menu-link">Редактировать задания</div>
            </Link>
            <div id="reset-variants" className="expand-menu-link" onClick={openModal}>Сбросить варианты</div>
            {modalOpen && (
                <div className="modal">
                    <div className="modal-content">
                        <span className="close-button" onClick={closeModal}>&times;</span>
                        <h2>Вы уверены, что хотите сбросить все варианты?</h2>
                        <div>
                            <button onClick={handleResetVariants} className="reset-button">Сбросить</button>
                            <button onClick={closeModal} className="cancel-button">Отмена</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default ExpandLabs;