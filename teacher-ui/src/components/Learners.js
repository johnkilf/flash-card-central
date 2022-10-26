import React, { useEffect, useState } from "react";
import userService from "../api/learnerService";
import moment from "moment";
import { Link, useNavigate } from "react-router-dom";

const Learners = () => {

    const navigate = useNavigate();

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

    const createUserClicked=() => {
        navigate("/learners/new")
    }


    
    const decksRepresentation = learners.map(learner => (
        <div className="item" key={learner.id}>
            <i className="large book middle aligned icon"></i>
            <div className="content">
            <Link to={`/learners/${learner.id}`} className="header">{learner.name}</Link> 
            <div className="description">Updated {moment(learner.updatedOn).fromNow()}</div>
            </div>
        </div>
    ));

    return (
    <div className="ui raised text segment">
        <h1 className="ui header">Learners</h1>
        
        <div className="ui relaxed divided list">
            {decksRepresentation}
        </div>
        <div className="ui divider"></div>
        <button className="ui green button" onClick={createUserClicked} >Create New Learner</button>
    </div>);
    
};

export default Learners;