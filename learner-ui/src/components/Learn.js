import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import cardService from "../api/cardService";
import schedulingService from "../api/schedulingService";
import moment from "moment";

const Learn = () => {
    const {learnerId, deckId} = useParams();

    const [deck, setDeck] = useState({});
    const [card, setCard] = useState({});
    const [showBack, setShowBack] = useState(false);
    const [completed, setCompleted] = useState(false);

    useEffect(() => {
        loadDeck();
        loadNextCard();
    }, []);


    const loadDeck = async () => {
        const response = await cardService.get(`/decks/${deckId}`);
        setDeck(response.data);
    }

    const loadNextCard = async () => {

        console.log("Loading next card");

        const scheduleResponse = await schedulingService.get(`/schedule/learners/${learnerId}/decks/${deckId}`);

        console.log(scheduleResponse);

        if (scheduleResponse.data.length === 0) {
            setCompleted(true);
        } else {
            setShowBack(false);
            setCard(scheduleResponse.data[0]);
        }
    }

    const answer = async (correct) => {
        const answerResponse = await schedulingService.post(`/revision/`,
        {
            deckId: deckId,
            learnerId: learnerId,
            cardId: card.details.id,
            success: correct
        });

        console.log(answerResponse);

        loadNextCard();
    }

    

    

    
    const getCardRepresentation = () => {

        const frontButton = (<div className="ui center aligned">
            <div className="ui basic grey button" onClick={() => setShowBack(true)}>Show</div>
        </div>);

        const backButtons = (<div className="ui two buttons">
            <div className="ui basic red button" onClick={() => answer(false)}>
                Incorrect <br /><br />
                
                ({moment().add(card.newDurationInSecondsWhenIncorrect, 'seconds').fromNow()})
            </div>
            <div className="ui basic green button" onClick={() => answer(true)}>
                Correct <br /><br />
                ({moment().add(card.newDurationInSecondsWhenCorrect, 'seconds').fromNow()})
            </div>
        </div>);

        const cardButtons = showBack ? backButtons : frontButton;
        const cardContent = showBack ? card.details.back : card.details.front;


        const cardRepresentation = (
            <div className="ui centered card" style={{minWidth: '23em'}} >
                <div className="content" style={{ padding: '3em' }}>
                    <div className="center aligned header">{cardContent}</div>
                </div>
                <div className="extra content">
                    {cardButtons}
                </div>
            </div>
        );
        return cardRepresentation;
    }
        


    var content = <h3 className="ui center aligned header" style={{padding: "3em"}}>Loading...</h3>;
    
    if(completed) {
        content = <h3 className="ui center aligned header" style={{padding: "3em"}}>Learning completed, come back tomorrow!</h3>;
    } else if(card.details) {
        content = getCardRepresentation();
    }

    
    

    return (
        <div className="ui raised text segment">
            <h1 style={{padding: '0.6em'}}>Learning from deck: {deck.name} </h1>
            <div className="ui center aligned">
                {content}
            </div>
            <br />
            <br />
            
        </div>
    );

    
};

export default Learn;