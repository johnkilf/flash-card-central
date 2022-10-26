import React, {useEffect, useState} from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import cardService from "../api/cardService";

const Deck = () => {
    const {deckId} = useParams();

    const navigate = useNavigate();

    const [deck, setDeck] = useState({});

    useEffect(() => {
        loadDeck();
    }, []);

    const loadDeck = async () => {
        const response = await cardService.get(`/decks/${deckId}`,
        {
            params: {}
        });
        
        console.log(response.data);
        setDeck(response.data);
    }

    const createCardClicked=() => {
        console.log("create deck clicked");
        navigate(`/decks/${deckId}/cards/new`);
    }
    
    var cardsRepresentation = [];
    
    if (deck.cards) {
        cardsRepresentation = deck.cards.map(card => (
            <div className="item" key={card.id}>
                <i className="large file icon middle aligned icon"></i>
                <div className="content">
                <Link to={`/decks/${deckId}/cards/${card.id}`} className="header">{card.front}</Link> 
                <div className="description">{card.back}</div>
                </div>
            </div>
        ));
    }


    return (
        <div className="ui raised text segment">
            <h1 className="ui header">Deck {deck.name}</h1>
            <h2 className="ui header">Cards</h2>
            <div className="ui relaxed divided list">
                {cardsRepresentation}
            </div>
            <div className="ui divider"></div>
            <button className="ui green button" onClick={createCardClicked} >Create New Card</button>
        </div>
    );
};

export default Deck;