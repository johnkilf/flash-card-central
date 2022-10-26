import axios from 'axios';

export default axios.create({
    baseURL: `${process.env.LEARNER_URI ? process.env.LEARNER_URI : 'http://localhost'}:8000`,
    headers: {}
});