import React, {useEffect, useState} from "react";
import { useParams, Link } from "react-router-dom";
import userService from "../api/learnerService";
import { Button, Header, Image, Modal } from 'semantic-ui-react'
import cardService from "../api/cardService";
import moment from "moment";
import schedulerService from "../api/schedulerService";

const Learner = () => {
    
    const [learner, setLearner] = useState({});

    const [allDecks, setAllDecks] = useState([]);
    const [assignedDeckIds, setAssignedDeckIds] = useState([]);
    const [modalOpen, setModalOpen] = useState(false);

    const {learnerId} = useParams()

    useEffect(() => {
        loadLearner();
        loadDecks();
    }, []);

    const loadLearner = async () => {
        const response = await userService.get(`/learners/${learnerId}`,
        {
            params: {}
        });
        
        console.log(response.data);
        setLearner(response.data);
    }

    const loadDecks = async () => {

        const decksResponse = await cardService.get(`decks`);
        setAllDecks(decksResponse.data);

        loadAssignedDeckIds();
    }

    const loadAssignedDeckIds= async () => {
        const deckIdsResponse = await schedulerService.get(`/learners/${learnerId}/decks`,
        {});

        const deckIds = deckIdsResponse.data.map(record => record.deckId);        
        
        console.log("deck ids " + deckIds);
        setAssignedDeckIds(deckIds);
    }

    const assignDeckClicked = () => {
        console.log("assign deck clicked");
        setModalOpen(true);
    }

    const onDeckSelected = async (deckId) => {

        const response = await schedulerService.post(`/learners/${learnerId}/decks`,
        {
            deckId: deckId
        });
        await loadAssignedDeckIds();
        setModalOpen(false);
    }

    // const decksRepresentation = [];

    console.log("alldecks " + allDecks);
    const unassignedDecksRepresentation = allDecks.filter(deck => !assignedDeckIds.includes(deck.id)).map(deck => (
        <div className="item" key={deck.id} onClick={() => onDeckSelected(deck.id)} >
            <i className="large book middle aligned icon"></i>
            <div className="content">
            <div className="header">{deck.name}</div>
            <div className="description">Updated {moment(deck.updatedOn).fromNow()}</div>
            </div>
        </div>
    ));

    const assignedDecksRepresentation = allDecks.filter(deck => assignedDeckIds.includes(deck.id)).map(deck => (
            <div className="item" key={deck.id}>
                <i className="large book middle aligned icon"></i>
                <div className="content">
                <Link to={`/decks/${deck.id}`} className="header">{deck.name}</Link> 
                <div className="description">Updated {moment(deck.updatedOn).fromNow()}</div>
                </div>
            </div>
        ));


    const modal = <Modal
        onClose={() => setModalOpen(false)}
        onOpen={() => setModalOpen(true)}
        open={modalOpen}
    >
        <Modal.Header>Select a Deck</Modal.Header>
        <Modal.Content>                
                <div className="ui relaxed divided selection list">
                    {unassignedDecksRepresentation}
                </div>
        </Modal.Content>
    </Modal>;
    return (
        <>
        
        <div className="ui raised text segment">
            <h1 className="ui header">Learner {learner.name}</h1>
            <h2 className="ui header">Decks Assigned</h2>
            <div className="ui relaxed divided list">
                {assignedDecksRepresentation}
            </div>
            <div className="ui divider"></div>
            <button className="ui green button" onClick={assignDeckClicked} >Assign Deck</button>
        </div>
        {modal}
        </>
        
    );
};

export default Learner;