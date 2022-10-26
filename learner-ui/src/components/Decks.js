import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import { Link } from "react-router-dom";
import schedulingService from "../api/schedulingService";
import cardService from "../api/cardService";
import moment from "moment";


const Decks = () => {

    const {learnerId} = useParams();
    const [decks, setDecks] = useState([]);
    const [noDecksAssigned, setNoDecksAssigned] = useState(false);

    const loadDecks = async () => {
        const deckIdsResponse = await schedulingService.get(`/learners/${learnerId}/decks`,
        {});

        if (deckIdsResponse.data.length === 0){
            setNoDecksAssigned(true);
            return;
        }

        const idArgument = String(deckIdsResponse.data.map(record => record.deckId));

        console.log(idArgument);

        const decksResponse = await cardService.get(`decks?id=${idArgument}`);

        console.log(decksResponse);

        setDecks(decksResponse.data);
        
    }

    useEffect(() => {
        loadDecks();
    }, []);

    var decksRepresentation = decks.map(deck => (
        <div className="item" key={deck.id}>
            <i className="large book middle aligned icon"></i>
            <div className="content">
            <Link to={`/${learnerId}/learn/${deck.id}`} className="header">{deck.name}</Link> 
            <div className="description">Updated {moment(deck.updatedOn).fromNow()}</div>
            </div>
        </div>
    ));

    if(noDecksAssigned){
        decksRepresentation = <h3 className="ui center aligned header" style={{padding: "3em"}}>No decks assigned to learn!</h3>
    }

    return (
    <div className="ui raised text segment">
        <h1 className="ui header">Decks</h1>
        
        <div className="ui relaxed divided list">
            {decksRepresentation}
        </div>
    </div>);
}


export default Decks;