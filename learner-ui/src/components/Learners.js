import React from "react";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";


import userService from "../api/userService"; 

const Learners = () => {

    const [learners, setLearners] = useState([]);

    useEffect(() => {
        loadLearners();
    }, []);


    const loadLearners = async () => {
        const response = await userService.get('/learners',
        {
            params: {}
        });
        
        console.log(response.data);
        setLearners(response.data);
    }


    
    const learnersRepresentation = learners.map(learner => (
        <div className="item" key={learner.id}>
            <i className="large user middle aligned icon"></i>
            <div className="content" style ={{padding: "1em"}}>
            <Link to={`/${learner.id}`} className="header">{learner.name}</Link> 
            </div>
        </div>
    ));

    return (
    <div className="ui raised text segment">
        <h1 className="ui header">"Login" as</h1>
        
        <div className="ui relaxed divided list">
            {learnersRepresentation}
        </div>
    </div>);
}

export default Learners;