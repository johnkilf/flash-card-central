import React, { useState } from "react";
import cardService from "../api/cardService";
import { useNavigate } from "react-router-dom";

const CreateDeck = () => {

    const navigate = useNavigate();

    const [name, setName] = useState('');

    const onFormSubmit = (event) => {
        event.preventDefault();
        console.log("submitting form");
        saveDeck();
    }


    const saveDeck = async () => {
        const response = await cardService.post('/decks',
        {
            params: {
                origin: '*'
            },
            name: name
        });
        
        const location = response.headers.location;
        const deckId = location.substring(location.lastIndexOf('/') + 1);

        navigate(`/decks/${deckId}`);
    }

    return (
    <div className="ui segment">
        <h1 className="ui header">Create Deck</h1>
        
        <form className="ui form" onSubmit={onFormSubmit}>
            <div className="field">
                <label>Deck Name</label>
                <input type="text" value={name} onChange={(e) => setName(e.target.value)} />
            </div>
            <div className="ui divider"></div>
            <button className="ui green button" onClick={onFormSubmit} >Create</button>
        </form>
    </div>
    );
};

export default CreateDeck;