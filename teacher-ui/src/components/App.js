import React from "react";
import { Route, Routes, NavLink } from "react-router-dom";


import Card from "./Card";
import Decks from "./Decks";
import Deck from "./Deck";
import CreateDeck from "./CreateDeck";
import Learners from "./Learners";
import CreateCard from "./CreateCard";
import CreateLearner from "./CreateLearner";
import Learner from "./Learner";
import Home from "./Home"


class App extends React.Component {


    render() {
        return (
            <div className="ui container">
                <h1 class="ui header" style={{padding: '1em'}}>Teacher App</h1>
                <nav>
                    <div className="ui two item menu">
                        <NavLink to="/decks" className='item'>Decks</NavLink> 
                        <NavLink to="/learners" className='item'>Learners</NavLink> 
                    </div>
                </nav>
                <Routes>
                    <Route path="/" element = {<Home />} />
                    <Route path="/learners" >
                        <Route index element = {<Learners />} />
                        <Route path="new" element= {<CreateLearner />}/>
                        <Route path=":learnerId" element = {<Learner />} />
                    </Route>
                    <Route path="/decks">
                        <Route index element= {<Decks />}/>
                        <Route path="new" element = {<CreateDeck />}/>
                        <Route path=":deckId" >
                            <Route index element = {<Deck />}/>
                            <Route path="cards/:cardId" element = {<Card />}/>
                            <Route path="cards/new" element = {<CreateCard />}/>
                        </Route>
                    </Route>
  
                    
                </Routes>
            </div>
        );



    }
}

export default App;