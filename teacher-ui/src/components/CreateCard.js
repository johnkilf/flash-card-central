import React, { useState } from "react";
import cardService from "../api/cardService";
import { useNavigate, useParams } from "react-router-dom";

const CreateCard = () => {

    const {deckId} = useParams();

    const navigate = useNavigate();

    const [front, setFront] = useState('');
    const [back, setBack] = useState('');

    const onFormSubmit = (event) => {
        event.preventDefault();
        console.log("submitting form");
        saveCard();
    }


    const saveCard = async () => {
        const response = await cardService.post(`/decks/${deckId}/cards`,
        {
            params: {
                origin: '*'
            },
            front: front,
            back: back
        });
        
        const location = response.headers.location;
        const cardId = location.substring(location.lastIndexOf('/') + 1);

        navigate(`/decks/${deckId}/cards/${cardId}`);
    }

    return (
    <div className="ui segment">
        <h1 className="ui header">Create Card</h1>
        
        <form className="ui form" onSubmit={onFormSubmit}>
            <div className="field">
                <label>Card Front</label>
                <input type="text" value={front} onChange={(e) => setFront(e.target.value)} />
            </div>
            <div className="field">
                <label>Card Back</label>
                <input type="text" value={back} onChange={(e) => setBack(e.target.value)} />
            </div>
            <div className="ui divider"></div>
            <button className="ui green button" onClick={onFormSubmit} >Create</button>
        </form>
    </div>
    );
};

export default CreateCard;