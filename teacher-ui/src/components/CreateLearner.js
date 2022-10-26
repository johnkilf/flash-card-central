import React, { useState } from "react";
import learnerService from "../api/learnerService";
import { useNavigate } from "react-router-dom";

const CreateLearner = () => {

    const navigate = useNavigate();

    const [name, setName] = useState('');

    const onFormSubmit = (event) => {
        event.preventDefault();
        console.log("submitting form");
        saveLearner();
    }


    const saveLearner = async () => {
        const response = await learnerService.post('/learners',
        {
            params: {
                origin: '*'
            },
            name: name
        });
        
        const location = response.headers.location;
        const learnerId = location.substring(location.lastIndexOf('/') + 1);

        navigate(`/learners/${learnerId}`);
    }

    return (
    <div className="ui segment">
        <h1 className="ui header">Create Learner</h1>
        
        <form className="ui form" onSubmit={onFormSubmit}>
            <div className="field">
                <label>Learner Name</label>
                <input type="text" value={name} onChange={(e) => setName(e.target.value)} />
            </div>
            <div className="ui divider"></div>
            <button className="ui green button" onClick={onFormSubmit} >Create</button>
        </form>
    </div>
    );
};

export default CreateLearner;