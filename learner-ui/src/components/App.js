import React from "react";
import { NavLink, Route, Routes } from "react-router-dom";
import Decks from "./Decks";
import Learn from "./Learn";
import Learners from "./Learners";

const App = () => {
    return (
        <div className="ui container">
                <h1 className="ui header" style={{padding: '1em'}}>Learner App</h1>
                <nav>
                    <div className="ui one item menu">
                        <NavLink to="/" className='item'>Login</NavLink> 
                    </div>
                </nav>
                <Routes>
                    <Route path="/" element = {<Learners />} />
                    <Route path="/:learnerId" >
                        <Route index element = {<Decks />} />
                        <Route path="learn/:deckId" element = {<Learn />} />                    
                    </Route>
                    
                </Routes>
            </div>
    );
}

export default App;