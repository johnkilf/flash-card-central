import React from "react";
import { useParams,  useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

import cardService from "../api/cardService";

const Card = () => {


    const {deckId, cardId} = useParams();

    const [card, setCard] = useState({});

    const navigate = useNavigate();


    useEffect(() => {
        loadCard();
    }, []);

    const loadCard = async () => {
        const response = await cardService.get(`/decks/${deckId}/cards/${cardId}`,
        {
            params: {}
        });
        
        console.log(response.data);
        setCard(response.data);
    }

    const backToDeckClicked = () => {
        navigate(`/decks/${deckId}`);
    }

    return (
        <div className="ui raised text segment">
            <h1 className="ui header">Card Details</h1>
            Front:
            <div className="ui centered card">
                <div className="content" style={{padding: '3em'}}>
                    <div className="center aligned header">{card.front}</div>
                </div>
            </div>
            Back:
            <div className="ui centered card">
                <div className="content" style={{padding: '3em'}}>
                    <div className="center aligned header">{card.back}</div>
                </div>
            </div>
            <div className="ui divider"></div>
            <button className="ui button" onClick={backToDeckClicked}>Back to Deck</button>

        </div>);
        

};

export default Card;