import React from 'react';
import '../styles/labworkinfo.css';

const LabWorkInfo = ({labwork}) => {
    return (
        <div className="labwork">
            <h2>{labwork.name}</h2>
            <p>{labwork.description}</p>
        </div>
    );
};

export default LabWorkInfo;