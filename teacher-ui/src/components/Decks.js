import React, { useEffect, useState } from "react";
import cardService from "../api/cardService";
import moment from "moment";
import { Link, useNavigate } from "react-router-dom";

const Decks = () => {

    const navigate = useNavigate();

    const [decks, setDecks] = useState([]);

    useEffect(() => {
        loadDecks();
    }, []);


    const loadDecks = async () => {
        const response = await cardService.get('/decks',
        {
            params: {}
        });
        
        console.log(response.data);
        setDecks(response.data);
    }

    const createDeckClicked=() => {
        console.log("create deck clicked");
        navigate("/decks/new")
    }


    
    const decksRepresentation = decks.map(deck => (
        <div className="item" key={deck.id}>
            <i className="large book middle aligned icon"></i>
            <div className="content">
            <Link to={`/decks/${deck.id}`} className="header">{deck.name}</Link> 
            <div className="description">Updated {moment(deck.updatedOn).fromNow()}</div>
            </div>
        </div>
    ));

    return (
    <div className="ui raised text segment">
        <h1 className="ui header">Decks</h1>
        
        <div className="ui relaxed divided list">
            {decksRepresentation}
        </div>
        <div className="ui divider"></div>
        <button className="ui green button" onClick={createDeckClicked} >Create New Deck</button>
    </div>);
    
};

export default Decks;